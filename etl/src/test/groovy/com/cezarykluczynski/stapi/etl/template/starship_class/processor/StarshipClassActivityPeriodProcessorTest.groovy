package com.cezarykluczynski.stapi.etl.template.starship_class.processor

import com.cezarykluczynski.stapi.etl.common.service.PeriodCandidateDetector
import com.cezarykluczynski.stapi.etl.template.starship_class.dto.ActivityPeriodDTO
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApiImpl
import spock.lang.Specification
import spock.lang.Unroll

class StarshipClassActivityPeriodProcessorTest extends Specification {

	private PeriodCandidateDetector periodCandidateDetectorMock

	private StarshipClassActivityPeriodProcessor starshipClassActivityPeriodProcessor

	void setup() {
		periodCandidateDetectorMock = Mock()
		starshipClassActivityPeriodProcessor = new StarshipClassActivityPeriodProcessor(new WikitextApiImpl(), periodCandidateDetectorMock)
	}

	@Unroll('when #text is passed, #activityPeriod is returned')
	void "parses various text values into activityPeriod"() {
		given:
		periodCandidateDetectorMock.isPeriodCandidate(_) >> true

		expect:
		starshipClassActivityPeriodProcessor.process(text) == activityPeriod

		where:
		text                                        | activityPeriod
		null                                        | null
		''                                          | null
		'No links to extract'                       | null
		'[[2377]]'                                  | ActivityPeriodDTO.of('2377', '2377')
		'[[2250s]]-'                                | ActivityPeriodDTO.of('2250s', null)
		'Through [[2196]]'                          | ActivityPeriodDTO.of('2196', '2196')
		'[[2375]]-'                                 | ActivityPeriodDTO.of('2375', null)
		'[[29th century]]'                          | ActivityPeriodDTO.of('29th century', '29th century')
		'[[15th century]], [[24th century]]'        | ActivityPeriodDTO.of('15th century', '24th century')
		'Late [[24th century]]'                     | ActivityPeriodDTO.of('24th century', '24th century')
		'mid-[[20th century]]'                      | ActivityPeriodDTO.of('20th century', '20th century')
		'[[2280s]] &ndash; [[2370s]]'               | ActivityPeriodDTO.of('2280s', '2370s')
		'[[2371]] ([[alternate timeline]])'         | ActivityPeriodDTO.of('2371', '2371')
		'[[2267]]-[[2270s]]'                        | ActivityPeriodDTO.of('2267', '2270s')
		'[[2270s]] - [[2280s]]'                     | ActivityPeriodDTO.of('2270s', '2280s')
		'early-[[22nd century]]'                    | ActivityPeriodDTO.of('22nd century', '22nd century')
		'[[2280s]]&ndash;[[2370s]]'                 | ActivityPeriodDTO.of('2280s', '2370s')
		'[[2240s]] &ndash; [[2270s]]'               | ActivityPeriodDTO.of('2240s', '2270s')
		'[[2350s]]-[[2370s]]'                       | ActivityPeriodDTO.of('2350s', '2370s')
		'[[2374]] onward'                           | ActivityPeriodDTO.of('2374', null)
		'[[16th century]] &ndash; [[22nd century]]' | ActivityPeriodDTO.of('16th century', '22nd century')
		'c. [[2270s]]-[[2370s]]'                    | ActivityPeriodDTO.of('2270s', '2370s')
		'[[2377]]&ndash;'                           | ActivityPeriodDTO.of('2377', null)
		'[[2250s]] &ndash;'                         | ActivityPeriodDTO.of('2250s', null)
	}

}
