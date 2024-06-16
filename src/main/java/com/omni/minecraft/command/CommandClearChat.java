package com.omni.minecraft.command;

import lombok.AllArgsConstructor;
import me.saiintbrisson.bukkit.command.command.BukkitContext;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import net.deslocher.omni.Helper;
import com.omni.minecraft.Omni;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class CommandClearChat {

  private final Omni omni;

  @Command(
    name = "clearchat",
    aliases = {"cc"},
    permission = "omni.commands.clearchat"
  )
  public void handler(BukkitContext context, @Optional String targetName) {
    CommandSender sender = context.getSender();

    if (targetName != null) {
      if (sender instanceof Player
        && !sender.hasPermission("omni.commands.clearchat.target")) {
        omni.getExecutorHandler().messenger(sender, "commands.clear-chat.cant-clear-target");
        return;
      }

      Player target = Bukkit.getPlayer(targetName);

      if (target == null) {
        omni.getExecutorHandler().messenger(sender, "commands.player.target-not-found");
        return;
      }

      target.sendMessage(Helper.CLEAR_CHAT_CACHE);

      omni.getExecutorHandler().messenger(sender, "commands.clear-chat.success-target", target.getName());
      return;
    }

    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
      onlinePlayer.sendMessage(Helper.CLEAR_CHAT_CACHE);
    }

    omni.getExecutorHandler().messenger(sender, "commands.clear-chat.success");
  }
}
