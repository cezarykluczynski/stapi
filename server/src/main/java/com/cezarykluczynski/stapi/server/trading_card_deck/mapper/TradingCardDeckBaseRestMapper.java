package com.cezarykluczynski.stapi.server.trading_card_deck.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardDeckBase;
import com.cezarykluczynski.stapi.model.trading_card_deck.dto.TradingCardDeckRequestDTO;
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.trading_card_deck.dto.TradingCardDeckRestBeanParams;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {EnumMapper.class, RequestSortRestMapper.class})
public interface TradingCardDeckBaseRestMapper {

	TradingCardDeckRequestDTO mapBase(TradingCardDeckRestBeanParams tradingCardDeckRestBeanParams);

	TradingCardDeckBase mapBase(TradingCardDeck tradingCardDeck);

	List<TradingCardDeckBase> mapBase(List<TradingCardDeck> tradingCardDeckList);

}
