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
public class CommandPublish {

  private final Omni omni;

  @Command(
    name = "publish",
    aliases = {"divulgar"},
    permission = "omni.commands.publish"
  )
  public void handler(BukkitContext context, @Optional String[] message) {
    CommandSender sender = context.getSender();

    if (!(sender instanceof Player)) {
      omni.getExecutorHandler().messenger(sender, "commands.only-player");
      return;
    }

    if (message == null) {
      omni.getExecutorHandler().messenger(sender, "commands.publish.usage");
      return;
    }

    String fullMessage = String.join(" ", message);

    if (!isValidLink(fullMessage)) {
      omni.getExecutorHandler().messenger(sender, "commands.publish.invalid-link");
      return;
    }

    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
      omni.getExecutorHandler().messenger(onlinePlayer, "commands.publish.success", sender.getName(), fullMessage);
    }
  }

  private boolean isValidLink(String input) {
    return true;
  }
}
