package com.vitu.omni.module.warp.event;

import com.vitu.omni.event.AbstractEvent;
import com.vitu.omni.module.warp.data.Warp;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WarpDeleteEvent extends AbstractEvent {

  private final Warp warp;
}
