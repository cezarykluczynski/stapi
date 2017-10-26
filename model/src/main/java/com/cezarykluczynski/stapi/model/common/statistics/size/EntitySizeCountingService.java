package com.cezarykluczynski.stapi.model.common.statistics.size;

import com.cezarykluczynski.stapi.model.common.service.RepositoryProvider;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
class EntitySizeCountingService {

	private final RepositoryProvider repositoryProvider;

	EntitySizeCountingService(RepositoryProvider repositoryProvider) {
		this.repositoryProvider = repositoryProvider;
	}

	Map<Class, Long> count() {
		return repositoryProvider.provide().entrySet()
				.stream()
				.map(entry -> Pair.of(entry.getKey(), entry.getValue().count()))
				.collect(Collectors.toMap(Pair::getLeft, Pair::getRight));
	}

}
