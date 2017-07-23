package com.cezarykluczynski.stapi.etl.trading_card.creation.processor.deck

import com.cezarykluczynski.stapi.util.constant.TagName
import org.assertj.core.util.Lists
import org.jsoup.nodes.Element
import spock.lang.Specification

class ElementTextNodesProcessorTest extends Specification {

	private static final String TEXT_NODE_1 = 'TEXT_NODE_1'
	private static final String TEXT_NODE_2 = 'TEXT_NODE_2'
	private static final String TEXT_NODE_3 = 'TEXT_NODE_3'
	private static final String TEXT_NODE_4 = 'TEXT_NODE_4'
	private static final String TEXT_NODE_5 = 'TEXT_NODE_5'

	private ElementTextNodesProcessor elementTextNodesProcessor

	void setup() {
		elementTextNodesProcessor = new ElementTextNodesProcessor()
	}

	void "extracts text nodes from element"() {
		given:
		Element tr = new Element(TagName.TR)
		Element td1 = new Element(TagName.TD)
		Element td2 = new Element(TagName.TD)
		Element td3 = new Element(TagName.TD)
		Element td4 = new Element(TagName.TD)
		Element p1 = new Element(TagName.P)
		Element p2 = new Element(TagName.P)
		Element p3 = new Element(TagName.P)
		Element p4 = new Element(TagName.P)
		td2.text(TEXT_NODE_1)
		p2.text(TEXT_NODE_2)
		td2.insertChildren(0, Lists.newArrayList(p1, p2))
		td3.text(TEXT_NODE_3)
		td4.text(TEXT_NODE_4)
		p3.text(TEXT_NODE_5)
		td4.insertChildren(0, Lists.newArrayList(p3, p4))
		tr.insertChildren(0, Lists.newArrayList(td1, td2, td3, td4))

		when:
		List<String> textNodeSet = elementTextNodesProcessor.process(tr)

		then:
		textNodeSet.size() == 5
		textNodeSet[0] == TEXT_NODE_2
		textNodeSet[1] == TEXT_NODE_1
		textNodeSet[2] == TEXT_NODE_3
		textNodeSet[3] == TEXT_NODE_5
		textNodeSet[4] == TEXT_NODE_4
	}

}
