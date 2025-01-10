package com.zvl.model2;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

public class Title {
  @JacksonXmlProperty(isAttribute = true, localName="xml:lang")
  private String lang = "nl-NL";
  
  @JacksonXmlText
  private String value;
  
  public Title(String title) {
    this.setValue(title);
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
