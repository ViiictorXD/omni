package com.vitu.omni.command;

import com.vitu.omni.model.Mode;
import lombok.AllArgsConstructor;
import me.saiintbrisson.bukkit.command.command.BukkitContext;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import com.vitu.omni.Omni;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class CommandGameMode {

  private final Omni omni;

  @Command(
    name = "gamemode",
    aliases = {"gm"},
    permission = "omni.commands.gamemode"
  )
  public void handler(BukkitContext context, @Optional String modeName, @Optional String targetName) {
    CommandSender sender = context.getSender();

    if (modeName == null) {
      omni.getExecutorHandler().messenger(sender, "commands.gm.usage-" + (sender instanceof Player ? "" : "console"));
      return;
    }

    Mode mode = Mode.of(modeName);
    if (mode == null) {
      omni.getExecutorHandler().messenger(sender, "commands.gm.mode-not-found");
      return;
    }

    if (targetName != null) {
      if (!(sender instanceof Player)
        && !sender.hasPermission("omni.commands.gamemode.target")) {
        omni.getExecutorHandler().messenger(sender, "commands.gm.cant-gm-target");
        return;
      }

      Player target = Bukkit.getPlayer(targetName);

      if (target == null) {
        omni.getExecutorHandler().messenger(sender, "commands.player.target-not-found");
        return;
      }

      if (target.getGameMode() == mode.getGameMode()) {
        omni.getExecutorHandler().messenger(sender, "commands.gm.target-already-that-mode");
        return;
      }

      target.setGameMode(mode.getGameMode());

      omni.getExecutorHandler().messenger(sender, "commands.gm.success-target", target.getName(), mode.beautyName());
      return;
    } else if (!(sender instanceof Player)) {
      omni.getExecutorHandler().messenger(sender, "commands.gm.usage-console");
      return;
    }

    Player player = (Player) sender;

    if (player.getGameMode() == mode.getGameMode()) {
      omni.getExecutorHandler().messenger(sender, "commands.gm.already-that-mode");
      return;
    }

    player.setGameMode(mode.getGameMode());

    omni.getExecutorHandler().messenger(sender, "commands.gm.success", mode.beautyName());
  }
}
