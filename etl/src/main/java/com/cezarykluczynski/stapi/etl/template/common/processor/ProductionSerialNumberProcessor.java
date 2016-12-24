package com.cezarykluczynski.stapi.etl.template.common.processor;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductionSerialNumberProcessor implements ItemProcessor<String, String> {

	@Override
	public String process(String item) throws Exception {
		if (item != null && item.length() < 20) {
			return item;
		} else {
			try {
				JSONObject jsonObject = new JSONObject(item);
				return jsonObject.has("content") ? (String) jsonObject.get("content") : null;
			} catch (JSONException e) {
				log.error("Could not parse production serial number {} as JSON, and it is too long", item);
				return null;
			}
		}
	}
}
