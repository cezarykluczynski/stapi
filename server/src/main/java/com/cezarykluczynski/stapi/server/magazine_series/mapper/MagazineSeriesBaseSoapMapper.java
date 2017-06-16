package com.cezarykluczynski.stapi.server.magazine_series.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesBase;
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesBaseRequest;
import com.cezarykluczynski.stapi.model.magazine_series.dto.MagazineSeriesRequestDTO;
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyHeaderSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.magazine.mapper.MagazineHeaderSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {MagazineHeaderSoapMapper.class, MagazineSeriesHeaderSoapMapper.class,
		CompanyHeaderSoapMapper.class, RequestSortSoapMapper.class})
public interface MagazineSeriesBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	@Mapping(source = "publishedYear.from", target = "publishedYearFrom")
	@Mapping(source = "publishedYear.to", target = "publishedYearTo")
	@Mapping(source = "numberOfIssues.from", target = "numberOfIssuesFrom")
	@Mapping(source = "numberOfIssues.to", target = "numberOfIssuesTo")
	MagazineSeriesRequestDTO mapBase(MagazineSeriesBaseRequest magazineSeriesBaseRequest);

	MagazineSeriesBase mapBase(MagazineSeries magazineSeries);

	List<MagazineSeriesBase> mapBase(List<MagazineSeries> magazineSeriesList);

}
