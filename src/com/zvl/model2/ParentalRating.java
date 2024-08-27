package com.zvl.model2;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ParentalRating {
  @JacksonXmlProperty(isAttribute=true)
  String href;
  
  @JacksonXmlProperty(localName="Name")
  Name name;
  
  public ParentalRating() {
    this.setHref("NicamParentalRatingCS:2007");
    setName(new Name());
  }
  
  public String getHref() {
    return href;
  }
  
  public void setHref(String href) {
    this.href = href;
  }
  
  public Name getName() {
    return name;
  }
  
  public void setName(Name name) {
    this.name = name;
  }
}
