package com.cezarykluczynski.stapi.etl.template.common.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductionSerialNumberProcessor implements ItemProcessor<String, String> {

	@Override
	public String process(String item) throws Exception {
		String processedItem = doProcess(item);
		return StringUtils.substringBefore(StringUtils.substringBefore(processedItem, "\n"), " ");
	}

	private String doProcess(String item) {
		if (item != null && item.length() < 20) {
			return item;
		} else {
			try {
				JSONObject jsonObject = new JSONObject(item);
				String key = "content";
				return jsonObject.has(key) ? (String) jsonObject.get(key) : null;
			} catch (JSONException e) {
				log.error("Could not parse production serial number \"{}\" as JSON, and it is too long", item);
				return null;
			}
		}
	}
}
