package com.zvl.model2;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ParentalGuidance {
  @JacksonXmlProperty(localName="ParentalRating")
  ParentalRating parentalRating = new ParentalRating();
  
  public ParentalRating getParentalRating() {
    return parentalRating;
  }
  
  public void setParentalRating(ParentalRating parentalRating) {
    this.parentalRating = parentalRating;
  }
}
