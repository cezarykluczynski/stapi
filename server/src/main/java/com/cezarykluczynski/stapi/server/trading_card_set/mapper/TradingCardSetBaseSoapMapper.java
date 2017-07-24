package com.cezarykluczynski.stapi.server.trading_card_set.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetBase;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetBaseRequest;
import com.cezarykluczynski.stapi.model.trading_card_set.dto.TradingCardSetRequestDTO;
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {EnumMapper.class, RequestSortSoapMapper.class})
public interface TradingCardSetBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	@Mapping(source = "releasedYear.from", target = "releaseYearFrom")
	@Mapping(source = "releasedYear.to", target = "releaseYearTo")
	@Mapping(source = "cardsPerPack.from", target = "cardsPerPackFrom")
	@Mapping(source = "cardsPerPack.to", target = "cardsPerPackTo")
	@Mapping(source = "packsPerBox.from", target = "packsPerBoxFrom")
	@Mapping(source = "packsPerBox.to", target = "packsPerBoxTo")
	@Mapping(source = "boxesPerCase.from", target = "boxesPerCaseFrom")
	@Mapping(source = "boxesPerCase.to", target = "boxesPerCaseTo")
	@Mapping(source = "productionRun.from", target = "productionRunFrom")
	@Mapping(source = "productionRun.to", target = "productionRunTo")
	@Mapping(source = "cardWidth.from", target = "cardWidthFrom")
	@Mapping(source = "cardWidth.to", target = "cardWidthTo")
	@Mapping(source = "cardHeight.from", target = "cardHeightFrom")
	@Mapping(source = "cardHeight.to", target = "cardHeightTo")
	TradingCardSetRequestDTO mapBase(TradingCardSetBaseRequest tradingCardSetBaseRequest);

	TradingCardSetBase mapBase(TradingCardSet tradingCardSet);

	List<TradingCardSetBase> mapBase(List<TradingCardSet> tradingCardSetList);

}
