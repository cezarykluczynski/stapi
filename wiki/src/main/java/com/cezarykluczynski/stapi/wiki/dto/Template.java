package com.cezarykluczynski.stapi.wiki.dto;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class Template {

	@Data
	public static class Part {

		private String key;

		private String value;

		private List<Template> templates;

	}

	private String title;

	private List<Part> parts = Lists.newArrayList();

}
