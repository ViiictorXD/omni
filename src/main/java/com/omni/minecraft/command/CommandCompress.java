package com.omni.minecraft.command;

import com.cryptomorin.xseries.XMaterial;
import lombok.AllArgsConstructor;
import me.saiintbrisson.bukkit.command.command.BukkitContext;
import me.saiintbrisson.minecraft.command.annotation.Command;
import com.omni.minecraft.Omni;
import net.deslocher.omni.data.Ore;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class CommandCompress {

  private final Omni omni;

  @Command(
    name = "compress",
    aliases = {"compactar"},
    permission = "omni.commands.compress"
  )
  public void handler(BukkitContext context) {
    CommandSender sender = context.getSender();

    if (!(sender instanceof Player)) {
      omni.getExecutorHandler().messenger(sender, "commands.only-player");
      return;
    }

    int compress = compress((Player) sender);
    if (compress <= 0) {
      omni.getExecutorHandler().messenger(sender, "commands.compress.fail");
      return;
    }

    omni.getExecutorHandler().messenger(sender, "commands.compress.success", compress);
  }

  private int compress(Player player) {
    PlayerInventory inventory = player.getInventory();
    ItemStack[] contents = inventory.getContents();

    List<ItemStack> giveBack = new ArrayList<>();

    int compress = 0;
    for (int index = 0; index < contents.length; index++) {
      ItemStack itemStack = contents[index];
      if (itemStack == null) continue;

      XMaterial material = XMaterial.matchXMaterial(itemStack.getType());

      if (material == XMaterial.AIR
        || itemStack.getAmount() < 9
        || itemStack.hasItemMeta()) {
        continue;
      }

      Ore ore = Ore.asSimple(material);
      if (ore == null) continue;

      int giveAmount = itemStack.getAmount() / 9; // 1
      int giveBackAmount = itemStack.getAmount() - (giveAmount * 9);

      inventory.remove(itemStack);

      ItemStack oreAsBlock = ore.getBlock().parseItem();
      oreAsBlock.setAmount(giveAmount);
      inventory.addItem(oreAsBlock);

      if (giveBackAmount > 0) {
        ItemStack oreAsSimple = ore.getMaterial().parseItem();
        oreAsSimple.setAmount(giveBackAmount);

        giveBack.add(oreAsSimple);
      }

      compress += giveAmount * 9;
    }

    for (ItemStack itemStack : giveBack) {
      for (ItemStack rest : inventory.addItem(itemStack).values()) {
        player.getWorld().dropItem(player.getLocation(), rest);
      }
    }

    return compress;
  }
}
