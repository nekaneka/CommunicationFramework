package com.dse.cf.data;

import java.io.Serializable;

public class TargetActuator implements Serializable {
	
	private String networkId;
	private String actuatorId;
	private String relayId;
	private boolean value;
	private String componentId;
	
	public TargetActuator(String networkId, String actuatorId, String relayId, boolean value) {
		super();
		this.networkId = networkId;
		this.actuatorId = actuatorId;
		this.relayId = relayId;
		this.value = value;
	}
	
	public String getComponentId() {
		return componentId;
	}

	public void setComponentId(String networkId, String actuatorId, String relayId) {
		this.componentId = networkId + ":" + actuatorId + ":" + relayId;
	}

	public String getNetworkId() {
		return networkId;
	}
	
	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}
	
	public String getActuatorId() {
		return actuatorId;
	}
	
	public void setActuatorId(String actuatorId) {
		this.actuatorId = actuatorId;
	}
	
	public String getRelayId() {
		return relayId;
	}
	
	public void setRelayId(String relayId) {
		this.relayId = relayId;
	}
	
	public boolean isValue() {
		return value;
	}
	
	public void setValue(boolean value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "TargetActuator [networkId=" + networkId + ", actuatorId=" + actuatorId + ", relayId=" + relayId
				+ ", value=" + value + "]";
	} 
	
}