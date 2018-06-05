package com.cezarykluczynski.stapi.sources.http.connector;

import com.google.common.collect.Lists;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class HttpConnector {

	private static final List<Long> NETWORK_TROUBLE_POSTPONES_TIMES = Lists.newArrayList(10000L, 30000L, 60000L, 300000L);

	private Integer lastNetworkTroublePostponeIndex = 0;

	private final HttpClientFactory httpClientFactory;

	public HttpConnector(HttpClientFactory httpClientFactory) {
		this.httpClientFactory = httpClientFactory;
	}

	public synchronized HttpEntity post(String url, List<NameValuePair> params) {
		return post(url, params, 0L);
	}

	@SuppressFBWarnings("SWL_SLEEP_WITH_LOCK_HELD")
	private HttpEntity post(String url, List<NameValuePair> params, Long waitPeriod) {
		if (waitPeriod > 0L) {
			try {
				Thread.sleep(waitPeriod);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		HttpEntity httpEntity = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

			HttpClient httpClient = httpClientFactory.create();
			HttpResponse httpResponse = httpClient.execute(httpPost);
			httpEntity = httpResponse.getEntity();
		} catch (Exception e) {
			log.error("Could not execute HTTP POST");
		}

		if (httpEntity == null) {
			long postpone = getNetworkTroublePostpone();
			log.info("Network troubles. Postponing next call for {} seconds", postpone / 1000);
			try {
				Thread.sleep(postpone);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			return post(url, params, waitPeriod);
		} else {
			if (lastNetworkTroublePostponeIndex > 0) {
				log.info("Network is back to normal");
			}
			lastNetworkTroublePostponeIndex = 0;
		}

		return httpEntity;
	}

	private long getNetworkTroublePostpone() {
		long networkTroublePostpone = NETWORK_TROUBLE_POSTPONES_TIMES.get(lastNetworkTroublePostponeIndex);
		lastNetworkTroublePostponeIndex = Math.min(lastNetworkTroublePostponeIndex + 1, NETWORK_TROUBLE_POSTPONES_TIMES.size() - 1);
		return networkTroublePostpone;
	}

}
