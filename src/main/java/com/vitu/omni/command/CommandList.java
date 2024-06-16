package com.vitu.omni.command;

import lombok.AllArgsConstructor;
import me.saiintbrisson.bukkit.command.command.BukkitContext;
import me.saiintbrisson.minecraft.command.annotation.Command;
import com.vitu.omni.Omni;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class CommandList {

  private final Omni omni;

  @Command(
    name = "list",
    aliases = {"list"},
    permission = "omni.commands.list"
  )
  public void handler(BukkitContext context) {
    CommandSender sender = context.getSender();

    if (!(sender instanceof Player)) {
      omni.getExecutorHandler().messenger(sender, "commands.only-player");
      return;
    }

    // TODO: Criar menu com lista de todos jogadores online
    sender.sendMessage("comando teste, falta o menu");
    /*essentials.getViewFrame().open(
      OnlinePlayersView.class,
      (Player) sender
    );*/
  }
}
