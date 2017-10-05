package com.cezarykluczynski.stapi.etl.template.soundtrack.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor
import com.cezarykluczynski.stapi.etl.reference.processor.ReferencesFromTemplatePartProcessor
import com.cezarykluczynski.stapi.etl.template.soundtrack.dto.SoundtrackTemplate
import com.cezarykluczynski.stapi.etl.template.soundtrack.dto.SoundtrackTemplateParameter
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.reference.entity.Reference
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import spock.lang.Specification

class SoundtrackTemplateRelationsEnrichingProcessorTest extends Specification {

	private static final String COMPOSER = 'COMPOSER'
	private static final String ADD_MUSIC = 'ADD_MUSIC'
	private static final String ORCHESTRATOR = 'ORCHESTRATOR'
	private static final String LABEL = 'LABEL'

	private WikitextToEntitiesProcessor wikitextToEntitiesProcessorMock

	private ReferencesFromTemplatePartProcessor referencesFromTemplatePartProcessorMock

	private SoundtrackTemplateRelationsEnrichingProcessor soundtrackTemplateRelationsEnrichingProcessor

	void setup() {
		wikitextToEntitiesProcessorMock = Mock()
		referencesFromTemplatePartProcessorMock = Mock()
		soundtrackTemplateRelationsEnrichingProcessor = new SoundtrackTemplateRelationsEnrichingProcessor(wikitextToEntitiesProcessorMock,
				referencesFromTemplatePartProcessorMock)
	}

	void "when composer part is found, WikitextToEntitiesProcessor is used to process it"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: SoundtrackTemplateParameter.COMPOSER,
				value: COMPOSER)
		Template sidebarSoundtrackTemplate = new Template(parts: Lists.newArrayList(templatePart))
		Staff staff1 = Mock()
		Staff staff2 = Mock()
		SoundtrackTemplate soundtrackTemplate = new SoundtrackTemplate()

		when:
		soundtrackTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarSoundtrackTemplate, soundtrackTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findStaff(COMPOSER) >> Lists.newArrayList(staff1, staff2)
		0 * _
		soundtrackTemplate.composers.contains staff1
		soundtrackTemplate.composers.contains staff2
	}

	void "when add music part is found, WikitextToEntitiesProcessor is used to process it"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: SoundtrackTemplateParameter.ADD_MUSIC,
				value: ADD_MUSIC)
		Template sidebarSoundtrackTemplate = new Template(parts: Lists.newArrayList(templatePart))
		Staff staff1 = Mock()
		Staff staff2 = Mock()
		SoundtrackTemplate soundtrackTemplate = new SoundtrackTemplate()

		when:
		soundtrackTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarSoundtrackTemplate, soundtrackTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findStaff(ADD_MUSIC) >> Lists.newArrayList(staff1, staff2)
		0 * _
		soundtrackTemplate.contributors.contains staff1
		soundtrackTemplate.contributors.contains staff2
	}

	void "when orchestrator is found, WikitextToEntitiesProcessor is used to process it"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: SoundtrackTemplateParameter.ORCHESTRATOR,
				value: ORCHESTRATOR)
		Template sidebarSoundtrackTemplate = new Template(parts: Lists.newArrayList(templatePart))
		Staff staff1 = Mock()
		Staff staff2 = Mock()
		SoundtrackTemplate soundtrackTemplate = new SoundtrackTemplate()

		when:
		soundtrackTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarSoundtrackTemplate, soundtrackTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findStaff(ORCHESTRATOR) >> Lists.newArrayList(staff1, staff2)
		0 * _
		soundtrackTemplate.orchestrators.contains staff1
		soundtrackTemplate.orchestrators.contains staff2
	}

	void "when label is found, WikitextToEntitiesProcessor is used to process it"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: SoundtrackTemplateParameter.LABEL,
				value: LABEL)
		Template sidebarSoundtrackTemplate = new Template(parts: Lists.newArrayList(templatePart))
		Company company1 = Mock()
		Company company2 = Mock()
		SoundtrackTemplate soundtrackTemplate = new SoundtrackTemplate()

		when:
		soundtrackTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarSoundtrackTemplate, soundtrackTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findCompanies(LABEL) >> Lists.newArrayList(company1, company2)
		0 * _
		soundtrackTemplate.labels.contains company1
		soundtrackTemplate.labels.contains company2
	}

	void "when reference part is found, ReferencesFromTemplatePartProcessor is used to process it"() {
		given:
		Template.Part templatePart = new Template.Part(key: SoundtrackTemplateParameter.REFERENCE)
		Template sidebarSoundtrackTemplate = new Template(parts: Lists.newArrayList(templatePart))
		Reference reference1 = Mock()
		Reference reference2 = Mock()
		SoundtrackTemplate soundtrackTemplate = new SoundtrackTemplate()

		when:
		soundtrackTemplateRelationsEnrichingProcessor.enrich(EnrichablePair.of(sidebarSoundtrackTemplate, soundtrackTemplate))

		then:
		1 * referencesFromTemplatePartProcessorMock.process(templatePart) >> Sets.newHashSet(reference1, reference2)
		0 * _
		soundtrackTemplate.references.contains reference1
		soundtrackTemplate.references.contains reference2
	}

}
