package com.cezarykluczynski.stapi.etl.template.common.processor

import com.cezarykluczynski.stapi.etl.template.common.factory.ContentRatingFactory
import com.cezarykluczynski.stapi.etl.template.service.TemplateFilter
import com.cezarykluczynski.stapi.model.content_rating.entity.ContentRating
import com.cezarykluczynski.stapi.model.content_rating.entity.enums.ContentRatingSystem
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import spock.lang.Specification

class ContentRatingsProcessorTest extends Specification {

	private static final String NICAM_VALUE = 'NICAM_VALUE'
	private static final String MPAA_VALUE = 'MPAA_VALUE'

	private TemplateFilter templateFilterMock

	private ContentRatingFactory contentRatingFactoryMock

	private ContentRatingsProcessor contentRatingsProcessor

	void setup() {
		templateFilterMock = Mock()
		contentRatingFactoryMock = Mock()
		contentRatingsProcessor = new ContentRatingsProcessor(templateFilterMock, contentRatingFactoryMock)
	}

	void "returns empty set when no template can be found"() {
		given:
		Template.Part templatePart = new Template.Part()

		when:
		Set<ContentRating> contentRatingSet = contentRatingsProcessor.process(templatePart)

		then:
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateTitle.VIDEO_RATINGS, TemplateTitle.VR, TemplateTitle.GAME_RATINGS,
				TemplateTitle.GR) >> Lists.newArrayList()
		contentRatingSet.empty
	}

	void "adds ratings that can be created"() {
		given:
		List<Template> templateList = Lists.newArrayList(
				new Template(parts: Lists.newArrayList(
						new Template.Part(),
						new Template.Part(key: 'nicam', value: NICAM_VALUE),
						new Template.Part(key: 'MPAA', value: MPAA_VALUE),
						new Template.Part(key: 'no a rating system', value: ''))))
		Template.Part templatePart = new Template.Part(templates: templateList)
		ContentRating nicamContentRating = Mock()

		when:
		Set<ContentRating> contentRatingSet = contentRatingsProcessor.process(templatePart)

		then:
		1 * templateFilterMock.filterByTitle(templateList, TemplateTitle.VIDEO_RATINGS, TemplateTitle.VR, TemplateTitle.GAME_RATINGS,
				TemplateTitle.GR) >> templateList
		1 * contentRatingFactoryMock.create(ContentRatingSystem.NICAM, NICAM_VALUE) >> nicamContentRating
		1 * contentRatingFactoryMock.create(ContentRatingSystem.MPAA, MPAA_VALUE) >> null
		!contentRatingSet.empty
		contentRatingSet[0] == nicamContentRating
	}

}
