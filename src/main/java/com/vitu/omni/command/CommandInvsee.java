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
public class CommandInvsee {

  private final Omni omni;

  @Command(
    name = "invsee",
    aliases = {"verinv"},
    permission = "omni.commands.invsee"
  )
  public void handler(BukkitContext context, @Optional String targetName) {
    CommandSender sender = context.getSender();

    if (!(sender instanceof Player)) {
      omni.getExecutorHandler().messenger(sender, "commands.only-player");
      return;
    }

    if (targetName == null) {
      omni.getExecutorHandler().messenger(sender, "commands.invsee.usage");
      return;
    }

    Player target = Bukkit.getPlayer(targetName);

    if (target == null) {
      omni.getExecutorHandler().messenger(sender, "commands.player.target-not-found");
      return;
    }

    ((Player) sender).openInventory(target.getInventory());
  }
}
