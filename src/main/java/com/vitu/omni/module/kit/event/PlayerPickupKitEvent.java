package com.vitu.omni.module.kit.event;

import com.vitu.omni.event.AbstractEvent;
import com.vitu.omni.module.kit.data.Kit;
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
