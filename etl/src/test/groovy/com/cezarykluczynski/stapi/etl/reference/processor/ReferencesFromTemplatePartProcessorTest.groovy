package com.cezarykluczynski.stapi.etl.reference.processor

import com.cezarykluczynski.stapi.model.common.service.GuidGenerator
import com.cezarykluczynski.stapi.model.reference.entity.Reference
import com.cezarykluczynski.stapi.model.reference.entity.enums.ReferenceType
import com.cezarykluczynski.stapi.model.reference.factory.ReferenceFactory
import com.cezarykluczynski.stapi.model.reference.repository.ReferenceRepository
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateName
import com.google.common.collect.Lists
import org.apache.commons.lang3.tuple.Pair
import spock.lang.Specification

class ReferencesFromTemplatePartProcessorTest extends Specification {

	private static final String ISBN_FULL = 'ISBN 0671008927'
	private static final String ISBN_BARE = '0671008927'
	private static final String GUID = 'ISBN0671008927'
	private static final String ASIN = 'ASINB223213FCF'

	private ReferenceRepository referenceRepositoryMock

	private GuidGenerator guidGeneratorMock

	private ReferenceFactory referenceFactoryMock

	private ReferencesFromTemplatePartProcessor referencesFromTemplatePartProcessor

	void setup() {
		referenceRepositoryMock = Mock(ReferenceRepository)
		guidGeneratorMock = Mock(GuidGenerator)
		referenceFactoryMock = Mock(ReferenceFactory)
		referencesFromTemplatePartProcessor = new ReferencesFromTemplatePartProcessor(referenceRepositoryMock, guidGeneratorMock,
				referenceFactoryMock)
	}

	void "returns empty set when template key is not 'reference'"() {
		given:
		Template.Part templatePart = new Template.Part(key: 'UNKNOWN')

		when:
		Set<Reference> referenceSet = referencesFromTemplatePartProcessor.process(templatePart)

		then:
		0 * _
		referenceSet.empty
	}

	void "ISBN containing template is parsed to Reference, when reference is already present"() {
		given:
		Template.Part templatePart = new Template.Part(key: TemplateName.REFERENCE, value: ISBN_FULL)
		Reference reference = Mock(Reference)

		when:
		Set<Reference> referenceSet = referencesFromTemplatePartProcessor.process(templatePart)

		then:
		1 * guidGeneratorMock.generateFromReference(_ as Pair) >> { Pair<ReferenceType, String> pair ->
			assert pair.key == ReferenceType.ISBN
			assert pair.value == ISBN_BARE
			GUID
		}
		1 * referenceRepositoryMock.findByGuid(GUID) >> Optional.of(reference)
		0 * _
		referenceSet.size() == 1
		referenceSet[0] == reference
	}

	void "ISBN containing template is parsed to Reference, when reference is not already present"() {
		given:
		Template.Part templatePart = new Template.Part(key: TemplateName.REFERENCE, value: ISBN_FULL)
		Reference reference = Mock(Reference)

		when:
		Set<Reference> referenceSet = referencesFromTemplatePartProcessor.process(templatePart)

		then:
		1 * guidGeneratorMock.generateFromReference(_ as Pair) >> { Pair<ReferenceType, String> pair ->
			assert pair.key == ReferenceType.ISBN
			assert pair.value == ISBN_BARE
			GUID
		}
		1 * referenceRepositoryMock.findByGuid(GUID) >> Optional.empty()
		1 * referenceFactoryMock.createFromGuid(GUID) >> reference
		1 * referenceRepositoryMock.save(_ as Reference) >> { Reference it -> it }
		0 * _
		referenceSet.size() == 1
		referenceSet[0] == reference
	}

	void "tolerates invalid value in template value"() {
		given:
		Template.Part templatePart = new Template.Part(key: TemplateName.REFERENCE, value: 'INVALID')

		when:
		Set<Reference> referenceSet = referencesFromTemplatePartProcessor.process(templatePart)

		then:
		0 * _
		referenceSet.empty
	}

	void "ASIN template in template part is parsed, when reference is already present"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: TemplateName.REFERENCE,
				templates: Lists.newArrayList(
						new Template(
								title: TemplateName.ASIN,
								parts: Lists.newArrayList(
										new Template.Part(value: ASIN)
								))))
		Reference reference = Mock(Reference)

		when:
		Set<Reference> referenceSet = referencesFromTemplatePartProcessor.process(templatePart)

		then:
		1 * guidGeneratorMock.generateFromReference(_ as Pair) >> { Pair<ReferenceType, String> pair ->
			assert pair.key == ReferenceType.ASIN
			assert pair.value == ASIN
			GUID
		}
		1 * referenceRepositoryMock.findByGuid(GUID) >> Optional.of(reference)
		0 * _
		referenceSet.size() == 1
		referenceSet[0] == reference
	}

	void "tolerates empty template list"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: TemplateName.REFERENCE,
				templates: Lists.newArrayList())

		when:
		Set<Reference> referenceSet = referencesFromTemplatePartProcessor.process(templatePart)

		then:
		0 * _
		referenceSet.empty
	}

	void "tolerates empty template part list"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: TemplateName.REFERENCE,
				templates: Lists.newArrayList(
						new Template(
								title: TemplateName.ASIN,
								parts: Lists.newArrayList())))

		when:
		Set<Reference> referenceSet = referencesFromTemplatePartProcessor.process(templatePart)

		then:
		0 * _
		referenceSet.empty
	}

	void "tolerates template with unrecognized title"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: TemplateName.REFERENCE,
				templates: Lists.newArrayList(
						new Template(
								title: TemplateName.BORN,
								parts: Lists.newArrayList(
										new Template.Part(value: ASIN)
								))))

		when:
		Set<Reference> referenceSet = referencesFromTemplatePartProcessor.process(templatePart)

		then:
		0 * _
		referenceSet.empty
	}

}
