package com.cezarykluczynski.stapi.etl.season.creation.processor

import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page
import com.cezarykluczynski.stapi.etl.mediawiki.api.PageApi
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import spock.lang.Specification

class SeasonNumberOfEpisodesProcessorTest extends Specification {

    private static final String TITLE = 'TITLE'

	private PageApi pageApiMock
	private TemplateFinder templateFinderMock
	private SeasonNumberOfEpisodesProcessor seasonNumberOfEpisodesProcessor

    void setup() {
        pageApiMock = Mock()
        templateFinderMock = Mock()
        seasonNumberOfEpisodesProcessor = new SeasonNumberOfEpisodesProcessor(pageApiMock, templateFinderMock)
    }

    void "returns null when template is not found"() {
        given:
        Page page = new Page(title: TITLE)

		when:
		Integer numberOfEpisodes = seasonNumberOfEpisodesProcessor.process(page)

		then:
		1 * pageApiMock.getTemplate(TITLE, MediaWikiSource.MEMORY_ALPHA_EN) >> null
		0 * _
		numberOfEpisodes == null
    }

	void "returns null when there are no episodes related templates"() {
		given:
		Page page = new Page(title: TITLE)
		Page templatePage = new Page()

		when:
		Integer numberOfEpisodes = seasonNumberOfEpisodesProcessor.process(page)

		then:
		1 * pageApiMock.getTemplate(TITLE, MediaWikiSource.MEMORY_ALPHA_EN) >> templatePage
		1 * templateFinderMock.findTemplates(templatePage, TemplateTitle.ROW) >> []
		0 * _
		numberOfEpisodes == null
	}

	void "returns number of episodes when episodes related templates are found"() {
		given:
		Page page = new Page(title: TITLE)
		Template rowTemplate1 = new Template(title: TemplateTitle.ROW)
		Template rowTemplate2 = new Template(title: TemplateTitle.ROW)
		Page templatePage = new Page()

		when:
		Integer numberOfEpisodes = seasonNumberOfEpisodesProcessor.process(page)

		then:
		1 * pageApiMock.getTemplate(TITLE, MediaWikiSource.MEMORY_ALPHA_EN) >> templatePage
		1 * templateFinderMock.findTemplates(templatePage, TemplateTitle.ROW) >> [rowTemplate1, rowTemplate2]
		0 * _
		numberOfEpisodes == 2
	}

}
