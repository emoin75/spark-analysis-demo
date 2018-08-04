package com.customer.data.model;

import java.io.Serializable;

public class CustomerData implements Serializable{

	private static final long serialVersionUID = -711435770380326723L;
	
	private String sessionId;
	
	private String phoneNumber;
	
	private String usageInSessionMB;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUsageInSessionMB() {
		return usageInSessionMB;
	}

	public void setUsageInSessionMB(String usageInSessionMB) {
		this.usageInSessionMB = usageInSessionMB;
	}

}
