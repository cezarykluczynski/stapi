package com.cezarykluczynski.stapi.server.company.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.CompanyBase;
import com.cezarykluczynski.stapi.client.v1.soap.CompanyBaseRequest;
import com.cezarykluczynski.stapi.model.company.dto.CompanyRequestDTO;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortSoapMapper.class})
public interface CompanyBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	CompanyRequestDTO mapBase(CompanyBaseRequest companyBaseRequest);

	CompanyBase mapBase(Company company);

	List<CompanyBase> mapBase(List<Company> companyList);

}
