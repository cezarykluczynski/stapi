package com.cezarykluczynski.stapi.server.common.metrics.service;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EndpointHitsPersister {

	public void persist(Map<Pair<String, String>, Long> endpointsHits) {
		// TODO
	}

}
