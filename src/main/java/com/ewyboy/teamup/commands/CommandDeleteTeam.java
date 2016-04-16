package com.ewyboy.teamup.commands;

import com.ewyboy.teamup.utility.Reference;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

import static com.ewyboy.teamup.utility.Reference.TeamData.*;

class CommandDeleteTeam extends CommandBase {
    @Override
    public String getCommandName() {
        return Reference.Commands.deleteTeam;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return Reference.CommandInfo.deleteTeam;
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

        List players = MinecraftServer.getServer().getConfigurationManager().playerEntityList;

        if (playerNBT.getBoolean(hasTeam)) {
            if (playerNBT.getBoolean(isTeamLeader)) {
                for (int i = 0; i < + players.size(); i++) {
                    EntityPlayer target = (EntityPlayer) players.get(i);
                    NBTTagCompound targetNBT = target.getEntityData();
                    if (targetNBT.getBoolean(hasTeam)) {
                        if (targetNBT.getString(teamNameNBT).equals(teamName)) {
                            target.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.getValueByName(playerNBT.getString(teamColorNBT)) + playerNBT.getString(teamNameNBT) + " just got deleted by: " + player.getDisplayName()));
                            if (targetNBT.getBoolean(isTeamLeader)) targetNBT.setBoolean(isTeamLeader, false);
                            targetNBT.setBoolean(hasTeam, false);
                            targetNBT.setString(teamNameNBT, "unteamed");
                            targetNBT.setString(teamColorNBT, "null");
                        }
                    }
                }
            } else {
                throw new WrongUsageException(Reference.ErrorMessages.notTeamLeader);
            }
        } else {
            throw new WrongUsageException(Reference.ErrorMessages.hasNoTeam);
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        EntityPlayer player = sender.getEntityWorld().getPlayerEntityByName(sender.getCommandSenderName());
        NBTTagCompound playerNBT = player.getEntityData();
        if (args.length == 2) return getListOfStringsMatchingLastWord(args, playerNBT.getString(teamNameNBT));

        return null;
    }
}
