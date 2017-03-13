package com.cezarykluczynski.stapi.server.staff.reader;

import com.cezarykluczynski.stapi.client.v1.soap.StaffRequest;
import com.cezarykluczynski.stapi.client.v1.soap.StaffResponse;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffSoapMapper;
import com.cezarykluczynski.stapi.server.staff.query.StaffSoapQuery;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class StaffSoapReader implements BaseReader<StaffRequest, StaffResponse> {

	private StaffSoapQuery staffSoapQuery;

	private StaffSoapMapper staffSoapMapper;

	private PageMapper pageMapper;

	@Inject
	public StaffSoapReader(StaffSoapQuery staffSoapQuery, StaffSoapMapper staffSoapMapper, PageMapper pageMapper) {
		this.staffSoapQuery = staffSoapQuery;
		this.staffSoapMapper = staffSoapMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public StaffResponse readBase(StaffRequest input) {
		Page<Staff> staffPage = staffSoapQuery.query(input);
		StaffResponse staffResponse = new StaffResponse();
		staffResponse.setPage(pageMapper.fromPageToSoapResponsePage(staffPage));
		staffResponse.getStaff().addAll(staffSoapMapper.map(staffPage.getContent()));
		return staffResponse;
	}

}
