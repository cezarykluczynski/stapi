package com.cezarykluczynski.stapi.model.throttle.dto;

import lombok.Data;

@Data
public class ThrottleStatistics {

	private boolean decremented;

	private Integer total;

	private Integer remaining;

}
