package com.ewyboy.teamup.commands;

import com.ewyboy.teamup.utility.Reference;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

import java.util.Arrays;
import java.util.List;

import static com.ewyboy.teamup.utility.Reference.TeamData.teamColorNBT;
import static com.ewyboy.teamup.utility.Reference.TeamData.teamNameNBT;

public class CommandTeamChat extends CommandBase {

    @Override
    public String getCommandName() {
        return "team";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "team-chat";
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

        List players = MinecraftServer.getServer().getConfigurationManager().playerEntityList;

        if (playerNBT.getBoolean(Reference.TeamData.hasTeam)) {
            for (int i = 0; i < + players.size(); i++) {
                EntityPlayer target = (EntityPlayer) players.get(i);
                NBTTagCompound targetNBT = target.getEntityData();

                if (targetNBT.getBoolean(Reference.TeamData.hasTeam)) {
                    if (targetNBT.getString(Reference.TeamData.teamNameNBT).equals(playerNBT.getString(Reference.TeamData.teamNameNBT))) {
                        EnumChatFormatting color = EnumChatFormatting.getValueByName(playerNBT.getString(teamColorNBT));
                        if (color == null) color = EnumChatFormatting.WHITE;
                        String chatText = "["+ color + playerNBT.getString(teamNameNBT) + EnumChatFormatting.WHITE +"] " + "<" + player.getDisplayName() + "> " + color + Arrays.toString(args).replace(",", "").replace("[", "").replace("]", "").trim();
                        target.addChatMessage(new ChatComponentTranslation(chatText));
                    }
                }
            }
        } else {
            throw new WrongUsageException(Reference.ErrorMessages.hasNoTeam);
        }
    }
}



















