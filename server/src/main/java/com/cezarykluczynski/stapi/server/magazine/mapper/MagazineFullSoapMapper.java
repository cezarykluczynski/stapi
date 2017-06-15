package com.cezarykluczynski.stapi.server.magazine.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.MagazineFull;
import com.cezarykluczynski.stapi.client.v1.soap.MagazineFullRequest;
import com.cezarykluczynski.stapi.model.magazine.dto.MagazineRequestDTO;
import com.cezarykluczynski.stapi.model.magazine.entity.Magazine;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffBaseSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {CompanyBaseSoapMapper.class, StaffBaseSoapMapper.class})
public interface MagazineFullSoapMapper {

	@Mapping(target = "title", ignore = true)
	@Mapping(target = "publishedYearFrom", ignore = true)
	@Mapping(target = "publishedYearTo", ignore = true)
	@Mapping(target = "numberOfPagesFrom", ignore = true)
	@Mapping(target = "numberOfPagesTo", ignore = true)
	@Mapping(target = "sort", ignore = true)
	MagazineRequestDTO mapFull(MagazineFullRequest magazineFullRequest);

	MagazineFull mapFull(Magazine magazine);

}
