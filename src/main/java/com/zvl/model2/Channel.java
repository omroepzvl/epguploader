package com.zvl.model2;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class Channel {
  
  @JacksonXmlProperty(isAttribute=true)
  String periodstart;
  
  @JacksonXmlProperty(isAttribute=true)
  String periodend;
  
  ChannelInfo channelinfo;
  
  @JacksonXmlElementWrapper(localName = "timeslots")
  List<Timeslot> timeslot;
  
  public Channel(String channelName, String contactEmail) {
    this.channelinfo = new ChannelInfo(channelName, contactEmail);
  }
  
  public String getPeriodstart() {
    return periodstart;
  }
  
  public void setPeriodstart(String periodstart) {
    this.periodstart = periodstart;
  }
  
  public String getPeriodend() {
    return periodend;
  }
  
  public void setPeriodend(String periodend) {
    this.periodend = periodend;
  }
  
  public ChannelInfo getChannelinfo() {
    return channelinfo;
  }
  
  public void setChannelinfo(ChannelInfo channelInfo) {
    this.channelinfo = channelInfo;
  }
  
  public List<Timeslot> getTimeslot() {
    return timeslot;
  }
  
  public void setTimeslot(List<Timeslot> timeslot) {
    this.timeslot = timeslot;
  }
}
