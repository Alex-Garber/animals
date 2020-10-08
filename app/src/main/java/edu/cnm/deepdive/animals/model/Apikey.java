package edu.cnm.deepdive.animals.model;

import com.google.gson.annotations.Expose;

public class Apikey {

@Expose
  private String message;
@Expose
  private String key;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }
}
