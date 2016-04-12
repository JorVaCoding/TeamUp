package com.ewyboy.teamup;

import com.ewyboy.teamup.commands.CommandTeamChat;
import com.ewyboy.teamup.events.TeamUpEventHandler;
import com.ewyboy.teamup.commands.PreFixBaseCommand;
import com.ewyboy.teamup.utility.Logger;
import com.ewyboy.teamup.utility.Reference;
import com.google.common.base.Stopwatch;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.common.MinecraftForge;

import java.util.concurrent.TimeUnit;

@Mod(modid = Reference.ModInfo.ModID, name = Reference.ModInfo.ModName, version = Reference.ModInfo.BuildVersion, acceptedMinecraftVersions = "["+ Reference.ModInfo.MinecraftVersion+"]")
public class TeamUp {

    @Mod.Instance(Reference.ModInfo.ModID)
    public static TeamUp instance;

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        Stopwatch watch = Stopwatch.createStarted();
            Logger.info("Firing up the TeamUp engine!");
                Logger.info("Loading in the TeamUp commands");
                event.registerServerCommand(new PreFixBaseCommand());
                event.registerServerCommand(new CommandTeamChat());
            Logger.info("All successfully loaded");
        Logger.info("TeamUp successfully loaded after: " + watch.elapsed(TimeUnit.MILLISECONDS) + "ms");
    }

    @Mod.EventHandler
    public void PreInit(FMLPreInitializationEvent event) {
        Logger.info("Loading in Event-subscriptions");
            MinecraftForge.EVENT_BUS.register(new TeamUpEventHandler());
        Logger.info("All Events successfully subscribed to");
    }
}
