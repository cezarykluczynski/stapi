package com.cezarykluczynski.stapi.server.trading_card_set.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardSetBase;
import com.cezarykluczynski.stapi.model.trading_card_set.dto.TradingCardSetRequestDTO;
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.trading_card_set.dto.TradingCardSetRestBeanParams;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {EnumMapper.class, RequestSortRestMapper.class})
public interface TradingCardSetBaseRestMapper {

	TradingCardSetRequestDTO mapBase(TradingCardSetRestBeanParams tradingCardSetRestBeanParams);

	TradingCardSetBase mapBase(TradingCardSet tradingCardSet);

	List<TradingCardSetBase> mapBase(List<TradingCardSet> tradingCardSetList);

}
