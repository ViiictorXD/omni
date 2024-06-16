package com.omni.minecraft.configuration;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigurationProvider {

  private final Map<String, FileConfiguration> cachedConfigurations
    = new ConcurrentHashMap<>();

  public FileConfiguration getConfiguration(String id) {
    return cachedConfigurations.get(id);
  }

  public void register(String id, FileConfiguration configuration) {
    cachedConfigurations.put(id, configuration);
  }

  public void unregister(String id) {
    cachedConfigurations.remove(id);
  }

}