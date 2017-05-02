package com.cezarykluczynski.stapi.server.common.reader;

import com.cezarykluczynski.stapi.model.common.service.EntityMatadataProvider;
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointMappingDTO;
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointMappingsDTO;
import com.cezarykluczynski.stapi.util.tool.StringUtil;
import com.google.common.collect.Lists;
import liquibase.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
class CommonMappingsReader {

	private static final List<String> EXCLUDED_ENTITIES = Lists.newArrayList("Throttle", "Page", "Reference", "CharacterSpecies");

	private final EntityMatadataProvider entityMatadataProvider;

	@Inject
	CommonMappingsReader(EntityMatadataProvider entityMatadataProvider) {
		this.entityMatadataProvider = entityMatadataProvider;
	}

	public RestEndpointMappingsDTO mappings() {
		RestEndpointMappingsDTO restEndpointMappingsDTO = new RestEndpointMappingsDTO();
		restEndpointMappingsDTO.getUrls().addAll(getAll());
		return restEndpointMappingsDTO;
	}

	private Set<RestEndpointMappingDTO> getAll() {
		return entityMatadataProvider.provideClassNameToSymbolMap()
				.entrySet()
				.stream()
				.filter(this::isApiEntity)
				.map(this::map)
				.collect(Collectors.toSet());
	}

	private RestEndpointMappingDTO map(Map.Entry<String, String> entry) {
		RestEndpointMappingDTO restEndpointMappingDTO = new RestEndpointMappingDTO();
		String[] entityFullNameParts = entry.getKey().split("\\.");
		String entityName = entityFullNameParts[entityFullNameParts.length - 1];
		restEndpointMappingDTO.setName(entityName);
		restEndpointMappingDTO.setSuffix(StringUtils.lowerCaseFirst(entityName));
		restEndpointMappingDTO.setSymbol(entry.getValue());
		return restEndpointMappingDTO;
	}

	private boolean isApiEntity(Map.Entry<String, String> entry) {
		return !StringUtil.endsWithAny(entry.getKey(), EXCLUDED_ENTITIES);
	}

}
