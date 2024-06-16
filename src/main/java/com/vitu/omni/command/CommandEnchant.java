package com.vitu.omni.command;

import lombok.AllArgsConstructor;
import me.saiintbrisson.bukkit.command.command.BukkitContext;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import com.vitu.omni.Omni;
import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class CommandEnchant {

  private final Omni omni;

  @Command(
    name = "enchant",
    aliases = {"encantar"},
    permission = "omni.commands.enchant"
  )
  public void handler(BukkitContext context, @Optional String enchantName, @Optional Integer enchantLevel) {
    CommandSender sender = context.getSender();

    if (!(sender instanceof Player)) {
      omni.getExecutorHandler().messenger(sender, "commands.only-player");
      return;
    }

    Player player = (Player) sender;
    ItemStack itemInHand = player.getItemInHand();

    if (itemInHand == null || itemInHand.getType() == Material.AIR) {
      omni.getExecutorHandler().messenger(sender, "commands.enchant.item-is-empty");
      return;
    }

    if (enchantName == null || enchantLevel == null) {
      omni.getExecutorHandler().messenger(sender, "commands.enchant.usage");
      return;
    }

    Enchantment enchantment = Enchantment.getByName(enchantName.toUpperCase());
    if (enchantment == null) {
      omni.getExecutorHandler().messenger(sender, "commands.enchant.enchant-not-found", enchantName, getAllEnchantmentNames());
      return;
    }

    if (enchantLevel <= 0) {
      omni.getExecutorHandler().messenger(sender, "commands.enchant.level-fail");
      return;
    }

    itemInHand.addUnsafeEnchantment(enchantment, enchantLevel);

    omni.getExecutorHandler().messenger(sender, "commands.enchant.success", enchantName, enchantLevel);
  }

  public String getAllEnchantmentNames() {
    List<String> enchantmentNames = new ArrayList<>();
    for (Enchantment enchantment : Enchantment.values()) {
      // TODO: Se isso não estiver funcionando, criar um método...
      enchantmentNames.add(WordUtils.capitalize(enchantment.getName()));
    }
    return String.join(", ", enchantmentNames.toArray(new String[0]));
  }
}
