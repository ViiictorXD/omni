package com.omni.minecraft.command;

import lombok.AllArgsConstructor;
import me.saiintbrisson.bukkit.command.command.BukkitContext;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import com.omni.minecraft.Omni;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class CommandAlert {

  private final Omni omni;

  @Command(
    name = "alert",
    aliases = {"alerta"},
    permission = "omni.commands.alert"
  )
  public void handler(BukkitContext context, @Optional String[] message) {
    CommandSender sender = context.getSender();

    if (message == null || message.length == 0) {
      omni.getExecutorHandler().messenger(sender, "commands.alert.usage");
      return;
    }

    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
      omni.getExecutorHandler().messenger(sender, "commands.alert.success-target", String.join(" ", message));
    }

    omni.getExecutorHandler().messenger(sender, "commands.alert.success", String.join(" ", message));
  }
}
