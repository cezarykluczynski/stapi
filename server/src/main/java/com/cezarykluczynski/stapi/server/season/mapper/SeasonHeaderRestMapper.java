package com.cezarykluczynski.stapi.server.season.mapper;

import com.cezarykluczynski.stapi.client.rest.model.SeasonHeader;
import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SeasonHeaderRestMapper {

	SeasonHeader map(Season season);

	List<SeasonHeader> map(List<Season> season);

}
