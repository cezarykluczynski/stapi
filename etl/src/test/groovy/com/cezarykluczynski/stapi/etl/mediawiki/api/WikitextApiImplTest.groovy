package com.cezarykluczynski.stapi.etl.mediawiki.api

import com.cezarykluczynski.stapi.etl.mediawiki.api.dto.PageLink
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template
import com.cezarykluczynski.stapi.etl.template.common.factory.ExternalLinkFactory
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.external_link.entity.ExternalLink
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
	private static final String TITLE = 'Jonathan Frakes'
	private static final String TITLE_UNDERSCORE = 'Jonathan_Frakes'
	private static final String WIKITEXT_EXTERNAL_LINKS_TEMPLATES = '{{facebook|StarTrek}}, {{IBDb-link|id=60702}}, ' +
			'{{IBDb-link|type=person|id=66778|name=René Auberjonois}}, {{IMDb-ep|tt0708804|The Last Outpost}}, ' +
			'{{IMDb-title|tt0088170|The Search For Spock}}, {{IMDb|name/nm0000638|James T. Kirk}}, ' +
			'{{instagram|michael.chabon|Michael Chabon\'s Instagram feed}}, {{instagram|ashley_judd|Ashley Judd|external}}, ' +
			'{{instagram|mollymaxineb}}, {{ISFDB|author|1339}}, {{mbeta}}, {{mb|Codobach}}, {{mbeta-quote|Exodus (novel)|Exodus}}, ' +
			'{{mbeta-title|Salt mine}}, {{myspace|andydick|Andy Dick\'s profile}}, * {{myspace|topshelf55||external}}' +
			'{{myspace|s=profile|index.cfm?fuseaction{{=}}user.viewprofile&friendID{{=}}63046395|Video|external}},' +
			'{{sf-encyc|sturgeon_theodore|Ted Sturgeon}}, {{sf-encyc|gerrold_david}}, ' +
			'{{sf-encyc|john|news}}, {{sf-encyc|terry-pratchett-(1948-2015)|Terry Pratchett obituary|news}}, ' +
			'{{st.com|article/exclusive-interview-frequent-trek-guest-eric-pierpoint}}, ' +
			'{{st.com|article/official-star-trek-convention-in-san-francisco}}, ' +
			'{{startrek.com|database_article/chang-general|General Chang|bl=1}},' +
			'{{Star Trek Minutiae|resources/scripts/202.txt}}, {{stowiki}}, {{stowiki|Species_8472}}, ' +
			'{{triviatribute|brentspiner.html}}, {{twitter|simonpegg||external}}, {{twitter|carljones|bl=1}}, ' +
			'{{wikipedia}}, {{wikipedia|Data}}, {{w|Butch and Sundance: The Early Days}}, {{wikipedia-title|Spot}}, ' +
			'{{wikipedia-quote}}, {{wikipedia-quote|Klingon}}, {{wt|Romulan}}, {{wikiquote}}, {{wikiquote|Patrick Stewart}}, ' +
			'{{youtube|TrekPro}}, {{YouTube|type=u|GeneraIGrin}}, {{YouTube|type=u|GeneraIGrinLong}}, ' +
			'{{youtube|type=v|LXGsmTWi6X4}}, {{youtube|type=video|LXGsmTWi6X4Long}}, ' +
			'{{YouTube|type=c|UCE8vp1lZ2Y4qx-rX48J40JA}}, {{YouTube|type=channel|UCE8vp1lZ2Y4qx-rX48J40JALong}}, ' +
			'{{youtube|type=p|PLdJ4SzBYWAXmaGvnEN_w59rxvRleTiqvK}}, {{youtube|type=playlist|PLdJ4SzBYWAXmaGvnEN_w59rxvRleTiqvKLong}}'

	WikitextApiImpl wikitextApiImpl

	void setup() {
		UidGenerator uidGenerator = Mock()
		wikitextApiImpl = new WikitextApiImpl(new ExternalLinkFactory(uidGenerator))
	}

	void "gets external links from wikitext"() {
		given:
		Page page = new Page(title: TITLE)

		when:
		List<ExternalLink> externalLinks = wikitextApiImpl.getExternalLinksFromWikitext(WIKITEXT_EXTERNAL_LINKS_TEMPLATES, page)

		then:
		externalLinks.stream().anyMatch { it.link == "https://memory-alpha.fandom.com/wiki/$TITLE_UNDERSCORE" }
		externalLinks.stream().anyMatch { it.link == 'https://www.facebook.com/StarTrek' }
		externalLinks.stream().anyMatch { it.link == 'https://www.ibdb.com/person.php?id=60702' }
		externalLinks.stream().anyMatch { it.link == 'https://www.ibdb.com/person.php?id=66778' }
		externalLinks.stream().anyMatch { it.link == 'https://www.imdb.com/title/tt0708804' }
		externalLinks.stream().anyMatch { it.link == 'https://www.imdb.com/title/tt0088170' }
		externalLinks.stream().anyMatch { it.link == 'https://www.imdb.com/name/nm0000638' }
		externalLinks.stream().anyMatch { it.link == 'https://instagram.com/michael.chabon' }
		externalLinks.stream().anyMatch { it.link == 'https://instagram.com/ashley_judd' }
		externalLinks.stream().anyMatch { it.link == 'https://instagram.com/mollymaxineb' }
		externalLinks.stream().anyMatch { it.link == 'https://www.isfdb.org/cgi-bin/ea.cgi?1339' }
		externalLinks.stream().anyMatch { it.link == "https://memory-beta.fandom.com/wiki/$TITLE_UNDERSCORE" }
		externalLinks.stream().anyMatch { it.link == 'https://memory-beta.fandom.com/wiki/Exodus_(novel)' }
		externalLinks.stream().anyMatch { it.link == 'https://memory-beta.fandom.com/wiki/Salt_mine' }
		externalLinks.stream().anyMatch { it.link == 'https://www.myspace.com/andydick' }
		externalLinks.stream().anyMatch { it.link == 'https://www.myspace.com/topshelf55' }
		externalLinks.stream().noneMatch { it.link == 'https://www.myspace.com/s=profile' }
		externalLinks.stream().anyMatch { it.link == 'https://www.sf-encyclopedia.com/entry/sturgeon_theodore' }
		externalLinks.stream().anyMatch { it.link == 'https://www.sf-encyclopedia.com/entry/gerrold_david' }
		externalLinks.stream().anyMatch { it.link == 'https://www.sf-encyclopedia.com/news/john' }
		externalLinks.stream().anyMatch { it.link == 'https://www.sf-encyclopedia.com/news/terry-pratchett-(1948-2015)' }
		externalLinks.stream().noneMatch { it.link == 'https://www.sf-encyclopedia.com/news/sturgeon_theodore' }
		externalLinks.stream().noneMatch { it.link == 'https://www.sf-encyclopedia.com/news/gerrold_david' }
		externalLinks.stream().noneMatch { it.link == 'https://www.sf-encyclopedia.com/entry/john' }
		externalLinks.stream().noneMatch { it.link == 'https://www.sf-encyclopedia.com/entry/terry-pratchett-(1948-2015)' }
		externalLinks.stream().anyMatch { it.link == 'https://www.startrek.com/article/exclusive-interview-frequent-trek-guest-eric-pierpoint' }
		externalLinks.stream().anyMatch { it.link == 'https://www.startrek.com/article/official-star-trek-convention-in-san-francisco' }
		externalLinks.stream().noneMatch { it.link.contains('database_article/chang-general') }
		externalLinks.stream().anyMatch { it.link == 'https://www.st-minutiae.com/resources/scripts/202.txt' }
		externalLinks.stream().anyMatch { it.link == "https://stowiki.net/wiki/$TITLE_UNDERSCORE" }
		externalLinks.stream().anyMatch { it.link == 'https://stowiki.net/wiki/Species_8472' }
		externalLinks.stream().anyMatch { it.link == 'https://triviatribute.com/brentspiner.html' }
		externalLinks.stream().anyMatch { it.link == 'https://x.com/simonpegg' }
		externalLinks.stream().noneMatch { it.link == 'https://x.com/carljones' }
		externalLinks.stream().anyMatch { it.link == "https://en.wikipedia.org/wiki/$TITLE_UNDERSCORE" }
		externalLinks.stream().anyMatch { it.link == 'https://en.wikipedia.org/wiki/Data' }
		externalLinks.stream().anyMatch { it.link == 'https://en.wikipedia.org/wiki/Butch_and_Sundance:_The_Early_Days' }
		externalLinks.stream().anyMatch { it.link == 'https://en.wikipedia.org/wiki/Spot' }
		externalLinks.stream().anyMatch { it.link == 'https://en.wikipedia.org/wiki/Klingon' }
		externalLinks.stream().anyMatch { it.link == 'https://en.wikipedia.org/wiki/Romulan' }
		externalLinks.stream().anyMatch { it.link == "https://en.wikiquote.org/wiki/$TITLE_UNDERSCORE" }
		externalLinks.stream().anyMatch { it.link == 'https://en.wikiquote.org/wiki/Patrick_Stewart' }
		externalLinks.stream().anyMatch { it.link == 'https://www.youtube.com/TrekPro' }
		externalLinks.stream().anyMatch { it.link == 'https://www.youtube.com/user/GeneraIGrin' }
		externalLinks.stream().anyMatch { it.link == 'https://www.youtube.com/user/GeneraIGrinLong' }
		externalLinks.stream().anyMatch { it.link == 'https://www.youtube.com/watch?v=LXGsmTWi6X4' }
		externalLinks.stream().anyMatch { it.link == 'https://www.youtube.com/watch?v=LXGsmTWi6X4Long' }
		externalLinks.stream().anyMatch { it.link == 'https://www.youtube.com/channel/UCE8vp1lZ2Y4qx-rX48J40JA' }
		externalLinks.stream().anyMatch { it.link == 'https://www.youtube.com/channel/UCE8vp1lZ2Y4qx-rX48J40JALong' }
		externalLinks.stream().anyMatch { it.link == 'https://www.youtube.com/playlist?list=PLdJ4SzBYWAXmaGvnEN_w59rxvRleTiqvK' }
		externalLinks.stream().anyMatch { it.link == 'https://www.youtube.com/playlist?list=PLdJ4SzBYWAXmaGvnEN_w59rxvRleTiqvKLong' }
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
