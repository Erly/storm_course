package com.erlantzoniga.stormcourse.model;

import java.io.Serializable;

public class Tweet implements Serializable {
  private String text;
  private float latitude;
  private float longitude;

  public Tweet(String text, float latitude, float longitude) {
    this.text = text;
    this.latitude = latitude;
    this.longitude = longitude;
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
