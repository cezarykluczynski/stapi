package com.cezarykluczynski.stapi.model.throttle.dto;

import lombok.Data;

@Data
public class ThrottleStatistics {

	boolean decremented;

	Integer total;

	Integer remaining;

}
