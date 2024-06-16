package com.omni.minecraft.executor.messenger;

import com.omni.minecraft.executor.messenger.sender.Sender;
import com.omni.minecraft.executor.messenger.sender.impl.ActionSender;
import com.omni.minecraft.executor.messenger.sender.impl.ActionsSender;
import com.omni.minecraft.executor.messenger.sender.impl.BroadcastSender;
import com.omni.minecraft.executor.messenger.sender.impl.NoneSender;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Messenger {

  ACTION(new ActionSender()),
  ACTIONS(new ActionsSender()),
  TITLE(new NoneSender()), // TODO: Criar "TitleSender()"
  TITLES(new NoneSender()), // TODO: Criar "TitlesSender()"
  BROADCAST(new BroadcastSender()),
  NONE(new NoneSender());

  private final Sender sender;

  public String strip(String subject) {
    return subject.replace("[" + toString().toLowerCase() + "]", "")
      .replace("[" + toString().toLowerCase() + "] ", "");
  }

  public static Messenger of(String subject) {
    return Arrays.stream(values())
      .filter(type -> subject.toLowerCase().startsWith("[" + type.name().toLowerCase() + "]"))
      .findAny()
      .orElse(NONE);
  }
}
