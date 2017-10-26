package com.cezarykluczynski.stapi.server.staff.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.StaffBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.StaffFullResponse;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.staff.dto.StaffRestBeanParams;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffBaseRestMapper;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffFullRestMapper;
import com.cezarykluczynski.stapi.server.staff.query.StaffRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class StaffRestReader implements BaseReader<StaffRestBeanParams, StaffBaseResponse>, FullReader<String, StaffFullResponse> {

	private final StaffRestQuery staffRestQuery;

	private final StaffBaseRestMapper staffBaseRestMapper;

	private final StaffFullRestMapper staffFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public StaffRestReader(StaffRestQuery staffRestQuery, StaffBaseRestMapper staffBaseRestMapper, StaffFullRestMapper staffFullRestMapper,
			PageMapper pageMapper, SortMapper sortMapper) {
		this.staffRestQuery = staffRestQuery;
		this.staffBaseRestMapper = staffBaseRestMapper;
		this.staffFullRestMapper = staffFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public StaffBaseResponse readBase(StaffRestBeanParams input) {
		Page<Staff> staffPage = staffRestQuery.query(input);
		StaffBaseResponse staffResponse = new StaffBaseResponse();
		staffResponse.setPage(pageMapper.fromPageToRestResponsePage(staffPage));
		staffResponse.setSort(sortMapper.map(input.getSort()));
		staffResponse.getStaff().addAll(staffBaseRestMapper.mapBase(staffPage.getContent()));
		return staffResponse;
	}

	@Override
	public StaffFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		StaffRestBeanParams staffRestBeanParams = new StaffRestBeanParams();
		staffRestBeanParams.setUid(uid);
		Page<Staff> staffPage = staffRestQuery.query(staffRestBeanParams);
		StaffFullResponse staffResponse = new StaffFullResponse();
		staffResponse.setStaff(staffFullRestMapper.mapFull(Iterables.getOnlyElement(staffPage.getContent(), null)));
		return staffResponse;
	}

}
