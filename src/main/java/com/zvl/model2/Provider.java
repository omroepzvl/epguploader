package com.zvl.model2;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

public class Provider {
  @JacksonXmlProperty(isAttribute=true)
  String bdsid;
  
  @JacksonXmlText
  String value;
  
  public String getBdsid() {
    return bdsid;
  }
  
  public void setBdsid(String bdsid) {
    this.bdsid = bdsid;
  }
  
  public String getValue() {
    return value;
  }
  
  public void setValue(String value) {
    this.value = value;
  }
}
