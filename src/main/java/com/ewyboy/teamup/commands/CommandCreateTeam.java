package com.ewyboy.teamup.commands;

import com.ewyboy.teamup.utility.Reference;
import com.ewyboy.teamup.utility.Reference.TeamData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;

import static com.ewyboy.teamup.utility.Reference.TeamData.hasTeam;
import static com.ewyboy.teamup.utility.Reference.TeamData.isTeamLeader;

public class CommandCreateTeam extends CommandBase {

    @Override
    public String getCommandName() {
        return Reference.Commands.createTeam;
    }

    @Override
    public String getCommandUsage(ICommandSender commandSender) {
        return Reference.CommandInfo.createTeam;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        String teamName = args[1];

        EntityPlayer player = getCommandSenderAsPlayer(sender);
        NBTTagCompound playerNBT = player.getEntityData();

        if (!playerNBT.getBoolean(hasTeam)) {
            playerNBT.setBoolean(hasTeam, true);
            playerNBT.setBoolean(isTeamLeader, true);
            if (playerNBT.getBoolean(isTeamLeader)) {
                playerNBT.setString(TeamData.teamNameNBT, teamName);
                playerNBT.setString(TeamData.teamColorNBT, "white");
                sender.addChatMessage(new ChatComponentText(sender.getCommandSenderName() + " created a new team with the name " + playerNBT.getString(TeamData.teamNameNBT)));
            } else {
                throw new WrongUsageException(Reference.ErrorMessages.notTeamLeader);
            }
        } else {
            throw new WrongUsageException(Reference.ErrorMessages.alreadyInTeam1);
        }
    }
}
