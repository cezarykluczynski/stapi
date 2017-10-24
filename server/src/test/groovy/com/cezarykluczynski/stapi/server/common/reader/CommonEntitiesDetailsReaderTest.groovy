package com.cezarykluczynski.stapi.server.common.reader

import com.cezarykluczynski.stapi.model.book.entity.Book
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType
import com.cezarykluczynski.stapi.model.common.service.EntityMetadataProvider
import com.cezarykluczynski.stapi.model.reference.entity.Reference
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointDetailsDTO
import com.google.common.collect.Maps
import org.hibernate.metadata.ClassMetadata
import spock.lang.Specification

class CommonEntitiesDetailsReaderTest extends Specification {

	private EntityMetadataProvider entityMetadataProviderMock

	private CommonEntitiesDetailsReader commonEntitiesDetailsReader

	private ClassMetadata referenceClassMetadata

	private ClassMetadata characterClassMetadata

	private ClassMetadata bookClassMetadata

	private Map<String, ClassMetadata> classMetadataMap

	private Map<String, String> classNameToSymbolMap

	void setup() {
		classMetadataMap = Maps.newLinkedHashMap()
		referenceClassMetadata = Mock()
		characterClassMetadata = Mock()
		bookClassMetadata = Mock()
		classMetadataMap.put('com.cezarykluczynski.stapi.model.reference.entity.Reference', referenceClassMetadata)
		classMetadataMap.put('com.cezarykluczynski.stapi.model.character.entity.Character', characterClassMetadata)
		classMetadataMap.put('com.cezarykluczynski.stapi.model.book.entity.Book', bookClassMetadata)

		classNameToSymbolMap = Maps.newHashMap()
		classNameToSymbolMap.put('com.cezarykluczynski.stapi.model.character.entity.Character', 'CH')
		classNameToSymbolMap.put('com.cezarykluczynski.stapi.model.book.entity.Book', 'BO')

		entityMetadataProviderMock = Mock()
		commonEntitiesDetailsReader = new CommonEntitiesDetailsReader(entityMetadataProviderMock)
	}

	void "maps entities metadata to RestEndpointDetailsDTO"() {
		when:
		RestEndpointDetailsDTO restEndpointDetailsDTO = commonEntitiesDetailsReader.details()

		then:
		1 * entityMetadataProviderMock.provideClassNameToMetadataMap() >> classMetadataMap
		1 * entityMetadataProviderMock.provideClassNameToSymbolMap() >> classNameToSymbolMap
		1 * referenceClassMetadata.mappedClass >> Reference
		1 * characterClassMetadata.mappedClass >> Character
		1 * bookClassMetadata.mappedClass >> Book
		0 * _
		restEndpointDetailsDTO.details.size() == 2
		restEndpointDetailsDTO.details[0].name == 'Character'
		restEndpointDetailsDTO.details[0].type == TrackedEntityType.FICTIONAL_PRIMARY
		restEndpointDetailsDTO.details[0].apiEndpointSuffix == 'character'
		restEndpointDetailsDTO.details[0].symbol == 'CH'
		restEndpointDetailsDTO.details[0].singularName == 'character'
		restEndpointDetailsDTO.details[0].pluralName == 'characters'
		restEndpointDetailsDTO.details[1].name == 'Book'
		restEndpointDetailsDTO.details[1].type == TrackedEntityType.REAL_WORLD_PRIMARY
		restEndpointDetailsDTO.details[1].apiEndpointSuffix == 'book'
		restEndpointDetailsDTO.details[1].symbol == 'BO'
		restEndpointDetailsDTO.details[1].singularName == 'book'
		restEndpointDetailsDTO.details[1].pluralName == 'books'
	}

}
