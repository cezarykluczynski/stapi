package com.cezarykluczynski.stapi.etl.common.dto;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class WikitextList {

	private String text;

	private Integer level;

	private List<WikitextList> children = Lists.newArrayList();

}
