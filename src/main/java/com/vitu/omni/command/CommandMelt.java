package com.vitu.omni.command;

import com.cryptomorin.xseries.XMaterial;
import lombok.AllArgsConstructor;
import me.saiintbrisson.bukkit.command.command.BukkitContext;
import me.saiintbrisson.minecraft.command.annotation.Command;
import com.vitu.omni.Omni;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@AllArgsConstructor
public class CommandMelt {

  private final Omni omni;

  @Command(
    name = "melt",
    aliases = {"derreter"},
    permission = "omni.commands.melt"
  )
  public void handler(BukkitContext context) {
    CommandSender sender = context.getSender();

    if (!(sender instanceof Player)) {
      omni.getExecutorHandler().messenger(sender, "commands.only-player");
      return;
    }

    int melt = melt((Player) sender);
    if (melt <= 0) {
      omni.getExecutorHandler().messenger(sender, "commands.melt.no-one");
      return;
    }

    omni.getExecutorHandler().messenger(sender, "commands.melt.success", melt);
  }

  private int melt(Player player) {
    PlayerInventory inventory = player.getInventory();
    ItemStack[] contents = inventory.getContents();

    int melt = 0;
    for (ItemStack itemStack : contents) {
      if (itemStack == null) continue;

      XMaterial material = XMaterial.matchXMaterial(itemStack.getType());
      if (!material.name().contains("ORE")) continue;

      Ore ore = Ore.directFrom(material);
      if (ore == null) continue;

      inventory.remove(itemStack);

      ItemStack oreAsBlock = ore.getMaterial().parseItem();
      oreAsBlock.setAmount(itemStack.getAmount());

      inventory.addItem(oreAsBlock);

      melt += itemStack.getAmount();
    }
    return melt;
  }
}
