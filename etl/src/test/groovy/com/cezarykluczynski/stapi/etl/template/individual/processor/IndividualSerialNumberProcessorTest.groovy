package com.cezarykluczynski.stapi.etl.template.individual.processor

import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApiImpl
import spock.lang.Specification
import spock.lang.Unroll

class IndividualSerialNumberProcessorTest extends Specification {

    private IndividualSerialNumberProcessor individualSerialNumberProcessor

    void setup() {
        individualSerialNumberProcessor = new IndividualSerialNumberProcessor(new WikitextApiImpl())
    }

    @Unroll('when #text is passed, #serialNumber is returned')
    void "parsed text into serial number"() {
        expect:
        individualSerialNumberProcessor.process(text) == serialNumber

        where:
        text                                                       | serialNumber
        null                                                       | null
        ''                                                         | null
        '[[Serial number|FSN 695-08-0827]]'                        | 'FSN 695-08-0827'
        '[[some link]] and [[Serial number|FSN 695-08-0827]]'      | 'FSN 695-08-0827'
        '[[serial number|FSN 695-08-0827]] and [[some link]] and ' | 'FSN 695-08-0827'
        '[[some link]] and [[some other link]]'                    | null
        'RT-825-081'                                               | 'RT-825-081'
    }

}
