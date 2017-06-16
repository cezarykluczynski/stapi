package com.cezarykluczynski.stapi.server.magazine_series.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineSeriesHeader;
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MagazineSeriesHeaderRestMapper {

	MagazineSeriesHeader map(MagazineSeries magazineSeries);

	List<MagazineSeriesHeader> map(List<MagazineSeries> magazineSeries);

}
