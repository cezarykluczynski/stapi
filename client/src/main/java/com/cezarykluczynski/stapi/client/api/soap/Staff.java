package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.StaffBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.StaffBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.StaffFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.StaffFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.StaffPortType;

public class Staff {

	private final StaffPortType staffPortType;

	private final ApiKeySupplier apiKeySupplier;

	public Staff(StaffPortType staffPortType, ApiKeySupplier apiKeySupplier) {
		this.staffPortType = staffPortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public StaffFullResponse get(StaffFullRequest request) {
		apiKeySupplier.supply(request);
		return staffPortType.getStaffFull(request);
	}

	public StaffBaseResponse search(StaffBaseRequest request) {
		apiKeySupplier.supply(request);
		return staffPortType.getStaffBase(request);
	}

}
