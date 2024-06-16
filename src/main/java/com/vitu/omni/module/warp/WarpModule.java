package com.vitu.omni.module.warp;

import com.vitu.omni.module.Module;
import com.vitu.omni.module.warp.command.CommandWarp;
import com.vitu.omni.module.warp.controller.WarpController;
import com.vitu.omni.module.warp.listener.WarpListener;
import com.vitu.omni.module.warp.manager.WarpManager;
import lombok.Getter;

@Getter
public class WarpModule extends Module {

  private WarpManager warpManager;
  private WarpController warpController;

  /*
    TO-DO LIST
    - Gerenciar os warps pelo gui
    - Efeito bonito pra teleportar
   */

  @Override
  public void onEnable() {
    warpManager = new WarpManager();
    warpController = new WarpController(this);

    warpController.loadAll();

    commands(new CommandWarp(this));
    listeners(new WarpListener(this));

    omni.getLogger().info("Modulo 'Warp' carregado.");
  }
}
