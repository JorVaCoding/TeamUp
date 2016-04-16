package com.ewyboy.teamup.events;

import com.ewyboy.teamup.utility.Reference;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.ServerChatEvent;

import java.util.List;

import static com.ewyboy.teamup.utility.Reference.TeamData.*;
public class TeamUpEventHandler {

    private static final String chatPrefix = "team:";

    @SubscribeEvent()
    public void onServerChatReceivedEvent(ServerChatEvent event) {
        if (event.player != null) {
            EntityPlayer player = event.player;
            NBTTagCompound playerNBT = player.getEntityData();

            if (playerNBT.getBoolean(hasTeam)) {
                event.setCanceled(true);
            }

            if (event.isCanceled()) {
                List players = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
                for (int i = 0; i < + players.size(); i++) {
                        EntityPlayer target = (EntityPlayer) players.get(i);
                        EnumChatFormatting color = EnumChatFormatting.getValueByName(playerNBT.getString(teamColorNBT));
                        if (color == null) color = EnumChatFormatting.WHITE;
                        String chatText = "["+ color + playerNBT.getString(teamNameNBT) + EnumChatFormatting.WHITE + "] " + "<" + player.getDisplayName() + "> " + event.message;
                        target.addChatMessage(new ChatComponentTranslation(chatText));
                }
            }
        }
    }

    @SubscribeEvent
    public void playerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        NBTTagCompound playerNBT = event.player.getEntityData();
        if (!playerNBT.getBoolean(Reference.NBT.HASPLAYERJOINEDBEFORE)) {
            playerNBT.setBoolean(Reference.NBT.HASPLAYERJOINEDBEFORE, true);
            playerNBT.setBoolean(hasTeam, false);
            playerNBT.setBoolean(isTeamLeader, false);
            playerNBT.setString(teamNameNBT, "unteamed");
            playerNBT.setString(teamColorNBT, "null");
        }
    }
}
