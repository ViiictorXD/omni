package com.vitu.omni.api.event.user;

import com.vitu.omni.api.user.User;
import com.vitu.omni.event.AbstractEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserNickChangeEvent extends AbstractEvent {

  private final User user;
  private final String oldNick;

}
