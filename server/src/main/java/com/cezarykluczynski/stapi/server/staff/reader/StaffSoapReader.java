package com.cezarykluczynski.stapi.server.staff.reader;

import com.cezarykluczynski.stapi.client.v1.soap.StaffBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.StaffBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.StaffFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.StaffFullResponse;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffBaseSoapMapper;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffFullSoapMapper;
import com.cezarykluczynski.stapi.server.staff.query.StaffSoapQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class StaffSoapReader implements BaseReader<StaffBaseRequest, StaffBaseResponse>, FullReader<StaffFullRequest, StaffFullResponse> {

	private final StaffSoapQuery staffSoapQuery;

	private final StaffBaseSoapMapper staffBaseSoapMapper;

	private final StaffFullSoapMapper staffFullSoapMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public StaffSoapReader(StaffSoapQuery staffSoapQuery, StaffBaseSoapMapper staffBaseSoapMapper, StaffFullSoapMapper staffFullSoapMapper,
			PageMapper pageMapper, SortMapper sortMapper) {
		this.staffSoapQuery = staffSoapQuery;
		this.staffBaseSoapMapper = staffBaseSoapMapper;
		this.staffFullSoapMapper = staffFullSoapMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public StaffBaseResponse readBase(StaffBaseRequest input) {
		Page<Staff> staffPage = staffSoapQuery.query(input);
		StaffBaseResponse staffResponse = new StaffBaseResponse();
		staffResponse.setPage(pageMapper.fromPageToSoapResponsePage(staffPage));
		staffResponse.setSort(sortMapper.map(input.getSort()));
		staffResponse.getStaff().addAll(staffBaseSoapMapper.mapBase(staffPage.getContent()));
		return staffResponse;
	}

	@Override
	public StaffFullResponse readFull(StaffFullRequest input) {
		StaticValidator.requireUid(input.getUid());
		Page<Staff> staffPage = staffSoapQuery.query(input);
		StaffFullResponse staffFullResponse = new StaffFullResponse();
		staffFullResponse.setStaff(staffFullSoapMapper.mapFull(Iterables.getOnlyElement(staffPage.getContent(), null)));
		return staffFullResponse;
	}

}
