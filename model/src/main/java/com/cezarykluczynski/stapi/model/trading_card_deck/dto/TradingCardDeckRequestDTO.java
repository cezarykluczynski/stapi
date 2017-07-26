package com.cezarykluczynski.stapi.model.trading_card_deck.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class TradingCardDeckRequestDTO {

	private String uid;

	private String name;

	private String tradingCardSetUid;

	private RequestSortDTO sort;

}
