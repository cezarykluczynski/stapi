package com.cezarykluczynski.stapi.server.trading_card_deck.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardDeckBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardDeckFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.trading_card_deck.dto.TradingCardDeckRestBeanParams;
import com.cezarykluczynski.stapi.server.trading_card_deck.reader.TradingCardDeckRestReader;
import com.cezarykluczynski.stapi.util.constant.ContentType;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.stereotype.Service;

@Service
@Produces(ContentType.APPLICATION_JSON_CHARSET_UTF8)
@CrossOriginResourceSharing(allowAllOrigins = CxfConfiguration.CORS_ALLOW_ALL_ORIGINS, maxAge = CxfConfiguration.CORS_MAX_AGE)
public class TradingCardDeckRestEndpoint {

	public static final String ADDRESS = "/v1/rest/tradingCardDeck";

	private final TradingCardDeckRestReader tradingCardDeckRestReader;

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
