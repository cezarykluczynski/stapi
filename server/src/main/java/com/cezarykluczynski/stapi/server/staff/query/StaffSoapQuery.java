package com.cezarykluczynski.stapi.server.staff.query;

import com.cezarykluczynski.stapi.client.v1.soap.StaffBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.StaffFullRequest;
import com.cezarykluczynski.stapi.model.staff.dto.StaffRequestDTO;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.model.staff.repository.StaffRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffBaseSoapMapper;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffFullSoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class StaffSoapQuery {

	private final StaffBaseSoapMapper staffBaseSoapMapper;

	private final StaffFullSoapMapper staffFullSoapMapper;

	private final PageMapper pageMapper;

	private final StaffRepository staffRepository;

	public StaffSoapQuery(StaffBaseSoapMapper staffBaseSoapMapper, StaffFullSoapMapper staffFullSoapMapper, PageMapper pageMapper,
			StaffRepository staffRepository) {
		this.staffBaseSoapMapper = staffBaseSoapMapper;
		this.staffFullSoapMapper = staffFullSoapMapper;
		this.pageMapper = pageMapper;
		this.staffRepository = staffRepository;
	}

	public Page<Staff> query(StaffBaseRequest staffRequest) {
		StaffRequestDTO staffRequestDTO = staffBaseSoapMapper.mapBase(staffRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(staffRequest.getPage());
		return staffRepository.findMatching(staffRequestDTO, pageRequest);
	}

	public Page<Staff> query(StaffFullRequest staffFullRequest) {
		StaffRequestDTO staffRequestDTO = staffFullSoapMapper.mapFull(staffFullRequest);
		return staffRepository.findMatching(staffRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
