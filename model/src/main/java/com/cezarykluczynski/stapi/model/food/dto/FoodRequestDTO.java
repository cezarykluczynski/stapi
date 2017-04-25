package com.cezarykluczynski.stapi.model.food.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class FoodRequestDTO {

	private String uid;

	private String name;

	private Boolean earthlyOrigin;

	private Boolean dessert;

	private Boolean fruit;

	private Boolean herbOrSpice;

	private Boolean sauce;

	private Boolean soup;

	private Boolean beverage;

	private Boolean alcoholicBeverage;

	private Boolean juice;

	private Boolean tea;

	private RequestSortDTO sort;

}
