package com.cezarykluczynski.stapi.server.staff.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.StaffBase;
import com.cezarykluczynski.stapi.client.v1.soap.StaffBaseRequest;
import com.cezarykluczynski.stapi.model.staff.dto.StaffRequestDTO;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {DateMapper.class, EnumMapper.class, RequestSortSoapMapper.class})
public interface StaffBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	@Mapping(source = "dateOfBirth.from", target = "dateOfBirthFrom")
	@Mapping(source = "dateOfBirth.to", target = "dateOfBirthTo")
	@Mapping(source = "dateOfDeath.from", target = "dateOfDeathFrom")
	@Mapping(source = "dateOfDeath.to", target = "dateOfDeathTo")
	StaffRequestDTO mapBase(StaffBaseRequest staffBaseRequest);

	StaffBase mapBase(Staff staff);

	List<StaffBase> mapBase(List<Staff> staffList);

}
