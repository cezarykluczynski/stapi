package com.cezarykluczynski.stapi.server.staff.mapper;

import com.cezarykluczynski.stapi.model.staff.dto.StaffRequestDTO;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestOrderMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.staff.dto.StaffRestBeanParams;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {DateMapper.class, EnumMapper.class, RequestOrderMapper.class})
public interface StaffRestMapper {

	@Mappings({
			@Mapping(target = "order", ignore = true)
	})
	StaffRequestDTO map(StaffRestBeanParams performerRestBeanParams);

	com.cezarykluczynski.stapi.client.v1.rest.model.Staff map(Staff series);

	List<com.cezarykluczynski.stapi.client.v1.rest.model.Staff> map(List<Staff> series);

}
