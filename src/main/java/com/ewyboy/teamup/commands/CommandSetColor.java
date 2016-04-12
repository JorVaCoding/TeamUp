package com.ewyboy.teamup.commands;

import com.ewyboy.teamup.utility.Reference;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.List;

import static com.mojang.realmsclient.gui.ChatFormatting.*;

public class CommandSetColor extends CommandBase {

    private static List<ChatFormatting> teamColors = new ArrayList<ChatFormatting>();
    private static List<String> colors = new ArrayList<String>();

    @Override
    public String getCommandName() {
        return Reference.Commands.setColor;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return Reference.CommandInfo.setColor;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        String selectedColor = args[1];

        EntityPlayer player = getCommandSenderAsPlayer(sender);
        NBTTagCompound playerNBT = player.getEntityData();

        List players = MinecraftServer.getServer().getConfigurationManager().playerEntityList;

        if (playerNBT.getBoolean(Reference.TeamData.isTeamLeader)) {
            for (ChatFormatting chatFormatting : teamColors) {
                if (selectedColor.contains(chatFormatting.getName())) {
                    for (int i = 0; i < + players.size(); i++) {
                        EntityPlayer target = (EntityPlayer) players.get(i);
                        NBTTagCompound targetNBT = target.getEntityData();
                        if (targetNBT.getBoolean(Reference.TeamData.hasTeam)) {
                            if (targetNBT.getString(Reference.TeamData.teamNameNBT).equals(playerNBT.getString(Reference.TeamData.teamNameNBT))) {
                                target.getEntityData().setString(Reference.TeamData.teamColorNBT, selectedColor);
                                target.addChatMessage(new ChatComponentText(ChatFormatting.getByName(selectedColor) + selectedColor + ChatFormatting.WHITE + " has been set for team " + ChatFormatting.getByName(selectedColor) + playerNBT.getString(Reference.TeamData.teamNameNBT)));
                            }
                        }
                    }
                    break;
                }
            }
        } else {
            throw new WrongUsageException(Reference.ErrorMessages.notTeamLeader);
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender commandSender, String[] args) {
        if (args.length == 2) {
            return getListOfStringsFromIterableMatchingLastWord(args, colors);
        } else if (args.length >= 3) {
            for (ChatFormatting chatFormatting : teamColors) {
                if (chatFormatting.getName().equalsIgnoreCase(args[1])) {
                    return this.addTabCompletionOptions(commandSender, args);
                }
            }
        } return null;
    } static {
        teamColors.add(RED);
        teamColors.add(DARK_RED);
        teamColors.add(BLUE);
        teamColors.add(DARK_BLUE);
        teamColors.add(GREEN);
        teamColors.add(DARK_GREEN);
        teamColors.add(YELLOW);
        teamColors.add(GOLD);
        teamColors.add(AQUA);
        teamColors.add(DARK_AQUA);
        teamColors.add(LIGHT_PURPLE);
        teamColors.add(DARK_PURPLE);
        for (ChatFormatting chatFormatting : teamColors) colors.add(chatFormatting.getName());
    }
}
