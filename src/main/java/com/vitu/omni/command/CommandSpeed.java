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
public class CommandSpeed {

  private final Omni omni;

  @Command(
    name = "speed",
    aliases = {"velocidade"},
    permission = "omni.commands.speed"
  )
  public void handler(BukkitContext context, @Optional Integer speed, @Optional String targetName) {
    CommandSender sender = context.getSender();

    if (speed == null) {
      omni.getExecutorHandler().messenger(sender, "commands.speed.usage" + (sender instanceof Player ? "" : "-console"));
      return;
    }

    if (speed <= 0) {
      omni.getExecutorHandler().messenger(sender, "commands.speed.min");
      return;
    }

    int maxWalk = omni.getConfig().getInt("speed-limitation.walking", 10);
    int maxFly = omni.getConfig().getInt("speed-limitation.flying", 10);

    if (targetName != null) {
      if (!(sender instanceof Player)
        && !sender.hasPermission("omni.commands.speed.target")) {
        omni.getExecutorHandler().messenger(sender, "commands.heal.cant-speed-target");
        return;
      }

      Player target = Bukkit.getPlayer(targetName);

      if (target == null) {
        omni.getExecutorHandler().messenger(sender, "commands.player.target-not-found");
        return;
      }

      int max = target.isFlying() ? maxFly : maxWalk;
      if (speed > max) {
        omni.getExecutorHandler().messenger(sender, "commands.speed.max", max);
        return;
      }

      String path;
      if (target.isFlying()) {
        path = "commands.speed.argument.fly";
        target.setFlySpeed((float) (speed / 10));
      } else {
        path = "commands.speed.argument.walk";
        target.setWalkSpeed((float) (speed / 10));
      }

      omni.getExecutorHandler().messenger(sender, "commands.speed.success-target", omni.getExecutorHandler().messenger(path), target.getName(), speed);
      return;
    } else if (!(sender instanceof Player)) {
      omni.getExecutorHandler().messenger(sender, "commands.speed.usage-console");
      return;
    }

    Player player = (Player) sender;

    int max = player.isFlying() ? maxFly : maxWalk;
    if (speed > max) {
      omni.getExecutorHandler().messenger(sender, "commands.speed.max", max);
      return;
    }

    String path;
    if (player.isFlying()) {
      path = "commands.speed.argument.fly";
      player.setFlySpeed((float) (speed / 10));
    } else {
      path = "commands.speed.argument.walk";
      player.setWalkSpeed((float) (speed / 10));
    }

    omni.getExecutorHandler().messenger(sender, "commands.speed.success", omni.getExecutorHandler().messenger(path), speed);
  }
}
