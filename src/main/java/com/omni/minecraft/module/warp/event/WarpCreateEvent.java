package com.omni.minecraft.module.warp.event;

import com.omni.minecraft.event.AbstractEvent;
import com.omni.minecraft.module.warp.data.Warp;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WarpCreateEvent extends AbstractEvent {

  private final Warp warp;
}
