package com.vitu.omni.command;

import com.vitu.omni.api.user.User;
import lombok.AllArgsConstructor;
import me.saiintbrisson.bukkit.command.command.BukkitContext;
import me.saiintbrisson.minecraft.command.annotation.Command;
import com.vitu.omni.Omni;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class CommandBack {

  private final Omni omni;

  @Command(
    name = "back",
    aliases = {"voltar"},
    permission = "omni.commands.back"
  )
  public void handler(BukkitContext context) {
    CommandSender sender = context.getSender();

    if (!(sender instanceof Player)) {
      omni.getExecutorHandler().messenger(sender, "commands.only-player");
      return;
    }

    User user = omni.getUserManager().getUser(sender.getName());

    if (user.getLastLocation() == null) {
      omni.getExecutorHandler().messenger(sender, "commands.back.last-location-null");
      return;
    }

    ((Player) sender).teleport(user.getLastLocation());

    omni.getExecutorHandler().messenger(sender, "commands.back.success");
  }
}
