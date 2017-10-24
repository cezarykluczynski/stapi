package com.cezarykluczynski.stapi.model.common.service

import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries
import com.cezarykluczynski.stapi.model.content_rating.entity.enums.ContentRatingSystem
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.page.entity.PageAware
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource
import com.cezarykluczynski.stapi.model.reference.entity.enums.ReferenceType
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
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

	private EntityMetadataProvider entityMetadataProvider

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

		entityMetadataProvider = Mock()
		entityMetadataProvider.provideClassNameToMetadataMap() >> classMetadataMap
		entityMetadataProvider.provideClassNameToSymbolMap() >> classNameToSymbolMap

		uidGenerator = new UidGenerator(entityMetadataProvider)
	}

	@Unroll('when #page and #clazz is passed, #uid is generated')
	void "when Page entity and it's base class is passed, valid UID is generated"() {
		expect:
		uidGenerator.generateFromPage(page, clazz) == uid

		where:
		page                                                                        | clazz       | uid
		new Page(pageId: 12L, mediaWikiSource: MediaWikiSource.MEMORY_ALPHA_EN)     | Character   | 'CHMA0000000012'
		new Page(pageId: 23421L, mediaWikiSource: MediaWikiSource.MEMORY_ALPHA_EN)  | Series      | 'SEMA0000023421'
		new Page(pageId: 876L, mediaWikiSource: MediaWikiSource.MEMORY_BETA_EN)     | Character   | 'CHMB0000000876'
		new Page(pageId: 6543L, mediaWikiSource: MediaWikiSource.MEMORY_BETA_EN)    | Series      | 'SEMB0000006543'
		new Page(pageId: 987654L, mediaWikiSource: MediaWikiSource.MEMORY_ALPHA_EN) | ComicSeries | 'CSMA0000987654'
	}

	void "throws exception when there is no mappings available for given class"() {
		when:
		uidGenerator.generateFromPage(new Page(pageId: 1L), NotTrackedPageAware)

		then:
		StapiRuntimeException stapiRuntimeException = thrown(StapiRuntimeException)
		stapiRuntimeException.message == 'No class metadata for entity com.cezarykluczynski.stapi.model.common.service' +
				'.UidGeneratorTest.NotTrackedPageAware.'
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
		StapiRuntimeException stapiRuntimeException = thrown(StapiRuntimeException)
		stapiRuntimeException.message == "Page ID ${UidGenerator.MAX_PAGE_ID + 1} is greater than allowed, cannot guarantee UID uniqueness."
				.toString()
	}

	@Unroll('when pair of #referenceType and #referenceNumber if passed, #uid is returned')
	void "when pair of ReferenceType and reference number is passed, it is converted to uid"() {
		expect:
		uidGenerator.generateForReference(Pair.of(referenceType, referenceNumber)) == uid

		where:
		referenceType      | referenceNumber     | uid
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
		ReferenceType.EAN  | '7332431036161'     | 'E7332431036161'
		ReferenceType.EAN  | '96385074'          | 'EAN80096385074'
		ReferenceType.EAN  | 'A223213FCF'        | null
		ReferenceType.EAN  | '73324310361611'    | null
		ReferenceType.EAN  | '963850741'         | null
		null               | '7332431036161'     | null
		ReferenceType.EAN  | null                | null
		ReferenceType.ISRC | 'CNA130401060'      | 'ISCNA130401060'
		ReferenceType.ISRC | 'CNA1304010601'     | null
		ReferenceType.ISRC | 'CNA13040106'       | null
		null               | 'CNA130401060'      | null
		ReferenceType.ISRC | null                | null
	}

	@Unroll('when #contentRatingSystem and #rating is passed, #uid is returned')
	void "when ContentRatingSystem and rating is passed, it is converted to uid"() {
		expect:
		uidGenerator.generateForContentRating(contentRatingSystem, rating) == uid

		where:
		contentRatingSystem      | rating     | uid
		ContentRatingSystem.ESRB | null       | null
		null                     | 'M'        | null
		ContentRatingSystem.ESRB | 'M'        | 'RATEESRB00000M'
		ContentRatingSystem.ESRB | 'ABCDE'    | 'RATEESRB0ABCDE'
		ContentRatingSystem.ESRB | 'ABCDEF'   | 'RATEESRBABCDEF'
		ContentRatingSystem.ESRB | 'ABCDEFG'  | 'RATEESRABCDEFG'
		ContentRatingSystem.ESRB | 'ABCDEFGH' | 'RATEESABCDEFGH'
		ContentRatingSystem.ESRB | 'M'        | 'RATEESRB00000M'
		ContentRatingSystem.MPAA | 'PG13'     | 'RATEMPAA00PG13'
	}

	@Unroll('when #iso639_1Code is passed, #uid is returned')
	void "when ISO 693-1 code is passed, it is converted to uid"() {
		expect:
		uidGenerator.generateForContentLanguage(iso639_1Code) == uid

		where:
		iso639_1Code | uid
		null         | null
		''           | null
		'A'          | null
		'BBB'        | null
		'en'         | 'LANG00000000EN'
		'EN'         | 'LANG00000000EN'
		'pl'         | 'LANG00000000PL'
		'PL'         | 'LANG00000000PL'
	}

	@Unroll('when #id is passed, #uid is returned for TradingCardSet')
	void "when id is passed, it is converted to uid for TradingCardSet"() {
		expect:
		uidGenerator.generateForTradingCardSet(id) == uid

		where:
		id     | uid
		null   | null
		1      | 'TCS00000000001'
		123456 | 'TCS00000123456'
	}

	@Unroll('when #tradingCardSet and #id is passed, #uid is returned')
	void "when trading card deck UID is passed, trading card deck uid is returned"() {
		expect:
		uidGenerator.generateForTradingCardDeck(tradingCardSet, id) == uid

		where:
		tradingCardSet                            | id | uid
		new TradingCardSet(uid: 'TCS00000000001') | 0  | 'TCD00000000100'
		new TradingCardSet(uid: 'TCS00000123456') | 1  | 'TCD00012345601'
		new TradingCardSet(uid: 'TCS00000123456') | 86 | 'TCD00012345686'
	}

	@Unroll('when #iso3166_1Alpha_2Code is passed, #uid is returned')
	void "when ISO 3166-1 alpha-2 code is passed, it is converted to uid"() {
		expect:
		uidGenerator.generateForCountry(iso3166_1Alpha_2Code) == uid

		where:
		iso3166_1Alpha_2Code | uid
		null                 | null
		''                   | null
		'A'                  | null
		'BBB'                | null
		'GB'                 | 'CU0000000000GB'
		'GB'                 | 'CU0000000000GB'
		'pl'                 | 'CU0000000000PL'
		'PL'                 | 'CU0000000000PL'
	}

	@Unroll('when #id is passed, #uid is returned for TradingCard')
	void "when id is passed, it is converted to uid for TradingCard"() {
		expect:
		uidGenerator.generateForTradingCard(id) == uid

		where:
		id     | uid
		null   | null
		1      | 'TC000000000001'
		123456 | 'TC000000123456'
	}

	@Unroll('when #genreName is passed, #uid is returned for Genre')
	void "when genre name is passed, it is converted to uid for Genre"() {
		expect:
		uidGenerator.generateForGenre(genreName) == uid

		where:
		genreName          | uid
		null               | null
		''                 | null
		'Action'           | 'GENR004BF6C9A4'
		'Action adventure' | 'GENRAAC3395B45'
	}

	@Unroll('when #code is passed, #uid is returned for Platform')
	void "when code is passed, it is converted to uid for Platform"() {
		expect:
		uidGenerator.generateForPlatform(code) == uid

		where:
		code          | uid
		null          | null
		''            | null
		'360'         | 'PLAT0000000360'
		'atari-st'    | 'PLAT000ATARIST'
		'atari2600'   | 'PLAT0ATARI2600'
		'gameboy'     | 'PLAT000GAMEBOY'
		'microvision' | 'PLATICROVISION'
	}

	@Unroll('when #code is passed, #uid is returned for Title')
	void "when code is passed, it is converted to uid for Title"() {
		expect:
		uidGenerator.generateForTitleListItem(page, pageSectionIndex) == uid

		where:
		page                     | pageSectionIndex | uid
		null                     | null             | null
		new Page(pageId: 11)     | null             | null
		null                     | 1                | null
		new Page(pageId: 11)     | 4                | 'TIMA0000001104'
		new Page(pageId: 542623) | 53               | 'TIMA0054262353'
	}

}
