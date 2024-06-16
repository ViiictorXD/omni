package com.vitu.omni.manager;

import com.vitu.omni.api.user.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class UserManager {

  private final List<User> cachedUsers
    = new ArrayList<>();

  public User getUser(String name) {
    return cachedUsers.stream()
      .filter(user -> user.getName().equalsIgnoreCase(name))
      .findAny()
      .orElse(null);
  }

  public User getUser(UUID uniqueId) {
    return cachedUsers.stream()
      .filter(user -> user.getUniqueId().equals(uniqueId))
      .findAny()
      .orElse(null);
  }

  public List<User> getOnlineUsers() {
    return cachedUsers.stream()
      .filter(user -> user.getTemporally().isOnline())
      .collect(Collectors.toList());
  }
}
