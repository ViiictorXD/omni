package com.vitu.omni.command;

import com.vitu.omni.Helper;
import lombok.AllArgsConstructor;
import me.saiintbrisson.bukkit.command.command.BukkitContext;
import me.saiintbrisson.minecraft.command.annotation.Command;
import com.vitu.omni.Omni;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class CommandTrash {

  private final Omni omni;

  @Command(
    name = "trash",
    aliases = {"lixo", "lixeira"},
    permission = "omni.commands.trash"
  )
  public void handler(BukkitContext context) {
    CommandSender sender = context.getSender();

    if (!(sender instanceof Player)) {
      omni.getExecutorHandler().messenger(sender, "commands.only-player");
      return;
    }

    ((Player) sender).openInventory(Bukkit.createInventory(null,
      9 * omni.getConfig().getInt("trash.size", 6),
      Helper.color(omni.getConfig().getString("trash.title"))
    ));
  }
}
