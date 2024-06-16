package com.vitu.omni.listener.container;

import com.vitu.omni.Constant;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerListener implements Listener {

  @EventHandler
  private void onDamage(EntityDamageEvent event) {
    if (event.getEntity().getType() == EntityType.PLAYER
      && Constant.GOD_MODE.contains(event.getEntity().getUniqueId())) {
      event.setCancelled(true);
    }
  }
}
