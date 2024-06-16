package com.vitu.omni;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;

@Getter
@AllArgsConstructor
public enum Version {

  V1_20_5(205, "v1.20.5"),
  V1_20_4(204, "v1.20.4"),
  V1_20_3(203, "v1.20.3"),
  V1_20_2(202, "v1.20.2"),
  V1_20_1(201, "v1.20.1"),
  V1_20(20, "v1.20"),
  V1_19(19, "v1.19"),
  V1_18(18, "v1.18"),
  V1_17(17, "v1.17"),
  V1_16(16, "v1.16"),
  V1_15(15, "v1.15"),
  V1_14(14, "v1.14"),
  V1_13(13, "v1.13"),
  V1_12(12, "v1.12"),
  V1_11(11, "v1.11"),
  V1_10(10, "v1.10"),
  V1_9(9, "v1.9"),
  V1_8(8, "v1.8"),
  UNKNOWN(-1, "Desconhecida");

  private int versionId;
  private String simpleName;

  public static Version getVersion() {
    return directFrom(Bukkit.getVersion());
  }

  public static Version directFrom(String packageName) {
    if (packageName.contains("1.25")) return V1_20_5;
    if (packageName.contains("1.24")) return V1_20_4;
    if (packageName.contains("1.23")) return V1_20_3;
    if (packageName.contains("1.22")) return V1_20_2;
    if (packageName.contains("1.21")) return V1_20_1;
    if (packageName.contains("1.20")) return V1_20;
    if (packageName.contains("1.19")) return V1_19;
    if (packageName.contains("1.18")) return V1_18;
    if (packageName.contains("1.17")) return V1_17;
    if (packageName.contains("1.16")) return V1_16;
    if (packageName.contains("1.15")) return V1_15;
    if (packageName.contains("1.14")) return V1_14;
    if (packageName.contains("1.13")) return V1_13;
    if (packageName.contains("1.12")) return V1_12;
    if (packageName.contains("1.11")) return V1_11;
    if (packageName.contains("1.10")) return V1_10;
    if (packageName.contains("1.9")) return V1_9;
    if (packageName.contains("1.8")) return V1_8;

    return UNKNOWN;
  }
}
