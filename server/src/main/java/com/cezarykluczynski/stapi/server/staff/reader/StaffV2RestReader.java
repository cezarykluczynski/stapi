package com.cezarykluczynski.stapi.server.staff.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.StaffV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.StaffV2FullResponse;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.staff.dto.StaffV2RestBeanParams;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffBaseRestMapper;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffFullRestMapper;
import com.cezarykluczynski.stapi.server.staff.query.StaffRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class StaffV2RestReader implements BaseReader<StaffV2RestBeanParams, StaffV2BaseResponse>,
		FullReader<String, StaffV2FullResponse> {

	private final StaffRestQuery staffRestQuery;

	private final StaffBaseRestMapper staffBaseRestMapper;

	private final StaffFullRestMapper staffFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public StaffV2RestReader(StaffRestQuery staffRestQuery, StaffBaseRestMapper staffBaseRestMapper,
			StaffFullRestMapper staffFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.staffRestQuery = staffRestQuery;
		this.staffBaseRestMapper = staffBaseRestMapper;
		this.staffFullRestMapper = staffFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public StaffV2BaseResponse readBase(StaffV2RestBeanParams input) {
		Page<Staff> staffPage = staffRestQuery.query(input);
		StaffV2BaseResponse staffResponse = new StaffV2BaseResponse();
		staffResponse.setPage(pageMapper.fromPageToRestResponsePage(staffPage));
		staffResponse.setSort(sortMapper.map(input.getSort()));
		staffResponse.getStaff().addAll(staffBaseRestMapper.mapV2Base(staffPage.getContent()));
		return staffResponse;
	}

	@Override
	public StaffV2FullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		StaffV2RestBeanParams staffV2RestBeanParams = new StaffV2RestBeanParams();
		staffV2RestBeanParams.setUid(uid);
		Page<Staff> staffPage = staffRestQuery.query(staffV2RestBeanParams);
		StaffV2FullResponse staffResponse = new StaffV2FullResponse();
		staffResponse.setStaff(staffFullRestMapper.mapV2Full(Iterables.getOnlyElement(staffPage.getContent(), null)));
		return staffResponse;
	}

}
