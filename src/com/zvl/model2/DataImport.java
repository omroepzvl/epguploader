package com.zvl.model2;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class DataImport {
  Header header;
  Schedule schedule;
  
  @JacksonXmlElementWrapper(localName = "programmelist")
  List<Programme> programme;
  
  @JacksonXmlProperty(isAttribute=true)
  String dtdversion = "1.0";
  
  public DataImport(String channelName) {
    this.setHeader(new Header(channelName));
  }
  
  public Header getHeader() {
    return header;
  }
  
  public void setHeader(Header header) {
    this.header = header;
  }
  
  public Schedule getSchedule() {
    return schedule;
  }
  
  public void setSchedule(Schedule schedule) {
    this.schedule = schedule;
  }
  
  public List<Programme> getProgramme() {
    return programme;
  }
  
  public void setProgramme(List<Programme> programme) {
    this.programme = programme;
  }
}
