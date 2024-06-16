package com.vitu.omni.module.kit.data;

import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
@Builder
public class KitUser {

  private UUID uniqueId;
  private String name;

  private Map<String, Long> kitDelay;
  private Map<String, Integer> kitPickup;
  private Map<String, Long> kitPickupDate;
}
