package com.cezarykluczynski.stapi.server.trading_card_deck.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckFull;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckFullRequest;
import com.cezarykluczynski.stapi.model.trading_card_deck.dto.TradingCardDeckRequestDTO;
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.country.mapper.CountrySoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {CountrySoapMapper.class, DateMapper.class, EnumMapper.class})
public interface TradingCardDeckFullSoapMapper {

	@Mapping(target = "name", ignore = true)
	@Mapping(target = "tradingCardSetUid", ignore = true)
	@Mapping(target = "sort", ignore = true)
	TradingCardDeckRequestDTO mapFull(TradingCardDeckFullRequest tradingCardDeckFullRequest);

	TradingCardDeckFull mapFull(TradingCardDeck tradingCardDeck);

}
