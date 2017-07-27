package com.cezarykluczynski.stapi.server.trading_card.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardHeader;
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TradingCardHeaderRestMapper {

	TradingCardHeader map(TradingCard tradingCard);

	List<TradingCardHeader> map(List<TradingCard> tradingCard);

}
