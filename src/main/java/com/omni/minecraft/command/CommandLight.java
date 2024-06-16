package com.omni.minecraft.command;

import lombok.AllArgsConstructor;
import me.saiintbrisson.bukkit.command.command.BukkitContext;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import com.omni.minecraft.Omni;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@AllArgsConstructor
public class CommandLight {

  private final Omni omni;

  @Command(
    name = "light",
    aliases = {"luz"},
    permission = "omni.commands.light"
  )
  public void handler(BukkitContext context, @Optional String targetName) {
    CommandSender sender = context.getSender();

    if (targetName != null) {
      if (!(sender instanceof Player)
        && !sender.hasPermission("omni.commands.light.target")) {
        omni.getExecutorHandler().messenger(sender, "commands.light.cant-light-target");
        return;
      }

      Player target = Bukkit.getPlayer(targetName);

      if (target == null) {
        omni.getExecutorHandler().messenger(sender, "commands.player.target-not-found");
        return;
      }

      if (target.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
        target.removePotionEffect(PotionEffectType.NIGHT_VISION);
        omni.getExecutorHandler().messenger(sender, "commands.light.success-disable-target", target.getName());
        return;
      }

      target.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 100));
      omni.getExecutorHandler().messenger(sender, "commands.light.success-enable-target", target.getName());
      return;
    } else if (!(sender instanceof Player)) {
      omni.getExecutorHandler().messenger(sender, "commands.light.usage");
      return;
    }

    Player player = (Player) sender;

    if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
      player.removePotionEffect(PotionEffectType.NIGHT_VISION);
      omni.getExecutorHandler().messenger(sender, "commands.light.success-disable");
      return;
    }

    player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 100));
    omni.getExecutorHandler().messenger(sender, "commands.light.success-enable");
  }
}
