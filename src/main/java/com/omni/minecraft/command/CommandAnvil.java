package com.omni.minecraft.command;

import lombok.AllArgsConstructor;
import me.saiintbrisson.bukkit.command.command.BukkitContext;
import me.saiintbrisson.minecraft.command.annotation.Command;
import com.omni.minecraft.Omni;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

@AllArgsConstructor
public class CommandAnvil {

  private final Omni omni;

  @Command(
    name = "anvil",
    aliases = {"bigorna"},
    permission = "omni.commands.anvil"
  )
  public void handler(BukkitContext context) {
    CommandSender sender = context.getSender();

    if (!(sender instanceof Player)) {
      omni.getExecutorHandler().messenger(sender, "commands.only-player");
      return;
    }

    ((Player) sender).openInventory(Bukkit.createInventory(null, InventoryType.ANVIL));
  }
}
