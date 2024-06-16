package com.omni.minecraft.executor.messenger.sender;

import org.bukkit.command.CommandSender;

public interface Sender {

  void send(CommandSender sender, String message);
}
