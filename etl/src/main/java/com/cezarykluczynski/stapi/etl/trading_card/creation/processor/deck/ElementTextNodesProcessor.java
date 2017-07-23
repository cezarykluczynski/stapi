package com.cezarykluczynski.stapi.etl.trading_card.creation.processor.deck;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElementTextNodesProcessor implements ItemProcessor<Element, List<String>> {

	@Override
	public List<String> process(Element item) throws Exception {
		List<String> textNodes = Lists.newArrayList();
		getElementsWithNonEmptyText(item).forEach(elementWithTextNodes -> {
			elementWithTextNodes.textNodes().forEach(textNode -> {
				String text = textNode.text();
				if (!textNodes.contains(text)) {
					textNodes.add(text);
				}
			});
		});
		return textNodes;
	}

	private static List<Element> getElementsWithNonEmptyText(Element element) {
		List<Element> elementsWithNonEmptyText = Lists.newArrayList();

		for (Element child : element.getAllElements()) {
			if (element.equals(child)) {
				continue;
			}

			elementsWithNonEmptyText.addAll(getElementsWithNonEmptyText(child));
			String text = child.text();
			if (StringUtils.isNotBlank(text)) {
				elementsWithNonEmptyText.add(child);
			}
		}

		return elementsWithNonEmptyText;
	}

}
