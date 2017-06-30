package com.cezarykluczynski.stapi.etl.template.video.processor

import com.cezarykluczynski.stapi.etl.template.video.dto.EpisodesCountDTO
import spock.lang.Specification
import spock.lang.Unroll

class VideoTemplateEpisodesCountProcessorTest extends Specification {

	VideoTemplateEpisodesCountProcessor videoTemplateEpisodesCountProcessor

	void setup() {
		videoTemplateEpisodesCountProcessor = new VideoTemplateEpisodesCountProcessor()
	}

	@Unroll('when #candidate string is passed, #result is returned')
	void "when candidate string is passed, EpisodeCountDTO is returned"() {
		expect:
		result == videoTemplateEpisodesCountProcessor.process(candidate)

		where:
		candidate                          | result
		null                               | new EpisodesCountDTO(numberOfEpisodes: null, numberOfFeatureLengthEpisodes: null)
		''                                 | new EpisodesCountDTO(numberOfEpisodes: null, numberOfFeatureLengthEpisodes: null)
		'13 (1 feature length)'            | new EpisodesCountDTO(numberOfEpisodes: 13, numberOfFeatureLengthEpisodes: 1)
		'14'                               | new EpisodesCountDTO(numberOfEpisodes: 14, numberOfFeatureLengthEpisodes: 0)
		'4 (2 feature-length)'             | new EpisodesCountDTO(numberOfEpisodes: 4, numberOfFeatureLengthEpisodes: 2)
		'6<br/>7 (Netherlands)'            | new EpisodesCountDTO(numberOfEpisodes: 6, numberOfFeatureLengthEpisodes: 0)
		'2 feature-length'                 | new EpisodesCountDTO(numberOfEpisodes: 2, numberOfFeatureLengthEpisodes: 2)
		'85 (80 TOS, 3 TNG, 1 DS9, 1 VOY)' | new EpisodesCountDTO(numberOfEpisodes: 85, numberOfFeatureLengthEpisodes: 0)
		'694 + 10 feature films'           | new EpisodesCountDTO(numberOfEpisodes: 694, numberOfFeatureLengthEpisodes: 0)
	}

}
