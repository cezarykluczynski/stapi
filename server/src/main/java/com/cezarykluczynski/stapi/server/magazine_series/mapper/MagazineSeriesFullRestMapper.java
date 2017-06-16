package com.cezarykluczynski.stapi.server.magazine_series.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineSeriesFull;
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.magazine.mapper.MagazineBaseRestMapper;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {MagazineBaseRestMapper.class, MagazineSeriesBaseRestMapper.class, CompanyBaseRestMapper.class})
public interface MagazineSeriesFullRestMapper {

	MagazineSeriesFull mapFull(MagazineSeries magazineSeries);

}
