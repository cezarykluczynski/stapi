package com.cezarykluczynski.stapi.server.staff.reader;

import com.cezarykluczynski.stapi.client.v1.soap.StaffRequest;
import com.cezarykluczynski.stapi.client.v1.soap.StaffResponse;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.Reader;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffSoapMapper;
import com.cezarykluczynski.stapi.server.staff.query.StaffQuery;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class StaffSoapReader implements Reader<StaffRequest, StaffResponse> {

	private StaffQuery staffQuery;

	private StaffSoapMapper staffSoapMapper;

	private PageMapper pageMapper;

	@Inject
	public StaffSoapReader(StaffQuery staffQuery, StaffSoapMapper staffSoapMapper, PageMapper pageMapper) {
		this.staffQuery = staffQuery;
		this.staffSoapMapper = staffSoapMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public StaffResponse read(StaffRequest input) {
		Page<Staff> staffPage = staffQuery.query(input);
		StaffResponse staffResponse = new StaffResponse();
		staffResponse.setPage(pageMapper.fromPageToSoapResponsePage(staffPage));
		staffResponse.getStaff().addAll(staffSoapMapper.map(staffPage.getContent()));
		return staffResponse;
	}

}
