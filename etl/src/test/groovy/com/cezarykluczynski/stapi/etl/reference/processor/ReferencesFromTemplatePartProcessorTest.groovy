package com.cezarykluczynski.stapi.etl.reference.processor

import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.reference.entity.Reference
import com.cezarykluczynski.stapi.model.reference.entity.enums.ReferenceType
import com.cezarykluczynski.stapi.model.reference.factory.ReferenceFactory
import com.cezarykluczynski.stapi.model.reference.repository.ReferenceRepository
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import org.apache.commons.lang3.tuple.Pair
import spock.lang.Specification

class ReferencesFromTemplatePartProcessorTest extends Specification {

	private static final String ISBN_FULL = 'ISBN 0671008927'
	private static final String TWO_FULL_ISBNS = 'ISBN 1563898500 (hardcover)<br />ISBN 1563899183 (paperback)'
	private static final String FULL_ISBN_1 = '1563898500'
	private static final String FULL_ISBN_2 = '1563899183'
	private static final String ISBN_BARE = '0671008927'
	private static final String UID_1 = 'ISBN1563898500'
	private static final String UID_2 = 'ISBN1563899183'
	private static final String ASIN = 'ASINB223213FCF'
	private static final String EAN_13_FULL = 'EAN: 7332431036161'
	private static final String EAN_13_BARE = '7332431036161'
	private static final String EAN_8_FULL = 'EAN 96385074'
	private static final String EAN_8_BARE = '96385074'
	private static final String ISRC_FULL = 'ISRC: CN-A13-04-01140/V.J9 '
	private static final String ISRC_BARE = 'CNA130401140'

	private ReferenceRepository referenceRepositoryMock

	private UidGenerator uidGeneratorMock

	private ReferenceFactory referenceFactoryMock

	private ReferencesFromTemplatePartProcessor referencesFromTemplatePartProcessor

	void setup() {
		referenceRepositoryMock = Mock()
		uidGeneratorMock = Mock()
		referenceFactoryMock = Mock()
		referencesFromTemplatePartProcessor = new ReferencesFromTemplatePartProcessor(referenceRepositoryMock, uidGeneratorMock,
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
		Template.Part templatePart = new Template.Part(key: TemplateTitle.REFERENCE, value: ISBN_FULL)
		Reference reference = Mock()

		when:
		Set<Reference> referenceSet = referencesFromTemplatePartProcessor.process(templatePart)

		then:
		1 * uidGeneratorMock.generateForReference(_ as Pair) >> { Pair<ReferenceType, String> pair ->
			assert pair.key == ReferenceType.ISBN
			assert pair.value == ISBN_BARE
			UID_1
		}
		1 * referenceRepositoryMock.findByUid(UID_1) >> Optional.of(reference)
		0 * _
		referenceSet.size() == 1
		referenceSet.contains reference
	}

	void "ISBNs containing template is parsed to References, when references is already present"() {
		given:
		Template.Part templatePart = new Template.Part(key: TemplateTitle.REFERENCE, value: TWO_FULL_ISBNS)
		Reference reference1 = Mock()
		Reference reference2 = Mock()

		when:
		Set<Reference> referenceSet = referencesFromTemplatePartProcessor.process(templatePart)

		then:
		1 * uidGeneratorMock.generateForReference(_ as Pair) >> { Pair<ReferenceType, String> pair ->
			assert pair.key == ReferenceType.ISBN
			assert pair.value == FULL_ISBN_1
			UID_1
		}
		1 * uidGeneratorMock.generateForReference(_ as Pair) >> { Pair<ReferenceType, String> pair ->
			assert pair.key == ReferenceType.ISBN
			assert pair.value == FULL_ISBN_2
			UID_2
		}
		1 * referenceRepositoryMock.findByUid(UID_1) >> Optional.of(reference1)
		1 * referenceRepositoryMock.findByUid(UID_2) >> Optional.of(reference2)
		0 * _
		referenceSet.size() == 2
		referenceSet.contains reference1
		referenceSet.contains reference2
	}

	void "ISBN containing template is parsed to Reference, when reference is not already present"() {
		given:
		Template.Part templatePart = new Template.Part(key: TemplateTitle.REFERENCE, value: ISBN_FULL)
		Reference reference = Mock()

		when:
		Set<Reference> referenceSet = referencesFromTemplatePartProcessor.process(templatePart)

		then:
		1 * uidGeneratorMock.generateForReference(_ as Pair) >> { Pair<ReferenceType, String> pair ->
			assert pair.key == ReferenceType.ISBN
			assert pair.value == ISBN_BARE
			UID_1
		}
		1 * referenceRepositoryMock.findByUid(UID_1) >> Optional.empty()
		1 * referenceFactoryMock.createFromUid(UID_1) >> reference
		1 * referenceRepositoryMock.save(_ as Reference) >> { Reference it -> it }
		0 * _
		referenceSet.size() == 1
		referenceSet.contains reference
	}

	void "tolerates invalid value in template value"() {
		given:
		Template.Part templatePart = new Template.Part(key: TemplateTitle.REFERENCE, value: 'INVALID')

		when:
		Set<Reference> referenceSet = referencesFromTemplatePartProcessor.process(templatePart)

		then:
		0 * _
		referenceSet.empty
	}

	void "ASIN template in template part is parsed, when reference is already present"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: TemplateTitle.REFERENCE,
				templates: Lists.newArrayList(
						new Template(
								title: TemplateTitle.ASIN,
								parts: Lists.newArrayList(
										new Template.Part(value: ASIN)
								))))
		Reference reference = Mock()

		when:
		Set<Reference> referenceSet = referencesFromTemplatePartProcessor.process(templatePart)

		then:
		1 * uidGeneratorMock.generateForReference(_ as Pair) >> { Pair<ReferenceType, String> pair ->
			assert pair.key == ReferenceType.ASIN
			assert pair.value == ASIN
			UID_1
		}
		1 * referenceRepositoryMock.findByUid(UID_1) >> Optional.of(reference)
		0 * _
		referenceSet.size() == 1
		referenceSet.contains reference
	}

	void "tolerates empty template list"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: TemplateTitle.REFERENCE,
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
				key: TemplateTitle.REFERENCE,
				templates: Lists.newArrayList(
						new Template(
								title: TemplateTitle.ASIN,
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
				key: TemplateTitle.REFERENCE,
				templates: Lists.newArrayList(
						new Template(
								title: TemplateTitle.BORN,
								parts: Lists.newArrayList(
										new Template.Part(value: ASIN)
								))))

		when:
		Set<Reference> referenceSet = referencesFromTemplatePartProcessor.process(templatePart)

		then:
		0 * _
		referenceSet.empty
	}

	void "EAN-13 containing template is parsed to Reference, when reference is already present"() {
		given:
		Template.Part templatePart = new Template.Part(key: TemplateTitle.REFERENCE, value: EAN_13_FULL)
		Reference reference = Mock()

		when:
		Set<Reference> referenceSet = referencesFromTemplatePartProcessor.process(templatePart)

		then:
		1 * uidGeneratorMock.generateForReference(_ as Pair) >> { Pair<ReferenceType, String> pair ->
			assert pair.key == ReferenceType.EAN
			assert pair.value == EAN_13_BARE
			UID_1
		}
		1 * referenceRepositoryMock.findByUid(UID_1) >> Optional.of(reference)
		0 * _
		referenceSet.size() == 1
		referenceSet.contains reference
	}

	void "EAN-8 containing template is parsed to Reference, when reference is already present"() {
		given:
		Template.Part templatePart = new Template.Part(key: TemplateTitle.REFERENCE, value: EAN_8_FULL)
		Reference reference = Mock()

		when:
		Set<Reference> referenceSet = referencesFromTemplatePartProcessor.process(templatePart)

		then:
		1 * uidGeneratorMock.generateForReference(_ as Pair) >> { Pair<ReferenceType, String> pair ->
			assert pair.key == ReferenceType.EAN
			assert pair.value == EAN_8_BARE
			UID_1
		}
		1 * referenceRepositoryMock.findByUid(UID_1) >> Optional.of(reference)
		0 * _
		referenceSet.size() == 1
		referenceSet.contains reference
	}

	void "ISRC containing template is parsed to Reference, when reference is already present"() {
		given:
		Template.Part templatePart = new Template.Part(key: TemplateTitle.REFERENCE, value: ISRC_FULL)
		Reference reference = Mock()

		when:
		Set<Reference> referenceSet = referencesFromTemplatePartProcessor.process(templatePart)

		then:
		1 * uidGeneratorMock.generateForReference(_ as Pair) >> { Pair<ReferenceType, String> pair ->
			assert pair.key == ReferenceType.ISRC
			assert pair.value == ISRC_BARE
			UID_1
		}
		1 * referenceRepositoryMock.findByUid(UID_1) >> Optional.of(reference)
		0 * _
		referenceSet.size() == 1
		referenceSet.contains reference
	}

}
