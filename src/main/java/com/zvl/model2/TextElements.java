package com.zvl.model2;

public class TextElements {
  Title title;
  Description description;
  
  public TextElements(String title, String description) {
    this.title = new Title(title);
    this.description = new Description(description);
  }
  
  public Title getTitle() {
    return title;
  }
  
  public void setTitle(Title title) {
    this.title = title;
  }
  
  public Description getDescription() {
    return description;
  }
  
  public void setDescription(Description description) {
    this.description = description;
  }
}
