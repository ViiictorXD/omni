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
public class CommandClear {

  private final Omni omni;

  @Command(
    name = "clear",
    aliases = {"limpar"},
    permission = "omni.commands.clear"
  )
  public void handler(BukkitContext context, @Optional String targetName) {
    CommandSender sender = context.getSender();

    if (targetName != null) {
      if (sender instanceof Player
        && !sender.hasPermission("omni.commands.clear.target")) {
        omni.getExecutorHandler().messenger(sender, "commands.clear.cant-clear-target");
        return;
      }

      Player target = Bukkit.getPlayer(targetName);

      if (target == null) {
        omni.getExecutorHandler().messenger(sender, "commands.player.target-not-found");
        return;
      }

      PlayerUtil.clearPlayer(target);

      omni.getExecutorHandler().messenger(sender, "commands.clear.success-target", target.getName());
      return;
    } else if (!(sender instanceof Player)) {
      omni.getExecutorHandler().messenger(sender, "commands.clear.usage-console");
      return;
    }

    PlayerUtil.clearPlayer((Player) sender);

    omni.getExecutorHandler().messenger(sender, "commands.clear.success");
  }
}
