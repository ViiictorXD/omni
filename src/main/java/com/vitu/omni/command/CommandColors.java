package com.vitu.omni.command;

import lombok.AllArgsConstructor;
import me.saiintbrisson.bukkit.command.command.BukkitContext;
import me.saiintbrisson.minecraft.command.annotation.Command;
import com.vitu.omni.Omni;
import org.bukkit.command.CommandSender;

@AllArgsConstructor
public class CommandColors {

  private final Omni omni;

  @Command(
    name = "colors",
    aliases = {"cores"},
    permission = "omni.commands.colors"
  )
  public void handler(BukkitContext context) {
    CommandSender sender = context.getSender();

    omni.getExecutorHandler().messenger(sender, "commands.colors.success");
  }
}
