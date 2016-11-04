package com.cezarykluczynski.stapi.model.staff.repository;

import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;
import com.cezarykluczynski.stapi.model.staff.dto.StaffRequestDTO;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;

public interface StaffRepositoryCustom extends CriteriaMatcher<StaffRequestDTO, Staff> {
}
