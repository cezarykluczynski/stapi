package com.cezarykluczynski.stapi.server.trading_card_set.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardSetFull;
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.country.mapper.CountryRestMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {CompanyBaseRestMapper.class, EnumMapper.class, CountryRestMapper.class})
public interface TradingCardSetFullRestMapper {

	@Mapping(target = "tradingCardDecks", ignore = true) // TODO
	@Mapping(target = "tradingCards", ignore = true) // TODO
	TradingCardSetFull mapFull(TradingCardSet tradingCardSet);

}
