package com.vitu.omni.module.warp.controller;

import com.cryptomorin.xseries.XMaterial;
import com.vitu.omni.Automator;
import com.vitu.omni.model.Schedule;
import com.vitu.omni.module.warp.WarpModule;
import com.vitu.omni.module.warp.data.Warp;
import com.vitu.omni.yaml.YamlFile;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;

@AllArgsConstructor
public class WarpController {

  private final WarpModule warpModule;

  public void teleport(Player player, Warp warp) {
    if (!player.hasPermission("omni.warps." + warp.getName())) {
      warpModule.getExecutorHandler().messenger(player, "commands.warp.no-permission", warp.getName());
      return;
    }

    if (player.isOp() || player.hasPermission("omni.warp.bypass")) {
      warp.teleport(player);

      warpModule.getExecutorHandler().messenger(player, "commands.warp.success", warp.getName());
      return;
    }

    Schedule schedule = Schedule.of(3, 3);
    schedule.setDuring(() -> warpModule.getExecutorHandler().messenger(player, "commands.warp.waiting", schedule.getCurrentCount()));
    schedule.setEnd(() -> {
      warpModule.getExecutorHandler().messenger(player, "commands.warp.success", warp.getName());
      warp.teleport(player);
    });

    Automator.QUEUE.add(schedule);
  }

  public void loadAll() {
    File[] files = new File(warpModule.getOmni().getDataFolder(), "warps").listFiles();

    if (files == null || files.length == 0) {
      warpModule.getLogger().warning("Nenhum warp foi carregado.");
      return;
    }

    for (File warpFile : files) {
      if (warpFile.isDirectory() || !warpFile.getName().endsWith(".yml")) {
        continue;
      }

      warpModule.getWarpManager().register(of(warpFile));
    }

    warpModule.getLogger().info("Foram carregado(s) " + warpModule.getWarpManager().size() + " warp(s).");
  }

  public void createWarp(Warp warp) {
    YamlFile yamlFile = new YamlFile(warpModule.getOmni(), String.format("warps/%s", warp.getName().toLowerCase()));
    yamlFile.set("name", warp.getName());

    yamlFile.set("location.world-name", warp.getLocation().getWorld().getName());
    yamlFile.set("location.x", warp.getLocation().getX());
    yamlFile.set("location.y", warp.getLocation().getY());
    yamlFile.set("location.z", warp.getLocation().getZ());
    yamlFile.set("location.yaw", warp.getLocation().getYaw());
    yamlFile.set("location.pitch", warp.getLocation().getPitch());

    yamlFile.set("icon.material", warp.getMaterial().name());
    yamlFile.set("icon.lore", warp.getLore());

    yamlFile.set("slot", warp.getSlotPosition());

    yamlFile.set("required-permission", warp.isRequiredPermission());
    yamlFile.set("direct-command", warp.isDirectCommand());

    yamlFile.save();
  }

  @SneakyThrows
  public void deleteWarp(Warp warp) {
    File file = new File(warpModule.getOmni().getDataFolder(), "warps/" + warp.getName().toLowerCase());

    if (!file.exists()) {
      return;
    }

    warpModule.getWarpManager().unregister(warp);

    FileUtils.forceDelete(file);
  }

  private Warp of(File file) {
    FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

    String name = configuration.getString("name");

    Location location = new Location(
      Bukkit.getWorld(configuration.getString("location.world-name")),
      configuration.getDouble("location.x"),
      configuration.getDouble("location.y"),
      configuration.getDouble("location.z"),
      (float) configuration.getDouble("location.yaw"),
      (float) configuration.getDouble("location.pitch")
    );

    XMaterial material = XMaterial.matchXMaterial(configuration.getString("icon.material"))
      .orElse(XMaterial.DIRT);
    String displayName = configuration.getString("icon.display-name", "");
    List<String> lore = configuration.getStringList("icon.lore");

    int slotPosition = configuration.getInt("slot");

    boolean requiredPermission = configuration.getBoolean("required-permission", false);
    boolean directCommand = configuration.getBoolean("direct-command", false);

    return Warp.builder()
      .name(name)
      .location(location)
      .material(material)
      .displayName(displayName)
      .lore(lore)
      .slotPosition(slotPosition)
      .requiredPermission(requiredPermission)
      .directCommand(directCommand)
      .build();
  }
}
