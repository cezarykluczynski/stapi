package com.cezarykluczynski.stapi.server.staff.reader;


import com.cezarykluczynski.stapi.client.v1.rest.model.StaffResponse;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.staff.dto.StaffRestBeanParams;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffRestMapper;
import com.cezarykluczynski.stapi.server.staff.query.StaffRestQuery;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class StaffRestReader implements BaseReader<StaffRestBeanParams, StaffResponse> {

	private StaffRestQuery staffRestQuery;

	private StaffRestMapper staffRestMapper;

	private PageMapper pageMapper;

	@Inject
	public StaffRestReader(StaffRestQuery staffRestQuery, StaffRestMapper staffRestMapper, PageMapper pageMapper) {
		this.staffRestQuery = staffRestQuery;
		this.staffRestMapper = staffRestMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public StaffResponse readBase(StaffRestBeanParams input) {
		Page<Staff> staffPage = staffRestQuery.query(input);
		StaffResponse staffResponse = new StaffResponse();
		staffResponse.setPage(pageMapper.fromPageToRestResponsePage(staffPage));
		staffResponse.getStaff().addAll(staffRestMapper.map(staffPage.getContent()));
		return staffResponse;
	}

}
