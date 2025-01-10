package com.zvl.model2;

public class ChannelInfo {
  ChannelId channelid;
  String channelname;
  ContactDetails contactdetails;
  
  public ChannelInfo(String channelName, String email) {
    this.channelid = new ChannelId();
    channelid.setBdsid(channelName);
    
    ContactDetails cd = new ContactDetails();
    Contact contact = new Contact();
    contact.setEmail(email);
    cd.setContact(contact);
    this.setChannelname(channelName);
    this.setContactdetails(cd);
  }
  
  public ChannelId getChannelid() {
    return channelid;
  }
  
  public void setChannelid(ChannelId channelid) {
    this.channelid = channelid;
  }
  
  public String getChannelname() {
    return channelname;
  }
  
  public void setChannelname(String channelname) {
    this.channelname = channelname;
  }
  
  public ContactDetails getContactdetails() {
    return contactdetails;
  }
  
  public void setContactdetails(ContactDetails contactdetails) {
    this.contactdetails = contactdetails;
  }
}
