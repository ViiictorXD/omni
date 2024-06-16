package com.omni.minecraft.executor;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExecutorContext {

  private ExecutorType type;
  private String context;
}
