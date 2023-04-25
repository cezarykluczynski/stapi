package com.cezarykluczynski.stapi.etl.template.common.processor

import com.cezarykluczynski.stapi.etl.mediawiki.api.PageApi
import com.cezarykluczynski.stapi.etl.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template
import spock.lang.Specification

class NumberOfEpisodesProcessorTest extends Specification {

    private static final String ORIGINAL_TITLE = 'ORIGINAL_TITLE'
    private static final String WIKITEXT = 'WIKITEXT'

    private PageApi pageApiMock
    private WikitextApi wikitextApiMock
    private NumberOfEpisodesProcessor numberOfEpisodesProcessor

    void setup() {
        pageApiMock = Mock()
        wikitextApiMock = Mock()
        numberOfEpisodesProcessor = new NumberOfEpisodesProcessor(pageApiMock, wikitextApiMock)
    }

    void "returns null for empty template list"() {
        expect:
        numberOfEpisodesProcessor.process(new Template.Part()) == null
    }

    void "returns null for when numbers of episodes count not be parsed" () {
        given:
        Template.Part item = new Template.Part()
        item.setTemplates([new Template(originalTitle: ORIGINAL_TITLE)])
        Page page = new Page(wikitext: WIKITEXT)

        when:
        Integer numberOfEpisodes = numberOfEpisodesProcessor.process(item)

        then:
        1 * pageApiMock.getTemplate(ORIGINAL_TITLE, MediaWikiSource.MEMORY_ALPHA_EN) >> page
        1 * wikitextApiMock.getWikitextWithoutNoInclude(WIKITEXT) >> 'gibberish'
        0 * _
        numberOfEpisodes == null
    }

    void "returns number of episodes" () {
        given:
        Template.Part item = new Template.Part()
        item.setTemplates([new Template(originalTitle: ORIGINAL_TITLE)])
        Page page = new Page(wikitext: WIKITEXT)

        when:
        Integer numberOfEpisodes = numberOfEpisodesProcessor.process(item)

        then:
        1 * pageApiMock.getTemplate(ORIGINAL_TITLE, MediaWikiSource.MEMORY_ALPHA_EN) >> page
        1 * wikitextApiMock.getWikitextWithoutNoInclude(WIKITEXT) >> '22'
        0 * _
        numberOfEpisodes == 22
    }

}
