package com.cezarykluczynski.stapi.etl.template.video_game.processor

import com.cezarykluczynski.stapi.etl.template.common.factory.PlatformFactory
import com.cezarykluczynski.stapi.etl.util.constant.TemplateParam
import com.cezarykluczynski.stapi.model.platform.entity.Platform
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import spock.lang.Specification

class VideoGameTemplatePlatformsProcessorTest extends Specification {

	private static final String VALUE1 = 'VALUE1'
	private static final String VALUE2 = 'VALUE2'
	private static final String VALUE3 = 'VALUE3'

	private PlatformFactory platformFactoryMock

	private VideoGameTemplatePlatformsProcessor videoGameTemplatePlatformsProcessor

	void setup() {
		platformFactoryMock = Mock()
		videoGameTemplatePlatformsProcessor = new VideoGameTemplatePlatformsProcessor(platformFactoryMock)
	}

	void "returns empty set when no templates were passed"() {
		when:
		Set<Platform> platformSet = videoGameTemplatePlatformsProcessor.process(new Template.Part())

		then:
		platformSet.empty
	}

	void "passes values to PlatformFactory"() {
		given:
		Template.Part qualifiedTemplatePart1 = new Template.Part(key: TemplateParam.FIRST, value: VALUE1)
		Template.Part qualifiedTemplatePart2 = new Template.Part(key: TemplateParam.FIRST, value: VALUE2)
		Template.Part qualifiedTemplatePart3 = new Template.Part(key: TemplateParam.FIRST, value: VALUE3)
		Template.Part templatePart = new Template.Part(
				templates: Lists.newArrayList(
						new Template(title: TemplateTitle.BORN),
						new Template(title: TemplateTitle.PLATFORM, parts: Lists.newArrayList(
								qualifiedTemplatePart1,
								new Template.Part(key: TemplateParam.SECOND)
						)),
						new Template(title: TemplateTitle.PLATFORM, parts: Lists.newArrayList(
								qualifiedTemplatePart2
						)),
						new Template(title: TemplateTitle.PLATFORM, parts: Lists.newArrayList(
								qualifiedTemplatePart3
						))
				)
		)
		Platform platform1 = Mock()
		Platform platform2 = Mock()

		when:
		Set<Platform> platformSet = videoGameTemplatePlatformsProcessor.process(templatePart)

		then:
		1 * platformFactoryMock.createForCode(VALUE1) >> Optional.of(platform1)
		1 * platformFactoryMock.createForCode(VALUE2) >> Optional.empty()
		1 * platformFactoryMock.createForCode(VALUE3) >> Optional.of(platform2)
		platformSet.size() == 2
		platformSet.contains platform1
		platformSet.contains platform2
	}

}
