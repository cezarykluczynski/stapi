package com.cezarykluczynski.stapi.sources.mediawiki.parser;

import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class JsonTemplateParser {

	private static final String KEY_ROOT = "root";
	private static final String KEY_NAME = "name";
	private static final String KEY_VALUE = "value";
	private static final String KEY_INDEX = "index";
	private static final String KEY_TEMPLATE = "template";
	private static final String KEY_CONTENT = "content";
	private static final String KEY_TITLE = "title";
	private static final String KEY_PART = "part";

	public List<Template> parse(String xmlText) {
		JSONObject jsonObject = XML.toJSONObject(xmlText);
		List<Template> templates = Lists.newArrayList();
		try {
			JSONObject root;

			try {
				root = (JSONObject) jsonObject.get(KEY_ROOT);
			} catch (ClassCastException e) {
				return templates;
			}

			JSONArray jsonArrayTemplates = new JSONArray();
			try {
				jsonArrayTemplates = arrayFromNode(root, KEY_TEMPLATE);
			} catch (RuntimeException e) {
				if (ExceptionUtils.indexOfThrowable(e, JSONException.class) == -1) {
					throw e;
				}
			}

			templates = toJsonObjectList(jsonArrayTemplates).stream()
					.map(this::toTemplate)
					.filter(Objects::nonNull)
					.collect(Collectors.toList());
		} catch (Exception e) {
			throw new StapiRuntimeException(e);
		}

		return templates;
	}

	private List<JSONObject> toJsonObjectList(JSONArray jsonArray) {
		List<JSONObject> jsonObjectList = Lists.newArrayList();

		for (int i = 0; i < jsonArray.length(); i++) {
			Object next = jsonArray.get(i);

			if (next instanceof JSONObject) {
				jsonObjectList.add((JSONObject) next);
			}
		}

		return jsonObjectList;
	}

	private Template toTemplate(JSONObject jsonObjectToConvert) {
		String title;
		try {
			title = jsonObjectToConvert.getString(KEY_TITLE);
		} catch (JSONException e) {
			return null;
		}

		Template template = new Template();

		template.setTitle(StringUtils.lowerCase(title));
		template.setParts(Lists.newArrayList());

		JSONArray jsonArrayParts;

		try {
			jsonArrayParts = arrayFromNode(jsonObjectToConvert, KEY_PART);
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

	private Template.Part toPart(JSONObject jsonObjectToConvert) {
		Template.Part templatePart = new Template.Part();
		String name = getName(jsonObjectToConvert);
		templatePart.setKey(name);
		addContentValueAndTemplates(templatePart, jsonObjectToConvert);
		return templatePart;
	}

	private String getName(JSONObject jsonObjectToConvert) {
		String name = null;
		try {
			name = jsonObjectToConvert.getString(KEY_NAME);
		} catch (JSONException e2) {
			try {
				JSONObject jsonObjectName;
				try {
					jsonObjectName = (JSONObject) jsonObjectToConvert.get(KEY_NAME);
				} catch (ClassCastException e3) {
					try {
						return String.valueOf(jsonObjectToConvert.getLong(KEY_NAME));
					} catch (Exception e4) {
						throw new StapiRuntimeException(e4);
					}
				} catch (Throwable e3) {
					throw new StapiRuntimeException(e3);
				}
				name = jsonObjectName.get(KEY_INDEX).toString();
			} catch (JSONException e3) {
				// do nothing
			}
		}

		return StringUtils.lowerCase(name);
	}

	private void addContentValueAndTemplates(Template.Part templatePart, JSONObject partJsonObject) {
		Triple<String, String, JSONArray> valueContentTemplateTriple = getValueOrTemplates(partJsonObject);

		templatePart.setContent(valueContentTemplateTriple.getMiddle());

		templatePart.setValue(valueContentTemplateTriple.getLeft());
		if (valueContentTemplateTriple.getRight() != null) {
			addTemplates(templatePart, valueContentTemplateTriple.getRight());
		}
	}

	private Triple<String, String, JSONArray> getValueOrTemplates(JSONObject partJsonObject) {
		String value = null;
		JSONArray partTemplatesJsonArray = null;
		String content = null;

		try {
			JSONObject jsonObjectValue = (JSONObject) partJsonObject.get(KEY_VALUE);
			partTemplatesJsonArray = arrayFromNode(jsonObjectValue, KEY_TEMPLATE);
		} catch (Exception e) {
			try {
				value = partJsonObject.get(KEY_VALUE).toString();
			} catch (JSONException e2) {
				// do nothing
			}
		}

		try {
			JSONObject jsonObjectValue = (JSONObject) partJsonObject.get(KEY_VALUE);
			content = jsonObjectValue.getString(KEY_CONTENT);
		} catch (Exception e) {
			//do nothing
		}

		return Triple.of(value, content, partTemplatesJsonArray);
	}

	private void addTemplates(Template.Part templatePart, JSONArray partTemplatesJsonArray) {
		templatePart.setTemplates(toJsonObjectList(partTemplatesJsonArray)
				.stream()
				.map(this::toTemplate)
				.filter(Objects::nonNull)
				.collect(Collectors.toList()));
	}

	private JSONArray arrayFromNode(JSONObject jsonObjectToConvert, String key) {
		JSONArray jsonArray;
		try {
			jsonArray = (JSONArray) jsonObjectToConvert.get(key);
		} catch (ClassCastException e) {
			try {
				JSONObject template = (JSONObject) jsonObjectToConvert.get(key);
				jsonArray = new JSONArray(Lists.newArrayList(template));
			} catch (Exception e2) {
				throw new StapiRuntimeException(e2);
			}
		}

		return jsonArray;
	}

}
