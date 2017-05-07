package com.cezarykluczynski.stapi.server.common.reader

import com.cezarykluczynski.stapi.model.book.entity.Book
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType
import com.cezarykluczynski.stapi.model.common.service.EntityMatadataProvider
import com.cezarykluczynski.stapi.model.reference.entity.Reference
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointDetailsDTO
import com.google.common.collect.Maps
import org.hibernate.metadata.ClassMetadata
import spock.lang.Specification

class CommonEntitiesDetailsReaderTest extends Specification {

	private EntityMatadataProvider entityMatadataProviderMock

	private CommonEntitiesDetailsReader commonEntitiesDetailsReader

	private ClassMetadata referenceClassMetadata

	private ClassMetadata characterClassMetadata

	private ClassMetadata bookClassMetadata

	private Map<String, ClassMetadata> classMetadataMap

	void setup() {
		classMetadataMap = Maps.newLinkedHashMap()
		referenceClassMetadata = Mock()
		characterClassMetadata = Mock()
		bookClassMetadata = Mock()
		classMetadataMap.put('com.cezarykluczynski.stapi.model.reference.entity.Reference', referenceClassMetadata)
		classMetadataMap.put('com.cezarykluczynski.stapi.model.character.entity.Character', characterClassMetadata)
		classMetadataMap.put('com.cezarykluczynski.stapi.model.book.entity.Book', bookClassMetadata)

		entityMatadataProviderMock = Mock()
		commonEntitiesDetailsReader = new CommonEntitiesDetailsReader(entityMatadataProviderMock)
	}

	void "maps entities metadata to RestEndpointDetailsDTO"() {
		when:
		RestEndpointDetailsDTO restEndpointDetailsDTO = commonEntitiesDetailsReader.details()

		then:
		1 * entityMatadataProviderMock.provideClassNameToMetadataMap() >> classMetadataMap
		1 * referenceClassMetadata.mappedClass >> Reference
		1 * characterClassMetadata.mappedClass >> Character
		1 * bookClassMetadata.mappedClass >> Book
		0 * _
		restEndpointDetailsDTO.details.size() == 2
		restEndpointDetailsDTO.details[0].name == 'Character'
		restEndpointDetailsDTO.details[0].type == TrackedEntityType.FICTIONAL_PRIMARY
		restEndpointDetailsDTO.details[0].singularName == 'character'
		restEndpointDetailsDTO.details[0].pluralName == 'characters'
		restEndpointDetailsDTO.details[1].name == 'Book'
		restEndpointDetailsDTO.details[1].type == TrackedEntityType.REAL_WORLD_PRIMARY
		restEndpointDetailsDTO.details[1].singularName == 'book'
		restEndpointDetailsDTO.details[1].pluralName == 'books'
	}

}
