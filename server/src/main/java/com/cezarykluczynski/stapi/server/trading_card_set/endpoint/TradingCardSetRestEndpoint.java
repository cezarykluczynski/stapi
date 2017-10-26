package com.cezarykluczynski.stapi.server.trading_card_set.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardSetBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardSetFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.trading_card_set.dto.TradingCardSetRestBeanParams;
import com.cezarykluczynski.stapi.server.trading_card_set.reader.TradingCardSetRestReader;
import com.cezarykluczynski.stapi.util.constant.ContentType;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.springframework.stereotype.Service;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Service
@Produces(ContentType.APPLICATION_JSON_CHARSET_UTF8)
@CrossOriginResourceSharing(allowAllOrigins = CxfConfiguration.CORS_ALLOW_ALL_ORIGINS, maxAge = CxfConfiguration.CORS_MAX_AGE)
public class TradingCardSetRestEndpoint {

	public static final String ADDRESS = "/v1/rest/tradingCardSet";

	private final TradingCardSetRestReader tradingCardSetRestReader;

	public TradingCardSetRestEndpoint(TradingCardSetRestReader tradingCardSetRestReader) {
		this.tradingCardSetRestReader = tradingCardSetRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public TradingCardSetFullResponse getTradingCardSet(@QueryParam("uid") String uid) {
		return tradingCardSetRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public TradingCardSetBaseResponse searchTradingCardSet(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return tradingCardSetRestReader.readBase(TradingCardSetRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public TradingCardSetBaseResponse searchTradingCardSet(@BeanParam TradingCardSetRestBeanParams tradingCardSetRestBeanParams) {
		return tradingCardSetRestReader.readBase(tradingCardSetRestBeanParams);
	}

}
