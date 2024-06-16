package com.vitu.omni.configuration;

import com.vitu.omni.Omni;
import lombok.AllArgsConstructor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

@AllArgsConstructor
public class ConfigurationLoader {

  private final Omni omni;

  public void generateDefaultStructure() {
    if (omni.getDataFolder().exists()) {
      return;
    }

    omni.saveDefaultConfig();

    omni.saveResource("messages/pt.yml", false);
    omni.saveResource("messages/en.yml", false);
  }

  public void setupMessages() {
    File[] files = new File(omni.getDataFolder(), "messages").listFiles();

    if (files == null || files.length == 0) {
      omni.getLogger().warning("Nenhum arquivo de mensagem foi carregado.");
      return;
    }

    for (File file : files) {
      if (file.isDirectory()) {
        continue;
      }

      String id = file.getName().replaceFirst("[.][^.]+$", "");

      omni.getConfigurationProvider().register(
        id, YamlConfiguration.loadConfiguration(file)
      );
    }
  }
}
