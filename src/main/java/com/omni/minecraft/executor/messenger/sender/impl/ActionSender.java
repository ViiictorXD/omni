package com.omni.minecraft.executor.messenger.sender.impl;

import com.cryptomorin.xseries.messages.ActionBar;
import com.omni.minecraft.executor.messenger.sender.Sender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ActionSender implements Sender {

  @Override
  public void send(CommandSender sender, String message) {
    if (sender instanceof Player) {
      ActionBar.sendActionBar((Player) sender, message);
      return;
    }

    sender.sendMessage(message);
  }
}
