package com.cezarykluczynski.stapi.server.common.reader;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.service.EntityMatadataProvider;
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointDetailDTO;
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointDetailsDTO;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
class CommonEntitiesDetailsReader {

	private final EntityMatadataProvider entityMatadataProvider;

	@Inject
	CommonEntitiesDetailsReader(EntityMatadataProvider entityMatadataProvider) {
		this.entityMatadataProvider = entityMatadataProvider;
	}

	RestEndpointDetailsDTO details() {
		List<RestEndpointDetailDTO> restEndpointDetailsDTOList = entityMatadataProvider.provideClassNameToMetadataMap()
				.entrySet()
				.stream()
				.map(this::map)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());

		return new RestEndpointDetailsDTO(restEndpointDetailsDTOList);
	}

	private RestEndpointDetailDTO map(Map.Entry<String, ClassMetadata> entry) {
		RestEndpointDetailDTO restEndpointDetailDTO = new RestEndpointDetailDTO();
		Class clazz = entry.getValue().getMappedClass();
		TrackedEntity trackedEntity = (TrackedEntity) clazz.getAnnotation(TrackedEntity.class);
		if (!trackedEntity.apiEntity()) {
			return null;
		}

		restEndpointDetailDTO.setName(clazz.getSimpleName());
		restEndpointDetailDTO.setType(trackedEntity.type());
		restEndpointDetailDTO.setSingularName(trackedEntity.singularName());
		restEndpointDetailDTO.setPluralName(trackedEntity.pluralName());
		return restEndpointDetailDTO;
	}

}
