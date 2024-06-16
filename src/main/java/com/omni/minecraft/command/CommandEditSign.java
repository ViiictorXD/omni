package com.omni.minecraft.command;

import lombok.AllArgsConstructor;
import me.saiintbrisson.bukkit.command.command.BukkitContext;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import com.omni.minecraft.Omni;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

@AllArgsConstructor
public class CommandEditSign {

  private final Omni omni;

  @Command(
    name = "editsign",
    aliases = {"editarplaca"},
    permission = "omni.commands.editsign"
  )//todo: arrumar essa porra
  public void handler(BukkitContext context, @Optional Integer signLine, @Optional String[] signNewValue) {
    CommandSender sender = context.getSender();

    if (!(sender instanceof Player)) {
      omni.getExecutorHandler().messenger(sender, "commands.only-player");
      return;
    }

    Player player = (Player) sender;

    Block targetBlock = player.getTargetBlock((Set<Material>) null, 10);
    if (targetBlock == null || targetBlock.getType().toString().contains("SIGN") || !(targetBlock instanceof Sign)) {
      omni.getExecutorHandler().messenger(sender, "commands.edit-sign.no-sign-found");
      return;
    }

    if (signLine == null || signNewValue == null) {
      omni.getExecutorHandler().messenger(sender, "commands.edit-sign.usage");
      return;
    }

    Sign sign = (Sign) targetBlock;
    sign.setLine(signLine, String.join(" ", signNewValue));
    sign.update();

    omni.getExecutorHandler().messenger(sender, "commands.edit-sign.success", signLine, String.join(" ", signNewValue));
  }
}
