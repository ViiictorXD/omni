package com.omni.minecraft.api.event.user;

import com.omni.minecraft.api.user.User;
import com.omni.minecraft.event.AbstractEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserNickChangeEvent extends AbstractEvent {

  private final User user;
  private final String oldNick;

}
