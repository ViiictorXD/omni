package com.omni.minecraft.module.kit;

import com.omni.minecraft.module.Module;
import com.omni.minecraft.module.kit.controller.KitController;
import com.omni.minecraft.module.kit.manager.KitManager;
import lombok.Getter;

@Getter
public class KitModule extends Module {

  private KitManager kitManager;
  private KitController kitController;
}
