package com.vitu.omni;

import com.google.common.collect.Sets;
import com.vitu.omni.configuration.ConfigurationLoader;
import com.vitu.omni.configuration.ConfigurationProvider;
import com.vitu.omni.database.SqlConnection;
import com.vitu.omni.database.connection.SqliteConnection;
import com.vitu.omni.database.storage.cache.CacheManager;
import com.vitu.omni.executor.ExecutorHandler;
import com.vitu.omni.manager.UserManager;
import com.vitu.omni.module.Module;
import com.vitu.omni.module.warp.WarpModule;
import com.vitu.omni.repository.UserRepository;
import lombok.Getter;
import lombok.SneakyThrows;
import me.saiintbrisson.bukkit.command.BukkitFrame;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

@Getter
public final class Omni extends JavaPlugin {

  private ConfigurationProvider configurationProvider;
  private ConfigurationLoader configurationLoader;

  private ExecutorHandler executorHandler;

  private SqlConnection sqlConnection;

  private CacheManager cacheManager;

  private UserManager userManager;
  private UserRepository userRepository;

  private BukkitFrame bukkitFrame;

  private Set<Module> modules;

  @Override
  public void onLoad() {
    modules = Sets.newHashSet(
      new WarpModule()
    );
  }

  @SneakyThrows
  @Override
  public void onEnable() {
    configurationProvider = new ConfigurationProvider();
    configurationLoader = new ConfigurationLoader(this);

    executorHandler = new ExecutorHandler(this);

    sqlConnection = new SqliteConnection(getDataFolder(), "omni");
    sqlConnection.connect();

    cacheManager = new CacheManager();

    userManager = new UserManager();
    userRepository = new UserRepository(sqlConnection);

    configurationLoader.generateDefaultStructure();
    configurationLoader.setupMessages();

    cacheManager.addCache(userRepository.getCache());

    modules.forEach(Module::onEnable);

    commands();
  }

  @Override
  public void onDisable() {
    modules.forEach(Module::onDisable);

    cacheManager.stop();

    if (sqlConnection != null && sqlConnection.hasConnection()) {
      sqlConnection.disconnect();
    }
  }

  public void commands(Object... commands) {
    if (bukkitFrame == null) {
      bukkitFrame = new BukkitFrame(this);
    }

    bukkitFrame.registerCommands(commands);
  }

  public void listeners(Listener... listeners) {
    PluginManager pluginManager = Bukkit.getPluginManager();

    for (Listener listener : listeners) {
      pluginManager.registerEvents(listener, this);
    }
  }

  public FileConfiguration defaultLangConfig() {
    return configurationProvider.getConfiguration(getConfig().getString("language", "pt"));
  }

  public static Omni inst() {
    return getPlugin(Omni.class);
  }
}
