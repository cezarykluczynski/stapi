package com.cezarykluczynski.stapi.server.trading_card.endpoint;

import com.cezarykluczynski.stapi.client.rest.model.TradingCardBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.TradingCardFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.trading_card.dto.TradingCardRestBeanParams;
import com.cezarykluczynski.stapi.server.trading_card.reader.TradingCardRestReader;
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
public class TradingCardRestEndpoint {

	public static final String ADDRESS = "/v1/rest/tradingCard";

	private final TradingCardRestReader tradingCardRestReader;

	public TradingCardRestEndpoint(TradingCardRestReader tradingCardRestReader) {
		this.tradingCardRestReader = tradingCardRestReader;
	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	public TradingCardFullResponse getTradingCard(@QueryParam("uid") String uid) {
		return tradingCardRestReader.readFull(uid);
	}

	@GET
	@Path("search")
	@Consumes(MediaType.APPLICATION_JSON)
	public TradingCardBaseResponse searchTradingCard(@BeanParam PageSortBeanParams pageSortBeanParams) {
		return tradingCardRestReader.readBase(TradingCardRestBeanParams.fromPageSortBeanParams(pageSortBeanParams));
	}

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public TradingCardBaseResponse searchTradingCard(@BeanParam TradingCardRestBeanParams tradingCardRestBeanParams) {
		return tradingCardRestReader.readBase(tradingCardRestBeanParams);
	}

}
