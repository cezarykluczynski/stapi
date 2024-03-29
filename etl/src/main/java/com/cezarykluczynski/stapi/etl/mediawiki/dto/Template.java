package com.cezarykluczynski.stapi.etl.mediawiki.dto;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class Template {

	private String title;

	private String originalTitle;

	private List<Part> parts = Lists.newArrayList();

	@Data
	@ToString
	public static class Part {

		private String key;

		private String value;

		private String content;

		private List<Template> templates = Lists.newArrayList();

	}

}
