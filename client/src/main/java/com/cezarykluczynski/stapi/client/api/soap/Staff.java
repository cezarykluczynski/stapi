package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.StaffBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.StaffBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.StaffFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.StaffFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.StaffPortType;

public class Staff {

	private final StaffPortType staffPortType;

	public Staff(StaffPortType staffPortType) {
		this.staffPortType = staffPortType;
	}

	@Deprecated
	public StaffFullResponse get(StaffFullRequest request) {
		return staffPortType.getStaffFull(request);
	}

	@Deprecated
	public StaffBaseResponse search(StaffBaseRequest request) {
		return staffPortType.getStaffBase(request);
	}

}
