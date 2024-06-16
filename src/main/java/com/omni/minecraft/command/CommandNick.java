package com.omni.minecraft.command;

import com.omni.minecraft.api.event.user.UserNickChangeEvent;
import com.omni.minecraft.api.user.User;
import lombok.AllArgsConstructor;
import me.saiintbrisson.bukkit.command.command.BukkitContext;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import com.omni.minecraft.Omni;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class CommandNick {

  private final Omni omni;

  @Command(
    name = "nick",
    permission = "omni.commands.nick"
  )
  public void handler(BukkitContext context, @Optional String nick, @Optional String targetName) {
    CommandSender sender = context.getSender();

    boolean isPlayer = sender instanceof Player;

    if (nick == null) {
      omni.getExecutorHandler().messenger(sender, "commands.nick.usage" + (isPlayer ? "" : "-console"));
      return;
    }

    if (targetName != null) {
      if (sender instanceof Player
        && !sender.hasPermission("omni.commands.nick.target")) {
        omni.getExecutorHandler().messenger(sender, "commands.nick.cant-rename-target");
        return;
      }

      Player target = Bukkit.getPlayer(targetName);

      if (target == null) {
        omni.getExecutorHandler().messenger(sender, "commands.player.target-not-found");
        return;
      }

      User user = omni.getUserManager().getUser(target.getUniqueId());

      if (user == null) {
        omni.getExecutorHandler().messenger(sender, "commands.player.target-not-found");
        return;
      }

      String oldNick = user.getNick();
      user.setNick(nick);

      Bukkit.getPluginManager().callEvent(new UserNickChangeEvent(user, oldNick));

      omni.getExecutorHandler().messenger(sender, "commands.nick.success-target", user.getName(), user.getNick());
      return;
    } else if (!(sender instanceof Player)) {
      omni.getExecutorHandler().messenger(sender, "commands.nick.usage-console");
      return;
    }

    User user = omni.getUserManager().getUser(sender.getName());

    String oldNick = user.getNick();
    user.setNick(nick);

    Bukkit.getPluginManager().callEvent(new UserNickChangeEvent(user, oldNick));

    omni.getExecutorHandler().messenger(sender, "commands.nick.success", user.getNick());
  }
}
