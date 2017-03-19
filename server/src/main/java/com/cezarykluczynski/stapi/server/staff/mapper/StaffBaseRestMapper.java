package com.cezarykluczynski.stapi.server.staff.mapper;

import com.cezarykluczynski.stapi.model.staff.dto.StaffRequestDTO;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.staff.dto.StaffRestBeanParams;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {DateMapper.class, EnumMapper.class, RequestSortRestMapper.class})
public interface StaffBaseRestMapper {

	StaffRequestDTO mapBase(StaffRestBeanParams staffRestBeanParams);

	com.cezarykluczynski.stapi.client.v1.rest.model.StaffBase mapBase(Staff staff);

	List<com.cezarykluczynski.stapi.client.v1.rest.model.StaffBase> mapBase(List<Staff> staffList);

}
