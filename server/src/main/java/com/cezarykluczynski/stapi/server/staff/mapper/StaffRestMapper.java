package com.cezarykluczynski.stapi.server.staff.mapper;

import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {EnumMapper.class})
public interface StaffRestMapper {

	com.cezarykluczynski.stapi.client.v1.rest.model.Staff map(Staff series);

	List<com.cezarykluczynski.stapi.client.v1.rest.model.Staff> map(List<Staff> series);

}
