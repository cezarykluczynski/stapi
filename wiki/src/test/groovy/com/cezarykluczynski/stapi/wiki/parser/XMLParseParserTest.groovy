package com.cezarykluczynski.stapi.wiki.parser

import com.cezarykluczynski.stapi.wiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.wiki.dto.Page
import com.google.common.collect.Lists
import spock.lang.Specification

class XMLParseParserTest extends Specification {

	private static final String XML = '''
		<api>
			<parse title="Patrick Stewart" pageid="2501">
				<categories>
					<cl sortkey="Stewart, Patrick" missing="" xml:space="preserve">Performers</cl>
					<cl sortkey="Stewart, Patrick" missing="" xml:space="preserve">TNG_performers</cl>
				</categories>
				<parsetree xml:space="preserve">&lt;root&gt;&lt;/root&gt;</parsetree>
			</parse>
		</api>
'''

	def "converts XML to Page"() {
		given:
		Page expectedPage = new Page()
		expectedPage.pageId = 2501L
		expectedPage.title = "Patrick Stewart"
		expectedPage.categories = Lists.newArrayList(
				new CategoryHeader(title: "Performers"),
				new CategoryHeader(title: "TNG_performers")
		)
		expectedPage.templates = Lists.newArrayList()

		when:
		Page page = new XMLParseParser(XML).getPage()

		then:
		page == expectedPage
	}

	def "convert exceptions to runtime exceptions"() {
		when:
		new XMLParseParser("").getPage()

		then:
		thrown(RuntimeException)
	}

}
