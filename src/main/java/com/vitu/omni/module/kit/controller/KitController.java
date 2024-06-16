package com.vitu.omni.module.kit.controller;

import com.vitu.omni.module.kit.KitModule;
import com.vitu.omni.module.kit.data.Kit;
import com.vitu.omni.yaml.YamlFile;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.time.Instant;

@AllArgsConstructor
public class KitController {

  private final KitModule kitModule;

  public void giveKit(Player player, Kit kit, boolean bypassDelay) {

  }

  public void loadAll() {
    File[] files = new File(kitModule.getOmni().getDataFolder(), "kits").listFiles();

    if (files == null || files.length == 0) {
      kitModule.getLogger().warning("Nenhum kit foi carregado.");
      return;
    }

    for (File kitFile : files) {
      if (kitFile.isDirectory() || !kitFile.getName().endsWith(".yml")) {
        continue;
      }

      kitModule.getKitManager().register(of(kitFile));
    }

    kitModule.getLogger().info("Foram carregado(s) " + kitModule.getKitManager().size() + " kit(s).");
  }

  public void createKit(Kit kit) {
    YamlFile yamlFile = new YamlFile(kitModule.getOmni(), "kits/" + kit.getName().toLowerCase());
    yamlFile.set("name", kit.getName());

    yamlFile.set("settings.permission", kit.isPermission());
    yamlFile.set("settings.pickup-one", kit.isPickupOne());
    yamlFile.set("settings.prevent-full-inventory", kit.isPreventFullInventory());

    yamlFile.set("items.content", kit.getContent());
    yamlFile.set("items.armor-content", kit.getArmorContent());

    yamlFile.set("icon", kit.getIcon());

    yamlFile.set("delay", (kit.getDelay() / 1000));

    yamlFile.set("settings.max-pickups", kit.getMaxPickups());

    yamlFile.set("date.create-at", kit.getCreateAt().toEpochMilli());
    yamlFile.set("date.modify-at", kit.getModifyAt().toEpochMilli());

    yamlFile.save();
  }

  @SneakyThrows
  public void deleteKit(Kit kit) {
    File file = new File(kitModule.getOmni().getDataFolder(), "kits/" + kit.getName().toLowerCase());

    if (!file.exists()) {
      return;
    }

    kitModule.getKitManager().unregister(kit);

    FileUtils.forceDelete(file);
  }

  private Kit of(File file) {
    FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

    String name = configuration.getString("name");

    boolean permission = configuration.getBoolean("settings.permission", true);
    boolean pickupOne = configuration.getBoolean("settings.pickup-one", false);
    boolean preventFullInventory = configuration.getBoolean("settings.prevent-full-inventory", true);

    String content = configuration.getString("items.content");
    String armorContent = configuration.getString("items.armor-content");

    String icon = configuration.getString("icon");

    long delay = configuration.getInt("delay") * 1000L;

    int maxPickups = configuration.getInt("settings.max-pickups");

    Instant createAt = Instant.ofEpochMilli(configuration.getLong("date.create-at", 0L));
    Instant modifyAt = Instant.ofEpochMilli(configuration.getLong("date.create-at", 0L));

    return Kit.builder()
      .name(name)
      .permission(permission)
      .pickupOne(pickupOne)
      .preventFullInventory(preventFullInventory)
      .content(content)
      .armorContent(armorContent)
      .icon(icon)
      .delay(delay)
      .maxPickups(maxPickups)
      .createAt(createAt)
      .modifyAt(modifyAt)
      .build();
  }
}
