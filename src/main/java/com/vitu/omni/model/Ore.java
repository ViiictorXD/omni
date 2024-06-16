package com.vitu.omni.model;

import com.cryptomorin.xseries.XMaterial;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Ore {

  COAL(XMaterial.COAL, XMaterial.COAL_ORE, XMaterial.COAL_BLOCK),
  IRON_INGOT(XMaterial.IRON_INGOT, XMaterial.IRON_ORE, XMaterial.IRON_BLOCK),
  GOLD_INGOT(XMaterial.GOLD_INGOT, XMaterial.GOLD_ORE, XMaterial.GOLD_BLOCK),
  GOLD_NUGGET(XMaterial.GOLD_NUGGET, XMaterial.GOLD_ORE, XMaterial.GOLD_BLOCK),
  DIAMOND(XMaterial.DIAMOND, XMaterial.DIAMOND_ORE, XMaterial.DIAMOND_BLOCK),
  EMERALD(XMaterial.EMERALD, XMaterial.EMERALD_ORE, XMaterial.EMERALD_BLOCK),
  REDSTONE(XMaterial.REDSTONE, XMaterial.REDSTONE_ORE, XMaterial.REDSTONE_BLOCK),
  COPPER(XMaterial.COPPER_INGOT, XMaterial.COPPER_ORE, XMaterial.COPPER_BLOCK),
  LAPIS(XMaterial.LAPIS_LAZULI, XMaterial.LAPIS_ORE, XMaterial.LAPIS_BLOCK);

  private final XMaterial material;
  private final XMaterial ore;
  private final XMaterial block;

  public static Ore simple(XMaterial material) {
    return Arrays.stream(values())
      .filter(ore -> ore.material == material)
      .findAny()
      .orElse(null);
  }

  public static Ore of(XMaterial material) {
    return Arrays.stream(values())
      .filter(ore -> ore.material == material || ore.ore == material || ore.block == material)
      .findAny()
      .orElse(null);
  }
}
