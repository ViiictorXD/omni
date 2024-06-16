package com.vitu.omni.database;

import com.vitu.omni.yaml.YamlFile;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class SqlProperty {

  private final Map<String, String> cachedProperties;

  public static SqlProperty of(YamlFile file) {
    return new SqlProperty(new HashMap<String, String>() {{
      put("host", file.getString("database.properties.host"));
      put("database", file.getString("database.properties.database"));
      put("username", file.getString("database.properties.username"));
      put("password", file.getString("database.properties.password"));
      put("port", file.getString("database.properties.port"));
    }});
  }

  public String getProperty(String key) {
    return cachedProperties.get(key);
  }

  public void addProperty(String key, String property) {
    cachedProperties.put(key, property);
  }
}
