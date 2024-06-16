package com.vitu.omni.module;

import com.vitu.omni.Omni;
import com.vitu.omni.configuration.ConfigurationProvider;
import com.vitu.omni.executor.ExecutorHandler;
import lombok.Getter;
import org.bukkit.event.Listener;

import java.util.logging.Logger;

@Getter
public abstract class Module implements Strategy {

  protected final Omni omni;

  protected final Logger logger;

  protected final ConfigurationProvider configurationProvider;
  protected final ExecutorHandler executorHandler;

  public Module() {
    this.omni = Omni.inst();

    this.logger = omni.getLogger();

    this.configurationProvider = omni.getConfigurationProvider();
    this.executorHandler = omni.getExecutorHandler();
  }

  @Override
  public void onEnable() {
  }

  @Override
  public void onDisable() {
  }

  protected void commands(Object... commands) {
    omni.commands(commands);
  }

  protected void listeners(Listener... listeners) {
    omni.listeners(listeners);
  }
}
