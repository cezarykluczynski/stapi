package com.cezarykluczynski.stapi.server.company.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.CompanyRequest;
import com.cezarykluczynski.stapi.model.company.dto.CompanyRequestDTO;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortSoapMapper.class})
public interface CompanySoapMapper {

	CompanyRequestDTO map(CompanyRequest companyRequest);

	com.cezarykluczynski.stapi.client.v1.soap.Company map(Company company);

	List<com.cezarykluczynski.stapi.client.v1.soap.Company> map(List<Company> companyList);

}
