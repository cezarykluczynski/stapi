package com.cezarykluczynski.stapi.server.company.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.CompanyHeader;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompanyHeaderSoapMapper {

	CompanyHeader map(Company company);

	List<CompanyHeader> map(List<Company> company);

}
