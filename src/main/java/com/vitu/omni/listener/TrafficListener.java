package com.vitu.omni.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TrafficListener implements Listener {

  @EventHandler
  private void onJoin(PlayerJoinEvent event) {}

  @EventHandler
  private void onQuit(PlayerQuitEvent event) {}
}
