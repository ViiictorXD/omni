package com.vitu.omni.module.kit.manager;

import com.vitu.omni.module.kit.data.Kit;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class KitManager {

  private final List<Kit> cachedKits
    = new ArrayList<>();

  public Kit getKit(String name) {
    return cachedKits.stream()
      .filter(kit -> kit.getName().equalsIgnoreCase(name))
      .findAny()
      .orElse(null);
  }

  public void register(Kit kit) {
    cachedKits.add(kit);
  }

  public void unregister(Kit kit) {
    cachedKits.remove(kit);
  }

  public boolean exists(String name) {
    return cachedKits.stream()
      .anyMatch(kit -> kit.getName().equalsIgnoreCase(name));
  }

  public int size() {
    return cachedKits.size();
  }
}
