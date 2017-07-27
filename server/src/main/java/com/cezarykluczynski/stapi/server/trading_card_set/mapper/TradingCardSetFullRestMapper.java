package com.cezarykluczynski.stapi.server.trading_card_set.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardSetFull;
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.country.mapper.CountryRestMapper;
import com.cezarykluczynski.stapi.server.trading_card_deck.mapper.TradingCardDeckBaseRestMapper;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {CompanyBaseRestMapper.class, EnumMapper.class, CountryRestMapper.class,
		TradingCardDeckBaseRestMapper.class})
public interface TradingCardSetFullRestMapper {

	TradingCardSetFull mapFull(TradingCardSet tradingCardSet);

}
