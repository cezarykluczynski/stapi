package com.cezarykluczynski.stapi.model.trading_card_set.repository;

import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;
import com.cezarykluczynski.stapi.model.trading_card_set.dto.TradingCardSetRequestDTO;
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet;

public interface TradingCardSetRepositoryCustom extends CriteriaMatcher<TradingCardSetRequestDTO, TradingCardSet> {
}
