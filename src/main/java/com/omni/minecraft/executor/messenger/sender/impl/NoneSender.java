package com.omni.minecraft.executor.messenger.sender.impl;

import com.omni.minecraft.executor.messenger.sender.Sender;
import org.bukkit.command.CommandSender;

public class NoneSender implements Sender {

  @Override
  public void send(CommandSender sender, String message) {
    sender.sendMessage(message);
  }
}
