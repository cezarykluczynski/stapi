package com.cezarykluczynski.stapi.etl.template.comics.processor.collection

import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicCollectionTemplate
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplate
import com.cezarykluczynski.stapi.etl.template.comics.processor.ComicsTemplatePageProcessor
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.comics.entity.Comics
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.google.common.collect.Sets
import spock.lang.Specification

class ComicCollectionTemplatePageProcessorTest extends Specification {

	private ComicsTemplatePageProcessor comicsTemplatePageProcessorMock

	private ComicsTemplateToComicCollectionTemplateProcessor comicsTemplateToComicCollectionTemplateProcessorMock

	private ComicCollectionTemplateWikitextComicsProcessor comicCollectionTemplateWikitextComicsProcessorMock

	private ComicCollectionTemplatePageProcessor comicCollectionTemplatePageProcessor

	void setup() {
		comicsTemplatePageProcessorMock = Mock()
		comicsTemplateToComicCollectionTemplateProcessorMock = Mock()
		comicCollectionTemplateWikitextComicsProcessorMock = Mock()
		comicCollectionTemplatePageProcessor = new ComicCollectionTemplatePageProcessor(comicsTemplatePageProcessorMock,
				comicsTemplateToComicCollectionTemplateProcessorMock, comicCollectionTemplateWikitextComicsProcessorMock)
	}

	void "returns null when ComicsTemplatePageProcessor returns null"() {
		given:
		Page page = new Page()

		when:
		ComicCollectionTemplate comicCollectionTemplate = comicCollectionTemplatePageProcessor.process(page)

		then:
		1 * comicsTemplatePageProcessorMock.process(page) >> null
		0 * _
		comicCollectionTemplate == null
	}

	void "maps not null ComicsTemplate to ComicCollectionTemplate, then adds comics from ComicCollectionTemplateWikitextComicsProcessor"() {
		given:
		Page page = new Page()
		ComicsTemplate comicsTemplate = new ComicsTemplate()
		ComicCollectionTemplate comicCollectionTemplate = new ComicCollectionTemplate()
		Comics comics1 = Mock()
		Comics comics2 = Mock()
		Character character1 = Mock()
		Character character2 = Mock()

		when:
		ComicCollectionTemplate comicCollectionTemplateOutput = comicCollectionTemplatePageProcessor.process(page)

		then:
		1 * comicsTemplatePageProcessorMock.process(page) >> comicsTemplate
		1 * comicsTemplateToComicCollectionTemplateProcessorMock.process(comicsTemplate) >> comicCollectionTemplate
		1 * comicCollectionTemplateWikitextComicsProcessorMock.process(page) >> Sets.newHashSet(comics1, comics2)
		1 * comics1.characters >> Sets.newHashSet(character1)
		1 * comics2.characters >> Sets.newHashSet(character2)
		0 * _
		comicCollectionTemplateOutput == comicCollectionTemplate
		comicCollectionTemplateOutput.comics.size() == 2
		comicCollectionTemplateOutput.comics.contains comics1
		comicCollectionTemplateOutput.comics.contains comics2
		comicCollectionTemplateOutput.characters.size() == 2
		comicCollectionTemplateOutput.characters.contains character1
		comicCollectionTemplateOutput.characters.contains character2
	}

}
