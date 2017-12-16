package com.cezarykluczynski.stapi.sources.genderize.client;

import com.cezarykluczynski.stapi.sources.genderize.dto.NameGenderDTO;
import com.cezarykluczynski.stapi.util.constant.SpringProfile;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@Profile(SpringProfile.GENDERIZE)
public class GenderizeClientConnectableImpl implements GenderizeClient {

	private Map<String, NameGenderDTO> nameGenderCache = Maps.newHashMap();

	private final String apiUrl;

	private int callsCount;

	private long lastCallTime;

	private long minimalInterval = 1000L;

	public GenderizeClientConnectableImpl(@Value("${source.genderize.apiUrl}") String apiUrl) {
		Preconditions.checkNotNull(apiUrl);
		this.apiUrl = apiUrl;
	}

	public synchronized NameGenderDTO getNameGender(String name) {
		if (nameGenderCache.containsKey(name)) {
			log.debug("Using name to gender cache for name \"{}\"", name);
			return nameGenderCache.get(name);
		}

		ensureInterval();

		try {
			URL url = new URL(apiUrl + "?name=" + name);
			URLConnection connection = url.openConnection();
			InputStream inputStream = null;
			InputStreamReader inputStreamReader = null;
			BufferedReader bufferedReader = null;
			String result;
			try {
				inputStream = connection.getInputStream();
				inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
				bufferedReader = new BufferedReader(inputStreamReader);
				result = bufferedReader.lines().collect(Collectors.joining("\n"));
			} finally {
				if (inputStream != null) {
					inputStream.close();
				}
				if (inputStreamReader != null) {
					inputStreamReader.close();
				}
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			}
			callsCount++;
			log.info("A total of {} calls were made to genderize.io API", callsCount);
			JSONObject jsonObject = new JSONObject(result);
			return tryParseResponse(jsonObject, name);
		} catch (Exception e) {
			log.error("Could not get details about name " + name + " from API because of exception", e);
			return null;
		}
	}

	private NameGenderDTO tryParseResponse(JSONObject jsonObject, String name) {
		NameGenderDTO nameGenderDTO = new NameGenderDTO();
		nameGenderDTO.setName(name);
		try {
			nameGenderDTO.setGender(jsonObject.getString("gender"));
		} catch (JSONException e2) {
			if (!e2.getMessage().contains("JSONObject[\"gender\"] not a string.")) {
				throw e2;
			}
		}
		try {
			nameGenderDTO.setProbability((float) jsonObject.getDouble("probability"));
		} catch (JSONException e2) {
			if (!e2.getMessage().contains("JSONObject[\"probability\"] not found.")) {
				throw e2;
			}

			return null;
		}
		nameGenderCache.put(name, nameGenderDTO);
		return nameGenderDTO;
	}

	private void ensureInterval() {
		long diff = System.currentTimeMillis() - lastCallTime;

		if (diff < minimalInterval) {
			long postpone = minimalInterval - diff;
			log.info("Postponing call to genderize.io for another {} milliseconds", postpone);
			try {
				Thread.sleep(postpone);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		lastCallTime = System.currentTimeMillis();
	}


}
