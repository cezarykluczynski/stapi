package com.cezarykluczynski.stapi.server.trading_card.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardFull;
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.country.mapper.CountryRestMapper;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {CompanyBaseRestMapper.class, EnumMapper.class, CountryRestMapper.class})
public interface TradingCardFullRestMapper {

	TradingCardFull mapFull(TradingCard tradingCard);

}
