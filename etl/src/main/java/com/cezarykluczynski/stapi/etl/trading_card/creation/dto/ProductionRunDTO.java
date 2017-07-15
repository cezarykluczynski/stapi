package com.cezarykluczynski.stapi.etl.trading_card.creation.dto;

import com.cezarykluczynski.stapi.model.trading_card_set.entity.enums.ProductionRunUnit;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class ProductionRunDTO {

	private Integer productionRun;

	private ProductionRunUnit productionRunUnit;

}
