package com.vitu.omni.executor.messenger.sender.impl;

import com.vitu.omni.executor.messenger.sender.Sender;
import org.bukkit.command.CommandSender;

public class NoneSender implements Sender {

  @Override
  public void send(CommandSender sender, String message) {
    sender.sendMessage(message);
  }
}
