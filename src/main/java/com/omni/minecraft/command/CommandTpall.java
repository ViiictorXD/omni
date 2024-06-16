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
public class CommandTpall {

  private final Omni omni;

  @Command(
    name = "tpall",
    permission = "omni.commands.tpall"
  )
  public void handler(BukkitContext context, @Optional String targetName) {
    CommandSender sender = context.getSender();

    if (targetName != null) {
      if (sender instanceof Player
        && !sender.hasPermission("omni.commands.tpall.target")) {
        omni.getExecutorHandler().messenger(sender, "commands.tpall.cant-tpall-target");
        return;
      }

      Player target = Bukkit.getPlayer(targetName);

      if (target == null) {
        omni.getExecutorHandler().messenger(sender, "commands.player.target-not-found");
        return;
      }

      int count = Bukkit.getOnlinePlayers().size() - 1;
      if (count <= 0) {
        omni.getExecutorHandler().messenger(sender, "commands.tpall.no-one-found");
        return;
      }

      for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
        if (onlinePlayer.getUniqueId().equals(target.getUniqueId())) continue;

        onlinePlayer.teleport(target);
      }

      omni.getExecutorHandler().messenger(sender, "commands.tpall.success-target", target.getName(), count);
      return;
    } else if (!(sender instanceof Player)) {
      omni.getExecutorHandler().messenger(sender, "commands.tpall.usage");
      return;
    }

    int count = Bukkit.getOnlinePlayers().size() - 1;
    if (count <= 0) {
      omni.getExecutorHandler().messenger(sender, "commands.tpall.no-one");
      return;
    }

    Player player = (Player) sender;
    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
      if (onlinePlayer.getUniqueId().equals(player.getUniqueId())) continue;

      onlinePlayer.teleport(player);
    }

    omni.getExecutorHandler().messenger(sender, "commands.tpall.success", count);
  }
}
