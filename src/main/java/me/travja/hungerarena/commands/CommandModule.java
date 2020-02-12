package me.travja.hungerarena.commands;

import me.travja.hungerarena.Main;
import me.travja.hungerarena.utils.Chat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.Set;

public abstract class CommandModule {

    protected String name;
    protected String permission;
    protected String permissionMessage;
    protected String usage;
    protected Set<String> aliases = new HashSet<>();
    protected CommandModule parent;
    protected Set<CommandModule> children = new HashSet<>();
    protected boolean requirePlayer = false;

    public CommandModule(CommandModule parent, String name, String permission, String permissionMessage, String usage, String... alias) {
        setParent(parent);
        setName(name);
        setPermission(permission);
        setPermissionMessage(Chat.translate(permissionMessage));
        setUsage(Chat.translate(usage));
        setAliases(alias);
        addAlias(name);
        if(this instanceof Listener)
            Main.self.getServer().getPluginManager().registerEvents((Listener) this, Main.self);
    }

    public void setParent(CommandModule parent) {
        if (!isIndependent())
            this.parent.removeChild(this);
        this.parent = parent;
        if (this.parent != null)
            this.parent.addChild(this);
    }

    public CommandModule getParent() {
        return this.parent;
    }

    public boolean isIndependent() {
        return this.parent == null;
    }

    public void addChild(CommandModule child) {
        this.children.add(child);
    }

    public void removeChild(CommandModule child) {
        this.children.remove(child);
    }

    public CommandModule getChild(String name) {
        for (CommandModule child : children)
            if (child.getAliases().contains(name.toLowerCase()))
                return child;

        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getPermissionMessage() {
        return permissionMessage;
    }

    public void setPermissionMessage(String permissionMessage) {
        this.permissionMessage = permissionMessage;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public void addAlias(String alias) {
        this.aliases.add(alias);
    }

    public void setAliases(String... alias) {
        this.aliases.clear();
        for (String ali : alias) {
            this.aliases.add(ali);
        }
    }

    public CommandModule setAliases(HashSet<String> aliases) {
        this.aliases = aliases;
        return this;
    }

    public void removeAlias(String alias) {
        this.aliases.remove(alias);
    }

    public Set<String> getAliases() {
        return aliases;
    }

    public CommandModule setRequirePlayer(boolean requirePlayer) {
        this.requirePlayer = requirePlayer;
        return this;
    }

    public boolean requirePlayer() {
        return this.requirePlayer;
    }

    public abstract boolean execute(CommandSender sender, Command cmd, String commandLabel, String[] args);

}
