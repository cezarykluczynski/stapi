package com.cezarykluczynski.stapi.etl.template.video_game.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor
import com.cezarykluczynski.stapi.etl.reference.processor.ReferencesFromTemplatePartProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.ContentRatingsProcessor
import com.cezarykluczynski.stapi.etl.template.video_game.dto.VideoGameTemplate
import com.cezarykluczynski.stapi.etl.template.video_game.dto.VideoGameTemplateParameter
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.content_rating.entity.ContentRating
import com.cezarykluczynski.stapi.model.genre.entity.Genre
import com.cezarykluczynski.stapi.model.platform.entity.Platform
import com.cezarykluczynski.stapi.model.reference.entity.Reference
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import spock.lang.Specification

class VideoGameTemplateRelationsEnrichingProcessorTest extends Specification {

	private static final String PUBLISHER = 'PUBLISHER'
	private static final String DEVELOPER = 'DEVELOPER'
	private static final String GENRES = 'GENRES'

	private WikitextToEntitiesProcessor wikitextToEntitiesProcessorMock

	private VideoGameTemplatePlatformsProcessor videoGameTemplatePlatformsProcessorMock

	private VideoGameTemplateGenresProcessor videoGameTemplateGenresProcessorMock

	private ContentRatingsProcessor contentRatingsProcessorMock

	private ReferencesFromTemplatePartProcessor referencesFromTemplatePartProcessorMock

	private VideoGameTemplateRelationsEnrichingProcessor videoGameTemplateRelationsEnrichingProcessor

	void setup() {
		wikitextToEntitiesProcessorMock = Mock()
		videoGameTemplatePlatformsProcessorMock = Mock()
		videoGameTemplateGenresProcessorMock = Mock()
		contentRatingsProcessorMock = Mock()
		referencesFromTemplatePartProcessorMock = Mock()
		videoGameTemplateRelationsEnrichingProcessor = new VideoGameTemplateRelationsEnrichingProcessor(wikitextToEntitiesProcessorMock,
				videoGameTemplatePlatformsProcessorMock, videoGameTemplateGenresProcessorMock, contentRatingsProcessorMock,
				referencesFromTemplatePartProcessorMock)
	}

	void "when publisher part is found, WikitextToEntitiesProcessor is used to process it"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: VideoGameTemplateParameter.PUBLISHER,
				value: PUBLISHER)
		Template sidebarVideoGameTemplate = new Template(parts: Lists.newArrayList(templatePart))
		Company company1 = Mock()
		Company company2 = Mock()
		VideoGameTemplate videoGameTemplate = new VideoGameTemplate()

		when:
		videoGameTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoGameTemplate, videoGameTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findCompanies(PUBLISHER) >> Lists.newArrayList(company1, company2)
		0 * _
		videoGameTemplate.publishers.contains company1
		videoGameTemplate.publishers.contains company2
	}

	void "when developer part is found, WikitextToEntitiesProcessor is used to process it"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: VideoGameTemplateParameter.DEVELOPER,
				value: DEVELOPER)
		Template sidebarVideoGameTemplate = new Template(parts: Lists.newArrayList(templatePart))
		Company company1 = Mock()
		Company company2 = Mock()
		VideoGameTemplate videoGameTemplate = new VideoGameTemplate()

		when:
		videoGameTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoGameTemplate, videoGameTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findCompanies(DEVELOPER) >> Lists.newArrayList(company1, company2)
		0 * _
		videoGameTemplate.developers.contains company1
		videoGameTemplate.developers.contains company2
	}

	void "when platform part is found, VideoGameTemplatePlatformsProcessor is used to process it"() {
		given:
		Template.Part templatePart = new Template.Part(key: VideoGameTemplateParameter.PLATFORM)
		Template sidebarVideoGameTemplate = new Template(parts: Lists.newArrayList(templatePart))
		Platform platform1 = Mock()
		Platform platform2 = Mock()
		VideoGameTemplate videoGameTemplate = new VideoGameTemplate()

		when:
		videoGameTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoGameTemplate, videoGameTemplate))

		then:
		1 * videoGameTemplatePlatformsProcessorMock.process(templatePart) >> Sets.newHashSet(platform1, platform2)
		0 * _
		videoGameTemplate.platforms.contains platform1
		videoGameTemplate.platforms.contains platform2
	}

	void "when genres part is found, VideoGameTemplateGenresProcessor is used to process it"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: VideoGameTemplateParameter.GENRES,
				value: GENRES)
		Template sidebarVideoGameTemplate = new Template(parts: Lists.newArrayList(templatePart))
		Genre genre1 = Mock()
		Genre genre2 = Mock()
		VideoGameTemplate videoGameTemplate = new VideoGameTemplate()

		when:
		videoGameTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoGameTemplate, videoGameTemplate))

		then:
		1 * videoGameTemplateGenresProcessorMock.process(GENRES) >> Sets.newHashSet(genre1, genre2)
		0 * _
		videoGameTemplate.genres.contains genre1
		videoGameTemplate.genres.contains genre2
	}

	void "when rating part is found, ContentRatingProcessor is used to process it"() {
		given:
		Template.Part templatePart = new Template.Part(key: VideoGameTemplateParameter.RATING)
		Template sidebarVideoGameTemplate = new Template(parts: Lists.newArrayList(templatePart))
		ContentRating contentRating1 = Mock()
		ContentRating contentRating2 = Mock()
		VideoGameTemplate videoGameTemplate = new VideoGameTemplate()

		when:
		videoGameTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoGameTemplate, videoGameTemplate))

		then:
		1 * contentRatingsProcessorMock.process(templatePart) >> Sets.newHashSet(contentRating1, contentRating2)
		0 * _
		videoGameTemplate.ratings.contains contentRating1
		videoGameTemplate.ratings.contains contentRating2
	}

	void "when reference part is found, ReferencesFromTemplatePartProcessor is used to process it"() {
		given:
		Template.Part templatePart = new Template.Part(key: VideoGameTemplateParameter.REFERENCE)
		Template sidebarVideoGameTemplate = new Template(parts: Lists.newArrayList(templatePart))
		Reference reference1 = Mock()
		Reference reference2 = Mock()
		VideoGameTemplate videoGameTemplate = new VideoGameTemplate()

		when:
		videoGameTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoGameTemplate, videoGameTemplate))

		then:
		1 * referencesFromTemplatePartProcessorMock.process(templatePart) >> Sets.newHashSet(reference1, reference2)
		0 * _
		videoGameTemplate.references.contains reference1
		videoGameTemplate.references.contains reference2
	}

}
