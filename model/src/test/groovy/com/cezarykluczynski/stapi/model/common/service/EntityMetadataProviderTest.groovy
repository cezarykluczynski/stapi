package com.cezarykluczynski.stapi.model.common.service

import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
import com.google.common.collect.Maps
import com.google.common.collect.Sets
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.metadata.ClassMetadata
import org.hibernate.metamodel.spi.MetamodelImplementor
import org.hibernate.persister.entity.SingleTableEntityPersister
import org.hibernate.tuple.entity.EntityTuplizer
import spock.lang.Specification

import javax.persistence.EntityManager
import javax.persistence.metamodel.EntityType

class EntityMetadataProviderTest extends Specification {

	private MetamodelImplementor metamodel

	private Set<EntityType> entities

	private EntityManager entityManagerMock

	private EntityMetadataProvider entityMetadataProvider

	private Map<String, ClassMetadata> classNameToMetadataMap

	void setup() {
		classNameToMetadataMap = Maps.newHashMap()

		metamodel = Mock()

		entities = Sets.newHashSet()

		EntityType characterEntityType = Mock()
		characterEntityType.javaType >> Character
		SingleTableEntityPersister characterClassMetadata = Mock()
		EntityTuplizer characterEntityTuplizer = Mock()
		characterEntityTuplizer.mappedClass >> Character
		characterClassMetadata.entityTuplizer >> characterEntityTuplizer
		classNameToMetadataMap.put('com.cezarykluczynski.stapi.model.character.entity.Character', characterClassMetadata)
		metamodel.entityPersister('com.cezarykluczynski.stapi.model.character.entity.Character') >> characterClassMetadata

		EntityType seriesEntityType = Mock()
		seriesEntityType.javaType >> Series
		SingleTableEntityPersister seriesClassMetadata = Mock()
		EntityTuplizer seriesEntityTuplizer = Mock()
		seriesEntityTuplizer.mappedClass >> Series
		seriesClassMetadata.entityTuplizer >> seriesEntityTuplizer
		classNameToMetadataMap.put('com.cezarykluczynski.stapi.model.series.entity.Series', seriesClassMetadata)
		metamodel.entityPersister('com.cezarykluczynski.stapi.model.series.entity.Series') >> seriesClassMetadata

		EntityType comicSeriesEntityType = Mock()
		comicSeriesEntityType.javaType >> ComicSeries
		SingleTableEntityPersister comicSeriesClassMetadata = Mock()
		EntityTuplizer comicSeriesEntityTuplizer = Mock()
		comicSeriesEntityTuplizer.mappedClass >> ComicSeries
		comicSeriesClassMetadata.entityTuplizer >> comicSeriesEntityTuplizer
		classNameToMetadataMap.put('com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries', comicSeriesClassMetadata)
		metamodel.entityPersister('com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries') >> comicSeriesClassMetadata

		entities.addAll(Sets.newHashSet(characterEntityType, seriesEntityType, comicSeriesEntityType))
		metamodel.entities >> entities

		SessionFactory sessionFactoryMock = Mock()

		Session session = Mock()
		session.sessionFactory >> sessionFactoryMock

		sessionFactoryMock.metamodel >> metamodel

		entityManagerMock = Mock()
		entityManagerMock.delegate >> session
	}

	void "throws exception when entities can no longer be mapped to unique symbols"() {
		given:
		EntityType charSequenceEntityType = Mock()
		charSequenceEntityType.javaType >> CharSequence
		SingleTableEntityPersister charSequenceClassMetadata = Mock()
		EntityTuplizer charSequenceEntityTuplizer = Mock()
		charSequenceEntityTuplizer.mappedClass >> CharSequence
		charSequenceClassMetadata.entityTuplizer >> charSequenceEntityTuplizer
		entities.add(charSequenceEntityType)
		metamodel.entityPersister('java.lang.CharSequence') >> charSequenceClassMetadata

		when:
		entityMetadataProvider = new EntityMetadataProvider(entityManagerMock)

		then:
		StapiRuntimeException stapiRuntimeException = thrown(StapiRuntimeException)
		stapiRuntimeException.message == 'Entity class collection no longer suitable for symbol generation. ' +
				'Trying to put symbol CH, but symbol already present.'
	}

	void "provides class name to symbol map"() {
		when:
		entityMetadataProvider = new EntityMetadataProvider(entityManagerMock)
		Map<String, String> classNameToSymbolMap = entityMetadataProvider.provideClassNameToSymbolMap()

		then:
		classNameToSymbolMap['com.cezarykluczynski.stapi.model.series.entity.Series'] == 'SE'
		classNameToSymbolMap['com.cezarykluczynski.stapi.model.character.entity.Character'] == 'CH'
		classNameToSymbolMap['com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries'] == 'CS'
	}

	void "provides class name to metadata map"() {
		when:
		entityMetadataProvider = new EntityMetadataProvider(entityManagerMock)
		Map<String, ClassMetadata> classNameToMetadataMapOutput = entityMetadataProvider.provideClassNameToMetadataMap()

		then:
		classNameToMetadataMapOutput == classNameToMetadataMap
	}

	void "provides class simple name to class map"() {
		when:
		entityMetadataProvider = new EntityMetadataProvider(entityManagerMock)
		Map<String, Class> classSimpleNameToClassMap = entityMetadataProvider.provideClassSimpleNameToClassMap()

		then:
		classSimpleNameToClassMap['Series'] == Series
		classSimpleNameToClassMap['Character'] == Character
		classSimpleNameToClassMap['ComicSeries'] == ComicSeries
	}

}
