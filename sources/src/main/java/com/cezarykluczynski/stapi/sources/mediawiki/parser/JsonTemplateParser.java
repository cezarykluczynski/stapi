package com.cezarykluczynski.stapi.sources.mediawiki.parser;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.google.common.collect.Lists;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.util.List;
import java.util.stream.Collectors;

class JsonTemplateParser {

	@Getter
	private List<Template> templates = Lists.newArrayList();

	private JSONObject jsonObject;

	JsonTemplateParser(String xmlText) {
		jsonObject = XML.toJSONObject(xmlText);
		parse();
	}

	private void parse() {
		try {
			JSONObject root;

			try {
				root = (JSONObject) jsonObject.get("root");
			} catch (ClassCastException e) {
				return;
			}

			JSONArray jsonArrayTemplates = arrayFromNode(root, "template");

			templates = toJsonObjectList(jsonArrayTemplates).stream()
					.map(this::toTemplate)
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private List<JSONObject> toJsonObjectList(JSONArray jsonArray) {
		List<JSONObject> jsonObjectList = Lists.newArrayList();

		for (int i = 0; i < jsonArray.length(); i++) {
			Object next = jsonArray.get(i);

			if(next instanceof JSONObject) {
				jsonObjectList.add((JSONObject) next);
			}
		}

		return jsonObjectList;
	}

	private Template toTemplate(JSONObject jsonObject) {
		String title = jsonObject.getString("title");

		Template template = new Template();

		template.setTitle(StringUtils.lowerCase(title));
		template.setParts(Lists.newArrayList());

		JSONArray jsonArrayParts;

		try {
			jsonArrayParts = arrayFromNode(jsonObject, "part");
		} catch (RuntimeException e) {
			return template;
		}

		if (jsonArrayParts != null) {
			addParts(template, jsonArrayParts);
		}

		return template;
	}

	private void addParts(Template template, JSONArray jsonArrayParts) {
		template.setParts(toJsonObjectList(jsonArrayParts)
				.stream()
				.map(this::toPart)
				.collect(Collectors.toList()));
	}

	private Template.Part toPart(JSONObject jsonObject) {
		Template.Part templatePart = new Template.Part();
		String name = getName(jsonObject);
		templatePart.setKey(name);
		addValueOrTemplates(templatePart, jsonObject);
		return templatePart;
	}

	private String getName(JSONObject jsonObject) {
		String name = null;

		try {
			name = jsonObject.getString("name");
		} catch (JSONException e2) {
			try {
				JSONObject jsonObjectName;
				try {
					jsonObjectName = (JSONObject) jsonObject.get("name");
				} catch (ClassCastException e3) {
					try {
						return String.valueOf(jsonObject.getLong("name"));
					} catch (Exception e4) {
						throw new RuntimeException(e4);
					}
				} catch (Throwable e3) {
					throw new RuntimeException(e3);
				}
				name = jsonObjectName.get("index").toString();
			} catch(JSONException e3) {
			}
		}

		return StringUtils.lowerCase(name);
	}

	private void addValueOrTemplates(Template.Part templatePart, JSONObject partJsonObject) {
		Pair<String, JSONArray> valueOrPartTemplatesJsonArray = getValueOrTemplates(partJsonObject);

		templatePart.setValue(valueOrPartTemplatesJsonArray.getLeft());
		if (valueOrPartTemplatesJsonArray.getRight() != null) {
			addTemplates(templatePart, valueOrPartTemplatesJsonArray.getRight());
		}
	}

	private Pair<String, JSONArray> getValueOrTemplates(JSONObject partJsonObject) {
		String value = null;
		JSONArray partTemplatesJsonArray = null;

		try {
			JSONObject jsonObjectValue = (JSONObject) partJsonObject.get("value");
			partTemplatesJsonArray = arrayFromNode(jsonObjectValue, "template");
		} catch (Exception e) {
			try {
				value = partJsonObject.get("value").toString();
			} catch (JSONException e2) {
			}
		}

		return Pair.of(value, partTemplatesJsonArray);
	}

	private void addTemplates(Template.Part templatePart, JSONArray partTemplatesJsonArray) {
		templatePart.setTemplates(toJsonObjectList(partTemplatesJsonArray)
				.stream()
				.map(this::toTemplate)
				.collect(Collectors.toList()));
	}

	private JSONArray arrayFromNode(JSONObject jsonObject, String key) {
		JSONArray jsonArray;
		try {
			jsonArray = (JSONArray) jsonObject.get(key);
		} catch (ClassCastException e) {
			try {
				JSONObject template = (JSONObject) jsonObject.get(key);
				jsonArray = new JSONArray(Lists.newArrayList(template));
			} catch(Exception e2) {
				throw new RuntimeException(e2);
			}
		}

		return jsonArray;
	}

}
