package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.ConflictBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ConflictBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ConflictFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ConflictFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ConflictPortType;

public class Conflict {

	private final ConflictPortType conflictPortType;

	private final ApiKeySupplier apiKeySupplier;

	public Conflict(ConflictPortType conflictPortType, ApiKeySupplier apiKeySupplier) {
		this.conflictPortType = conflictPortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public ConflictFullResponse get(ConflictFullRequest request) {
		apiKeySupplier.supply(request);
		return conflictPortType.getConflictFull(request);
	}

	public ConflictBaseResponse search(ConflictBaseRequest request) {
		apiKeySupplier.supply(request);
		return conflictPortType.getConflictBase(request);
	}

}
