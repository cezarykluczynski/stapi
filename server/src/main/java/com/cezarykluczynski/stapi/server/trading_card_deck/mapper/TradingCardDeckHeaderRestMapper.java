package com.cezarykluczynski.stapi.server.trading_card_deck.mapper;

import com.cezarykluczynski.stapi.client.rest.model.TradingCardDeckHeader;
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TradingCardDeckHeaderRestMapper {

	TradingCardDeckHeader map(TradingCardDeck tradingCardDeck);

	List<TradingCardDeckHeader> map(List<TradingCardDeck> tradingCardDeck);

}
