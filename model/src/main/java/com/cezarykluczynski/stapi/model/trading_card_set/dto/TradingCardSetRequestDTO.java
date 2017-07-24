package com.cezarykluczynski.stapi.model.trading_card_set.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import com.cezarykluczynski.stapi.model.trading_card_set.entity.enums.ProductionRunUnit;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class TradingCardSetRequestDTO {

	private String uid;

	private String name;

	private Integer releaseYearFrom;

	private Integer releaseYearTo;

	private Integer cardsPerPackFrom;

	private Integer cardsPerPackTo;

	private Integer packsPerBoxFrom;

	private Integer packsPerBoxTo;

	private Integer boxesPerCaseFrom;

	private Integer boxesPerCaseTo;

	private Integer productionRunFrom;

	private Integer productionRunTo;

	private ProductionRunUnit productionRunUnit;

	private Double cardWidthFrom;

	private Double cardWidthTo;

	private Double cardHeightFrom;

	private Double cardHeightTo;

	private RequestSortDTO sort;

}
