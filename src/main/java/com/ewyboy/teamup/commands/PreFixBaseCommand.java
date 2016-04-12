package com.ewyboy.teamup.commands;

import com.ewyboy.teamup.utility.Reference;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

import java.util.ArrayList;
import java.util.List;

public class PreFixBaseCommand extends CommandBase {
    private static List<CommandBase> modCommands = new ArrayList<CommandBase>();
    private static List<String> commands = new ArrayList<String>();

    @Override
    public String getCommandName() {
        return Reference.ModInfo.ModID;
    }

    @Override
    public String getCommandUsage(ICommandSender commandSender) {
        return "Prefix for TeamUp commands";
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
    public void processCommand(ICommandSender commandSender, String[] args) {
        if (args.length >= 1) {
            for (CommandBase command : modCommands) {
                if (command.getCommandName().equalsIgnoreCase(args[0]) && command.canCommandSenderUseCommand(commandSender)) {
                    command.processCommand(commandSender, args);
                }
            }
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender commandSender, String[] args) {
        if (args.length == 1) {
            return getListOfStringsFromIterableMatchingLastWord(args, commands);
        } else if (args.length >= 2) {
            for (CommandBase command : modCommands) {
                if (command.getCommandName().equalsIgnoreCase(args[0])) {
                    return command.addTabCompletionOptions(commandSender, args);
                }
            }
        } return null;
    } static {
        modCommands.add(new CommandCreateTeam());
        modCommands.add(new CommandDeleteTeam());
        modCommands.add(new CommandAddPlayer());
        modCommands.add(new CommandRemovePlayer());
        modCommands.add(new CommandSetColor());
        modCommands.add(new CommandLeaveTeam());
        for (CommandBase commandBase : modCommands) commands.add(commandBase.getCommandName());
    }
}