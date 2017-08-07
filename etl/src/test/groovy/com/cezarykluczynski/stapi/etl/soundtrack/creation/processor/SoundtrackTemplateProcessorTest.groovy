package com.cezarykluczynski.stapi.etl.soundtrack.creation.processor

import com.cezarykluczynski.stapi.etl.template.soundtrack.dto.SoundtrackTemplate
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.soundtrack.entity.Soundtrack
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.util.AbstractSoundtrackTest
import com.google.common.collect.Sets

class SoundtrackTemplateProcessorTest extends AbstractSoundtrackTest {

	private UidGenerator uidGeneratorMock

	private SoundtrackTemplateProcessor soundtrackTemplateProcessor

	void setup() {
		uidGeneratorMock = Mock()
		soundtrackTemplateProcessor = new SoundtrackTemplateProcessor(uidGeneratorMock)
	}

	void "converts SoundtrackTemplate to Soundtrack"() {
		given:
		Page page = Mock()
		Company label1 = Mock()
		Company label2 = Mock()
		Staff composer1 = Mock()
		Staff composer2 = Mock()
		Staff contributor1 = Mock()
		Staff contributor2 = Mock()
		Staff orchestrator1 = Mock()
		Staff orchestrator2 = Mock()
		SoundtrackTemplate soundtrackTemplate = new SoundtrackTemplate(
				title: TITLE,
				page: page,
				releaseDate: RELEASE_DATE,
				length: LENGTH,
				labels: Sets.newHashSet(label1, label2),
				composers: Sets.newHashSet(composer1, composer2),
				contributors: Sets.newHashSet(contributor1, contributor2),
				orchestrators: Sets.newHashSet(orchestrator1, orchestrator2),
		)

		when:
		Soundtrack soundtrack = soundtrackTemplateProcessor.process(soundtrackTemplate)

		then:
		1 * uidGeneratorMock.generateFromPage(page, Soundtrack) >> UID
		soundtrack.title == TITLE
		soundtrack.page == page
		soundtrack.uid == UID
		soundtrack.releaseDate == RELEASE_DATE
		soundtrack.length == LENGTH
		soundtrack.labels.contains label1
		soundtrack.labels.contains label2
		soundtrack.composers.contains composer1
		soundtrack.composers.contains composer2
		soundtrack.contributors.contains contributor1
		soundtrack.contributors.contains contributor2
		soundtrack.orchestrators.contains orchestrator1
		soundtrack.orchestrators.contains orchestrator2
	}

}
