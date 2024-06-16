package com.omni.minecraft.command;

import lombok.AllArgsConstructor;
import me.saiintbrisson.bukkit.command.command.BukkitContext;
import me.saiintbrisson.minecraft.command.annotation.Command;
import me.saiintbrisson.minecraft.command.annotation.Optional;
import me.saiintbrisson.minecraft.command.command.Context;
import com.omni.minecraft.Omni;
import net.deslocher.omni.util.ColorUtil;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class CommandEditItem {

  private final Omni omni;

  @Command(
    name = "edititem",
    aliases = {"editaritem"},
    permission = "omni.commands.edititem"
  )
  public void handler(BukkitContext context) {
    CommandSender sender = context.getSender();

    if (!(sender instanceof Player)) {
      omni.getExecutorHandler().messenger(sender, "commands.only-player");
      return;
    }

    omni.getExecutorHandler().messenger(sender, "commands.edit-item.usage");
  }

  @Command(
    name = "edititem.name",
    aliases = {"nome"},
    permission = "omni.commands.edititem.name"
  )
  public void handlerName(Context<Player> context, @Optional String[] newName) {
    Player sender = context.getSender();

    ItemStack itemInHand = sender.getItemInHand();
    if (itemInHand == null || itemInHand.getType() == Material.AIR) {
      omni.getExecutorHandler().messenger(sender, "commands.edit-item.item-is-empty");
      return;
    }

    if (newName == null) {
      omni.getExecutorHandler().messenger(sender, "commands.edit-item.usage-name");
      return;
    }

    ItemMeta itemMeta = itemInHand.getItemMeta();
    itemMeta.setDisplayName(ColorUtil.color(String.join(" ", newName)));

    itemInHand.setItemMeta(itemMeta);

    omni.getExecutorHandler().messenger(sender, "commands.edit-item.success-new-name", String.join(" ", newName));
  }

  @Command(
    name = "edititem.amount",
    aliases = {"quantia"},
    permission = "omni.commands.edititem.amount"
  )
  public void handlerAmount(Context<Player> context, @Optional Integer newAmount) {
    Player sender = context.getSender();

    ItemStack itemInHand = sender.getItemInHand();
    if (itemInHand == null || itemInHand.getType() == Material.AIR) {
      omni.getExecutorHandler().messenger(sender, "commands.edit-item.item-is-empty");
      return;
    }

    if (newAmount == null) {
      omni.getExecutorHandler().messenger(sender, "commands.edit-item.usage-amount");
      return;
    }

    if (newAmount <= 0) {
      omni.getExecutorHandler().messenger(sender, "commands.edit-item.amount-fail");
      return;
    }

    itemInHand.setAmount(newAmount);

    omni.getExecutorHandler().messenger(sender, "commands.edit-item.success-new-amount", newAmount);
  }

  @Command(
    name = "edititem.addlore",
    aliases = {"adicionarlore"},
    permission = "omni.commands.edititem.addlore"
  )
  public void handlerAddLore(Context<Player> context, @Optional String[] newLine) {
    Player sender = context.getSender();

    ItemStack itemInHand = sender.getItemInHand();
    if (itemInHand == null || itemInHand.getType() == Material.AIR) {
      omni.getExecutorHandler().messenger(sender, "commands.edit-item.empty");
      return;
    }

    if (newLine == null) {
      omni.getExecutorHandler().messenger(sender, "commands.edit-item.usage-add-lore");
      return;
    }

    String addedLine = ColorUtil.color(String.join(" ", newLine));

    ItemMeta itemMeta = itemInHand.getItemMeta();

    List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
    lore.add(addedLine);

    itemMeta.setLore(lore);
    itemInHand.setItemMeta(itemMeta);

    omni.getExecutorHandler().messenger(sender, "commands.edit-item.success-add-lore", addedLine);
  }

  @Command(
    name = "edititem.removelore",
    aliases = {"removerlore"},
    permission = "omni.commands.edititem.removelore"
  )
  public void handlerRemoveLore(Context<Player> context) {
    Player sender = context.getSender();

    ItemStack itemInHand = sender.getItemInHand();
    if (itemInHand == null || itemInHand.getType() == Material.AIR) {
      omni.getExecutorHandler().messenger(sender, "commands.edit-item.empty");
      return;
    }

    ItemMeta itemMeta = itemInHand.getItemMeta();
    itemMeta.setLore(new ArrayList<>());

    itemInHand.setItemMeta(itemMeta);

    omni.getExecutorHandler().messenger(sender, "commands.edit-item.success-remove-lore");
  }

  @Command(
    name = "edititem.addflag",
    aliases = {"adicionarflag"},
    permission = "omni.commands.edititem.addflag"
  )
  public void handlerAddFlag(Context<Player> context, @Optional String[] newFlags) {
    Player sender = context.getSender();

    ItemStack itemInHand = sender.getItemInHand();
    if (itemInHand == null || itemInHand.getType() == Material.AIR) {
      omni.getExecutorHandler().messenger(sender, "commands.edit-item.empty");
      return;
    }

    if (newFlags == null) {
      omni.getExecutorHandler().messenger(sender, "commands.edit-item.usage-add-flag");
      return;
    }

    List<ItemFlag> flags = new ArrayList<>();
    List<String> error = new ArrayList<>();

    for (String flagName : newFlags) {
      try {
        flags.add(ItemFlag.valueOf(flagName.toUpperCase()));
      } catch (Exception ignored) {
        error.add(flagName);
      }
    }

    if (flags.isEmpty()) {
      omni.getExecutorHandler().messenger(sender, "commands.edit-item.add-flag-fail-1");
      return;
    }

    ItemMeta itemMeta = itemInHand.getItemMeta();
    itemMeta.addItemFlags(flags.toArray(new ItemFlag[0]));
    itemInHand.setItemMeta(itemMeta);

    omni.getExecutorHandler().messenger(sender, "commands.edit-item.success-add-flag", String.join(", ", newFlags));

    if (!error.isEmpty()) {
      omni.getExecutorHandler().messenger(sender, "commands.edit-item.add-flag-fail-2", String.join(", ", error.toArray(new String[0])));
    }
  }

  @Command(
    name = "edititem.removeflag",
    aliases = {"removerflag"},
    permission = "omni.commands.edititem.removeflag"
  )
  public void handlerRemoveFlag(Context<Player> context) {
    Player sender = context.getSender();

    ItemStack itemInHand = sender.getItemInHand();
    if (itemInHand == null || itemInHand.getType() == Material.AIR) {
      omni.getExecutorHandler().messenger(sender, "commands.edit-item.empty");
      return;
    }

    ItemMeta itemMeta = itemInHand.getItemMeta();
    itemMeta.removeItemFlags(ItemFlag.values());

    itemInHand.setItemMeta(itemMeta);

    omni.getExecutorHandler().messenger(sender, "commands.edit-item.success-remove-flag");
  }
}
