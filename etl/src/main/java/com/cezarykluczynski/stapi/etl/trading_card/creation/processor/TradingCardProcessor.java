package com.cezarykluczynski.stapi.etl.trading_card.creation.processor;

import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard;
import com.cezarykluczynski.stapi.util.constant.AttributeName;
import com.cezarykluczynski.stapi.util.constant.TagName;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
@Slf4j
public class TradingCardProcessor implements ItemProcessor<Element, TradingCard> {

	private static final String NO_IMAGE_AVAILABLE = "No Image Available";

	private final TradingCardItemNumberProcessor tradingCardItemNumberProcessor;

	private final UidGenerator uidGenerator;

	private final TradingCardPropertiesProcessor tradingCardPropertiesProcessor;

	@Inject
	public TradingCardProcessor(TradingCardItemNumberProcessor tradingCardItemNumberProcessor, UidGenerator uidGenerator,
			TradingCardPropertiesProcessor tradingCardPropertiesProcessor) {
		this.tradingCardItemNumberProcessor = tradingCardItemNumberProcessor;
		this.uidGenerator = uidGenerator;
		this.tradingCardPropertiesProcessor = tradingCardPropertiesProcessor;
	}

	@Override
	public TradingCard process(Element item) throws Exception {
		if (item.getAllElements().size() == 1 && item.getAllElements().get(0) == item) {
			return null;
		}

		Elements anchorCandidates = item.getElementsByTag(TagName.A);
		Elements imgCandidates = item.getElementsByTag(TagName.IMG);

		if (imgCandidates.size() == 1 && NO_IMAGE_AVAILABLE.equals(imgCandidates.get(0).attr(AttributeName.ALT))) {
			return null;
		}

		if (anchorCandidates.size() != 1) {
			log.warn("One anchor expected when creating trading card, {} found instead", anchorCandidates.size());
			return null;
		}

		Element anchor = anchorCandidates.get(0);

		if (!anchor.getElementsByTag(TagName.IMG).isEmpty()) {
			return null;
		}

		Integer itemNumber = tradingCardItemNumberProcessor.process(anchor);

		if (itemNumber == null) {
			log.warn("Could not extract item number from anchor {}", anchor);
			return null;
		}

		TradingCardPropertiesProcessor.TradingCardProperties tradingCardProperties = tradingCardPropertiesProcessor.process(anchor);

		if (tradingCardProperties == null) {
			return null;
		}

		TradingCard tradingCard = new TradingCard();
		tradingCard.setUid(uidGenerator.generateForTradingCard(itemNumber));
		tradingCard.setName(tradingCardProperties.getName());
		tradingCard.setNumber(tradingCardProperties.getNumber());
		tradingCard.setReleaseYear(tradingCardProperties.getReleaseYear());
		tradingCard.setProductionRun(tradingCardProperties.getProductionRun());
		return tradingCard;
	}

}
