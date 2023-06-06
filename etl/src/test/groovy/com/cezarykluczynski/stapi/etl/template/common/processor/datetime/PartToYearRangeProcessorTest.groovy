package com.cezarykluczynski.stapi.etl.template.common.processor.datetime

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.YearRange
import com.cezarykluczynski.stapi.etl.template.service.TemplateFilter
import com.cezarykluczynski.stapi.etl.mediawiki.api.WikitextApiImpl
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

class PartToYearRangeProcessorTest extends Specification {

	private static final Template START_TEMPLATE = new Template(title: TemplateTitle.Y, parts: [new Template.Part(key: '1', value: '1997')])
	private static final Template END_TEMPLATE = new Template(title: TemplateTitle.YEARLINK, parts: [new Template.Part(key: '1', value: '2005')])
	private static final Integer START_YEAR = 1997
	private static final Integer END_YEAR = 2005

	private PartToYearRangeProcessor partToYearRangeProcessor

	void setup() {
		partToYearRangeProcessor = new PartToYearRangeProcessor(new YearlinkToYearProcessor(), new MonthlinkTemplateToMonthYearCandiateProcessor(),
				new TemplateFilter(), new WikitextApiImpl())
	}

	void "returns empty YearRange when value is null, templates are null"() {
		when:
		YearRange yearRange = partToYearRangeProcessor.process(createTemplatePart(null, null))

		then:
		yearRange.yearFrom == null
		yearRange.yearTo == null
	}

	void "returns start date when value contains a single number"() {
		when:
		YearRange yearRange = partToYearRangeProcessor.process(createTemplatePart('1995', null))

		then:
		yearRange.yearFrom == 1995
		yearRange.yearTo == null
	}

	void "returns start date and end date, when value contains then, separated by &ndash;"() {
		when:
		YearRange yearRange = partToYearRangeProcessor.process(createTemplatePart('1990&ndash;2000', null))

		then:
		yearRange.yearFrom == 1990
		yearRange.yearTo == 2000
	}

	void "returns start date and end date, when value contains them, separated by &ndash; and spaces"() {
		when:
		YearRange yearRange = partToYearRangeProcessor.process(createTemplatePart('2001 &ndash; 2005', null))

		then:
		yearRange.yearFrom == 2001
		yearRange.yearTo == 2005
	}

	void "returns start date and end date, when value contains them, separated by &ndash; and spaces, first is a link"() {
		when:
		YearRange yearRange = partToYearRangeProcessor.process(createTemplatePart('[[2001]] &ndash; 2005', null))

		then:
		yearRange.yearFrom == 2001
		yearRange.yearTo == 2005
	}

	void "returns start date and end date, when value contains them, separated by &ndash; and spaces, second is a link"() {
		when:
		YearRange yearRange = partToYearRangeProcessor.process(createTemplatePart('2001 &ndash; [[2005]]', null))

		then:
		yearRange.yearFrom == 2001
		yearRange.yearTo == 2005
	}

	void "returns start date and not end date, when value contains only one followed by &ndash;"() {
		when:
		YearRange yearRange = partToYearRangeProcessor.process(createTemplatePart('2017 &ndash;', null))

		then:
		yearRange.yearFrom == 2017
		yearRange.yearTo == null
	}

	void "returns start date and end date, when start is y template, and end is text, separated by &ndash;"() {
		when:
		YearRange yearRange = partToYearRangeProcessor.process(createTemplatePart('&ndash; 1999', [
				new Template(title: 'y', parts: [new Template.Part(key: '1', value: '1992')])
		]))

		then:
		yearRange.yearFrom == 1992
		yearRange.yearTo == 1999
	}

	void "returns start date and when y template is accompanied by m template, separated by &ndash;"() {
		when:
		YearRange yearRange = partToYearRangeProcessor.process(createTemplatePart('&ndash;', [
				new Template(title: 'm', parts: [new Template.Part(key: '1', value: 'October')]),
				new Template(title: 'y', parts: [new Template.Part(key: '1', value: '2018')])
		]))

		then:
		yearRange.yearFrom == 2018
		yearRange.yearTo == null
	}

	void "returns start date and when when year is embedded in m template, separated by &ndash;"() {
		when:
		YearRange yearRange = partToYearRangeProcessor.process(createTemplatePart('&ndash;', [
				new Template(title: 'm', parts: [new Template.Part(key: '1', value: 'October'), new Template.Part(key: '2', value: '2018')]),
		]))

		then:
		yearRange.yearFrom == 2018
		yearRange.yearTo == null
	}

	void "returns start date when only y template is present, separated by &ndash;"() {
		when:
		YearRange yearRange = partToYearRangeProcessor.process(createTemplatePart('&ndash;', [
				new Template(title: 'y', parts: [new Template.Part(key: '1', value: '2019')])
		]))

		then:
		yearRange.yearFrom == 2019
		yearRange.yearTo == null
	}

	void "returns start date and end date two y templates are present, separated by &ndash;"() {
		when:
		YearRange yearRange = partToYearRangeProcessor.process(createTemplatePart('&ndash;', [
				new Template(title: 'y', parts: [new Template.Part(key: '1', value: '1987')]),
				new Template(title: 'y', parts: [new Template.Part(key: '1', value: '1994')])
		]))

		then:
		yearRange.yearFrom == 1987
		yearRange.yearTo == 1994
	}

	void "returns start date and end date, when value contains then, separated by ' to '"() {
		when:
		YearRange yearRange = partToYearRangeProcessor.process(createTemplatePart('1990 to 2000', null))

		then:
		yearRange.yearFrom == 1990
		yearRange.yearTo == 2000
	}

	void "valid Part is converted to YearRange with both dates"() {
		given:
		List<Template> templateList = Lists.newArrayList(
				START_TEMPLATE, END_TEMPLATE
		)
		Template.Part part = createTemplatePart(null, templateList)

		when:
		YearRange yearRange = partToYearRangeProcessor.process(part)

		then:
		yearRange.yearFrom == START_YEAR
		yearRange.yearTo == END_YEAR
	}

	void "Part with only start year is converted to YearRange with only start year"() {
		given:
		List<Template> templateList = Lists.newArrayList(
				START_TEMPLATE
		)
				Template.Part part = createTemplatePart(null, templateList)

		when:
		YearRange yearRange = partToYearRangeProcessor.process(part)

		then:
		yearRange.yearFrom == START_YEAR
		yearRange.yearTo == null
	}

	void "Part with more than 2 year templates results in empty YearRange"() {
		given:
		List<Template> templateList = Lists.newArrayList(
				START_TEMPLATE, END_TEMPLATE, START_TEMPLATE
		)
		Template.Part part = createTemplatePart(null, templateList)

		when:
		YearRange yearRange = partToYearRangeProcessor.process(part)

		then: 'both years are null'
		yearRange.yearFrom == null
		yearRange.yearTo == null
	}

	private static Template.Part createTemplatePart(String value, List<Template> templates) {
		new Template.Part(
				value: value,
				templates: templates)
	}

}
