package com.cezarykluczynski.stapi.sources.mediawiki.cache;

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PageCacheStorage {

	private final Map<Key, Page> storage = Maps.newHashMap();

	public synchronized void put(Page page) {
		Preconditions.checkNotNull(page, "Page stored in PageCacheStorage cannot be null");
		Preconditions.checkArgument(page.getRedirectPath().isEmpty(), String.format("Page %s that is an effect of redirect cannot be stored "
				+ "in PageCacheStorage", page.getTitle()));
		storage.put(createKey(page.getTitle(), page.getMediaWikiSource()), page);
	}

	public Page get(String title, MediaWikiSource mediaWikiSource) {
		return storage.get(createKey(title, mediaWikiSource));
	}

	private static Key createKey(String title, MediaWikiSource mediaWikiSource) {
		return Key.of(title, mediaWikiSource);
	}

	@AllArgsConstructor(staticName = "of")
	@Data
	private static class Key {

		private String title;

		private MediaWikiSource mediaWikiSource;

	}

}
