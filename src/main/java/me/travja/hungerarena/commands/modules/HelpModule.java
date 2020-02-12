package me.travja.hungerarena.commands.modules;

import me.travja.hungerarena.commands.CommandModule;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;

public class HelpCommand extends CommandModule {

    public HelpCommand(CommandModule parent, String name, String permission, String permissionMessage, String usage, String... alias) {
        super(parent, name, permission, permissionMessage, usage, alias);
    }

    private ChatColor c = ChatColor.YELLOW;
    private ArrayList<String> list = new ArrayList<>(Arrays.asList(
            c + "/ha - Displays author message!",
            c + "/ha arena [create|delete] [name] - Creates or deletes an arena.",
            c + "/ha help - Displays this screen!",
            c + "/ha join <name> - Joins the game with the given name!",
            c + "/ha leave - Makes you leave the game!",
            c + "/ha refill <name> - Refills all chests! Leaving the name blank will refill all games!",
            c + "/ha kick [player] - Kicks a player from the arena!",
            c + "/ha list - Opens a GUI to list players and their health!",
            c + "/ha manage - Opens a GUI to manage the games!",
            c + "/ha tp [player] - Teleports you to a tribute!",
            c + "/ha setspawn [name] - Sets the spawn for dead tributes in the given arena!",
            c + "/ha start <name> - Force starts the game! If no name is given it will attempt to start the game you are in.",
            c + "/ha ready - Votes for the game to start!",
            c + "/ha reload - Reloads the config!",
            c + "/ha rlist (1,2,3,4,etc) - See who's ready! Numbers are optional",
            c + "/ha watch [1,2,3,4,etc] - Lets you watch the tributes!",
            c + "/ha warpall [1,2,3,4,etc] - Warps all tribute into position!",

            c + "/startpoint [name|cancel] <number> - Sets the starting points of tributes in a specific arena! If a number is left out, it will start setting at the furthest unset point.",
            c + "/sponsor [Player] [ItemID] [Amount] - Lets you sponsor someone!"
    ));

    @Override
    public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
        int page = 0;
        if (args.length >= 2)
            try {
                page = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
            }

        int pages = (int) Math.ceil((double) list.size() / 8d);

        sender.sendMessage(ChatColor.GREEN + "----HungerArena Help----");
        for (int i = 8 * page; i < 8 + 8 * page; i++)
            sender.sendMessage(list.get(i));
        sender.sendMessage(ChatColor.GREEN + "---------(" + (page + 1) + "/" + pages + ")---------");
        if (page + 1 < pages)
            sender.sendMessage(ChatColor.GRAY + "/" + label + " help " + (page + 1) + " for next page");
        return true;
    }

}