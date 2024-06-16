package com.vitu.omni.executor;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ExecutorType {

  COMMAND(new String[] { "[command]" }),
  MESSAGE(new String[] { "[chat]", "[action]", "[actions]", "[title]", "[titles]", "[broadcast]" }),
  NONE(new String[0]);

  private final String[] identifiers;

  public String strip(String subject) {
    for (String identifier : identifiers) {
      subject = subject.replace(identifier, "");
    }
    return subject;
  }

  public static ExecutorType of(String subject) {
    String lowerCaseSubject = subject.toLowerCase();
    return Arrays.stream(values())
      .filter(type -> Arrays.stream(type.getIdentifiers()).anyMatch(lowerCaseSubject::startsWith))
      .findAny()
      .orElse(MESSAGE);
  }
}
