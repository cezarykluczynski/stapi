package com.cezarykluczynski.stapi.server.common.healthcheck;

import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import org.springframework.stereotype.Service;

@Service
public class CommonDatabaseStatusValidator {

	private final SeriesRepository seriesRepository;

	public CommonDatabaseStatusValidator(SeriesRepository seriesRepository) {
		this.seriesRepository = seriesRepository;
	}

	public void validateDatabaseAccess() {
		// use SeriesRepository to check DB access, it has the least entities
		try {
			seriesRepository.count();
		} catch (Exception e) {
			throw new StapiRuntimeException(String.format("DB is down with error: \"%s\"", e.getMessage()), e);
		}
	}

}
