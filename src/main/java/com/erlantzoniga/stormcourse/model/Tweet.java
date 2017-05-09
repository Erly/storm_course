package com.erlantzoniga.stormcourse.model;

import java.io.Serializable;
import java.util.UUID;

public class Tweet implements Serializable {
  private UUID id;
  private String text;
  private float latitude;
  private float longitude;

  public Tweet(UUID id, String text, float latitude, float longitude) {
    this.id = id;
    this.text = text;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public float getLatitude() {
    return latitude;
  }

  public void setLatitude(float latitude) {
    this.latitude = latitude;
  }

  public float getLongitude() {
    return longitude;
  }

  public void setLongitude(float longitude) {
    this.longitude = longitude;
  }
}
