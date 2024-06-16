package com.vitu.omni.module.kit;

import com.vitu.omni.module.Module;
import com.vitu.omni.module.kit.controller.KitController;
import com.vitu.omni.module.kit.manager.KitManager;
import lombok.Getter;

@Getter
public class KitModule extends Module {

  private KitManager kitManager;
  private KitController kitController;
}
