package com.vitu.omni;

import com.vitu.omni.model.Schedule;

import java.util.*;

public class Automator implements Runnable {

  public static final Set<Schedule> PERSISTENT = new HashSet<>();
  public static final Set<Schedule> QUEUE = new HashSet<>();

  private static final Omni OMNI = Omni.inst();

  @Override
  public void run() {
    Iterator<Schedule> iterator = QUEUE.iterator();
    while (iterator.hasNext()) {
      Schedule schedule = iterator.next();

      if (schedule.getCurrentCount() == schedule.getStartCount()
        && schedule.getStart() != null) {
        schedule.getStart().run();
      }
      if (schedule.getCurrentCount() <= 0) {
        if (schedule.getEnd() != null)
          schedule.getEnd().run();

        iterator.remove();
        continue;
      }

      if (schedule.getDuring() != null)
        schedule.getDuring().run();

      schedule.setCurrentCount(schedule.getCurrentCount() - 1);
    }
  }

  static {
    PERSISTENT.add(Schedule.of(() -> {
      /*for (Player player : Bukkit.getOnlinePlayers()) {
        User user = omni.getUserManagement().getUser(player.getUniqueId());
        if (user == null) continue;

        Map<UUID, Long> tpaRequests = user.getTemporally().getTpaRequests();

        for (Map.Entry<UUID, Long> entry : tpaRequests.entrySet()) {
          UUID key = entry.getKey();
          Long value = entry.getValue();

          Player target = Bukkit.getPlayer(key);
          if (target == null) {
            tpaRequests.remove(key);
            continue;
          }

          if (System.currentTimeMillis() >= value) {
            tpaRequests.remove(key);
          }
        }
      }*/
    }));
  }
}
