package com.dse.cf.data;

import java.io.Serializable;

public class SwitchData implements Serializable{
	private String networkId;
	private String switchId;

	public SwitchData() {
		// TODO Auto-generated constructor stub
	}
	
	public SwitchData(String switchId, String networkId) {
		this.switchId = switchId; 
		this.networkId = networkId;
	}

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	public String getSwitchId() {
		return switchId;
	}

	public void setSwitchId(String switchId) {
		this.switchId = switchId;
	}
}
