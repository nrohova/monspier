package com.management.money.monspier.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class ErrorState {

  private final Map<String, Collection<String>> errors = new HashMap<>();

  public boolean hasErrors() {
    return !this.errors.isEmpty();
  }

  public Map<String, Collection<String>> getErrors() {
    return this.errors;
  }

  public void addError(String message) {
    addError("__ERROR__", message);
  }

  /**
   * Adds a field error message.
   */
  public void addError(String field, String message) {
    this.errors.computeIfAbsent(field, key -> new ArrayList<>()).add(message);
  }
}