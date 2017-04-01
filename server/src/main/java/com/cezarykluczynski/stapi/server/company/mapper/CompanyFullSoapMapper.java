package com.cezarykluczynski.stapi.server.company.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.CompanyFull;
import com.cezarykluczynski.stapi.client.v1.soap.CompanyFullRequest;
import com.cezarykluczynski.stapi.model.company.dto.CompanyRequestDTO;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortSoapMapper.class})
public interface CompanyFullSoapMapper {

	@Mapping(target = "name", ignore = true)
	@Mapping(target = "broadcaster", ignore = true)
	@Mapping(target = "collectibleCompany", ignore = true)
	@Mapping(target = "conglomerate", ignore = true)
	@Mapping(target = "digitalVisualEffectsCompany", ignore = true)
	@Mapping(target = "distributor", ignore = true)
	@Mapping(target = "gameCompany", ignore = true)
	@Mapping(target = "filmEquipmentCompany", ignore = true)
	@Mapping(target = "makeUpEffectsStudio", ignore = true)
	@Mapping(target = "mattePaintingCompany", ignore = true)
	@Mapping(target = "modelAndMiniatureEffectsCompany", ignore = true)
	@Mapping(target = "postProductionCompany", ignore = true)
	@Mapping(target = "productionCompany", ignore = true)
	@Mapping(target = "propCompany", ignore = true)
	@Mapping(target = "recordLabel", ignore = true)
	@Mapping(target = "specialEffectsCompany", ignore = true)
	@Mapping(target = "tvAndFilmProductionCompany", ignore = true)
	@Mapping(target = "videoGameCompany", ignore = true)
	@Mapping(target = "sort", ignore = true)
	CompanyRequestDTO mapFull(CompanyFullRequest companyFullRequest);

	CompanyFull mapFull(Company company);

}
