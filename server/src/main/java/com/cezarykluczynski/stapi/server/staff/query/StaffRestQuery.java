package com.cezarykluczynski.stapi.server.staff.query;

import com.cezarykluczynski.stapi.model.staff.dto.StaffRequestDTO;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.model.staff.repository.StaffRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.staff.dto.StaffRestBeanParams;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffBaseRestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class StaffRestQuery {

	private final StaffBaseRestMapper staffBaseRestMapper;

	private final PageMapper pageMapper;

	private final StaffRepository staffRepository;

	public StaffRestQuery(StaffBaseRestMapper staffBaseRestMapper, PageMapper pageMapper, StaffRepository staffRepository) {
		this.staffBaseRestMapper = staffBaseRestMapper;
		this.pageMapper = pageMapper;
		this.staffRepository = staffRepository;
	}

	public Page<Staff> query(StaffRestBeanParams staffRestBeanParams) {
		StaffRequestDTO staffRequestDTO = staffBaseRestMapper.mapBase(staffRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(staffRestBeanParams);
		return staffRepository.findMatching(staffRequestDTO, pageRequest);
	}

}
