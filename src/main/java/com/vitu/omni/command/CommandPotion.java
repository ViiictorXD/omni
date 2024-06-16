package com.vitu.omni.command;

import com.cryptomorin.xseries.XMaterial;
import lombok.AllArgsConstructor;
import me.saiintbrisson.bukkit.command.command.BukkitContext;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import com.vitu.omni.Omni;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@AllArgsConstructor
public class CommandPotion {

  private final Omni omni;

  @Command(
    name = "potion",
    aliases = {"pocao"},
    permission = "omni.commands.potion"
  )
  public void handler(BukkitContext context, @Optional String effectType, @Optional Integer duration, @Optional Integer amplifier) {
    CommandSender sender = context.getSender();

    if (!(sender instanceof Player)) {
      omni.getExecutorHandler().messenger(sender, "commands.only-player");
      return;
    }

    Player player = (Player) sender;

    if (effectType == null || duration == null || amplifier == null) {
      omni.getExecutorHandler().messenger(sender, "commands.potion.usage");
      return;
    }

    ItemStack itemInHand = player.getItemInHand();
    if (itemInHand == null) {
      omni.getExecutorHandler().messenger(sender, "commands.potion.item-is-empty");
      return;
    }

    if (!XMaterial.matchXMaterial(itemInHand.getType()).name().contains("POTION")) {
      omni.getExecutorHandler().messenger(sender, "commands.potion.need-be-potion-type");
      return;
    }

    PotionEffectType type = PotionEffectType.getByName(effectType);
    if (type == null) {
      omni.getExecutorHandler().messenger(sender, "commands.potion.effect-type-not-found");
      return;
    }

    if (duration == 0 || amplifier == 0) {
      omni.getExecutorHandler().messenger(sender, "commands.potion.valid-level");
      return;
    }

    PotionMeta potionMeta = (PotionMeta) itemInHand.getItemMeta();
    potionMeta.addCustomEffect(new PotionEffect(type, duration, amplifier), true);

    itemInHand.setItemMeta(potionMeta);

    omni.getExecutorHandler().messenger(sender, "commands.potion.success");
  }
}
