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
public class CommandFly {

  private final Omni omni;

  @Command(
    name = "fly",
    aliases = {"voar"},
    permission = "omni.commands.fly"
  )
  public void handler(BukkitContext context, @Optional String targetName) {
    CommandSender sender = context.getSender();

    if (targetName != null) {
      if (sender instanceof Player
        && !sender.hasPermission("omni.commands.fly.target")) {
        omni.getExecutorHandler().messenger(sender, "commands.fly.cant-fly-target");
        return;
      }
      Player target = Bukkit.getPlayer(targetName);

      if (target == null) {
        omni.getExecutorHandler().messenger(sender, "commands.player.target-not-found");
        return;
      }

      target.setAllowFlight(!target.getAllowFlight());

      omni.getExecutorHandler().messenger(sender, "commands.fly.success-" + (target.getAllowFlight() ? "enable" : "disable") + "-target", target.getName());
      return;
    } else if (!(sender instanceof Player)) {
      omni.getExecutorHandler().messenger(sender, "commands.fly.usage");
      return;
    }

    Player player = (Player) sender;

    String worldName = player.getWorld().getName();

    if (!player.hasPermission("omni.fly.bypass." + worldName)
      && omni.getConfig().getStringList("disallowed-fly-worlds").contains(player.getWorld().getName())) {
      omni.getExecutorHandler().messenger(player, "commands.fly.no-permission-that-world");
      return;
    }

    player.setAllowFlight(!player.getAllowFlight());

    omni.getExecutorHandler().messenger(player, "commands.fly.success-" + (player.getAllowFlight() ? "enable" : "disable"));
  }
}
