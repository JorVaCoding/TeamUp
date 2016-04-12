package com.ewyboy.teamup.utility;

import com.mojang.realmsclient.gui.ChatFormatting;

public class Reference {

    public static final class ModInfo {
        public static final String ModID = "teamup";
        public static final String ModName = "TeamUp";
        static final String VersionMajor = "1";
        static final String VersionMinor = "0";
        static final String VersionPatch = "0";
        public static final String BuildVersion = VersionMajor + "." + VersionMinor + "." + VersionPatch;
        public static final String MinecraftVersion = "1.7.10";
    }

    public static final class Path {
        public static final String clientProxyPath = "com.ewyboy.teamup.proxies.ClientProxy";
        public static final String commonProxyPath = "com.ewyboy.teamup.proxies.CommonProxy";
    }

    public static final class TeamData {
        public static final String isTeamLeader = "isTeamLeader";
        public static final String hasTeam = "hasTeam";
        public static final String teamNameNBT = "teamNameNBT";
        public static final String teamColorNBT = "teamColorNBT";
    }

    public static final class NBT {
        public static final String HASPLAYERJOINEDBEFORE = "hasPlayerJoinedBefore";
    }


    public static final class Commands {
    public static final String createTeam = "CreateTeam";
    public static final String deleteTeam = "DeleteTeam";
    public static final String addPlayer = "AddPlayer";
    public static final String removePlayer = "RemovePlayer";
    public static final String setColor = "SetColor";
    public static final String leaveTeam = "LeaveTeam";
    }

    private static final String spacing = " | ";

    public static final class CommandInfo {
        public static final String createTeam   = "Creates A New Team"                  + spacing + "Args: TeamName"  + spacing + "Access: All";
        public static final String deleteTeam   = "Deletes A Existing Team"             + spacing + "Args: TeamName"  + spacing + "Access: TeamLeader";
        public static final String addPlayer    = "Adds A Player To Existing Team"      + spacing + "Args: Player"    + spacing + "Access: TeamLeader";
        public static final String removePlayer = "Removes A Player From Existing Team" + spacing + "Args: Player"    + spacing + "Access: TeamLeader";
        public static final String setColor     = "Sets A Color For Your Team"          + spacing + "Args: ColorName" + spacing + "Access: TeamLeader";
        public static final String leaveTeam    = "Removes You From Current Team"       + spacing + "Args: None"      + spacing + "Access: All";
    }

    private static final String error = ChatFormatting.RED + "Error: ";

    public static final class ErrorMessages {
        public static final String alreadyInTeam1 = error + "You are already in an existing team.";
        public static final String alreadyInTeam2 = error + "Player is already in an existing team.";
        public static final String notTeamLeader = error + "You are not a team leader.";
        public static final String hasNoTeam = error + "You are not in any teams.";
        public static final String cantLeaveTeam = error + "You can't leave your own team. Assign a new leader or delete it.";
    }
}
