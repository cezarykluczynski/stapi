package com.cezarykluczynski.stapi.server.staff.mapper;

import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {EnumMapper.class})
public interface StaffSoapMapper {

	com.cezarykluczynski.stapi.client.v1.soap.Staff map(Staff series);

	List<com.cezarykluczynski.stapi.client.v1.soap.Staff> map(List<Staff> series);

}
