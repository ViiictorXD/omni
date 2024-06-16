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
public class CommandAction {

  private final Omni omni;

  @Command(
    name = "action",
    aliases = {"actionbar"},
    permission = "omni.commands.action"
  )
  public void onAction(BukkitContext context, @Optional String[] message) {
    CommandSender sender = context.getSender();

    String fullMessage = String.join(" ", message);

    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
      omni.getExecutorHandler().messenger(onlinePlayer, "commands.action.success-target", fullMessage);
    }

    omni.getExecutorHandler().messenger(sender, "commands.action.success");
  }
}
