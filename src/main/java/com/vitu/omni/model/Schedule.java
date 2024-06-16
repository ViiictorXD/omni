package com.vitu.omni.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Schedule {

  private Runnable start;
  private Runnable end;
  private Runnable during;

  private int startCount;
  private int currentCount;

  public static Schedule of(int startCount, int currentCount) {
    return Schedule.builder()
      .startCount(startCount)
      .currentCount(currentCount)
      .build();
  }

  public static Schedule of(Runnable start, Runnable during, Runnable end) {
    return Schedule.builder()
      .start(start)
      .during(during)
      .end(end)
      .build();
  }

  public static Schedule of(Runnable start, Runnable end) {
    return of(start, null, end);
  }

  public static Schedule of(Runnable during) {
    return of(null, during, null);
  }
}
