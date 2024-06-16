package com.omni.minecraft.module.kit.event;

import com.omni.minecraft.event.AbstractEvent;
import com.omni.minecraft.module.kit.data.Kit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
@AllArgsConstructor
public class PlayerPickupKitEvent extends AbstractEvent {

  private final Player player;
  private final Kit kit;

  private final boolean bypassDelay;
}
