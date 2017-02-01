package com.cezarykluczynski.stapi.model.common.service

import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.page.entity.PageAware
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.google.common.collect.Maps
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.metadata.ClassMetadata
import spock.lang.Specification
import spock.lang.Unroll

import javax.persistence.EntityManager

class GuidGeneratorTest extends Specification {

	@SuppressWarnings('GetterMethodCouldBeProperty')
	static class NotTrackedPageAware implements PageAware {

		@Override
		Page getPage() {
			null
		}

		@Override
		void setPage(Page page) {
		}

		@Override
		String getGuid() {
			null
		}

		@Override
		void setGuid(String guid) {
		}

	}

	private EntityManager entityManagerMock

	private GuidGenerator guidGenerator

	private Map<String, ClassMetadata> classMetadataMap

	void setup() {
		classMetadataMap = Maps.newHashMap()

		ClassMetadata characterClassMetadata = Mock(ClassMetadata)
		characterClassMetadata.mappedClass >> Character
		classMetadataMap.put('com.cezarykluczynski.stapi.model.character.entity.Character', characterClassMetadata)
		ClassMetadata seriesClassMetadata = Mock(ClassMetadata)
		seriesClassMetadata.mappedClass >> Series
		classMetadataMap.put('com.cezarykluczynski.stapi.model.series.entity.Series', seriesClassMetadata)
		ClassMetadata comicSeriesClassMetadata = Mock(ClassMetadata)
		comicSeriesClassMetadata.mappedClass >> ComicSeries
		classMetadataMap.put('com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries', comicSeriesClassMetadata)

		SessionFactory sessionFactory = Mock(SessionFactory)
		sessionFactory.allClassMetadata >> classMetadataMap

		Session session = Mock()
		session.sessionFactory >> sessionFactory

		entityManagerMock = Mock(EntityManager)
		entityManagerMock.delegate >> session

		guidGenerator = new GuidGenerator(entityManagerMock)
	}

	@Unroll('when #page and #clazz is passed, #guid is generated')
	void "when Page entity and it's base class is passed, valid GUID is generated"() {
		expect:
		guid == guidGenerator.generateFromPage(page, clazz)

		where:
		guid             | page                                                                        | clazz
		'CHMA0000000012' | new Page(pageId: 12L, mediaWikiSource: MediaWikiSource.MEMORY_ALPHA_EN)     | Character
		'SEMA0000023421' | new Page(pageId: 23421L, mediaWikiSource: MediaWikiSource.MEMORY_ALPHA_EN)  | Series
		'CHMB0000000876' | new Page(pageId: 876L, mediaWikiSource: MediaWikiSource.MEMORY_BETA_EN)     | Character
		'SEMB0000006543' | new Page(pageId: 6543L, mediaWikiSource: MediaWikiSource.MEMORY_BETA_EN)    | Series
		'CSMA0000987654' | new Page(pageId: 987654L, mediaWikiSource: MediaWikiSource.MEMORY_ALPHA_EN) | ComicSeries
	}

	void "throws exception when there is no mappings available for given class"() {
		when:
		guidGenerator.generateFromPage(new Page(pageId: 1L), NotTrackedPageAware)

		then:
		RuntimeException runtimeException = thrown(RuntimeException)
		runtimeException.message == 'No class metadata for entity com.cezarykluczynski.stapi.model.common.service.GuidGeneratorTest.NotTrackedPageAware.'
	}

	void "throws exception when page is null"() {
		when:
		guidGenerator.generateFromPage(null, Character)

		then:
		thrown(NullPointerException)
	}

	void "throws exception when page ID is null"() {
		when:
		guidGenerator.generateFromPage(new Page(), Character)

		then:
		thrown(NullPointerException)
	}

	void "throws exception when page ID is too large"() {
		when:
		guidGenerator.generateFromPage(new Page(pageId: GuidGenerator.MAX_PAGE_ID + 1), Character)

		then:
		RuntimeException runtimeException = thrown(RuntimeException)
		runtimeException.message == "Page ID ${GuidGenerator.MAX_PAGE_ID + 1} is greater than allowed, cannot guarantee GUID uniqueness.".toString()
	}

	void "throws exception when entities can no longer be mapped to unique symbols"() {
		given:
		ClassMetadata classMetadata = Mock(ClassMetadata)
		classMetadata.mappedClass >> CharSequence
		classMetadataMap.put('java.lang.CharSequence', classMetadata)

		when:
		guidGenerator = new GuidGenerator(entityManagerMock)

		then:
		RuntimeException runtimeException = thrown(RuntimeException)
		runtimeException.message == 'Entity class collection no longer suitable for symbol generation. Trying to put symbol CH, but symbol already present.'
	}

}
