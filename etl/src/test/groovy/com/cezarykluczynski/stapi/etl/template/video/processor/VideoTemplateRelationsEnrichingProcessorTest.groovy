package com.cezarykluczynski.stapi.etl.template.video.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.reference.processor.ReferencesFromTemplatePartProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.ContentLanguagesProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.ContentRatingsProcessor
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplateParameter
import com.cezarykluczynski.stapi.model.content_language.entity.ContentLanguage
import com.cezarykluczynski.stapi.model.content_rating.entity.ContentRating
import com.cezarykluczynski.stapi.model.reference.entity.Reference
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import spock.lang.Specification

class VideoTemplateRelationsEnrichingProcessorTest extends Specification {

	private static final String LANGUAGE = 'LANGUAGE'
	private static final String SUBTITLES = 'SUBTITLES'
	private static final String DUBBED = 'DUBBED'

	private ContentRatingsProcessor contentRatingsProcessorMock

	private ReferencesFromTemplatePartProcessor referencesFromTemplatePartProcessorMock

	private ContentLanguagesProcessor contentLanguagesProcessorMock

	private VideoTemplateRelationsEnrichingProcessor videoTemplateRelationsEnrichingProcessor

	void setup() {
		contentRatingsProcessorMock = Mock()
		referencesFromTemplatePartProcessorMock = Mock()
		contentLanguagesProcessorMock = Mock()
		videoTemplateRelationsEnrichingProcessor = new VideoTemplateRelationsEnrichingProcessor(contentRatingsProcessorMock,
				referencesFromTemplatePartProcessorMock, contentLanguagesProcessorMock)
	}

	void "when rating part is found, ContentRatingProcessor is used to process it"() {
		given:
		Template.Part templatePart = new Template.Part(key: VideoTemplateParameter.RATING)
		Template sidebarVideoTemplate = new Template(parts: Lists.newArrayList(templatePart))
		ContentRating contentRating1 = Mock()
		ContentRating contentRating2 = Mock()
		VideoTemplate videoTemplate = new VideoTemplate()

		when:
		videoTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoTemplate, videoTemplate))

		then:
		1 * contentRatingsProcessorMock.process(templatePart) >> Sets.newHashSet(contentRating1, contentRating2)
		0 * _
		videoTemplate.ratings.contains contentRating1
		videoTemplate.ratings.contains contentRating2
	}

	void "when language part is found, ContentLanguagesProcessor is used to process it"() {
		given:
		Template sidebarVideoTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: VideoTemplateParameter.LANGUAGE,
				value: LANGUAGE)))
		ContentLanguage contentLanguage1 = Mock()
		ContentLanguage contentLanguage2 = Mock()
		VideoTemplate videoTemplate = new VideoTemplate()

		when:
		videoTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoTemplate, videoTemplate))

		then:
		1 * contentLanguagesProcessorMock.process(LANGUAGE) >> Sets.newHashSet(contentLanguage1, contentLanguage2)
		0 * _
		videoTemplate.languages.contains contentLanguage1
		videoTemplate.languages.contains contentLanguage2
	}

	void "when subtitles part is found, ContentLanguagesProcessor is used to process it"() {
		given:
		Template sidebarVideoTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: VideoTemplateParameter.SUBTITLES,
				value: SUBTITLES)))
		ContentLanguage contentLanguage1 = Mock()
		ContentLanguage contentLanguage2 = Mock()
		VideoTemplate videoTemplate = new VideoTemplate()

		when:
		videoTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoTemplate, videoTemplate))

		then:
		1 * contentLanguagesProcessorMock.process(SUBTITLES) >> Sets.newHashSet(contentLanguage1, contentLanguage2)
		0 * _
		videoTemplate.languagesSubtitles.contains contentLanguage1
		videoTemplate.languagesSubtitles.contains contentLanguage2
	}

	void "when dubbed part is found, ContentLanguagesProcessor is used to process it"() {
		given:
		Template sidebarVideoTemplate = new Template(parts: Lists.newArrayList(new Template.Part(
				key: VideoTemplateParameter.DUBBED,
				value: DUBBED)))
		ContentLanguage contentLanguage1 = Mock()
		ContentLanguage contentLanguage2 = Mock()
		VideoTemplate videoTemplate = new VideoTemplate()

		when:
		videoTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoTemplate, videoTemplate))

		then:
		1 * contentLanguagesProcessorMock.process(DUBBED) >> Sets.newHashSet(contentLanguage1, contentLanguage2)
		0 * _
		videoTemplate.languagesDubbed.contains contentLanguage1
		videoTemplate.languagesDubbed.contains contentLanguage2
	}

	void "when reference part is found, ReferencesFromTemplatePartProcessor is used to process it"() {
		given:
		Template.Part templatePart = new Template.Part(key: VideoTemplateParameter.REFERENCE)
		Template sidebarVideoTemplate = new Template(parts: Lists.newArrayList(templatePart))
		Reference reference1 = Mock()
		Reference reference2 = Mock()
		VideoTemplate videoTemplate = new VideoTemplate()

		when:
		videoTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarVideoTemplate, videoTemplate))

		then:
		1 * referencesFromTemplatePartProcessorMock.process(templatePart) >> Sets.newHashSet(reference1, reference2)
		0 * _
		videoTemplate.references.contains reference1
		videoTemplate.references.contains reference2
	}

}
