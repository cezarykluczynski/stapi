package com.cezarykluczynski.stapi.server.magazine.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.MagazineBase;
import com.cezarykluczynski.stapi.client.v1.soap.MagazineBaseRequest;
import com.cezarykluczynski.stapi.model.magazine.dto.MagazineRequestDTO;
import com.cezarykluczynski.stapi.model.magazine.entity.Magazine;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortSoapMapper.class})
public interface MagazineBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	@Mapping(source = "publishedYear.from", target = "publishedYearFrom")
	@Mapping(source = "publishedYear.to", target = "publishedYearTo")
	@Mapping(source = "numberOfPages.from", target = "numberOfPagesFrom")
	@Mapping(source = "numberOfPages.to", target = "numberOfPagesTo")
	MagazineRequestDTO mapBase(MagazineBaseRequest magazineBaseRequest);

	MagazineBase mapBase(Magazine magazine);

	List<MagazineBase> mapBase(List<Magazine> magazineList);

}
