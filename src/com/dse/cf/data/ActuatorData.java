package com.dse.cf.data;

import java.io.Serializable;
import java.util.List;


public class ActuatorData implements Serializable {
	
	private int numberOfRelays;
	private List<RelayData> relays;
	private String actuatorId;
	private String networkId;
	
	public ActuatorData() {
		
	}

	public int getNumberOfRelays() {
		return numberOfRelays;
	}

	public void setNumberOfRelays(int numberOfRelays) {
		this.numberOfRelays = numberOfRelays;
	}

	public List<RelayData> getRelays() {
		return relays;
	}

	public void setRelays(List<RelayData> relays) {
		this.relays = relays;
	}

	public String getActuatorId() {
		return actuatorId;
	}

	public void setActuatorId(String actuatorId) {
		this.actuatorId = actuatorId;
	}

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}
	
	@Override
	public String toString() {
		return "Actuator [numberOfRelays=" + numberOfRelays + ", relays=" + relays + ", actuatorId=" + actuatorId
				+ ", networkId=" + networkId + "]";
	}
	
}
