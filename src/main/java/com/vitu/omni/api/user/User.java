package com.vitu.omni.api.user;

import com.vitu.omni.database.storage.model.named.NamedEntity;
import lombok.*;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@RequiredArgsConstructor
public class User extends NamedEntity {

  private UUID uniqueId;
  private String name;

  private String nick;

  private Location lastLocation;

  private Temporally temporally;

  @Builder.Default
  private Instant firstSeen = Instant.now();

  @Builder.Default
  private Instant lastSeen = Instant.now();

  @Data
  @Builder
  @AllArgsConstructor
  public static class Temporally {

    private Player base;

    private Map<UUID, Long> tpaRequests;

    public boolean isOnline() {
      return base != null && base.isOnline();
    }
  }
}
