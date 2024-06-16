package com.omni.minecraft.model;

import com.omni.minecraft.Omni;
import lombok.Getter;
import org.bukkit.GameMode;

import java.util.Arrays;

@Getter
public enum Mode {

  SURVIVAL(GameMode.SURVIVAL, "survival", "sobrevivencia", "s", "0"),
  CREATIVE(GameMode.CREATIVE, "creative", "criativo", "c", "1"),
  ADVENTURE(GameMode.ADVENTURE, "adventure", "aventura", "a", "2"),
  SPECTATOR(GameMode.SPECTATOR, "spectator", "espectador", "3");

  private static final Omni OMNI = Omni.inst();

  private final GameMode gameMode;
  private final String[] alias;

  Mode(GameMode gameMode, String... alias) {
    this.gameMode = gameMode;
    this.alias = alias;
  }

  public String beautyName() {
    return OMNI.defaultLangConfig()
      .getString("gm-names." + name().toLowerCase(), name().toLowerCase());
  }

  public static Mode of(String name) {
    return Arrays.stream(values())
      .filter(mode -> mode.name().equalsIgnoreCase(name))
      .findAny()
      .orElse(null);
  }
}
