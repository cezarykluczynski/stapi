package com.cezarykluczynski.stapi.model.common.service

import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.google.common.collect.Maps
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.metadata.ClassMetadata
import spock.lang.Specification

import javax.persistence.EntityManager

class EntityMatadataProviderTest extends Specification {

	private EntityManager entityManagerMock

	private EntityMatadataProvider entityMatadataProvider

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
		classNameToMetadataMap.put('com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries', comicSeriesClassMetadata)

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
		entityMatadataProvider = new EntityMatadataProvider(entityManagerMock)

		then:
		RuntimeException runtimeException = thrown(RuntimeException)
		runtimeException.message == 'Entity class collection no longer suitable for symbol generation. Trying to put symbol CH, but symbol already present.'
	}

	void "provides class name to symbol map"() {
		when:
		entityMatadataProvider = new EntityMatadataProvider(entityManagerMock)
		Map<String, String> classNameToSymbolMap = entityMatadataProvider.provideClassNameToSymbolMap()

		then:
		classNameToSymbolMap['com.cezarykluczynski.stapi.model.series.entity.Series'] == 'SE'
		classNameToSymbolMap['com.cezarykluczynski.stapi.model.character.entity.Character'] == 'CH'
		classNameToSymbolMap['com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries'] == 'CS'
	}

	void "provides class name to metadata map"() {
		when:
		entityMatadataProvider = new EntityMatadataProvider(entityManagerMock)
		Map<String, ClassMetadata> classNameToMetadataMapOutput = entityMatadataProvider.provideClassNameToMetadataMap()

		then:
		classNameToMetadataMapOutput == classNameToMetadataMap
	}

}
