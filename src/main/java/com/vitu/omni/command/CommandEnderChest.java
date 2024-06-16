package com.vitu.omni.command;

import lombok.AllArgsConstructor;
import me.saiintbrisson.bukkit.command.command.BukkitContext;
import me.saiintbrisson.minecraft.command.annotation.Command;
import com.vitu.omni.Omni;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class CommandEnderChest {

  private final Omni omni;

  @Command(
    name = "enderchest",
    aliases = {"echest", "ec"},
    permission = "omni.commands.enderchest"
  )
  public void handler(BukkitContext context) {
    CommandSender sender = context.getSender();

    if (!(sender instanceof Player)) {
      omni.getExecutorHandler().messenger(sender, "commands.only-player");
      return;
    }

    ((Player) sender).openInventory(((Player) sender).getEnderChest());
  }
}
