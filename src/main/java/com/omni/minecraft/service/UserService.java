package com.omni.minecraft.service;

import com.omni.minecraft.Omni;
import com.omni.minecraft.api.user.User;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.Iterator;

@AllArgsConstructor
public class UserService {

  private final Omni omni;

  public void init() {
    for (Player player : Bukkit.getOnlinePlayers()) {

    }
  }

  public void join(Player player) throws SQLException {
    User user = omni.getUserRepository().findOrCreate(player.getUniqueId());

    user.setName(player.getName());

    join(user);
  }

  public void quit(Player player) {
    Iterator<User> i = logged.iterator();

    while (i.hasNext()) {
      User user = i.next();

      if (user.getUuid().equals(player.getUniqueId())) {
        user.setBase(null);
        quit(user);
        i.remove();
        break;
      }

    }
  }

  private void join(User user) {
    user.addReference();
  }

  private void quit(User user) {
    user.removeReference();
  }

}
