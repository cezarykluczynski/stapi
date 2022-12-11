package com.cezarykluczynski.stapi.server.company.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyBase;
import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyV2Base;
import com.cezarykluczynski.stapi.model.company.dto.CompanyRequestDTO;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.company.dto.CompanyRestBeanParams;
import com.cezarykluczynski.stapi.server.company.dto.CompanyV2RestBeanParams;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortRestMapper.class})
public interface CompanyBaseRestMapper {

	@Mapping(target = "streamingService", ignore = true)
	@Mapping(target = "visualEffectsCompany", ignore = true)
	@Mapping(target = "publisher", ignore = true)
	@Mapping(target = "publicationArtStudio", ignore = true)
	CompanyRequestDTO mapBase(CompanyRestBeanParams companyRestBeanParams);

	CompanyBase mapBase(Company company);

	List<CompanyBase> mapBase(List<Company> companyList);

	CompanyRequestDTO mapV2Base(CompanyV2RestBeanParams companyV2RestBeanParams);

	CompanyV2Base mapV2Base(Company company);

	List<CompanyV2Base> mapV2Base(List<Company> companyList);

}
