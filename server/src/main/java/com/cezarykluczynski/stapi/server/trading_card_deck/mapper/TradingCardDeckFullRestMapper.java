package com.cezarykluczynski.stapi.server.trading_card_deck.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardDeckFull;
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.country.mapper.CountryRestMapper;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {CompanyBaseRestMapper.class, EnumMapper.class, CountryRestMapper.class})
public interface TradingCardDeckFullRestMapper {

	TradingCardDeckFull mapFull(TradingCardDeck tradingCardDeck);

}
