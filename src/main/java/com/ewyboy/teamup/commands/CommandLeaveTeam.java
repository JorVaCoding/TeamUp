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

public class CommandLeaveTeam extends CommandBase {

    @Override
    public String getCommandName() {
        return Reference.Commands.leaveTeam;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return Reference.Commands.leaveTeam;
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
        EntityPlayer player = getCommandSenderAsPlayer(sender);
        NBTTagCompound playerNBT = player.getEntityData();

        if (!playerNBT.getBoolean(isTeamLeader)) {
            if (playerNBT.getBoolean(hasTeam)) {
                List players = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
                for (int i = 0; i < + players.size(); i++) {
                    EntityPlayer target = (EntityPlayer) players.get(i);
                    target.addChatMessage(new ChatComponentText(EnumChatFormatting.ITALIC + player.getDisplayName() + " have left team: " + EnumChatFormatting.getValueByName(playerNBT.getString(teamColorNBT)) + playerNBT.getString(teamNameNBT)));
                }
                playerNBT.setBoolean(hasTeam, false);
                playerNBT.setString(teamNameNBT, "unteamed");
                playerNBT.setString(teamColorNBT, "null");
            } else {
                throw new WrongUsageException(Reference.ErrorMessages.hasNoTeam);
            }
        } else {
            throw new WrongUsageException(Reference.ErrorMessages.cantLeaveTeam);
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
