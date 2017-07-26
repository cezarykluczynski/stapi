package com.cezarykluczynski.stapi.server.trading_card_deck.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckBase;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckBaseRequest;
import com.cezarykluczynski.stapi.model.trading_card_deck.dto.TradingCardDeckRequestDTO;
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {EnumMapper.class, RequestSortSoapMapper.class})
public interface TradingCardDeckBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	TradingCardDeckRequestDTO mapBase(TradingCardDeckBaseRequest tradingCardDeckBaseRequest);

	TradingCardDeckBase mapBase(TradingCardDeck tradingCardDeck);

	List<TradingCardDeckBase> mapBase(List<TradingCardDeck> tradingCardDeckList);

}
