package com.cezarykluczynski.stapi.server.staff.mapper;

import com.cezarykluczynski.stapi.client.rest.model.StaffBase;
import com.cezarykluczynski.stapi.client.rest.model.StaffV2Base;
import com.cezarykluczynski.stapi.model.staff.dto.StaffRequestDTO;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.staff.dto.StaffRestBeanParams;
import com.cezarykluczynski.stapi.server.staff.dto.StaffV2RestBeanParams;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {DateMapper.class, EnumMapper.class, RequestSortRestMapper.class})
public interface StaffBaseRestMapper {

	@Mapping(target = "filmationProductionStaff", ignore = true)
	@Mapping(target = "merchandiseStaff", ignore = true)
	@Mapping(target = "comicCoverArtist", ignore = true)
	StaffRequestDTO mapBase(StaffRestBeanParams staffRestBeanParams);

	StaffBase mapBase(Staff staff);

	List<StaffBase> mapBase(List<Staff> staffList);

	StaffRequestDTO mapV2Base(StaffV2RestBeanParams staffV2RestBeanParams);

	List<StaffV2Base> mapV2Base(List<Staff> staffList);

}
