package com.omni.minecraft.module.warp.listener;

import com.omni.minecraft.module.warp.WarpModule;
import com.omni.minecraft.module.warp.data.Warp;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

@AllArgsConstructor
public class WarpListener implements Listener {

  private final WarpModule warpModule;

  @EventHandler
  private void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
    Player player = event.getPlayer();
    String message = event.getMessage();

    String arg0 = message.split(" ")[0];

    if (!arg0.startsWith("/")) {
      return;
    }

    for (Warp warp : warpModule.getWarpManager().getCachedWarps()) {
      if (!warp.isDirectCommand()) {
        continue;
      }

      if (!arg0.equalsIgnoreCase(String.format("/%s", warp.getName()))) {
        continue;
      }

      event.setCancelled(true);

      warpModule.getWarpController().teleport(player, warp);
    }
  }
}
