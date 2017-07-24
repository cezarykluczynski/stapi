package com.cezarykluczynski.stapi.server.trading_card_set.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardSetHeader;
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TradingCardSetHeaderRestMapper {

	TradingCardSetHeader map(TradingCardSet tradingCardSet);

	List<TradingCardSetHeader> map(List<TradingCardSet> tradingCardSet);

}
