package com.zvl.model2;

public class Header {
  Provider provider;
  Created created;
  
  public Header(String channelName) {
    this.setCreated(new Created());
    
    Provider provider = new Provider();
    provider.setBdsid(channelName);
    provider.setValue(channelName);
    this.setProvider(provider);
  }
  
  public Provider getProvider() {
    return provider;
  }
  
  public void setProvider(Provider provider) {
    this.provider = provider;
  }
  
  public Created getCreated() {
    return created;
  }
  
  public void setCreated(Created created) {
    this.created = created;
  }
}
