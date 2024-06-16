package com.omni.minecraft.executor.messenger.sender.impl;

import com.cryptomorin.xseries.messages.ActionBar;
import com.omni.minecraft.executor.messenger.sender.Sender;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ActionsSender implements Sender {

  @Override
  public void send(CommandSender sender, String message) {
    for (Player target : Bukkit.getOnlinePlayers()) {
      if (sender.getName().equalsIgnoreCase(target.getName())) {
        continue;
      }

      ActionBar.sendActionBar(target, message);
    }

    if (sender instanceof Player) {
      ActionBar.sendActionBar((Player) sender, message);
      return;
    }

    sender.sendMessage(message);
  }
}
