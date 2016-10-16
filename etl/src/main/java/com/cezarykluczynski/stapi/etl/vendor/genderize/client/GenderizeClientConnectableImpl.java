package com.cezarykluczynski.stapi.etl.vendor.genderize.client;

import com.cezarykluczynski.stapi.etl.vendor.genderize.dto.NameGender;
import com.cezarykluczynski.stapi.util.constants.SpringProfiles;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@Profile(SpringProfiles.GENDERIZE)
public class GenderizeClientConnectableImpl implements GenderizeClient {

	private Map<String, NameGender> nameGenderCache = Maps.newHashMap();

	private String apiUrl;

	private int callsCount = 0;

	@Inject
	public GenderizeClientConnectableImpl(@Value("${url.genderize}") String apiUrl) {
		Preconditions.checkNotNull(apiUrl);
		this.apiUrl = apiUrl;
	}

	public NameGender getNameGender(String name) {
		if (nameGenderCache.containsKey(name)) {
			log.info("Using name to gender cache for name {}", name);
			return nameGenderCache.get(name);
		}

		try {
			URL url = new URL(apiUrl + "?name=" + name);
			URLConnection con = url.openConnection();
			InputStream in = con.getInputStream();
			String result = new BufferedReader(new InputStreamReader(in))
					.lines().collect(Collectors.joining("\n"));
			callsCount++;
			log.info("A total of {} calls were made to genderize.io API", callsCount);
			JSONObject jsonObject = new JSONObject(result);
			return tryParseResponse(jsonObject, name);
		} catch(Exception e) {
			log.warn("Could not get details about name " + name + " from API because of exception", e);
			return null;
		}
	}

	private NameGender tryParseResponse(JSONObject jsonObject, String name) {
		NameGender nameGender = new NameGender();
		nameGender.setName(name);
		try {
			nameGender.setGender(jsonObject.getString("gender"));
		} catch (JSONException e2) {
			if (!e2.getMessage().contains("JSONObject[\"gender\"] not a string.")) {
				throw e2;
			}
		}
		try {
			nameGender.setProbability(Float.parseFloat(jsonObject.getString("probability")));
		} catch(JSONException e2) {
			if (!e2.getMessage().contains("JSONObject[\"probability\"] not found.")) {
				throw e2;
			}

			return null;
		}
		nameGenderCache.put(name, nameGender);
		return nameGender;
	}

}
