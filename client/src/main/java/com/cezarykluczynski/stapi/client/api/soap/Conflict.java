package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.ConflictBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ConflictBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ConflictFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ConflictFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ConflictPortType;

public class Conflict {

	private final ConflictPortType conflictPortType;

	public Conflict(ConflictPortType conflictPortType) {
		this.conflictPortType = conflictPortType;
	}

	@Deprecated
	public ConflictFullResponse get(ConflictFullRequest request) {
		return conflictPortType.getConflictFull(request);
	}

	@Deprecated
	public ConflictBaseResponse search(ConflictBaseRequest request) {
		return conflictPortType.getConflictBase(request);
	}

}
