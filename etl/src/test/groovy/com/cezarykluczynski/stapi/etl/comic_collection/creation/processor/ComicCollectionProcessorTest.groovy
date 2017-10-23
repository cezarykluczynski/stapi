package com.cezarykluczynski.stapi.etl.comic_collection.creation.processor

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicCollectionTemplate
import com.cezarykluczynski.stapi.etl.template.comics.processor.collection.ComicCollectionTemplatePageProcessor
import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class ComicCollectionProcessorTest extends Specification {

	private PageHeaderProcessor pageHeaderProcessorMock

	private ComicCollectionTemplatePageProcessor comicCollectionTemplatePageProcessorMock

	private ComicCollectionTemplateProcessor comicCollectionTemplateProcessorMock

	private ComicCollectionProcessor comicCollectionProcessor

	void setup() {
		pageHeaderProcessorMock = Mock()
		comicCollectionTemplatePageProcessorMock = Mock()
		comicCollectionTemplateProcessorMock = Mock()
		comicCollectionProcessor = new ComicCollectionProcessor(pageHeaderProcessorMock, comicCollectionTemplatePageProcessorMock,
				comicCollectionTemplateProcessorMock)
	}

	void "converts PageHeader to ComicCollection"() {
		given:
		PageHeader pageHeader = new PageHeader()
		Page page = new Page()
		ComicCollectionTemplate comicCollectionTemplate = new ComicCollectionTemplate()
		ComicCollection comicCollection = new ComicCollection()

		when:
		ComicCollection comicCollectionOutput = comicCollectionProcessor.process(pageHeader)

		then: 'processors are used in right order'
		1 * pageHeaderProcessorMock.process(pageHeader) >> page

		and:
		1 * comicCollectionTemplatePageProcessorMock.process(page) >> comicCollectionTemplate

		and:
		1 * comicCollectionTemplateProcessorMock.process(comicCollectionTemplate) >> comicCollection

		then: 'last processor output is returned'
		comicCollectionOutput == comicCollection
	}

}
