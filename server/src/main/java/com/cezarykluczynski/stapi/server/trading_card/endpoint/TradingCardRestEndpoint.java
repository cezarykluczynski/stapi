package com.cezarykluczynski.stapi.server.trading_card.endpoint;

import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardFullResponse;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import com.cezarykluczynski.stapi.server.configuration.CxfConfiguration;
import com.cezarykluczynski.stapi.server.trading_card.dto.TradingCardRestBeanParams;
import com.cezarykluczynski.stapi.server.trading_card.reader.TradingCardRestReader;
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
