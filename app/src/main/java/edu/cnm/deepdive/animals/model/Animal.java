package edu.cnm.deepdive.animals.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Animal {
@Expose
  private String name;
@Expose
  private Taxonomy taxonomy;
@Expose
  private String location;
@Expose
  private Speed speed;
@Expose
  private String diet;
@Expose
  private String lifespan;

  @SerializedName("image")
@Expose
  private String imageUrl;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Taxonomy getTaxonomy() {
    return taxonomy;
  }

  public void setTaxonomy(Taxonomy taxonomy) {
    this.taxonomy = taxonomy;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public Speed getSpeed() {
    return speed;
  }

  public void setSpeed(Speed speed) {
    this.speed = speed;
  }

  public String getDiet() {
    return diet;
  }

  public void setDiet(String diet) {
    this.diet = diet;
  }

  public String getLifespan() {
    return lifespan;
  }

  public void setLifespan(String lifespan) {
    this.lifespan = lifespan;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }
}
