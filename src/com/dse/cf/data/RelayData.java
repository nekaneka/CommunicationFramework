package com.dse.cf.data;

import java.io.Serializable;

public class RelayData implements Serializable {
	
	private String relayId;
	private boolean relayStatus;
	
	public RelayData() {
		
	}

	public String getRelayId() {
		return relayId;
	}

	public void setRelayId(String relayId) {
		this.relayId = relayId;
	}

	public boolean getRelayStatus() {
		return relayStatus;
	}

	public void setRelayStatus(boolean relayStatus) {
		this.relayStatus = relayStatus;
	}

	@Override
	public String toString() {
		return "Relay [relayId=" + relayId + ", relayStatus=" + relayStatus + "]";
	}
	
}
