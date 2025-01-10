package com.zvl.model2;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Programme {
  @JacksonXmlProperty(isAttribute=true)
  String id;
  String imageUrl;
  
  TextElements textElements;
  
  @JacksonXmlProperty(localName="ParentalGuidance")
  ParentalGuidance parentalGuidance;
  
  public Programme(String id) {
    this.setId(id);
    this.setParentalGuidance(new ParentalGuidance());
  }
  
  public String getId() {
    return id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public TextElements getTextElements() {
    return textElements;
  }
  
  public void setTextElements(TextElements textElements) {
    this.textElements = textElements;
  }
  
  public String getImageUrl() {
    return imageUrl;
  }
  
  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }
  
  public ParentalGuidance getParentalGuidance() {
    return parentalGuidance;
  }
  
  public void setParentalGuidance(ParentalGuidance parentalGuidance) {
    this.parentalGuidance = parentalGuidance;
  }
}
