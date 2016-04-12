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

import java.util.ArrayList;
import java.util.List;

public class CommandSetColor extends CommandBase {

    private static List<EnumChatFormatting> teamColors = new ArrayList<EnumChatFormatting>();
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
            for (EnumChatFormatting chatFormatting : teamColors) {
                if (selectedColor.contains(chatFormatting.name())) {
                    for (int i = 0; i < + players.size(); i++) {
                        EntityPlayer target = (EntityPlayer) players.get(i);
                        NBTTagCompound targetNBT = target.getEntityData();
                        if (targetNBT.getBoolean(Reference.TeamData.hasTeam)) {
                            if (targetNBT.getString(Reference.TeamData.teamNameNBT).equals(playerNBT.getString(Reference.TeamData.teamNameNBT))) {
                                target.getEntityData().setString(Reference.TeamData.teamColorNBT, selectedColor);
                                target.addChatMessage(new ChatComponentText(EnumChatFormatting.getValueByName(selectedColor) + selectedColor + EnumChatFormatting.WHITE + " has been set for team " + EnumChatFormatting.getValueByName(selectedColor) + playerNBT.getString(Reference.TeamData.teamNameNBT)));
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
            for (EnumChatFormatting chatFormatting : teamColors) {
                if (chatFormatting.name().equalsIgnoreCase(args[1])) {
                    return this.addTabCompletionOptions(commandSender, args);
                }
            }
        } return null;
    } static {
        teamColors.add(EnumChatFormatting.RED);
        teamColors.add(EnumChatFormatting.DARK_RED);
        teamColors.add(EnumChatFormatting.BLUE);
        teamColors.add(EnumChatFormatting.DARK_BLUE);
        teamColors.add(EnumChatFormatting.GREEN);
        teamColors.add(EnumChatFormatting.DARK_GREEN);
        teamColors.add(EnumChatFormatting.YELLOW);
        teamColors.add(EnumChatFormatting.GOLD);
        teamColors.add(EnumChatFormatting.AQUA);
        teamColors.add(EnumChatFormatting.DARK_AQUA);
        teamColors.add(EnumChatFormatting.LIGHT_PURPLE);
        teamColors.add(EnumChatFormatting.DARK_PURPLE);
        for (EnumChatFormatting chatFormatting : teamColors) colors.add(chatFormatting.name());
    }
}
