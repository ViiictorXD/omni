package com.omni.minecraft.module.warp.manager;

import com.omni.minecraft.module.warp.data.Warp;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class WarpManager {

  private final List<Warp> cachedWarps
    = new ArrayList<>();

  public Warp getWarp(String name) {
    return cachedWarps.stream()
      .filter(warp -> warp.getName().equalsIgnoreCase(name))
      .findAny()
      .orElse(null);
  }

  public void register(Warp warp) {
    cachedWarps.add(warp);
  }

  public void unregister(Warp warp) {
    cachedWarps.remove(warp);
  }

  public boolean exists(String warpName) {
    return cachedWarps.stream()
      .anyMatch(warp -> warp.getName().equalsIgnoreCase(warpName));
  }

  public int size() {
    return cachedWarps.size();
  }
}
