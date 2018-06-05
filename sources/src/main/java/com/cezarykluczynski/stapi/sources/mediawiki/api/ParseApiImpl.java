package com.cezarykluczynski.stapi.sources.mediawiki.api;

import com.cezarykluczynski.stapi.sources.http.connector.HttpConnector;
import com.cezarykluczynski.stapi.sources.mediawiki.configuration.MediaWikiSourcesProperties;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.CharStreams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Service
@Slf4j
public class ParseApiImpl implements ParseApi {

	private static final String API_TRUE = "1";

	private final String expandTemplatesUrl;

	private final HttpConnector httpConnector;

	public ParseApiImpl(HttpConnector httpConnector, MediaWikiSourcesProperties mediaWikiSourcesProperties) {
		this.httpConnector = httpConnector;
		String apiUrl = mediaWikiSourcesProperties.getTechnicalHelper().getApiUrl();

		if (apiUrl == null) {
			throw new StapiRuntimeException("Technical helper API url cannot be null");
		}

		boolean isWikipedia = apiUrl.contains("wikipedia.org");
		String from = isWikipedia ? "w/api.php" : "api.php";
		String to = isWikipedia ? "wiki/Special:ExpandTemplates" : "index.php/Special:ExpandTemplates";
		expandTemplatesUrl = apiUrl.replace(from, to);
		if (StringUtils.equals(apiUrl, expandTemplatesUrl)) {
			throw new StapiRuntimeException("Technical helper API url is malformed, does not seems like a MediaWiki API");
		}
	}

	@Override
	public synchronized String parseWikitextToXmlTree(String wikitext) {
		List<NameValuePair> params = createParams(wikitext);
		HttpEntity entity = doPostParams(params);
		return entity == null ? null : getXmlTree(entity);
	}

	private String getXmlTree(HttpEntity entity) {
		try {
			InputStream inputStream = entity.getContent();
			try {
				String result = CharStreams.toString(new InputStreamReader(inputStream, Charsets.UTF_8));
				Document doc = Jsoup.parse(result);
				Element xmlTextarea = doc.select("textarea[name=output]").first();
				return xmlTextarea == null ? null : xmlTextarea.text();
			} finally {
				inputStream.close();
			}
		} catch (Exception e) {
			return null;
		}
	}

	private static List<NameValuePair> createParams(String wikitext) {
		List<NameValuePair> params = Lists.newArrayList();
		params.add(new BasicNameValuePair("wpInput", wikitext));
		params.add(new BasicNameValuePair("wpRemoveComments", API_TRUE));
		params.add(new BasicNameValuePair("wpGenerateXml", API_TRUE));
		params.add(new BasicNameValuePair("wpEditToken", "+\\"));
		params.add(new BasicNameValuePair("title", "Special:ExpandTemplates"));
		return params;
	}

	private HttpEntity doPostParams(List<NameValuePair> params) {
		return httpConnector.post(expandTemplatesUrl, params);
	}


}
