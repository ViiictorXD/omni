package com.vitu.omni.module.warp.data;

import com.cryptomorin.xseries.XMaterial;
import com.vitu.omni.Helper;
import lombok.Builder;
import lombok.Data;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class Warp {

  private String name;

  private Location location;

  @Builder.Default
  private XMaterial material = XMaterial.DIRT;
  private String displayName;
  private List<String> lore;

  @Builder.Default
  private int slotPosition = 0;

  @Builder.Default
  private boolean requiredPermission = false;
  @Builder.Default
  private boolean directCommand = false;

  public ItemStack asItemStack() {
    ItemStack itemStack = material.parseItem();
    ItemMeta itemMeta = itemStack.getItemMeta();

    if (displayName != null && !displayName.isEmpty())
      itemMeta.setDisplayName(Helper.color(displayName));

    if (lore != null && !lore.isEmpty())
      itemMeta.setLore(lore.stream().map(Helper::color).collect(Collectors.toList()));

    itemStack.setItemMeta(itemMeta);

    return itemStack;
  }

  public void teleport(Entity entity) {
    if (location == null) {
      return;
    }

    entity.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
  }
}
