package com.cezarykluczynski.stapi.model.common.service

import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.page.entity.PageAware
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource
import com.cezarykluczynski.stapi.model.reference.entity.enums.ReferenceType
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.google.common.collect.Maps
import org.apache.commons.lang3.tuple.Pair
import org.hibernate.metadata.ClassMetadata
import spock.lang.Specification
import spock.lang.Unroll

class UidGeneratorTest extends Specification {

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
		String getUid() {
			null
		}

		@Override
		void setUid(String uid) {
		}

	}

	private EntityMatadataProvider entityMatadataProvider

	private UidGenerator uidGenerator

	private Map<String, ClassMetadata> classMetadataMap

	private Map<String, String> classNameToSymbolMap = Maps.newHashMap()

	void setup() {
		classMetadataMap = Maps.newHashMap()

		ClassMetadata characterClassMetadata = Mock()
		characterClassMetadata.mappedClass >> Character
		classMetadataMap.put('com.cezarykluczynski.stapi.model.character.entity.Character', characterClassMetadata)
		ClassMetadata seriesClassMetadata = Mock()
		seriesClassMetadata.mappedClass >> Series
		classMetadataMap.put('com.cezarykluczynski.stapi.model.series.entity.Series', seriesClassMetadata)
		ClassMetadata comicSeriesClassMetadata = Mock()
		comicSeriesClassMetadata.mappedClass >> ComicSeries
		classMetadataMap.put('com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries', comicSeriesClassMetadata)

		classNameToSymbolMap = Maps.newHashMap()
		classNameToSymbolMap.put('com.cezarykluczynski.stapi.model.character.entity.Character', 'CH')
		classNameToSymbolMap.put('com.cezarykluczynski.stapi.model.series.entity.Series', 'SE')
		classNameToSymbolMap.put('com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries', 'CS')

		entityMatadataProvider = Mock()
		entityMatadataProvider.provideClassNameToMetadataMap() >> classMetadataMap
		entityMatadataProvider.provideClassNameToSymbolMap() >> classNameToSymbolMap

		uidGenerator = new UidGenerator(entityMatadataProvider)
	}

	@Unroll('when #page and #clazz is passed, #uid is generated')
	void "when Page entity and it's base class is passed, valid UID is generated"() {
		expect:
		uid == uidGenerator.generateFromPage(page, clazz)

		where:
		uid              | page                                                                        | clazz
		'CHMA0000000012' | new Page(pageId: 12L, mediaWikiSource: MediaWikiSource.MEMORY_ALPHA_EN)     | Character
		'SEMA0000023421' | new Page(pageId: 23421L, mediaWikiSource: MediaWikiSource.MEMORY_ALPHA_EN)  | Series
		'CHMB0000000876' | new Page(pageId: 876L, mediaWikiSource: MediaWikiSource.MEMORY_BETA_EN)     | Character
		'SEMB0000006543' | new Page(pageId: 6543L, mediaWikiSource: MediaWikiSource.MEMORY_BETA_EN)    | Series
		'CSMA0000987654' | new Page(pageId: 987654L, mediaWikiSource: MediaWikiSource.MEMORY_ALPHA_EN) | ComicSeries
	}

	void "throws exception when there is no mappings available for given class"() {
		when:
		uidGenerator.generateFromPage(new Page(pageId: 1L), NotTrackedPageAware)

		then:
		RuntimeException runtimeException = thrown(RuntimeException)
		runtimeException.message == 'No class metadata for entity com.cezarykluczynski.stapi.model.common.service.UidGeneratorTest.NotTrackedPageAware.'
	}

	void "throws exception when page is null"() {
		when:
		uidGenerator.generateFromPage(null, Character)

		then:
		thrown(NullPointerException)
	}

	void "throws exception when page ID is null"() {
		when:
		uidGenerator.generateFromPage(new Page(), Character)

		then:
		thrown(NullPointerException)
	}

	void "throws exception when page ID is too large"() {
		when:
		uidGenerator.generateFromPage(new Page(pageId: UidGenerator.MAX_PAGE_ID + 1), Character)

		then:
		RuntimeException runtimeException = thrown(RuntimeException)
		runtimeException.message == "Page ID ${UidGenerator.MAX_PAGE_ID + 1} is greater than allowed, cannot guarantee UID uniqueness.".toString()
	}

	@Unroll('when pair of #referenceType and #referenceNumber if passed, #result is returned')
	void "when pair of ReferenceType and reference number is passed, it is converted to uid"() {
		expect:
		result == uidGenerator.generateFromReference(Pair.of(referenceType, referenceNumber))

		where:
		referenceType      | referenceNumber     | result
		ReferenceType.ASIN | 'B001PUYIGQ'        | 'ASINB001PUYIGQ'
		ReferenceType.ASIN | 'B223213FCF'        | 'ASINB223213FCF'
		ReferenceType.ASIN | '232342342X'        | 'ASIN232342342X'
		ReferenceType.ASIN | '2323423421'        | 'ASIN2323423421'
		ReferenceType.ASIN | 'A223213FCF'        | null
		null               | 'B001PUYIGQ'        | null
		ReferenceType.ASIN | null                | null
		ReferenceType.ISBN | '9971502100'        | 'ISBN9971502100'
		ReferenceType.ISBN | '960 425 059 0'     | 'ISBN9604250590'
		ReferenceType.ISBN | '99921-58-10-6'     | 'ISBN9992158106'
		ReferenceType.ISBN | '9780306406157'     | 'I9780306406157'
		ReferenceType.ISBN | '978-0-306-40615-6' | 'I9780306406156'
		ReferenceType.ISBN | '978406154'         | null
		ReferenceType.ISBN | '978-0-306-4061546' | null
		null               | '9971502100'        | null
		ReferenceType.ISBN | null                | null
	}

}
