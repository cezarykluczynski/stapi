package com.cezarykluczynski.stapi.etl.template.magazine_series.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.NumberOfPartsProcessor
import com.cezarykluczynski.stapi.etl.template.magazine_series.dto.MagazineSeriesTemplate
import com.cezarykluczynski.stapi.etl.template.magazine_series.dto.MagazineSeriesTemplateParameter
import com.cezarykluczynski.stapi.etl.template.publishable_series.dto.PublishableSeriesTemplate
import com.cezarykluczynski.stapi.etl.template.publishable_series.processor.PublishableSeriesTemplatePartsEnrichingProcessor
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

class MagazineSeriesTemplatePartsEnrichingProcessorTest extends Specification {

	private static final String ISSUES_STRING = '12'
	private static final String EDITOR = 'EDITOR'
	private static final Integer ISSUES_INTEGER = 12
	private static final Integer ISSUES_INTEGER_2 = 22

	private PublishableSeriesTemplatePartsEnrichingProcessor publishableSeriesTemplatePartsEnrichingProcessorMock

	private NumberOfPartsProcessor numberOfPartsProcessorMock

	private WikitextToEntitiesProcessor wikitextToEntitiesProcessorMock

	private MagazineSeriesTemplatePartsEnrichingProcessor magazineSeriesTemplatePartsEnrichingProcessor

	void setup() {
		publishableSeriesTemplatePartsEnrichingProcessorMock = Mock()
		numberOfPartsProcessorMock = Mock()
		wikitextToEntitiesProcessorMock = Mock()
		magazineSeriesTemplatePartsEnrichingProcessor = new MagazineSeriesTemplatePartsEnrichingProcessor(
				publishableSeriesTemplatePartsEnrichingProcessorMock, numberOfPartsProcessorMock, wikitextToEntitiesProcessorMock)
	}

	void "passes enrichable pair to PublishableSeriesTemplatePartsEnrichingProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: MagazineSeriesTemplateParameter.PUBLISHER)
		List<Template.Part> templatePartList = Lists.newArrayList(templatePart)
		MagazineSeriesTemplate magazineSeriesTemplate = new MagazineSeriesTemplate()

		when:
		magazineSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(templatePartList, magazineSeriesTemplate))

		then:
		1 * publishableSeriesTemplatePartsEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<List<Template.Part>, PublishableSeriesTemplate> enrichablePair ->
				assert enrichablePair.input == templatePartList
				assert enrichablePair.output == magazineSeriesTemplate
		}
		0 * _
	}

	void "parses number of issues"() {
		given:
		Template.Part templatePart = new Template.Part(key: MagazineSeriesTemplateParameter.ISSUES, value: ISSUES_STRING)
		MagazineSeriesTemplate magazineSeriesTemplate = new MagazineSeriesTemplate()

		when:
		magazineSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), magazineSeriesTemplate))

		then:
		1 * publishableSeriesTemplatePartsEnrichingProcessorMock.enrich(_)
		1 * numberOfPartsProcessorMock.process(ISSUES_STRING) >> ISSUES_INTEGER
		0 * _
		magazineSeriesTemplate.numberOfIssues == ISSUES_INTEGER
	}

	void "does not set number of issues, when value is already present"() {
		given:
		Template.Part templatePart = new Template.Part(key: MagazineSeriesTemplateParameter.ISSUES, value: ISSUES_STRING)
		MagazineSeriesTemplate magazineSeriesTemplate = new MagazineSeriesTemplate(numberOfIssues: ISSUES_INTEGER_2)

		when:
		magazineSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), magazineSeriesTemplate))

		then:
		1 * publishableSeriesTemplatePartsEnrichingProcessorMock.enrich(_)
		0 * _
		magazineSeriesTemplate.numberOfIssues == ISSUES_INTEGER_2
	}

	void "adds editors from WikitextStaffProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: MagazineSeriesTemplateParameter.EDITOR, value: EDITOR)
		MagazineSeriesTemplate magazineSeriesTemplate = new MagazineSeriesTemplate()
		Staff editor1 = Mock()
		Staff editor2 = Mock()

		when:
		magazineSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), magazineSeriesTemplate))

		then:
		1 * publishableSeriesTemplatePartsEnrichingProcessorMock.enrich(_)
		1 * wikitextToEntitiesProcessorMock.findStaff(EDITOR) >> Lists.newArrayList(editor1, editor2)
		0 * _
		magazineSeriesTemplate.editors.size() == 2
		magazineSeriesTemplate.editors.contains editor1
		magazineSeriesTemplate.editors.contains editor2
	}

}
