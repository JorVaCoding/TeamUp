package com.ewyboy.teamup.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

import static com.ewyboy.teamup.utility.Reference.*;
import static com.ewyboy.teamup.utility.Reference.TeamData.*;

public class CommandAddPlayer extends CommandBase {

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public String getCommandName() {
        return Commands.addPlayer;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return CommandInfo.addPlayer;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        String playerName = args[1];

        EntityPlayer player = getCommandSenderAsPlayer(sender);
        NBTTagCompound playerNBT = player.getEntityData();
        EntityPlayer target = sender.getEntityWorld().getPlayerEntityByName(playerName);
        NBTTagCompound targetNBT = target.getEntityData();

        if (!targetNBT.getBoolean(hasTeam)) {
            if (playerNBT.getBoolean(isTeamLeader)) {
                targetNBT.setBoolean(hasTeam, true);
                targetNBT.setString(teamNameNBT, playerNBT.getString(teamNameNBT));
                targetNBT.setString(teamColorNBT, playerNBT.getString(teamColorNBT));
                player.addChatMessage(new ChatComponentText(EnumChatFormatting.ITALIC + playerName + " was added to your team: " + EnumChatFormatting.getValueByName(playerNBT.getString(teamColorNBT)) + playerNBT.getString(teamNameNBT)));
                target.addChatMessage(new ChatComponentText(EnumChatFormatting.ITALIC + player.getCommandSenderName() + " added you to the team: " + EnumChatFormatting.getValueByName(playerNBT.getString(teamColorNBT)) + playerNBT.getString(teamNameNBT)));
            } else {
                throw new WrongUsageException(ErrorMessages.notTeamLeader);
            }
        } else {
            throw new WrongUsageException(ErrorMessages.alreadyInTeam2);
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        return args.length >= 2 ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
    }
}
