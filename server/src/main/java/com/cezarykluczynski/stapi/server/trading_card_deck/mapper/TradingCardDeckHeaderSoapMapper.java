package com.cezarykluczynski.stapi.server.trading_card_deck.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckHeader;
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TradingCardDeckHeaderSoapMapper {

	TradingCardDeckHeader map(TradingCardDeck tradingCardDeck);

	List<TradingCardDeckHeader> map(List<TradingCardDeck> tradingCardDeck);

}
