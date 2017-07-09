package com.cezarykluczynski.stapi.sources.wordpress.connector.afrozaar

import com.afrozaar.wordpress.wpapi.v2.model.Content
import com.afrozaar.wordpress.wpapi.v2.model.Page as WordPressPage
import com.afrozaar.wordpress.wpapi.v2.model.Title
import com.cezarykluczynski.stapi.sources.wordpress.dto.Page
import spock.lang.Specification

class WordPressPageMapperTest extends Specification {

	private static final Long ID = 1L
	private static final String SLUG = 'SLUG'
	private static final String RAW_TITLE = 'RAW_TITLE'
	private static final String RENDERED_TITLE = 'RENDERED_TITLE'
	private static final String RAW_CONTENT = 'RAW_CONTENT'
	private static final String RENDERED_CONTENT = 'RENDERED_CONTENT'

	private WordPressPageMapper wordPressPageMapper

	void setup() {
		wordPressPageMapper = new WordPressPageMapper()
	}

	void "maps null to null"() {
		when:
		Page page = wordPressPageMapper.map(null)

		then:
		page == null
	}

	void "maps WordPress page with title"() {
		given:
		WordPressPage wordPressPage = new WordPressPage(
				id: ID,
				slug: SLUG,
				title: new Title(
						raw: RAW_TITLE,
						rendered: RENDERED_TITLE))

		when:
		Page page = wordPressPageMapper.map(wordPressPage)

		then:
		page.id == ID
		page.slug == SLUG
		page.rawTitle == RAW_TITLE
		page.renderedTitle == RENDERED_TITLE
	}

	void "maps WordPress page with content"() {
		given:
		WordPressPage wordPressPage = new WordPressPage(
				id: ID,
				slug: SLUG,
				content: new Content(
						raw: RAW_CONTENT,
						rendered: RENDERED_CONTENT))

		when:
		Page page = wordPressPageMapper.map(wordPressPage)

		then:
		page.id == ID
		page.slug == SLUG
		page.rawContent == RAW_CONTENT
		page.renderedContent == RENDERED_CONTENT
	}

	void "maps WordPress page without title and without content"() {
		given:
		WordPressPage wordPressPage = new WordPressPage(
				id: ID,
				slug: SLUG)

		when:
		Page page = wordPressPageMapper.map(wordPressPage)

		then:
		page.id == ID
		page.slug == SLUG
		page.rawTitle == null
		page.renderedTitle == null
		page.rawContent == null
		page.renderedContent == null
	}

}
