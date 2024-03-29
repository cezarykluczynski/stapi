package com.cezarykluczynski.stapi.server.company.mapper;

import com.cezarykluczynski.stapi.client.rest.model.CompanyFull;
import com.cezarykluczynski.stapi.client.rest.model.CompanyV2Full;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class)
public interface CompanyFullRestMapper {

	CompanyFull mapFull(Company company);

	CompanyV2Full mapV2Full(Company company);

}
