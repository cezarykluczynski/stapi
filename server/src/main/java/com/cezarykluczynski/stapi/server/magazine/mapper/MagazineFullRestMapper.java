package com.cezarykluczynski.stapi.server.magazine.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineFull;
import com.cezarykluczynski.stapi.model.magazine.entity.Magazine;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffBaseRestMapper;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {CompanyBaseRestMapper.class, StaffBaseRestMapper.class})
public interface MagazineFullRestMapper {

	MagazineFull mapFull(Magazine magazine);

}
