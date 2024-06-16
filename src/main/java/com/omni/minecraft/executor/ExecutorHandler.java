package com.omni.minecraft.executor;

import com.omni.minecraft.Helper;
import com.omni.minecraft.Omni;
import com.omni.minecraft.executor.messenger.Messenger;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.text.MessageFormat;
import java.util.List;

@AllArgsConstructor
public class ExecutorHandler {

  private final Omni omni;

  public void run(CommandSender sender, String id, String path, Object... objects) {
    FileConfiguration configuration = omni.getConfigurationProvider().getConfiguration(id);
    if (configuration == null) {
      return;
    }

    List<ExecutorContext> contexts = ExecutorUtil.generateContexts(configuration, path);
    if (contexts.isEmpty()) {
      return;
    }

    for (ExecutorContext context : contexts) {
      ExecutorType type = context.getType();
      String subject = context.getContext();

      if (objects != null && objects.length > 0) {
        subject = MessageFormat.format(subject, objects);
      }

      if (type == ExecutorType.COMMAND) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), type.strip(subject));
        continue;
      }

      Messenger messenger = Messenger.of(subject);
      messenger.getSender().send(sender, Helper.color(messenger.strip(subject)));
    }
  }

  public String from(String id, String path, Object... objects) {
    FileConfiguration configuration = omni.getConfigurationProvider().getConfiguration(id);
    if (configuration == null) {
      return null;
    }

    if (configuration.contains(path)) {
      if (objects != null && objects.length > 0) {
        return Helper.color(MessageFormat.format(configuration.getString(path), objects));
      }
      return Helper.color(configuration.getString(path));
    }
    return null;
  }

  public void messenger(CommandSender sender, String path, Object... objects) {
    run(sender, omni.getConfig().getString("language", "pt"), path, objects);
  }

  public String messenger(String path, Object... objects) {
    return from(omni.getConfig().getString("language", "pt"), path, objects);
  }
}
