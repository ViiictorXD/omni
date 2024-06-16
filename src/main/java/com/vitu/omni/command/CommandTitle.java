package com.vitu.omni.command;

import com.cryptomorin.xseries.messages.Titles;
import lombok.AllArgsConstructor;
import me.saiintbrisson.bukkit.command.command.BukkitContext;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import com.vitu.omni.Omni;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class CommandTitle {

  private final Omni omni;

  @Command(
    name = "title",
    aliases = {"titulo"},
    permission = "omni.commands.title"
  )
  public void handler(BukkitContext context, @Optional String[] message) {
    CommandSender sender = context.getSender();

    String fullMessage = String.join(" ", message);

    if (fullMessage.contains("<nl>")) {
      String title = fullMessage.split("<nl>")[0];
      String subtitle = fullMessage.split("<nl>")[1];

      for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
        Titles.sendTitle(onlinePlayer, title, subtitle);
      }
    } else {
      for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
        Titles.sendTitle(onlinePlayer, fullMessage, "");
      }
    }

    omni.getExecutorHandler().messenger(sender, "commands.title.sent");
  }
}
