package com.cezarykluczynski.stapi.etl.template.series.processor

import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.company.repository.CompanyRepository
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource
import com.cezarykluczynski.stapi.etl.mediawiki.api.PageApi
import com.cezarykluczynski.stapi.etl.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.etl.mediawiki.api.dto.PageLink
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import spock.lang.Specification

class SeriesTemplateCompanyProcessorTest extends Specification {

	private static final String WIKITEXT = 'WIKITEXT'
	private static final String CONTENT = 'CONTENT'
	private static final String COMPANY_NAME = 'COMPANY_NAME'
	private static final String COMPANY_NAME_REDIRECT = 'COMPANY_NAME_REDIRECT'

	private WikitextApi wikitextApiMock

	private PageApi pageApiMock

	private CompanyRepository companyRepositoryMock

	private SeriesTemplateCompanyProcessor seriesTemplateCompanyProcessor

	void setup() {
		wikitextApiMock = Mock()
		pageApiMock = Mock()
		companyRepositoryMock = Mock()
		seriesTemplateCompanyProcessor = new SeriesTemplateCompanyProcessor(wikitextApiMock, pageApiMock, companyRepositoryMock)
	}

	void "returns null when no page links could be found"() {
		when:
		Company companyOutput = seriesTemplateCompanyProcessor.process(new Template.Part(value: WIKITEXT, content: CONTENT))

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> Lists.newArrayList()
		1 * wikitextApiMock.getPageLinksFromWikitext(CONTENT) >> Lists.newArrayList()
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
		1 * companyRepositoryMock.findByPageTitleWithPageMediaWikiSource(COMPANY_NAME, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(company)
		0 * _
		companyOutput == company
	}

	void "gets company from template wikitext link"() {
		given:
		Template.Part templatePart = new Template.Part(value: '', templates: [new Template(parts: [new Template.Part(value: WIKITEXT)])])
		Company company = Mock()

		when:
		Company companyOutput = seriesTemplateCompanyProcessor.process(templatePart)

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext('') >> Lists.newArrayList()
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> Lists.newArrayList(new PageLink(title: COMPANY_NAME))
		1 * companyRepositoryMock.findByPageTitleWithPageMediaWikiSource(COMPANY_NAME, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(company)
		0 * _
		companyOutput == company
	}

	void "gets company from template content wikitext link"() {
		given:
		Template.Part templatePart = new Template.Part(value: '', content: WIKITEXT)
		Company company = Mock()

		when:
		Company companyOutput = seriesTemplateCompanyProcessor.process(templatePart)

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext('') >> Lists.newArrayList()
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> Lists.newArrayList(new PageLink(title: COMPANY_NAME))
		1 * companyRepositoryMock.findByPageTitleWithPageMediaWikiSource(COMPANY_NAME, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(company)
		0 * _
		companyOutput == company
	}

	void "returns null when company from wikitext link cannot be found, and page is not a redirect"() {
		given:
		Template.Part templatePart = new Template.Part(value: WIKITEXT)

		when:
		Company companyOutput = seriesTemplateCompanyProcessor.process(templatePart)

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> Lists.newArrayList(new PageLink(title: COMPANY_NAME))
		1 * companyRepositoryMock.findByPageTitleWithPageMediaWikiSource(COMPANY_NAME, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.empty()
		1 * pageApiMock.getPage(COMPANY_NAME, com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource.MEMORY_ALPHA_EN) >>
				new Page(title: COMPANY_NAME)
		0 * _
		companyOutput == null
	}

	void "returns null when company from wikitext link cannot be found, and page is a redirect"() {
		given:
		Template.Part templatePart = new Template.Part(value: WIKITEXT)

		when:
		Company companyOutput = seriesTemplateCompanyProcessor.process(templatePart)

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> Lists.newArrayList(new PageLink(title: COMPANY_NAME))
		1 * companyRepositoryMock.findByPageTitleWithPageMediaWikiSource(COMPANY_NAME, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.empty()
		1 * pageApiMock.getPage(COMPANY_NAME, com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource.MEMORY_ALPHA_EN) >>
				new Page(title: COMPANY_NAME_REDIRECT)
		1 * companyRepositoryMock.findByPageTitleWithPageMediaWikiSource(COMPANY_NAME_REDIRECT, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.empty()
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
		1 * companyRepositoryMock.findByPageTitleWithPageMediaWikiSource(COMPANY_NAME, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(company)
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
		1 * companyRepositoryMock.findByPageTitleWithPageMediaWikiSource(COMPANY_NAME, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.empty()
		1 * pageApiMock.getPage(COMPANY_NAME, com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource.MEMORY_ALPHA_EN) >>
				new Page(title: COMPANY_NAME)
		0 * _
		companyOutput == null
	}

	void "returns null when dis template cannot be parse into title"() {
		given:
		Template template = new Template(title: TemplateTitle.DIS)
		Template.Part templatePart = new Template.Part(
				value: WIKITEXT,
				content: CONTENT,
				templates: Lists.newArrayList(template))

		when:
		Company companyOutput = seriesTemplateCompanyProcessor.process(templatePart)

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> Lists.newArrayList()
		1 * wikitextApiMock.disTemplateToPageTitle(template) >> null
		1 * wikitextApiMock.getPageLinksFromWikitext(CONTENT) >> Lists.newArrayList()
		0 * _
		companyOutput == null
	}

}
