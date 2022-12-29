package com.cezarykluczynski.stapi.etl.template.book.processor;

import com.cezarykluczynski.stapi.util.tool.StringUtil;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookProductionNumberProcessor implements ItemProcessor<String, String> {

	private static final String BR_START = "<br";
	private static final String NEW_LINE = "\n";
	private static final String OPEN_BRACKET = "(";
	private static final int MAX_CHARS = 32;

	@Override
	public String process(String item) throws Exception {
		if (StringUtils.isBlank(item)) {
			return null;
		}

		String result = StringUtils.trim(StringUtil.substringBeforeAll(item, Lists.newArrayList(BR_START, NEW_LINE, OPEN_BRACKET)));
		if (result.length() > MAX_CHARS) {
			log.error("Production number {} was trimmed down to {}, but it's still over {} chars, trimming further.",
					item, result, MAX_CHARS);
			result = result.substring(0, MAX_CHARS);
		}

		return result;
	}
}
