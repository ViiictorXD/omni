package com.vitu.omni.command;

import lombok.AllArgsConstructor;
import me.saiintbrisson.bukkit.command.command.BukkitContext;
import me.saiintbrisson.minecraft.command.annotation.Command;
import com.vitu.omni.Omni;
import org.bukkit.Bukkit;

@AllArgsConstructor
public class CommandOnline {

  private final Omni omni;

  @Command(
    name = "online",
    permission = "omni.commands.online"
  )
  public void handler(BukkitContext context) {
    omni.getExecutorHandler().messenger(context.getSender(), "online.success", Bukkit.getOnlinePlayers().size(), Bukkit.getMaxPlayers());
  }
}
