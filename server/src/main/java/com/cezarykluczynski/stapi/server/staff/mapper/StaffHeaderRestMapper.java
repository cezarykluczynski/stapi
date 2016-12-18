package com.cezarykluczynski.stapi.server.staff.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.StaffHeader;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StaffHeaderRestMapper {

	StaffHeader map(Staff staff);

	List<StaffHeader> map(List<Staff> staff);

}
