package com.cezarykluczynski.stapi.server.trading_card.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardBase;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardBaseRequest;
import com.cezarykluczynski.stapi.model.trading_card.dto.TradingCardRequestDTO;
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {EnumMapper.class, RequestSortSoapMapper.class})
public interface TradingCardBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	TradingCardRequestDTO mapBase(TradingCardBaseRequest tradingCardBaseRequest);

	TradingCardBase mapBase(TradingCard tradingCard);

	List<TradingCardBase> mapBase(List<TradingCard> tradingCardList);

}
