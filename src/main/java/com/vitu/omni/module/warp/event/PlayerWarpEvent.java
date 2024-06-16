package com.vitu.omni.module.warp.event;

import com.vitu.omni.event.AbstractEvent;
import com.vitu.omni.module.warp.data.Warp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
@AllArgsConstructor
public class PlayerWarpEvent extends AbstractEvent {

  private final Player player;
  private final Warp warp;
}
