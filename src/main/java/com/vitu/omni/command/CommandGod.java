package com.vitu.omni.command;

import com.vitu.omni.Constant;
import lombok.AllArgsConstructor;
import me.saiintbrisson.bukkit.command.command.BukkitContext;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import com.vitu.omni.Omni;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class CommandGod {

  private final Omni omni;

  @Command(
    name = "god",
    aliases = {"deus"},
    permission = "omni.commands.god"
  )
  public void handler(BukkitContext context, @Optional String targetName) {
    CommandSender sender = context.getSender();

    if (targetName != null) {
      if (!(sender instanceof Player)
        && !sender.hasPermission("omni.commands.god.target")) {
        omni.getExecutorHandler().messenger(sender, "commands.god.cant-god-target");
        return;
      }

      Player target = Bukkit.getPlayer(targetName);

      if (target == null) {
        omni.getExecutorHandler().messenger(sender, "commands.player.target-not-found");
        return;
      }

      if (Constant.GOD_MODE.remove(target.getUniqueId())) {
        omni.getExecutorHandler().messenger(sender, "commands.god.success-disable-target", target.getName());
        return;
      }

      Constant.GOD_MODE.add(target.getUniqueId());
      omni.getExecutorHandler().messenger(sender, "commands.god.success-disable-target", target.getName());
      return;
    } else if (!(sender instanceof Player)) {
      omni.getExecutorHandler().messenger(sender, "commands.god.usage");
      return;
    }

    Player player = (Player) sender;

    if (Constant.GOD_MODE.remove(player.getUniqueId())) {
      omni.getExecutorHandler().messenger(sender, "commands.god.disable");
      return;
    }

    Constant.GOD_MODE.add(player.getUniqueId());
    omni.getExecutorHandler().messenger(sender, "commands.god.enable");
  }
}
