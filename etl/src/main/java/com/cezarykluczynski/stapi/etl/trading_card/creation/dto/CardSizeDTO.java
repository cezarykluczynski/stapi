package com.cezarykluczynski.stapi.etl.trading_card.creation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class CardSizeDTO {

	private Double width;

	private Double height;

}
