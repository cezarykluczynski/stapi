package com.cezarykluczynski.stapi.server.trading_card.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardBase;
import com.cezarykluczynski.stapi.model.trading_card.dto.TradingCardRequestDTO;
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.trading_card.dto.TradingCardRestBeanParams;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {EnumMapper.class, RequestSortRestMapper.class})
public interface TradingCardBaseRestMapper {

	TradingCardRequestDTO mapBase(TradingCardRestBeanParams tradingCardRestBeanParams);

	TradingCardBase mapBase(TradingCard tradingCard);

	List<TradingCardBase> mapBase(List<TradingCard> tradingCardList);

}
