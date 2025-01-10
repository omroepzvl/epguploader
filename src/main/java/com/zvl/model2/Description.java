package com.zvl.model2;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

public class Description {
  @JacksonXmlProperty(isAttribute = true, localName="xml:lang")
  private String lang = "nl-NL";
  
  @JacksonXmlText
  private String value;
  
  public Description(String description) {
    this.setValue(description);
  }
  
  public String getLang() {
    return lang;
  }
  
  public void setLang(String lang) {
    this.lang = lang;
  }
  
  public String getValue() {
    return value;
  }
  
  public void setValue(String value) {
    this.value = value;
  }
}
