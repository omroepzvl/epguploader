package com.zvl.model;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
  String id;
  String name;
  String category_id;
  String start_time;
  String end_time;
  List<Item> items = new ArrayList<>();
  
  public String getId() {
    return id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getCategory_id() {
    return category_id;
  }
  
  public void setCategory_id(String category_id) {
    this.category_id = category_id;
  }
  
  public String getStart_time() {
    return start_time;
  }
  
  public void setStart_time(String start_time) {
    this.start_time = start_time;
  }
  
  public String getEnd_time() {
    return end_time;
  }
  
  public void setEnd_time(String end_time) {
    this.end_time = end_time;
  }
  
  public List<Item> getItems() {
    return items;
  }
  
  public void setItems(List<Item> items) {
    this.items = items;
  }
}
