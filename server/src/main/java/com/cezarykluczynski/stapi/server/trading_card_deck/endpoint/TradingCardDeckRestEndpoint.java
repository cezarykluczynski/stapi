package com.cezarykluczynski.stapi.server.trading_card_deck.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardDeckBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardDeckFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.trading_card_deck.dto.TradingCardDeckRestBeanParams;
import com.cezarykluczynski.stapi.server.trading_card_deck.reader.TradingCardDeckRestReader;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Service
@Produces(MediaType.APPLICATION_JSON)
public class TradingCardDeckRestEndpoint {

	public static final String ADDRESS = "/v1/rest/tradingCardDeck";

	private final TradingCardDeckRestReader tradingCardDeckRestReader;

	@Inject
	public TradingCardDeckRestEndpoint(TradingCardDeckRestReader tradingCardDeckRestReader) {
		this.tradingCardDeckRestReader = tradingCardDeckRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public TradingCardDeckFullResponse getTradingCardDeck(@QueryParam("uid") String uid) {
		return tradingCardDeckRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public TradingCardDeckBaseResponse searchTradingCardDeck(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return tradingCardDeckRestReader.readBase(TradingCardDeckRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public TradingCardDeckBaseResponse searchTradingCardDeck(@BeanParam TradingCardDeckRestBeanParams tradingCardDeckRestBeanParams) {
		return tradingCardDeckRestReader.readBase(tradingCardDeckRestBeanParams);
	}

}
