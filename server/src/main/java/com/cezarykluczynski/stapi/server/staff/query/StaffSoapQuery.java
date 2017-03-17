package com.cezarykluczynski.stapi.server.staff.query;

import com.cezarykluczynski.stapi.client.v1.soap.StaffBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.StaffFullRequest;
import com.cezarykluczynski.stapi.model.staff.dto.StaffRequestDTO;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.model.staff.repository.StaffRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffSoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class StaffSoapQuery {

	private StaffSoapMapper staffSoapMapper;

	private PageMapper pageMapper;

	private StaffRepository staffRepository;

	@Inject
	public StaffSoapQuery(StaffSoapMapper staffSoapMapper, PageMapper pageMapper, StaffRepository staffRepository) {
		this.staffSoapMapper = staffSoapMapper;
		this.pageMapper = pageMapper;
		this.staffRepository = staffRepository;
	}

	public Page<Staff> query(StaffBaseRequest staffRequest) {
		StaffRequestDTO staffRequestDTO = staffSoapMapper.mapBase(staffRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(staffRequest.getPage());
		return staffRepository.findMatching(staffRequestDTO, pageRequest);
	}

	public Page<Staff> query(StaffFullRequest staffFullRequest) {
		StaffRequestDTO staffRequestDTO = staffSoapMapper.mapFull(staffFullRequest);
		return staffRepository.findMatching(staffRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
