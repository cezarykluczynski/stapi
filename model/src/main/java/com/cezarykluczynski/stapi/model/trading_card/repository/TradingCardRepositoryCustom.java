package com.cezarykluczynski.stapi.model.trading_card.repository;

import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;
import com.cezarykluczynski.stapi.model.trading_card.dto.TradingCardRequestDTO;
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard;

public interface TradingCardRepositoryCustom extends CriteriaMatcher<TradingCardRequestDTO, TradingCard> {
}
