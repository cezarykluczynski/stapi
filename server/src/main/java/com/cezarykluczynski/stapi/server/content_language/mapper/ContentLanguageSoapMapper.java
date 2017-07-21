package com.cezarykluczynski.stapi.server.content_language.mapper;

import com.cezarykluczynski.stapi.model.content_language.entity.ContentLanguage;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {EnumMapper.class})
public interface ContentLanguageSoapMapper {

	com.cezarykluczynski.stapi.client.v1.soap.ContentLanguage map(ContentLanguage contentLanguage);

}
