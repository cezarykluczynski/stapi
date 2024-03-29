package com.cezarykluczynski.stapi.server.content_language.mapper;

import com.cezarykluczynski.stapi.model.content_language.entity.ContentLanguage;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {EnumMapper.class})
public interface ContentLanguageRestMapper {

	com.cezarykluczynski.stapi.client.rest.model.ContentLanguage map(ContentLanguage contentLanguage);

}
