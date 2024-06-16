package com.omni.minecraft;

import org.bukkit.ChatColor;

public class Helper {

  public static String color(String context) {
    return ChatColor.translateAlternateColorCodes('&', context);
  }
}
