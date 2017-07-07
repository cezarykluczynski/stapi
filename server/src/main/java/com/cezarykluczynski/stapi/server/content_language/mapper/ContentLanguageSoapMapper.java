package com.cezarykluczynski.stapi.server.content_language.mapper;

import com.cezarykluczynski.stapi.model.content_language.entity.ContentLanguage;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {EnumMapper.class})
public interface ContentLanguageSoapMapper {

	@Mapping(target = "uid", ignore = true)
	@Mapping(target = "iso6391Code", source = "iso639_1Code")
	com.cezarykluczynski.stapi.client.v1.soap.ContentLanguage map(ContentLanguage contentLanguage);

}
