package com.cezarykluczynski.stapi.sources.mediawiki.api

import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import spock.lang.Specification
import spock.lang.Unroll

class WikitextApiImplTest extends Specification {

	private static final String WIKITEXT = 'blah blah [[Some page|description]] and [[another page]] blah blah [[blah'
	private static final String WIKITEXT_WITH_TEMPLATES = '{{realworld}}{{sidebar planet\nName=Mantiles}}blah blah{{ds9|Some page}} blah'
	private static final String WIKITEXT_WITHOUT_TEMPLATES = 'blah blah blah'
	private static final String WIKITEXT_WITH_LINKS = '\'\'[[Star Trek]]\'\' created by [[Gene Roddenberry]]'
	private static final String WIKITEXT_WITHOUT_LINKS = '\'\'Star Trek\'\' created by Gene Roddenberry'
	private static final String DIS_TEMPLATE_PAGE_NAME = 'Page'
	private static final String DIS_TEMPLATE_PAGE_DETAIL = '(detail)'

	WikitextApiImpl wikitextApiImpl

	void setup() {
		wikitextApiImpl = new WikitextApiImpl()
	}

	void "gets titles from wikitext"() {
		when:
		List<String> pageList = wikitextApiImpl
				.getPageTitlesFromWikitext(WIKITEXT)

		then:
		pageList.size() == 2
		pageList[0] == 'Some page'
		pageList[1] == 'another page'
	}

	void "gets page links from wikitext"() {
		when:
		List<PageLink> pageList = wikitextApiImpl
				.getPageLinksFromWikitext(WIKITEXT)

		then:
		pageList.size() == 2
		pageList[0].title == 'Some page'
		pageList[0].description == 'description'
		pageList[0].startPosition == 10
		pageList[0].endPosition == 35
		pageList[1].title == 'another page'
		pageList[1].description == null
		pageList[1].startPosition == 40
		pageList[1].endPosition == 56
	}

	void "removes templates from wikitext"() {
		when:
		String wikitextWithoutTemplates = wikitextApiImpl.getWikitextWithoutTemplates(WIKITEXT_WITH_TEMPLATES)

		then:
		wikitextWithoutTemplates == WIKITEXT_WITHOUT_TEMPLATES
	}

	void "gets text without links"() {
		when:
		String wikitextWithoutLinks = wikitextApiImpl.getWikitextWithoutLinks(WIKITEXT_WITH_LINKS)

		then:
		wikitextWithoutLinks == WIKITEXT_WITHOUT_LINKS
	}

	@SuppressWarnings('LineLength')
	@Unroll('returns #linkTitle when #template is passed')
	void "returns links title from DIS templates"() {
		expect:
		wikitextApiImpl.disTemplateToPageTitle(template) == pageLink

		where:
		template                                                                                                                              | pageLink
		new Template()                                                                                                                        | null
		new Template(title: TemplateTitle.D)                                                                                                  | null
		new Template(title: TemplateTitle.DIS)                                                                                                | null
		createValid(new Template.Part(key: '1', value: DIS_TEMPLATE_PAGE_NAME))                                                               | DIS_TEMPLATE_PAGE_NAME
		createValid(new Template.Part(key: '1', value: DIS_TEMPLATE_PAGE_NAME), new Template.Part(key: 'other'))                              | DIS_TEMPLATE_PAGE_NAME
		createValid(new Template.Part(key: '1', value: DIS_TEMPLATE_PAGE_NAME), new Template.Part(key: '2', value: DIS_TEMPLATE_PAGE_DETAIL)) | "${DIS_TEMPLATE_PAGE_NAME} (${DIS_TEMPLATE_PAGE_DETAIL})"
		createValid(new Template.Part(key: '1'), new Template.Part(key: '2'), new Template.Part(key: '3'))                                    | null
	}

	private static Template createValid(Template.Part... templateParts) {
		new Template(title: TemplateTitle.DIS, parts: Lists.newArrayList(templateParts))
	}

}
