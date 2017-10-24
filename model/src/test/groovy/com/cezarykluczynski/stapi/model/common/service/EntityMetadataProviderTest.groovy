package com.cezarykluczynski.stapi.model.common.service

import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
import com.google.common.collect.Maps
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.metadata.ClassMetadata
import spock.lang.Specification

import javax.persistence.EntityManager

class EntityMetadataProviderTest extends Specification {

	private EntityManager entityManagerMock

	private EntityMetadataProvider entityMetadataProvider

	private Map<String, ClassMetadata> classNameToMetadataMap

	void setup() {
		classNameToMetadataMap = Maps.newHashMap()

		ClassMetadata characterClassMetadata = Mock()
		characterClassMetadata.mappedClass >> Character
		classNameToMetadataMap.put('com.cezarykluczynski.stapi.model.character.entity.Character', characterClassMetadata)
		ClassMetadata seriesClassMetadata = Mock()
		seriesClassMetadata.mappedClass >> Series
		classNameToMetadataMap.put('com.cezarykluczynski.stapi.model.series.entity.Series', seriesClassMetadata)
		ClassMetadata comicSeriesClassMetadata = Mock()
		comicSeriesClassMetadata.mappedClass >> ComicSeries
		classNameToMetadataMap.put('com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries', comicSeriesClassMetadata)

		SessionFactory sessionFactory = Mock()
		sessionFactory.allClassMetadata >> classNameToMetadataMap

		Session session = Mock()
		session.sessionFactory >> sessionFactory

		entityManagerMock = Mock()
		entityManagerMock.delegate >> session
	}

	@SuppressWarnings('LineLength')
	void "throws exception when entities can no longer be mapped to unique symbols"() {
		given:
		ClassMetadata classMetadata = Mock()
		classMetadata.mappedClass >> CharSequence
		classNameToMetadataMap.put('java.lang.CharSequence', classMetadata)

		when:
		entityMetadataProvider = new EntityMetadataProvider(entityManagerMock)

		then:
		StapiRuntimeException stapiRuntimeException = thrown(StapiRuntimeException)
		stapiRuntimeException.message == 'Entity class collection no longer suitable for symbol generation. Trying to put symbol CH, but symbol already present.'
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
