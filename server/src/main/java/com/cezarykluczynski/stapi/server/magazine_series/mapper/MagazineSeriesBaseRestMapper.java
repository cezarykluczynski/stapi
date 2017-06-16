package com.cezarykluczynski.stapi.server.magazine_series.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineSeriesBase;
import com.cezarykluczynski.stapi.model.magazine_series.dto.MagazineSeriesRequestDTO;
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries;
import com.cezarykluczynski.stapi.server.magazine_series.dto.MagazineSeriesRestBeanParams;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortRestMapper.class})
public interface MagazineSeriesBaseRestMapper {

	MagazineSeriesRequestDTO mapBase(MagazineSeriesRestBeanParams magazineSeriesRestBeanParams);

	MagazineSeriesBase mapBase(MagazineSeries magazineSeries);

	List<MagazineSeriesBase> mapBase(List<MagazineSeries> magazineSeriesList);

}
