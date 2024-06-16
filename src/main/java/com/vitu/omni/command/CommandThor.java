package com.vitu.omni.command;

import lombok.AllArgsConstructor;
import me.saiintbrisson.bukkit.command.command.BukkitContext;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import com.vitu.omni.Omni;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class CommandThor {

  private final Omni omni;

  @Command(
    name = "thor",
    permission = "omni.commands.thor"
  )
  public void handler(BukkitContext context, @Optional String targetName, @Optional Double damage) {
    CommandSender sender = context.getSender();

    if (damage == null) {
      damage = 1.0D;
    }

    if (targetName != null) {
      if (!(sender instanceof Player)
        && !sender.hasPermission("omni.commands.thor.target")) {
        omni.getExecutorHandler().messenger(sender, "commands.thor.cant-thor-target");
        return;
      }

      if (targetName.equalsIgnoreCase("all")) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
          onlinePlayer.getWorld().strikeLightning(onlinePlayer.getLocation());
        }
        omni.getExecutorHandler().messenger(sender, "commands.thor.success-all");
        return;
      }

      Player target = Bukkit.getPlayer(targetName);

      if (target == null) {
        omni.getExecutorHandler().messenger(sender, "commands.player.target-not-found");
        return;
      }

      target.getWorld().strikeLightning(target.getLocation());

      omni.getExecutorHandler().messenger(sender, "commands.thor.success-target", target.getName());
      return;
    }

    omni.getExecutorHandler().messenger(sender, "commands.thor.usage");
  }
}
