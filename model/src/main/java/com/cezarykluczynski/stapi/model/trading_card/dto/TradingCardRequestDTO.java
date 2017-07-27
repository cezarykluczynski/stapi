package com.cezarykluczynski.stapi.model.trading_card.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class TradingCardRequestDTO {

	private String uid;

	private String name;

	private String tradingCardDeckUid;

	private String tradingCardSetUid;

	private RequestSortDTO sort;

}
