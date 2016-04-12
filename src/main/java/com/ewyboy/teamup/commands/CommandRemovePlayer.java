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
import static com.ewyboy.teamup.utility.Reference.TeamData.teamColorNBT;
import static com.ewyboy.teamup.utility.Reference.TeamData.teamNameNBT;

public class CommandRemovePlayer extends CommandBase {

    @Override
    public String getCommandName() {
        return Reference.Commands.removePlayer;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return Reference.CommandInfo.removePlayer;
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
        String playerName = args[1];

        EntityPlayer player = getCommandSenderAsPlayer(sender);
        NBTTagCompound playerNBT = player.getEntityData();
        EntityPlayer target = sender.getEntityWorld().getPlayerEntityByName(playerName);
        NBTTagCompound targetNBT = target.getEntityData();

        if (targetNBT.getBoolean(hasTeam)) {
            if (playerNBT.getBoolean(isTeamLeader)) {
                targetNBT.setBoolean(hasTeam, false);
                targetNBT.setString(teamNameNBT, "unteamed");
                targetNBT.setString(teamColorNBT, "null");
                player.addChatMessage(new ChatComponentText(EnumChatFormatting.ITALIC + playerName + " was removed from your team: " + EnumChatFormatting.getValueByName(playerNBT.getString(teamColorNBT)) + playerNBT.getString(teamNameNBT)));
                target.addChatMessage(new ChatComponentText(EnumChatFormatting.ITALIC + player.getCommandSenderName() + " removed you from the team: " + EnumChatFormatting.getValueByName(playerNBT.getString(teamColorNBT)) + playerNBT.getString(teamNameNBT)));
            } else {
                throw new WrongUsageException(Reference.ErrorMessages.notTeamLeader);
            }
        } else {
            throw new WrongUsageException(Reference.ErrorMessages.hasNoTeam);
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        return args.length >= 2 ? getListOfStringsMatchingLastWord(args, MinecraftServer.getServer().getAllUsernames()) : null;
    }
}
