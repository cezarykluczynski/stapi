package com.cezarykluczynski.stapi.server.magazine_series.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesFull;
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesFullRequest;
import com.cezarykluczynski.stapi.model.magazine_series.dto.MagazineSeriesRequestDTO;
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.magazine.mapper.MagazineBaseSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {MagazineBaseSoapMapper.class, MagazineSeriesBaseSoapMapper.class, CompanyBaseSoapMapper.class})
public interface MagazineSeriesFullSoapMapper {

	@Mapping(target = "title", ignore = true)
	@Mapping(target = "publishedYearFrom", ignore = true)
	@Mapping(target = "publishedYearTo", ignore = true)
	@Mapping(target = "numberOfIssuesFrom", ignore = true)
	@Mapping(target = "numberOfIssuesTo", ignore = true)
	@Mapping(target = "sort", ignore = true)
	MagazineSeriesRequestDTO mapFull(MagazineSeriesFullRequest magazineSeriesFullRequest);

	MagazineSeriesFull mapFull(MagazineSeries magazineSeries);

}
