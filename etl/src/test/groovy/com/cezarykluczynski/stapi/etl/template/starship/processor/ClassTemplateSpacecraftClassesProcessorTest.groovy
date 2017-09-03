package com.cezarykluczynski.stapi.etl.template.starship.processor

import com.cezarykluczynski.stapi.etl.util.constant.TemplateParam
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass
import com.cezarykluczynski.stapi.model.spacecraft_class.repository.SpacecraftClassRepository
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import spock.lang.Specification

class ClassTemplateSpacecraftClassesProcessorTest extends Specification {

	private static final String CONSTITUTION = 'Constitution'
	private static final String FEDERATION = 'Federation'
	private static final String CONSTITUTION_CLASS = 'Constitution class'
	private static final String CONSTITUTION_CLASS_FEDERATION = 'Constitution class (Federation)'
	private static final String CONSTITUTION_CLASS_ALTERNATE_REALITY = 'Constitution class (alternate reality)'
	private static final MediaWikiSource MEDIA_WIKI_SOURCE = MediaWikiSource.MEMORY_ALPHA_EN

	private SpacecraftClassRepository spacecraftClassRepositoryMock

	private ClassTemplateSpacecraftClassesProcessor classTemplateSpacecraftClassesProcessor

	void setup() {
		spacecraftClassRepositoryMock = Mock()
		classTemplateSpacecraftClassesProcessor = new ClassTemplateSpacecraftClassesProcessor(spacecraftClassRepositoryMock)
	}

	void "when class template cannot be found, empty list is returned"() {
		given:
		Template.Part templatePart = new Template.Part(templates: Lists.newArrayList(
				new Template(title: TemplateTitle.ASIN)
		))

		when:
		List<SpacecraftClass> spacecraftClassList = classTemplateSpacecraftClassesProcessor.process(templatePart)

		then:
		spacecraftClassList.empty
	}

	void "when first template part is found, it is used to search in repository"() {
		given:
		Template.Part templatePart = new Template.Part(templates: Lists.newArrayList(
				new Template(
						title: TemplateTitle.CLASS,
						parts: Lists.newArrayList(
								new Template.Part(
										key: TemplateParam.FIRST,
										value: CONSTITUTION
								)))))
		SpacecraftClass spacecraftClass = Mock()

		when:
		List<SpacecraftClass> spacecraftClassList = classTemplateSpacecraftClassesProcessor.process(templatePart)

		then:
		1 * spacecraftClassRepositoryMock.findByPageTitleAndPageMediaWikiSource(CONSTITUTION_CLASS, MEDIA_WIKI_SOURCE) >> Optional.of(spacecraftClass)
		0 * _
		spacecraftClassList.size() == 1
		spacecraftClassList.contains spacecraftClass
	}

	void "when first and second template part is found, and second template part is 'alt', both are used to search in repository"() {
		given:
		Template.Part templatePart = new Template.Part(templates: Lists.newArrayList(
				new Template(
						title: TemplateTitle.CLASS,
						parts: Lists.newArrayList(
								new Template.Part(
										key: TemplateParam.FIRST,
										value: CONSTITUTION
								),
								new Template.Part(
										key: TemplateParam.SECOND,
										value: ClassTemplateSpacecraftClassesProcessor.ALT
								)))))
		SpacecraftClass spacecraftClass = Mock()

		when:
		List<SpacecraftClass> spacecraftClassList = classTemplateSpacecraftClassesProcessor.process(templatePart)

		then:
		1 * spacecraftClassRepositoryMock.findByPageTitleAndPageMediaWikiSource(CONSTITUTION_CLASS_ALTERNATE_REALITY, MEDIA_WIKI_SOURCE) >>
				Optional.of(spacecraftClass)
		0 * _
		spacecraftClassList.size() == 1
		spacecraftClassList.contains spacecraftClass
	}

	void "when first and second template part is found, and second template part is not 'alt', both are used to search in repository"() {
		given:
		Template.Part templatePart = new Template.Part(templates: Lists.newArrayList(
				new Template(
						title: TemplateTitle.CLASS,
						parts: Lists.newArrayList(
								new Template.Part(
										key: TemplateParam.FIRST,
										value: CONSTITUTION
								),
								new Template.Part(
										key: TemplateParam.SECOND,
										value: FEDERATION
								)))))
		SpacecraftClass spacecraftClass = Mock()

		when:
		List<SpacecraftClass> spacecraftClassList = classTemplateSpacecraftClassesProcessor.process(templatePart)

		then:
		1 * spacecraftClassRepositoryMock.findByPageTitleAndPageMediaWikiSource(CONSTITUTION_CLASS_FEDERATION, MEDIA_WIKI_SOURCE) >>
				Optional.of(spacecraftClass)
		0 * _
		spacecraftClassList.size() == 1
		spacecraftClassList.contains spacecraftClass
	}

	void "template without parts is tolerated"() {
		given:
		Template.Part templatePart = new Template.Part(templates: Lists.newArrayList(
				new Template(
						title: TemplateTitle.CLASS,
						parts: Lists.newArrayList())))

		when:
		List<SpacecraftClass> spacecraftClassList = classTemplateSpacecraftClassesProcessor.process(templatePart)

		then:
		0 * _
		spacecraftClassList.empty
	}

}
