package com.cezarykluczynski.stapi.server.magazine.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineHeader;
import com.cezarykluczynski.stapi.model.magazine.entity.Magazine;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MagazineHeaderRestMapper {

	MagazineHeader map(Magazine magazine);

	List<MagazineHeader> map(List<Magazine> magazine);

}
