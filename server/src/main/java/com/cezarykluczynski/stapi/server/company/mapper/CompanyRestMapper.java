package com.cezarykluczynski.stapi.server.company.mapper;

import com.cezarykluczynski.stapi.model.company.dto.CompanyRequestDTO;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.company.dto.CompanyRestBeanParams;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortRestMapper.class})
public interface CompanyRestMapper {

	CompanyRequestDTO mapBase(CompanyRestBeanParams companyRestBeanParams);

	com.cezarykluczynski.stapi.client.v1.rest.model.CompanyBase mapBase(Company company);

	List<com.cezarykluczynski.stapi.client.v1.rest.model.CompanyBase> mapBase(List<Company> companyList);

	com.cezarykluczynski.stapi.client.v1.rest.model.CompanyFull mapFull(Company company);

}
