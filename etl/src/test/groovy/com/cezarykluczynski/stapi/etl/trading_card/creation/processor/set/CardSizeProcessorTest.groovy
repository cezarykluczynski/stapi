package com.cezarykluczynski.stapi.etl.trading_card.creation.processor.set

import com.cezarykluczynski.stapi.etl.trading_card.creation.dto.CardSizeDTO
import spock.lang.Specification
import spock.lang.Unroll

class CardSizeProcessorTest extends Specification {

	private CardSizeProcessor cardSizeProcessor

	void setup() {
		cardSizeProcessor = new CardSizeProcessor()
	}

	@SuppressWarnings('LineLength')
	@Unroll('when #input is passed, #output is returned')
	void "when card size is passed, it is parsed to CardSizeDTO"() {
		expect:
		output == cardSizeProcessor.process(input)

		where:
		input                                                                                | output
		'Unknown'                                                                            | null
		'Various'                                                                            | null
		'2½” x 4½”'                                                                          | CardSizeDTO.of(2.5d, 4.5d)
		'2¾” × 3¾”'                                                                          | CardSizeDTO.of(2.75d, 3.75d)
		'1¾” x 3½”'                                                                          | CardSizeDTO.of(1.75d, 3.5d)
		'2½” x 3½”'                                                                          | CardSizeDTO.of(2.5d, 3.5d)
		'2¼” x 3½”'                                                                          | CardSizeDTO.of(2.25d, 3.5d)
		'3¼” x 4⅜”'                                                                          | CardSizeDTO.of(3.25d, 4.375d)
		'2⅜” x 3⅜”'                                                                          | CardSizeDTO.of(2.375d, 3.375d)
		'2″ x 3″'                                                                            | CardSizeDTO.of(2d, 3d)
		'1⅜” x 2½”'                                                                          | CardSizeDTO.of(1.375d, 2.5d)
		'2½” x 3½” 2½” x 3½”'                                                                | CardSizeDTO.of(2.5d, 3.5d)
		'2⅛” x 3¼”'                                                                          | CardSizeDTO.of(2.125d, 3.25d)
		'2⅛” x 3⅛”'                                                                          | CardSizeDTO.of(2.125d, 3.125d)
		'3⅝” x 5⅛”'                                                                          | CardSizeDTO.of(3.625d, 5.125d)
		'2¼” x 3¼”'                                                                          | CardSizeDTO.of(2.25d, 3.25d)
		'1¼” x 1¾”'                                                                          | CardSizeDTO.of(1.25d, 1.75d)
		'2½” x 4¾”'                                                                          | CardSizeDTO.of(2.5d, 4.75d)
		'3½” x 5¼”'                                                                          | CardSizeDTO.of(3.5d, 5.25d)
		'1¾” x 3⅝”'                                                                          | CardSizeDTO.of(1.75d, 3.625d)
		'2½” x 4″'                                                                           | CardSizeDTO.of(2.5d, 4d)
		'2″ x 3½”'                                                                           | CardSizeDTO.of(2d, 3.5d)
		'1⅞” x 2¾”'                                                                          | CardSizeDTO.of(1.875d, 2.75d)
		'1⅞” x 2⅝”'                                                                          | CardSizeDTO.of(1.875d, 2.625d)
		'2″ x 2½”'                                                                           | CardSizeDTO.of(2d, 2.5d)
		'5″ x 7″'                                                                            | CardSizeDTO.of(5d, 7d)
		'23⁄8” x 37⁄16“'                                                                     | CardSizeDTO.of(2.875d, 2.3125d)
		'21⁄4” x 35⁄16”'                                                                     | CardSizeDTO.of(5.25d, 2.1875d)
		'1 1/2″x 1 1/4″ ???'                                                                 | CardSizeDTO.of(1.5d, 1.25d)
		'2⅝ x 5⅝ (Card) 2¾ x 7¾ (Case)'                                                      | CardSizeDTO.of(2.625d, 5.625d)
		'2″ x 3″ (Silver Card) 2½” x 3½” (Silver Card in Case) 2½” x 3½” (Paper Card nulls)' | null
	}

}
