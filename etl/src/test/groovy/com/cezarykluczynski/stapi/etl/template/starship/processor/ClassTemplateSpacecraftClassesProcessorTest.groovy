package com.cezarykluczynski.stapi.etl.template.starship.processor

import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService
import com.cezarykluczynski.stapi.etl.util.constant.TemplateParam
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass
import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import org.apache.commons.lang3.tuple.Pair
import spock.lang.Specification

class ClassTemplateSpacecraftClassesProcessorTest extends Specification {

	private static final String CONSTITUTION = 'Constitution'
	private static final String FEDERATION = 'Federation'
	private static final String CONSTITUTION_CLASS = 'Constitution class'
	private static final String CONSTITUTION_CLASS_FEDERATION = 'Constitution class (Federation)'
	private static final String CONSTITUTION_CLASS_ALTERNATE_REALITY = 'Constitution class (alternate reality)'
	private static final String CONSTITUTION_TYPE = 'Constitution type'
	private static final MediaWikiSource MEDIA_WIKI_SOURCE = MediaWikiSource.MEMORY_ALPHA_EN

	private EntityLookupByNameService entityLookupByNameServiceMock

	private ClassTemplateSpacecraftClassesProcessor classTemplateSpacecraftClassesProcessor

	void setup() {
		entityLookupByNameServiceMock = Mock()
		classTemplateSpacecraftClassesProcessor = new ClassTemplateSpacecraftClassesProcessor(entityLookupByNameServiceMock)
	}

	void "when class template cannot be found, empty pair is returned"() {
		given:
		Template.Part templatePart = new Template.Part(templates: Lists.newArrayList(
				new Template(title: TemplateTitle.ASIN)
		))

		when:
		Pair<List<SpacecraftClass>, List<SpacecraftType>> pair = classTemplateSpacecraftClassesProcessor.process(templatePart)

		then:
		pair.left.empty
		pair.right.empty
	}

	void "when first template part is found, it is used to search in the service"() {
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
		Pair<List<SpacecraftClass>, List<SpacecraftType>> pair = classTemplateSpacecraftClassesProcessor.process(templatePart)

		then:
		1 * entityLookupByNameServiceMock.findSpacecraftClassByName(CONSTITUTION_CLASS, MEDIA_WIKI_SOURCE) >> Optional.of(spacecraftClass)
		0 * _
		pair.left.size() == 1
		pair.left.contains spacecraftClass
		pair.right.empty
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
		Pair<List<SpacecraftClass>, List<SpacecraftType>> pair = classTemplateSpacecraftClassesProcessor.process(templatePart)

		then:
		1 * entityLookupByNameServiceMock.findSpacecraftClassByName(CONSTITUTION_CLASS_ALTERNATE_REALITY, MEDIA_WIKI_SOURCE) >>
				Optional.of(spacecraftClass)
		0 * _
		pair.left.size() == 1
		pair.left.contains spacecraftClass
		pair.right.empty
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
		Pair<List<SpacecraftClass>, List<SpacecraftType>> pair = classTemplateSpacecraftClassesProcessor.process(templatePart)

		then:
		1 * entityLookupByNameServiceMock.findSpacecraftClassByName(CONSTITUTION_CLASS_FEDERATION, MEDIA_WIKI_SOURCE) >>
				Optional.of(spacecraftClass)
		0 * _
		pair.left.size() == 1
		pair.left.contains spacecraftClass
		pair.right.empty
	}

	void "when type template is found, it is used to return spacecraft types"() {
		given:
		Template.Part templatePart = new Template.Part(templates: Lists.newArrayList(
				new Template(
						title: TemplateTitle.TYPE,
						parts: Lists.newArrayList(
								new Template.Part(
										key: TemplateParam.FIRST,
										value: CONSTITUTION
								)))))
		SpacecraftType spacecraftType = Mock()

		when:
		Pair<List<SpacecraftClass>, List<SpacecraftType>> pair = classTemplateSpacecraftClassesProcessor.process(templatePart)

		then:
		1 * entityLookupByNameServiceMock.findSpacecraftClassByName(CONSTITUTION_TYPE, MEDIA_WIKI_SOURCE) >> Optional.empty()
		1 * entityLookupByNameServiceMock.findSpacecraftTypeByName(CONSTITUTION_TYPE, MEDIA_WIKI_SOURCE) >> Optional.of(spacecraftType)
		0 * _
		pair.left.empty
		pair.right.size() == 1
		pair.right.contains spacecraftType
	}

	void "template without parts is tolerated"() {
		given:
		Template.Part templatePart = new Template.Part(templates: Lists.newArrayList(
				new Template(title: TemplateTitle.CLASS, parts: [])))

		when:
		Pair<List<SpacecraftClass>, List<SpacecraftType>> pair = classTemplateSpacecraftClassesProcessor.process(templatePart)

		then:
		0 * _
		pair.left.empty
		pair.right.empty
	}

}
