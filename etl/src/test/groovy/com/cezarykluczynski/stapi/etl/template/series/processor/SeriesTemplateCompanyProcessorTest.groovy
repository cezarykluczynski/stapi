package com.cezarykluczynski.stapi.etl.template.series.processor

import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.company.repository.CompanyRepository
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import spock.lang.Specification

class SeriesTemplateCompanyProcessorTest extends Specification {

	private static final String WIKITEXT = 'WIKITEXT'
	private static final String COMPANY_NAME = 'COMPANY_NAME'

	private WikitextApi wikitextApiMock

	private CompanyRepository companyRepositoryMock

	private SeriesTemplateCompanyProcessor seriesTemplateCompanyProcessor

	void setup() {
		wikitextApiMock = Mock()
		companyRepositoryMock = Mock()
		seriesTemplateCompanyProcessor = new SeriesTemplateCompanyProcessor(wikitextApiMock, companyRepositoryMock)
	}

	void "returns null when no page links could be found"() {
		when:
		Company companyOutput = seriesTemplateCompanyProcessor.process(new Template.Part(value: WIKITEXT))

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> Lists.newArrayList()
		0 * _
		companyOutput == null
	}

	void "gets company from wikitext link"() {
		given:
		Template.Part templatePart = new Template.Part(value: WIKITEXT)
		Company company = Mock()

		when:
		Company companyOutput = seriesTemplateCompanyProcessor.process(templatePart)

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> Lists.newArrayList(new PageLink(title: COMPANY_NAME))
		1 * companyRepositoryMock.findByPageTitleAndPageMediaWikiSource(COMPANY_NAME, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(company)
		0 * _
		companyOutput == company
	}

	void "returns null when company from wikitext link cannot be found"() {
		given:
		Template.Part templatePart = new Template.Part(value: WIKITEXT)

		when:
		Company companyOutput = seriesTemplateCompanyProcessor.process(templatePart)

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> Lists.newArrayList(new PageLink(title: COMPANY_NAME))
		1 * companyRepositoryMock.findByPageTitleAndPageMediaWikiSource(COMPANY_NAME, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.empty()
		0 * _
		companyOutput == null
	}

	void "gets company from dis template"() {
		given:
		Template template = new Template(title: TemplateTitle.DIS)
		Template.Part templatePart = new Template.Part(
				value: WIKITEXT,
				templates: Lists.newArrayList(template))
		Company company = Mock()

		when:
		Company companyOutput = seriesTemplateCompanyProcessor.process(templatePart)

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> Lists.newArrayList()
		1 * wikitextApiMock.disTemplateToPageTitle(template) >> COMPANY_NAME
		1 * companyRepositoryMock.findByPageTitleAndPageMediaWikiSource(COMPANY_NAME, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(company)
		0 * _
		companyOutput == company
	}

	void "returns null when company from dis template cannot be found"() {
		given:
		Template template = new Template(title: TemplateTitle.DIS)
		Template.Part templatePart = new Template.Part(
				value: WIKITEXT,
				templates: Lists.newArrayList(template))

		when:
		Company companyOutput = seriesTemplateCompanyProcessor.process(templatePart)

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> Lists.newArrayList()
		1 * wikitextApiMock.disTemplateToPageTitle(template) >> COMPANY_NAME
		1 * companyRepositoryMock.findByPageTitleAndPageMediaWikiSource(COMPANY_NAME, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.empty()
		0 * _
		companyOutput == null
	}

	void "returns null when dis template cannot be parse into title"() {
		given:
		Template template = new Template(title: TemplateTitle.DIS)
		Template.Part templatePart = new Template.Part(
				value: WIKITEXT,
				templates: Lists.newArrayList(template))

		when:
		Company companyOutput = seriesTemplateCompanyProcessor.process(templatePart)

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> Lists.newArrayList()
		1 * wikitextApiMock.disTemplateToPageTitle(template) >> null
		0 * _
		companyOutput == null
	}

}
