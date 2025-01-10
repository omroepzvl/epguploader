package com.zvl.model2;

public class Contact {
  Name2 name = new Name2();
  String email;
  String phonenumber = "-";
  String address = "-";
  
  public Name2 getName() {
    return name;
  }
  
  public void setName(Name2 name) {
    this.name = name;
  }
  
  public String getEmail() {
    return email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getPhonenumber() {
    return phonenumber;
  }
  
  public void setPhonenumber(String phonenumber) {
    this.phonenumber = phonenumber;
  }
  
  public String getAddress() {
    return address;
  }
  
  public void setAddress(String address) {
    this.address = address;
  }
}
