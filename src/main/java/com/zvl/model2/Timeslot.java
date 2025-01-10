package com.zvl.model2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Timeslot {
  
  @JacksonXmlProperty(isAttribute=true)
  String date;
  
  @JacksonXmlProperty(isAttribute=true)
  String type = "programme";
  
  @JacksonXmlProperty(isAttribute=true)
  String eventid;
  
  @JacksonXmlProperty(isAttribute=true)
  private String onairduration;
  
  @JsonIgnore
  public long durationMs;
  
  ProgrammeRef programmeref;
  
  public String getDate() {
    return date;
  }
  
  public void setDate(String date) {
    this.date = date;
  }
  
  public String getType() {
    return type;
  }
  
  public void setType(String type) {
    this.type = type;
  }
  
  public String getEventid() {
    return eventid;
  }
  
  public void setEventid(String eventid) {
    this.eventid = eventid;
  }
  
  public String getOnairduration() {
    return this.onairduration;
  }
  
  public void setOnairduration(String onairduration) {
    this.onairduration = onairduration;
  }
  
  public ProgrammeRef getProgrammeref() {
    return programmeref;
  }
  
  public void setProgrammeref(ProgrammeRef programmeref) {
    this.programmeref = programmeref;
  }
}
