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
public class CommandBurn {

  private final Omni omni;

  @Command(
    name = "burn",
    aliases = {"queimar"},
    permission = "omni.commands.burn"
  )
  public void handler(BukkitContext context, @Optional String targetName, @Optional Double damage) {
    CommandSender sender = context.getSender();

    if (damage == null) {
      damage = 1.0D;
    }

    if (targetName != null) {
      if (!(sender instanceof Player)
        && !sender.hasPermission("omni.commands.burn.target")) {
        omni.getExecutorHandler().messenger(sender, "commands.burn.cant-burn-target");
        return;
      }

      if (targetName.equalsIgnoreCase("all")) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
          // TODO: Fazer o jogador pegar fogo
          onlinePlayer.getWorld().strikeLightning(onlinePlayer.getLocation());
        }

        omni.getExecutorHandler().messenger(sender, "commands.burn.success-all");
        return;
      }
      Player target = Bukkit.getPlayer(targetName);

      if (target == null) {
        omni.getExecutorHandler().messenger(sender, "commands.player.target-not-found");
        return;
      }

      // TODO: Fazer o jogador pegar fogo
      target.getWorld().strikeLightning(target.getLocation());

      omni.getExecutorHandler().messenger(sender, "commands.burn.success-target", target.getName());
      return;
    }

    omni.getExecutorHandler().messenger(sender, "commands.burn.usage");
  }
}
