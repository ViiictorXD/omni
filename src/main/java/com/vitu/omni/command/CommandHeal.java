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
public class CommandHeal {

  private final Omni omni;

  @Command(
    name = "heal",
    aliases = {"curar"},
    permission = "omni.commands.heal"
  )
  public void handler(BukkitContext context, @Optional String targetName) {
    CommandSender sender = context.getSender();

    if (targetName != null) {
      if (!(sender instanceof Player)
        && !sender.hasPermission("omni.commands.heal.target")) {
        omni.getExecutorHandler().messenger(sender, "commands.heal.cant-heal-target");
        return;
      }

      Player target = Bukkit.getPlayer(targetName);

      if (target == null) {
        omni.getExecutorHandler().messenger(sender, "commands.player.target-not-found");
        return;
      }

      target.setHealth(20.0D);
      omni.getExecutorHandler().messenger(sender, "commands.heal.success-target", target.getName());
      return;
    } else if (!(sender instanceof Player)) {
      omni.getExecutorHandler().messenger(sender, "commands.heal.usage");
      return;
    }

    Player player = (Player) sender;
    player.setHealth(20.0D);

    omni.getExecutorHandler().messenger(sender, "commands.heal.success");
  }
}
