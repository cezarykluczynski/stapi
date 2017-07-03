package com.cezarykluczynski.stapi.etl.template.common.processor

import com.cezarykluczynski.stapi.etl.template.common.factory.ContentLanguageFactory
import com.cezarykluczynski.stapi.model.content_language.entity.ContentLanguage
import spock.lang.Specification

class ContentLanguagesProcessorTest extends Specification {

	private static final String WESTERN_FRISIAN = 'Western Frisian'
	private static final String KLINGON = 'Klingon'
	private static final String VOLAPUK = 'Volapük'
	private static final String CORNISH = 'Cornish'
	private static final String JAVANESE = 'Javanese'
	private static final String LINGALA = 'Lingala'

	private static final String LANGUAGES  = "${WESTERN_FRISIAN}, ${KLINGON}, ${VOLAPUK}<br>${CORNISH}<br />${JAVANESE}<br/>${LINGALA}"

	private ContentLanguageFactory contentLanguageFactoryMock

	private ContentLanguagesProcessor contentLanguagesProcessor

	void setup() {
		contentLanguageFactoryMock = Mock()
		contentLanguagesProcessor = new ContentLanguagesProcessor(contentLanguageFactoryMock)
	}

	void "creates sets of content language from given string"() {
		given:
		ContentLanguage westernFrisianContentLanguage = Mock()
		ContentLanguage volapukContentLanguage = Mock()
		ContentLanguage javaneseContentLanguage = Mock()

		when:
		Set<ContentLanguage> contentLanguageSet = contentLanguagesProcessor.process(LANGUAGES)

		then:
		1 * contentLanguageFactoryMock.createForName(WESTERN_FRISIAN) >> Optional.of(westernFrisianContentLanguage)
		1 * contentLanguageFactoryMock.createForName(KLINGON) >> Optional.empty()
		1 * contentLanguageFactoryMock.createForName(VOLAPUK) >> Optional.of(volapukContentLanguage)
		1 * contentLanguageFactoryMock.createForName(CORNISH) >> Optional.empty()
		1 * contentLanguageFactoryMock.createForName(JAVANESE) >> Optional.of(javaneseContentLanguage)
		1 * contentLanguageFactoryMock.createForName(LINGALA) >> Optional.empty()
		0 * _
		contentLanguageSet.size() == 3
		contentLanguageSet.contains westernFrisianContentLanguage
		contentLanguageSet.contains volapukContentLanguage
		contentLanguageSet.contains javaneseContentLanguage
	}

}
