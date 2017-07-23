package com.cezarykluczynski.stapi.etl.trading_card.creation.processor.common

import spock.lang.Specification
import spock.lang.Unroll

class TradingCardItemsProcessorTest extends Specification {

	private TradingCardItemsProcessor tradingCardItemsProcessor

	void setup() {
		tradingCardItemsProcessor = new TradingCardItemsProcessor()
	}

	@Unroll('when #input is passed, #output is returned')
	void "when string candidate is passed, packs per box is returned"() {
		expect:
		tradingCardItemsProcessor.process(input) == output

		where:
		input                                           | output
		null                                            | null
		// packs per box
		'Unknown'                                       | null
		'Unknown Unknown'                               | null
		'N/A'                                           | null
		'9'                                             | 9
		'15'                                            | 15
		'20'                                            | 20
		'42?'                                           | 42
		'50'                                            | 50
		'100'                                           | 100
		'36 24'                                         | 36
		'36 36'                                         | 36
		'40+1 CD'                                       | 40
		'20 + 1 Sound'                                  | 20
		'20 + 1 Archive'                                | 20
		'18 Retail 36 Hobby'                            | 18
		'16 Walmart 18 Retail 24 Hobby'                 | 18
		'12 Retail 36 Hobby'                            | 12
		'36 Hobby Box 18 Blockbuster Box'               | 18
		'36 (Regular) 36 (Blockbuster) 24 (Wal-Mart)'   | 36
		'36 Hobby 20 Retail 24 Retail'                  | 24
		// cards per deck
		'8'                                             | 8
		'19'                                            | 19
		'72'                                            | 72
		'100'                                           | 100
		'8 15'                                          | 15
		'9 36'                                          | 36
		'9 Retail 9 Hobby'                              | 9
		'10 + 1 Sticker'                                | 10
		'8 (Regular) 8 (Blockbuster) 16 (Wal-Mart)'     | 8
		'5 + 1 Sticker 5 + 1 Sticker'                   | 5
		'4 + 1 ship'                                    | 4
		'Unknown 3'                                     | 3
		'8 Hobby 8 Retail 15 Retail'                    | 15
		'2 or more'                                     | 2
		// boxes per case
		'Unknown Unknown Unknown'                       | null
		'12 & 20'                                       | 20
		'10 Hobby Unk Retail Unk Retail'                | 10
		'6 20'                                          | 20
		'Unk (Regular) Unk (Blockbuster) 20 (Wal-Mart)' | 20
		// card production run
		'10,000'                                        | 10000
	}

}
