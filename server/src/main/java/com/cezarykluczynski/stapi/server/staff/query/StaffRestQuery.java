package com.cezarykluczynski.stapi.server.staff.query;

import com.cezarykluczynski.stapi.model.staff.dto.StaffRequestDTO;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.model.staff.repository.StaffRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.staff.dto.StaffRestBeanParams;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffRestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class StaffRestQuery {

	private StaffRestMapper staffRestMapper;

	private PageMapper pageMapper;

	private StaffRepository staffRepository;

	@Inject
	public StaffRestQuery(StaffRestMapper staffRestMapper, PageMapper pageMapper, StaffRepository staffRepository) {
		this.staffRestMapper = staffRestMapper;
		this.pageMapper = pageMapper;
		this.staffRepository = staffRepository;
	}

	public Page<Staff> query(StaffRestBeanParams staffRestBeanParams) {
		StaffRequestDTO staffRequestDTO = staffRestMapper.map(staffRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(staffRestBeanParams);
		return staffRepository.findMatching(staffRequestDTO, pageRequest);
	}

}
