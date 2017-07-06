package com.cezarykluczynski.stapi.etl.template.video.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.AbstractTemplateProcessorTest
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplate
import com.cezarykluczynski.stapi.etl.template.video.dto.VideoTemplateParameter
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.ReflectionTestUtils
import com.google.common.collect.Lists
import spock.lang.Unroll

class VideoTemplateDigitalFormatsEnrichingProcessorTest extends AbstractTemplateProcessorTest {

	private static final String YES = 'yes'
	private static final String NO = 'no'

	private VideoTemplateDigitalFormatsEnrichingProcessor videoTemplateDigitalFormatsEnrichingProcessor

	void setup() {
		videoTemplateDigitalFormatsEnrichingProcessor = new VideoTemplateDigitalFormatsEnrichingProcessor()
	}

	@Unroll('set #flagName flag when #template is passed; expect #trueBooleans not null fields')
	@SuppressWarnings('LineLength')
	void "sets flagName when page is passed"() {
		given:
		VideoTemplate videoTemplate = new VideoTemplate()

		expect:
		videoTemplateDigitalFormatsEnrichingProcessor.enrich(EnrichablePair.of(template, videoTemplate))
		videoTemplate[flagName] == flag
		ReflectionTestUtils.getNumberOfTrueBooleanFields(videoTemplate) == trueBooleans

		where:
		template                                                                                      | flagName                       | flag  | trueBooleans
		new Template(parts: Lists.newArrayList())                                                     | 'amazonDigitalRelease'         | null  | 0
		new Template(parts: Lists.newArrayList())                                                     | 'dailymotionDigitalRelease'    | null  | 0
		new Template(parts: Lists.newArrayList())                                                     | 'googlePlayDigitalRelease'     | null  | 0
		new Template(parts: Lists.newArrayList())                                                     | 'iTunesDigitalRelease'         | null  | 0
		new Template(parts: Lists.newArrayList())                                                     | 'ultraVioletDigitalRelease'    | null  | 0
		new Template(parts: Lists.newArrayList())                                                     | 'vimeoDigitalRelease'          | null  | 0
		new Template(parts: Lists.newArrayList())                                                     | 'vuduDigitalRelease'           | null  | 0
		new Template(parts: Lists.newArrayList())                                                     | 'xboxSmartGlassDigitalRelease' | null  | 0
		new Template(parts: Lists.newArrayList())                                                     | 'youTubeDigitalRelease'        | null  | 0
		new Template(parts: Lists.newArrayList())                                                     | 'netflixDigitalRelease'        | null  | 0
		new Template(parts: Lists.newArrayList(createTemplatePart(VideoTemplateParameter.DFAZ, NO)))  | 'amazonDigitalRelease'         | false | 0
		new Template(parts: Lists.newArrayList(createTemplatePart(VideoTemplateParameter.DFDM, NO)))  | 'dailymotionDigitalRelease'    | false | 0
		new Template(parts: Lists.newArrayList(createTemplatePart(VideoTemplateParameter.DFGP, NO)))  | 'googlePlayDigitalRelease'     | false | 0
		new Template(parts: Lists.newArrayList(createTemplatePart(VideoTemplateParameter.DFIT, NO)))  | 'iTunesDigitalRelease'         | false | 0
		new Template(parts: Lists.newArrayList(createTemplatePart(VideoTemplateParameter.DFUV, NO)))  | 'ultraVioletDigitalRelease'    | false | 0
		new Template(parts: Lists.newArrayList(createTemplatePart(VideoTemplateParameter.DFVO, NO)))  | 'vimeoDigitalRelease'          | false | 0
		new Template(parts: Lists.newArrayList(createTemplatePart(VideoTemplateParameter.DFVU, NO)))  | 'vuduDigitalRelease'           | false | 0
		new Template(parts: Lists.newArrayList(createTemplatePart(VideoTemplateParameter.DFXB, NO)))  | 'xboxSmartGlassDigitalRelease' | false | 0
		new Template(parts: Lists.newArrayList(createTemplatePart(VideoTemplateParameter.DFYT, NO)))  | 'youTubeDigitalRelease'        | false | 0
		new Template(parts: Lists.newArrayList(createTemplatePart(VideoTemplateParameter.DFNF, NO)))  | 'netflixDigitalRelease'        | false | 0
		new Template(parts: Lists.newArrayList(createTemplatePart(VideoTemplateParameter.DFAZ, YES))) | 'amazonDigitalRelease'         | true  | 1
		new Template(parts: Lists.newArrayList(createTemplatePart(VideoTemplateParameter.DFDM, YES))) | 'dailymotionDigitalRelease'    | true  | 1
		new Template(parts: Lists.newArrayList(createTemplatePart(VideoTemplateParameter.DFGP, YES))) | 'googlePlayDigitalRelease'     | true  | 1
		new Template(parts: Lists.newArrayList(createTemplatePart(VideoTemplateParameter.DFIT, YES))) | 'iTunesDigitalRelease'         | true  | 1
		new Template(parts: Lists.newArrayList(createTemplatePart(VideoTemplateParameter.DFUV, YES))) | 'ultraVioletDigitalRelease'    | true  | 1
		new Template(parts: Lists.newArrayList(createTemplatePart(VideoTemplateParameter.DFVO, YES))) | 'vimeoDigitalRelease'          | true  | 1
		new Template(parts: Lists.newArrayList(createTemplatePart(VideoTemplateParameter.DFVU, YES))) | 'vuduDigitalRelease'           | true  | 1
		new Template(parts: Lists.newArrayList(createTemplatePart(VideoTemplateParameter.DFXB, YES))) | 'xboxSmartGlassDigitalRelease' | true  | 1
		new Template(parts: Lists.newArrayList(createTemplatePart(VideoTemplateParameter.DFYT, YES))) | 'youTubeDigitalRelease'        | true  | 1
		new Template(parts: Lists.newArrayList(createTemplatePart(VideoTemplateParameter.DFNF, YES))) | 'netflixDigitalRelease'        | true  | 1
	}

}
