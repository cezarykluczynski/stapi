package com.cezarykluczynski.stapi.server.trading_card.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardFull;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardFullRequest;
import com.cezarykluczynski.stapi.model.trading_card.dto.TradingCardRequestDTO;
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.country.mapper.CountrySoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {CountrySoapMapper.class, DateMapper.class, EnumMapper.class})
public interface TradingCardFullSoapMapper {

	@Mapping(target = "name", ignore = true)
	@Mapping(target = "tradingCardDeckUid", ignore = true)
	@Mapping(target = "tradingCardSetUid", ignore = true)
	@Mapping(target = "sort", ignore = true)
	TradingCardRequestDTO mapFull(TradingCardFullRequest tradingCardFullRequest);

	TradingCardFull mapFull(TradingCard tradingCard);

}
