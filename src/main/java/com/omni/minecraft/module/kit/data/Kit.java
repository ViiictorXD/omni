package com.omni.minecraft.module.kit.data;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class Kit {

  private String name;

  private boolean permission;
  private boolean pickupOne;
  private boolean preventFullInventory;

  private String content;
  private String armorContent;

  private String icon;

  private long delay;

  private int maxPickups;

  @Builder.Default
  private Instant createAt = Instant.now();

  @Builder.Default
  private Instant modifyAt = Instant.now();
}
