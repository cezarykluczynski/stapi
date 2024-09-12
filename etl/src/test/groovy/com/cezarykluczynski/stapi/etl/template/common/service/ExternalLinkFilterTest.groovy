package com.cezarykluczynski.stapi.etl.template.common.service

import com.cezarykluczynski.stapi.model.external_link.entity.ExternalLink
import com.cezarykluczynski.stapi.model.external_link.entity.enums.ExternalLinkType
import com.cezarykluczynski.stapi.model.external_link.entity.enums.ExternalLinkTypeVariant
import spock.lang.Specification

class ExternalLinkFilterTest extends Specification {

	ExternalLinkFilter externalLinkFilter

	void setup() {
		externalLinkFilter = new ExternalLinkFilter()
	}

	void "detects person link by type"() {
		given:
		ExternalLink externalLink = new ExternalLink(type: ExternalLinkType.FACEBOOK)

		expect:
		externalLinkFilter.isPersonLink(externalLink)
	}

	void "detects non-person link by type"() {
		given:
		ExternalLink externalLink = new ExternalLink(type: ExternalLinkType.CHAKOTEYA)

		expect:
		!externalLinkFilter.isPersonLink(externalLink)
	}

	void "detects person link by variant"() {
		given:
		ExternalLink externalLink = new ExternalLink(variant: ExternalLinkTypeVariant.YOUTUBE_USER)

		expect:
		externalLinkFilter.isPersonLink(externalLink)
	}

	void "detects non-person link by variant"() {
		given:
		ExternalLink externalLink = new ExternalLink(variant: ExternalLinkTypeVariant.IMDB_TITLE)

		expect:
		!externalLinkFilter.isPersonLink(externalLink)
	}

	void "returns false for person when input's external link is null"() {
		expect:
		!externalLinkFilter.isPersonLink(null)
	}

	void "detects episode or movie link by type"() {
		given:
		ExternalLink externalLink = new ExternalLink(type: ExternalLinkType.CHAKOTEYA)

		expect:
		externalLinkFilter.isEpisodeOrMovieLink(externalLink)
	}

	void "detects non-episode or movie link by type"() {
		given:
		ExternalLink externalLink = new ExternalLink(type: ExternalLinkType.FACEBOOK)

		expect:
		!externalLinkFilter.isEpisodeOrMovieLink(externalLink)
	}

	void "detects episode or movie link by variant"() {
		given:
		ExternalLink externalLink = new ExternalLink(variant: ExternalLinkTypeVariant.IMDB_TITLE)

		expect:
		externalLinkFilter.isEpisodeOrMovieLink(externalLink)
	}

	void "detects non-episode or movie link by variant"() {
		given:
		ExternalLink externalLink = new ExternalLink(variant: ExternalLinkTypeVariant.IMDB_NAME)

		expect:
		!externalLinkFilter.isEpisodeOrMovieLink(externalLink)
	}

	void "returns false for episode or movie when input is null"() {
		expect:
		!externalLinkFilter.isEpisodeOrMovieLink(null)
	}

	void "returns false for episode or movie when input's external link is null"() {
		expect:
		!externalLinkFilter.isEpisodeOrMovieLink(null)
	}

}
