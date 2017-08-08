package com.cezarykluczynski.stapi.sources.mediawiki.api

import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import spock.lang.Specification
import spock.lang.Unroll

class WikitextApiImplTest extends Specification {

	private static final String WIKITEXT = 'blah blah [[Some page| description]] and [[another page]] blah blah [[blah'
	private static final String WIKITEXT_WITH_TEMPLATES = '{{realworld}}{{sidebar planet\nName=Mantiles}}blah blah{{ds9|Some page}} blah'
	private static final String WIKITEXT_WITH_DIS = '* [[Malibu DS9]]:\n** #1: "{{dis|Stowaway|comic}}"\n** #2: "[[Stowaway, Part II]]"\n' +
			'** #3: "{{dis|Old Wounds|comic|the comic}}"\n** #4: "[[Emancipation, Part I]]"'
	private static final String WIKITEXT_WITHOUT_TEMPLATES = 'blah blah blah'
	private static final String WIKITEXT_WITH_LINKS = '\'\'[[Star Trek]]\'\' created by [[Gene Roddenberry]], [[25th anniversary|25th Anniversary]]'
	private static final String WIKITEXT_WITHOUT_LINKS = '\'\'Star Trek\'\' created by Gene Roddenberry, 25th Anniversary'
	private static final String DIS_TEMPLATE_PAGE_NAME = 'Page'
	private static final String DIS_TEMPLATE_PAGE_DETAIL = '(detail)'

	WikitextApiImpl wikitextApiImpl

	void setup() {
		wikitextApiImpl = new WikitextApiImpl()
	}

	void "gets titles from wikitext"() {
		when:
		List<String> pageList = wikitextApiImpl.getPageTitlesFromWikitext(WIKITEXT)

		then:
		pageList.size() == 2
		pageList[0] == 'Some page'
		pageList[1] == 'another page'
	}

	void "gets page links from wikitext"() {
		when:
		List<PageLink> pageLinkList = wikitextApiImpl.getPageLinksFromWikitext(WIKITEXT)

		then:
		pageLinkList.size() == 2
		pageLinkList[0].title == 'Some page'
		pageLinkList[0].untrimmedDescription == ' description'
		pageLinkList[0].description == 'description'
		pageLinkList[0].startPosition == 10
		pageLinkList[0].endPosition == 36
		pageLinkList[1].title == 'another page'
		pageLinkList[1].description == null
		pageLinkList[1].startPosition == 41
		pageLinkList[1].endPosition == 57
	}

	void "gets page links from wikitext, including those from 'dis' template"() {
		when:
		List<PageLink> pageLinkList = wikitextApiImpl.getPageLinksFromWikitext(WIKITEXT_WITH_DIS)

		then:
		pageLinkList.size() == 5
		pageLinkList[0].title == 'Malibu DS9'
		pageLinkList[0].description == null
		pageLinkList[0].startPosition == 2
		pageLinkList[0].endPosition == 16

		pageLinkList[1].title == 'Stowaway (comic)'
		pageLinkList[1].description == 'Stowaway'
		pageLinkList[1].startPosition == 30
		pageLinkList[1].endPosition == 48

		pageLinkList[2].title == 'Stowaway, Part II'
		pageLinkList[2].description == null
		pageLinkList[2].startPosition == 58
		pageLinkList[2].endPosition == 79

		pageLinkList[3].title == 'Old Wounds (comic)'
		pageLinkList[3].description == 'the comic'
		pageLinkList[3].startPosition == 93
		pageLinkList[3].endPosition == 123

		pageLinkList[4].title == 'Emancipation, Part I'
		pageLinkList[4].description == null
		pageLinkList[4].startPosition == 133
		pageLinkList[4].endPosition == 157
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
