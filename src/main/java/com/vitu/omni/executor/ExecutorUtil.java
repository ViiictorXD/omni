package com.vitu.omni.executor;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ExecutorUtil {

  public static List<ExecutorContext> generateContexts(FileConfiguration configuration, String path) {
    if (configuration.contains(path)) {
      List<ExecutorContext> output = new ArrayList<>();

      Object object = configuration.get(path);
      if (object instanceof String) {
        String subject = String.valueOf(object);

        output.add(new ExecutorContext(ExecutorType.of(subject), subject));
      }

      if (object instanceof Collection) {
        List<String> contexts = (List<String>) object;

        for (String context : contexts) {
          output.add(new ExecutorContext(ExecutorType.of(context), context));
        }
      }

      return output;
    }
    return new ArrayList<>();
  }
}
