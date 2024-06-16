package com.omni.minecraft.command;

import lombok.AllArgsConstructor;
import me.saiintbrisson.bukkit.command.command.BukkitContext;
import me.saiintbrisson.minecraft.command.annotation.Command;
import com.omni.minecraft.Omni;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class CommandHat {

  private final Omni omni;

  @Command(
    name = "hat",
    aliases = {"chapeu"},
    permission = "omni.commands.hat"
  )
  public void handler(BukkitContext context) {
    CommandSender sender = context.getSender();

    if (!(sender instanceof Player)) {
      omni.getExecutorHandler().messenger(sender, "commands.only-player");
      return;
    }

    Player player = (Player) sender;

    ItemStack itemInHand = player.getItemInHand();
    ItemStack helmet = player.getInventory().getHelmet();

    if (itemInHand.getType() == Material.AIR
      && !itemInHand.getType().name().contains("HELMET")
      && (!itemInHand.getType().isBlock() || !itemInHand.getType().isSolid())) {
      omni.getExecutorHandler().messenger(sender, "commands.hat.invalid");
      return;
    }

    player.getInventory().setHelmet(itemInHand);
    player.setItemInHand(helmet);

    omni.getExecutorHandler().messenger(sender, "commands.hat.success");
  }
}
