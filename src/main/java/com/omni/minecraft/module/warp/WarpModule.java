package com.omni.minecraft.module.warp;

import com.omni.minecraft.module.Module;
import com.omni.minecraft.module.warp.command.CommandWarp;
import com.omni.minecraft.module.warp.controller.WarpController;
import com.omni.minecraft.module.warp.listener.WarpListener;
import com.omni.minecraft.module.warp.manager.WarpManager;
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
