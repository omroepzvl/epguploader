package com.zvl.model2;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ProgrammeRef {
  
  @JacksonXmlProperty(isAttribute=true)
  String idref;
  
  public String getIdref() {
    return idref;
  }
  
  public void setIdref(String idref) {
    this.idref = idref;
  }
}
