package com.cezarykluczynski.stapi.etl.template.common.factory;

import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.external_link.entity.ExternalLink;
import com.cezarykluczynski.stapi.model.external_link.entity.enums.ExternalLinkType;
import com.cezarykluczynski.stapi.model.external_link.entity.enums.ExternalLinkTypeVariant;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RegExUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExternalLinkFactory {

	private final UidGenerator uidGenerator;

	@SuppressWarnings("ParameterAssignment")
	public ExternalLink ofTypeVariantAndResourceId(ExternalLinkType type, ExternalLinkTypeVariant variant, String resourceId) {
		resourceId = fixWikiResource(type, resourceId);
		String fullLink = fullLink(type, resourceId);
		if (fullLink == null) {
			fullLink = fullLink(variant, resourceId);
		}
		if (fullLink == null) {
			throw new RuntimeException(String.format("Cannot map type %s and variant %s to full link", type, variant));
		}
		if (type == null) {
			type = typeFromVariant(variant);
		}

		ExternalLink externalLink = new ExternalLink();
		externalLink.setType(type);
		externalLink.setVariant(variant);
		externalLink.setResourceId(resourceId);
		externalLink.setLink(fullLink);
		externalLink.setUid(uidGenerator.generateForExternalLink(externalLink.getLink()));
		return externalLink;
	}

	private String fullLink(ExternalLinkType type, String resourceId) {
		if (type == null) {
			return null;
		}
		return switch (type) {
			case FACEBOOK -> String.format("https://www.facebook.com/%s", resourceId);
			case INSTAGRAM -> String.format("https://instagram.com/%s", resourceId);
			case MEMORY_ALPHA -> String.format("https://memory-alpha.fandom.com/wiki/%s", resourceId);
			case MEMORY_BETA -> String.format("https://memory-beta.fandom.com/wiki/%s", resourceId);
			case MYSPACE -> String.format("https://www.myspace.com/%s", resourceId);
			case STAR_TREK_COM -> String.format("https://www.startrek.com/%s", resourceId);
			case STAR_TREK_MINUTIAE -> String.format("https://www.st-minutiae.com/%s", resourceId);
			case STAR_TREK_ONLINE_WIKI -> String.format("https://stowiki.net/wiki/%s", resourceId);
			case TRIVIA_TRIBUTE -> String.format("https://triviatribute.com/%s", resourceId);
			case TWITTER -> String.format("https://x.com/%s", resourceId);
			case WIKIPEDIA -> String.format("https://en.wikipedia.org/wiki/%s", resourceId);
			case WIKIQUOTE -> String.format("https://en.wikiquote.org/wiki/%s", resourceId);
			default -> null;
		};
	}

	private String fullLink(ExternalLinkTypeVariant typeVariant, String resourceId) {
		if (typeVariant == null) {
			return null;
		}
		return switch (typeVariant) {
			case IBDB_PERSON -> String.format("https://www.ibdb.com/person.php?id=%s", resourceId);
			case IMDB_TITLE -> String.format("https://www.imdb.com/title/%s", resourceId);
			case IMDB_NAME -> String.format("https://www.imdb.com/name/%s", resourceId);
			case ISFDB_AUTHOR -> String.format("https://www.isfdb.org/cgi-bin/ea.cgi?%s", resourceId);
			case SF_ENCYCLOPEDIA_ENTRY -> String.format("https://www.sf-encyclopedia.com/entry/%s", resourceId);
			case SF_ENCYCLOPEDIA_NEWS -> String.format("https://www.sf-encyclopedia.com/news/%s", resourceId);
			case YOUTUBE -> String.format("https://www.youtube.com/%s", resourceId);
			case YOUTUBE_VIDEO -> String.format("https://www.youtube.com/watch?v=%s", resourceId);
			case YOUTUBE_CHANNEL -> String.format("https://www.youtube.com/channel/%s", resourceId);
			case YOUTUBE_USER -> String.format("https://www.youtube.com/user/%s", resourceId);
			case YOUTUBE_PLAYLIST -> String.format("https://www.youtube.com/playlist?list=%s", resourceId);
		};
	}

	private String fixWikiResource(ExternalLinkType type, String resourceId) {
		if (type == null) {
			return resourceId;
		}
		return switch (type) {
			case MEMORY_ALPHA, MEMORY_BETA, STAR_TREK_ONLINE_WIKI, WIKIPEDIA, WIKIQUOTE -> wikiResource(resourceId);
			default -> resourceId;
		};
	}

	private String wikiResource(String resourceId) {
		return resourceId == null ? null : RegExUtils.replaceAll(resourceId, " ", "_");
	}

	private ExternalLinkType typeFromVariant(ExternalLinkTypeVariant typeVariant) {
		return switch (typeVariant) {
			case YOUTUBE, YOUTUBE_VIDEO, YOUTUBE_PLAYLIST, YOUTUBE_USER, YOUTUBE_CHANNEL -> ExternalLinkType.YOUTUBE;
			case IBDB_PERSON -> ExternalLinkType.IBDB;
			case IMDB_TITLE, IMDB_NAME -> ExternalLinkType.IMDB;
			case SF_ENCYCLOPEDIA_ENTRY, SF_ENCYCLOPEDIA_NEWS -> ExternalLinkType.SF_ENCYCLOPEDIA;
			case ISFDB_AUTHOR -> ExternalLinkType.ISFDB;
		};
	}

}
