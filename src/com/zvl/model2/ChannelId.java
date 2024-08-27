package com.zvl.model2;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ChannelId {
  @JacksonXmlProperty(isAttribute=true)
  String bdsid;
  
  public String getBdsid() {
    return bdsid;
  }
  
  public void setBdsid(String bdsid) {
    this.bdsid = bdsid;
  }
}
