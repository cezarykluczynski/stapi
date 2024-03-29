package com.cezarykluczynski.stapi.etl.common.service;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UrlContentRetriever {

	private final OkHttpClient okHttpClient;

	@SuppressFBWarnings("EI_EXPOSE_REP2")
	public UrlContentRetriever(OkHttpClient okHttpClient) {
		this.okHttpClient = okHttpClient;
	}

	public String getBody(String url) {
		Request request = new Request.Builder()
				.url(url)
				.build();

		try {
			Response accessTokenResponse = okHttpClient.newCall(request).execute();
			return accessTokenResponse.body().string();
		} catch (IOException e) {
			return null;
		}
	}

}
