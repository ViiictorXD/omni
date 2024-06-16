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
public class CommandPing {

  private final Omni omni;

  @Command(
    name = "ping",
    permission = "omni.commands.ping"
  )
  public void handler(BukkitContext context, @Optional String targetName) {
    CommandSender sender = context.getSender();

    if (targetName != null) {
      if (!(sender instanceof Player)
        && !sender.hasPermission("omni.commands.ping.other")) {
        omni.getExecutorHandler().messenger(sender, "commands.ping.cant-ping-other");
        return;
      }

      Player target = Bukkit.getPlayer(targetName);

      if (target == null) {
        omni.getExecutorHandler().messenger(sender, "commands.player.target-not-found");
        return;
      }

      omni.getExecutorHandler().messenger(sender, "commands.ping.ping-target", target.getName(), 0 /* TODO: Arrumar isso depois */);
      return;
    } else if (!(sender instanceof Player)) {
      omni.getExecutorHandler().messenger(sender, "commands.ping.usage");
      return;
    }

    omni.getExecutorHandler().messenger(sender, "commands.ping.success", 0 /* TODO: Arrumar isso depois */);
  }
}
