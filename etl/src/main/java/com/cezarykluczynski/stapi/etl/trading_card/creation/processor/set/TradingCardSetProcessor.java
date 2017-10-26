package com.cezarykluczynski.stapi.etl.trading_card.creation.processor.set;

import com.cezarykluczynski.stapi.etl.common.service.JsoupParser;
import com.cezarykluczynski.stapi.etl.trading_card.creation.processor.card.TradingCardsTablesProcessor;
import com.cezarykluczynski.stapi.etl.trading_card.creation.service.TradingCardSetFilter;
import com.cezarykluczynski.stapi.etl.trading_card.creation.service.TradingCardSetLinker;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet;
import com.cezarykluczynski.stapi.sources.wordpress.dto.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TradingCardSetProcessor implements ItemProcessor<Page, TradingCardSet> {

	private static final String TRADING_CARD_SET_TABLE_CLASS = "gradienttable";
	private static final String TRADING_CARDS_TABLE_CLASS = "tablepress";

	private final TradingCardSetFilter tradingCardSetFilter;

	private final JsoupParser jsoupParser;

	private final TradingCardSetTableProcessor tradingCardSetTableProcessor;

	private final TradingCardsTablesProcessor tradingCardsTablesProcessor;

	private final UidGenerator uidGenerator;

	private final TradingCardSetLinker tradingCardSetLinker;

	public TradingCardSetProcessor(TradingCardSetFilter tradingCardSetFilter, JsoupParser jsoupParser,
			TradingCardSetTableProcessor tradingCardSetTableProcessor, TradingCardsTablesProcessor tradingCardsTablesProcessor,
			UidGenerator uidGenerator, TradingCardSetLinker tradingCardSetLinker) {
		this.tradingCardSetFilter = tradingCardSetFilter;
		this.jsoupParser = jsoupParser;
		this.tradingCardSetTableProcessor = tradingCardSetTableProcessor;
		this.tradingCardsTablesProcessor = tradingCardsTablesProcessor;
		this.uidGenerator = uidGenerator;
		this.tradingCardSetLinker = tradingCardSetLinker;
	}

	@Override
	public TradingCardSet process(Page item) throws Exception {
		if (tradingCardSetFilter.shouldBeFilteredOut(item)) {
			return null;
		}

		String title = item.getRenderedTitle();

		Document document = jsoupParser.parse(item.getRenderedContent());
		Elements tradingCardSetTableCandidates = document.getElementsByClass(TRADING_CARD_SET_TABLE_CLASS);
		Elements tradingCardsTableCandidates = document.getElementsByClass(TRADING_CARDS_TABLE_CLASS);

		TradingCardSet tradingCardSet = null;

		if (tradingCardSetTableCandidates.size() == 1) {
			tradingCardSet = tradingCardSetTableProcessor.process(tradingCardSetTableCandidates.first());
			if (tradingCardSet != null) {
				tradingCardSet.setName(StringEscapeUtils.unescapeHtml4(title));
				tradingCardSet.setUid(uidGenerator.generateForTradingCardSet(item.getId()));
			}
		} else {
			log.info("Could not find trading card set table on page \"{}\"", title);
		}

		int tradingCardsTableCandidatesSize = tradingCardsTableCandidates.size();

		if (tradingCardsTableCandidatesSize == 1 && tradingCardSet != null) {
			tradingCardSet.getTradingCardDecks().addAll(tradingCardsTablesProcessor.process(tradingCardsTableCandidates));
			tradingCardSetLinker.linkAll(tradingCardSet);
		} else {
			if (tradingCardsTableCandidatesSize != 1) {
				log.warn("Expected to find one table with cards on page \"{}\", but found {}", title, tradingCardsTableCandidatesSize);
			} else {
				log.warn("There was cards table present, but table set was null for page \"{}\"", title);
			}
		}

		return tradingCardSet;
	}

}
