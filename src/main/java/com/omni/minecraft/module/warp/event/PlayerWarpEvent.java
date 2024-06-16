package com.omni.minecraft.module.warp.event;

import com.omni.minecraft.event.AbstractEvent;
import com.omni.minecraft.module.warp.data.Warp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
@AllArgsConstructor
public class PlayerWarpEvent extends AbstractEvent {

  private final Player player;
  private final Warp warp;
}
