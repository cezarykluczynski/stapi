package com.cezarykluczynski.stapi.server.trading_card_set.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetFull;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetFullRequest;
import com.cezarykluczynski.stapi.model.trading_card_set.dto.TradingCardSetRequestDTO;
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.country.mapper.CountrySoapMapper;
import com.cezarykluczynski.stapi.server.trading_card_deck.mapper.TradingCardDeckBaseSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {CountrySoapMapper.class, DateMapper.class, EnumMapper.class,
		TradingCardDeckBaseSoapMapper.class})
public interface TradingCardSetFullSoapMapper {

	@Mapping(target = "name", ignore = true)
	@Mapping(target = "releaseYearFrom", ignore = true)
	@Mapping(target = "releaseYearTo", ignore = true)
	@Mapping(target = "cardsPerPackFrom", ignore = true)
	@Mapping(target = "cardsPerPackTo", ignore = true)
	@Mapping(target = "packsPerBoxFrom", ignore = true)
	@Mapping(target = "packsPerBoxTo", ignore = true)
	@Mapping(target = "boxesPerCaseFrom", ignore = true)
	@Mapping(target = "boxesPerCaseTo", ignore = true)
	@Mapping(target = "productionRunFrom", ignore = true)
	@Mapping(target = "productionRunTo", ignore = true)
	@Mapping(target = "productionRunUnit", ignore = true)
	@Mapping(target = "cardWidthFrom", ignore = true)
	@Mapping(target = "cardWidthTo", ignore = true)
	@Mapping(target = "cardHeightFrom", ignore = true)
	@Mapping(target = "cardHeightTo", ignore = true)
	@Mapping(target = "sort", ignore = true)
	TradingCardSetRequestDTO mapFull(TradingCardSetFullRequest tradingCardSetFullRequest);

	TradingCardSetFull mapFull(TradingCardSet tradingCardSet);

}
