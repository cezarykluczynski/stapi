package com.cezarykluczynski.stapi.server.magazine.mapper;

import com.cezarykluczynski.stapi.model.magazine.dto.MagazineRequestDTO;
import com.cezarykluczynski.stapi.model.magazine.entity.Magazine;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.magazine.dto.MagazineRestBeanParams;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortRestMapper.class})
public interface MagazineBaseRestMapper {

	MagazineRequestDTO mapBase(MagazineRestBeanParams magazineRestBeanParams);

	com.cezarykluczynski.stapi.client.v1.rest.model.MagazineBase mapBase(Magazine magazine);

	List<com.cezarykluczynski.stapi.client.v1.rest.model.MagazineBase> mapBase(List<Magazine> magazineList);

}
