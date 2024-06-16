package com.omni.minecraft.command;

import lombok.AllArgsConstructor;
import me.saiintbrisson.bukkit.command.command.BukkitContext;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import com.omni.minecraft.Omni;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class CommandFeed {

  private final Omni omni;

  @Command(
    name = "feed",
    aliases = {"comida"},
    permission = "omni.commands.feed"
  )
  public void handler(BukkitContext context, @Optional String targetName) {
    CommandSender sender = context.getSender();

    if (targetName != null) {
      if (sender instanceof Player
        && !sender.hasPermission("omni.commands.feed.target")) {
        omni.getExecutorHandler().messenger(sender, "commands.feed.cant-feed-target");
        return;
      }

      Player target = Bukkit.getPlayer(targetName);

      if (target == null) {
        omni.getExecutorHandler().messenger(sender, "commands.player.target-not-found");
        return;
      }

      target.setFoodLevel(20);

      omni.getExecutorHandler().messenger(sender, "commands.feed.success-target", target.getName());
      return;
    } else if (!(sender instanceof Player)) {
      omni.getExecutorHandler().messenger(sender, "commands.feed.usage");
      return;
    }

    ((Player) sender).setFoodLevel(20);

    omni.getExecutorHandler().messenger(sender, "commands.feed.success");
  }
}
