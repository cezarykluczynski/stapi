package com.cezarykluczynski.stapi.etl.mediawiki.api

import com.cezarykluczynski.stapi.etl.mediawiki.api.dto.PageLink
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import org.jsoup.Jsoup
import spock.lang.Specification
import spock.lang.Unroll

class WikitextApiImplTest extends Specification {

	private static final String WIKITEXT = 'blah blah [[Some page| description]] and [[another page]] blah blah {{dis|Mark|technician}} ' +
			'blah {{mu|Picard}} blah {{alt|Riker}} blah {{federation}} blah {{dvd|Star Trek: The Motion Picture}} blah'
	private static final String HTML = 'blah blah <span class="new" title="Some page"> description</span> and ' +
			'<span class="new" title="another page (page does not exist)">another page</span> blah blah ' +
			'<a href="/wiki/Mark (technican)" title="Mark (technican)">Mark (technican)</a> blah ' +
			'<span class="new" title="Picard (mirror) (page does not exist)">Picard</span> blah ' +
			'<a href="/wiki/Riker_(alternate_reality)" title="Riker (alternate reality)">Riker (alternate reality)</a> blah'
	private static final String WIKITEXT_WITH_TEMPLATES = '{{realworld}}{{sidebar planet\nName=Mantiles}}blah blah{{ds9|Some page}} blah'
	private static final String WIKITEXT_WITH_DIS = '* [[Malibu DS9]]:\n** #1: "{{dis|Stowaway|comic}}"\n** #2: "[[Stowaway, Part II]]"\n' +
			'** #3: "{{dis|Old Wounds|comic|the comic}}"\n** #4: "[[Emancipation, Part I]]"'
	private static final String WIKITEXT_WITH_NO_INCLUDE = '<noinclude>The number of produced {{s|TAS}} episodes: ' +
			'</noinclude>22<noinclude>[[Category:Memory Alpha constants templates|{{PAGENAME}}]]</noinclude>'
	private static final String WIKITEXT_WITHOUT_TEMPLATES = 'blah blah blah'
	private static final String WIKITEXT_WITH_LINKS = '\'\'[[Star Trek]]\'\' created by [[Gene Roddenberry]], ' +
			'[[25th anniversary|25th Anniversary]]; [[IMDb:nm6618259|Jenna Vaughn]], [[A. Conan Doyle]]'
	private static final String WIKITEXT_WITHOUT_LINKS = '\'\'Star Trek\'\' created by Gene Roddenberry, ' +
			'25th Anniversary; Jenna Vaughn, A. Conan Doyle'
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
		pageList.size() == 7
		pageList[0] == 'Some page'
		pageList[1] == 'another page'
		pageList[2] == 'Mark (technician)'
		pageList[3] == 'Picard (mirror)'
		pageList[4] == 'Riker (alternate reality)'
		pageList[5] == 'United Federation of Planets'
		pageList[6] == 'Star Trek: The Motion Picture (DVD)'
	}

	void "gets titles from wikitext, excluding not found"() {
		given:

		Page page = new Page(htmlDocument: Jsoup.parse(HTML))

		when:
		List<String> pageList = wikitextApiImpl.getPageTitlesFromWikitextExcludingNotFound(WIKITEXT, page)

		then:
		pageList.size() == 4
		pageList[0] == 'Mark (technician)'
		pageList[1] == 'Riker (alternate reality)'
		pageList[2] == 'United Federation of Planets'
		pageList[3] == 'Star Trek: The Motion Picture (DVD)'
	}

	void "gets page links from wikitext"() {
		when:
		List<PageLink> pageLinkList = wikitextApiImpl.getPageLinksFromWikitext(WIKITEXT)

		then:
		pageLinkList.size() == 7
		pageLinkList[0].title == 'Some page'
		pageLinkList[0].untrimmedDescription == ' description'
		pageLinkList[0].description == 'description'
		pageLinkList[0].startPosition == 10
		pageLinkList[0].endPosition == 36
		pageLinkList[1].title == 'another page'
		pageLinkList[1].description == null
		pageLinkList[1].startPosition == 41
		pageLinkList[1].endPosition == 57
		pageLinkList[2].title == 'Mark (technician)'
		pageLinkList[2].description == 'Mark'
		pageLinkList[2].startPosition == 72
		pageLinkList[2].endPosition == 91
		pageLinkList[3].title == 'Picard (mirror)'
		pageLinkList[3].description == 'Picard'
		pageLinkList[3].startPosition == 100
		pageLinkList[3].endPosition == 110
		pageLinkList[4].title == 'Riker (alternate reality)'
		pageLinkList[4].description == 'Riker'
		pageLinkList[4].startPosition == 120
		pageLinkList[4].endPosition == 129
		pageLinkList[5].title == 'United Federation of Planets'
		pageLinkList[5].description == 'United Federation of Planets'
		pageLinkList[5].startPosition == 135
		pageLinkList[5].endPosition == 149
		pageLinkList[6].startPosition == 159
		pageLinkList[6].endPosition == 192
		pageLinkList[6].title == 'Star Trek: The Motion Picture (DVD)'
		pageLinkList[6].description == 'Star Trek: The Motion Picture'
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

	void "removes noinclude from wikitext"() {
		when:
		String wikitextWithoutNoInclude = wikitextApiImpl.getWikitextWithoutNoInclude(WIKITEXT_WITH_NO_INCLUDE)

		then:
		wikitextWithoutNoInclude == '22'
	}

	void "gets text without links"() {
		when:
		String wikitextWithoutLinks = wikitextApiImpl.getWikitextWithoutLinks(WIKITEXT_WITH_LINKS)

		then:
		wikitextWithoutLinks == WIKITEXT_WITHOUT_LINKS
	}

	void "gets text without links (null)"() {
		when:
		String wikitextWithoutLinks = wikitextApiImpl.getWikitextWithoutLinks(null)

		then:
		wikitextWithoutLinks == null
	}

	@SuppressWarnings('LineLength')
	@Unroll('returns #pageLink when #template is passed')
	void "returns links title from DIS templates"() {
		expect:
		wikitextApiImpl.disTemplateToPageTitle(template) == pageLink

		where:
		template                                                                                                                              | pageLink
		new Template() | null
		new Template(title: TemplateTitle.D)                                                                                                  | null
		new Template(title: TemplateTitle.DIS)                                                                                                | null
		createValid(new Template.Part(key: '1', value: DIS_TEMPLATE_PAGE_NAME))                                                               | DIS_TEMPLATE_PAGE_NAME
		createValid(new Template.Part(key: '1', value: DIS_TEMPLATE_PAGE_NAME), new Template.Part(key: 'other'))                              | DIS_TEMPLATE_PAGE_NAME
		createValid(new Template.Part(key: '1', value: DIS_TEMPLATE_PAGE_NAME), new Template.Part(key: '2', value: DIS_TEMPLATE_PAGE_DETAIL)) | "${DIS_TEMPLATE_PAGE_NAME} (${DIS_TEMPLATE_PAGE_DETAIL})"
		createValid(new Template.Part(key: '1'), new Template.Part(key: '2'), new Template.Part(key: '3'))                                    | null
	}

	void "extracts template names from wikitext"() {
		when:
		List<String> templates = wikitextApiImpl.getTemplateNamesFromWikitext(WIKITEXT_WITH_TEMPLATES)

		then:
		templates == ['realworld', 'sidebar planet', 'ds9']
	}

	private static Template createValid(Template.Part... templateParts) {
		new Template(title: TemplateTitle.DIS, parts: Lists.newArrayList(templateParts))
	}

}
