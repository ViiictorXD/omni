package com.vitu.omni.executor.messenger.sender.impl;

import com.vitu.omni.executor.messenger.sender.Sender;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class BroadcastSender implements Sender {

  @Override
  public void send(CommandSender sender, String message) {
    Bukkit.broadcastMessage(message);
  }
}
