package com.example.reactive.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * @author Octavian Krody, octavian.krody@sap.com
 * @since 10/07/2018
 */
public class User {
  @JsonProperty("name")
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
