package com.cezarykluczynski.stapi.sources.genderize.client;

import com.cezarykluczynski.stapi.sources.genderize.dto.NameGenderDTO;
import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@Slf4j
@Profile(SpringProfile.GENDERIZE)
public class GenderizeClientConnectableImpl implements GenderizeClient {

	private static final long MINIMAL_INTERVAL = 1000L;

	private final Map<String, NameGenderDTO> nameGenderCache = Maps.newHashMap();

	private final String apiUrl;

	private final RestTemplate restTemplate;

	private int callsCount;

	private long lastCallTime;

	@SuppressFBWarnings("EI_EXPOSE_REP2")
	public GenderizeClientConnectableImpl(@Value("${source.genderize.apiUrl}") String apiUrl, RestTemplate restTemplate) {
		Preconditions.checkNotNull(apiUrl);
		this.apiUrl = apiUrl;
		this.restTemplate = restTemplate;
	}

	@SuppressFBWarnings("NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE")
	public synchronized NameGenderDTO getNameGender(String name) {
		if (nameGenderCache.containsKey(name)) {
			final NameGenderDTO nameGenderDTO = nameGenderCache.get(name);
			if (nameGenderDTO.getGender() == null || nameGenderDTO.getProbability() == null) {
				return null;
			}
			return nameGenderDTO;
		}

		ensureInterval();

		try {
			String queryUrl = String.format("%s?name=%s", apiUrl, name);
			final ResponseEntity<NameGenderDTO> nameGenderDTOResponseEntity = restTemplate
					.getForEntity(queryUrl, NameGenderDTO.class);
			callsCount++;
			if (callsCount % 10 == 0) {
				log.info("A total of {} calls were made to genderize.io API", callsCount);
			}
			if (nameGenderDTOResponseEntity.getStatusCode().is2xxSuccessful()) {
				final NameGenderDTO nameGenderDTO = nameGenderDTOResponseEntity.getBody();
				nameGenderCache.put(name, nameGenderDTO);
				if (nameGenderDTO.getGender() == null || nameGenderDTO.getProbability() == null) {
					return null;
				}
				return nameGenderDTO;
			}
		} catch (Exception e) {
			// just return null
		}

		return null;
	}

	private void ensureInterval() {
		long diff = System.currentTimeMillis() - lastCallTime;

		if (diff < MINIMAL_INTERVAL) {
			long postpone = MINIMAL_INTERVAL - diff;
			log.info("Postponing call to genderize.io for another {} milliseconds.", postpone);
			try {
				Thread.sleep(postpone);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		lastCallTime = System.currentTimeMillis();
	}

}
