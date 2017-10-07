package com.cezarykluczynski.stapi.server.common.throttle;

import com.cezarykluczynski.stapi.server.common.throttle.credential.RequestCredentialType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

class FrequentRequestsKey {

	private RequestCredentialType requestCredentialType;

	private String value;

	public static FrequentRequestsKey of(RequestCredentialType requestCredentialType, String value) {
		FrequentRequestsKey key = new FrequentRequestsKey();
		key.setRequestCredentialType(requestCredentialType);
		key.setValue(value);
		return key;
	}

	RequestCredentialType getRequestCredentialType() {
		return requestCredentialType;
	}

	void setRequestCredentialType(RequestCredentialType requestCredentialType) {
		this.requestCredentialType = requestCredentialType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object object) {
		return EqualsBuilder.reflectionEquals(this, object, false);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(getRequestCredentialType())
				.append(getValue())
				.toHashCode();
	}

}
