package com.cezarykluczynski.stapi.etl.template.common.service;

import com.cezarykluczynski.stapi.model.external_link.entity.ExternalLink;
import com.cezarykluczynski.stapi.model.external_link.entity.enums.ExternalLinkType;
import com.cezarykluczynski.stapi.model.external_link.entity.enums.ExternalLinkTypeVariant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExternalLinkFilter {

	private static final List<ExternalLinkType> REAL_WORLD_PERSON_LINK_TYPES = List.of(
			ExternalLinkType.FACEBOOK,
			ExternalLinkType.IBDB,
			ExternalLinkType.IMDB,
			ExternalLinkType.ISFDB,
			ExternalLinkType.INSTAGRAM,
			ExternalLinkType.MEMORY_ALPHA,
			ExternalLinkType.MEMORY_BETA,
			ExternalLinkType.MYSPACE,
			ExternalLinkType.SF_ENCYCLOPEDIA,
			ExternalLinkType.STAR_TREK_COM,
			ExternalLinkType.STAR_TREK_ONLINE_WIKI,
			ExternalLinkType.TRIVIA_TRIBUTE,
			ExternalLinkType.TWITTER,
			ExternalLinkType.WIKIPEDIA,
			ExternalLinkType.YOUTUBE
	);

	private static final List<ExternalLinkTypeVariant> REAL_WORLD_PERSON_LINK_TYPE_VARIANTS = List.of(
			ExternalLinkTypeVariant.IMDB_NAME,
			ExternalLinkTypeVariant.YOUTUBE,
			ExternalLinkTypeVariant.YOUTUBE_USER,
			ExternalLinkTypeVariant.YOUTUBE_CHANNEL
	);

	private static final List<ExternalLinkType> EPISODES_AND_MOVIES_LINK_TYPES = List.of(
			ExternalLinkType.CHAKOTEYA,
			ExternalLinkType.IMDB,
			ExternalLinkType.STAR_TREK_MINUTIAE,
			ExternalLinkType.MEMORY_ALPHA,
			ExternalLinkType.MEMORY_BETA,
			ExternalLinkType.SF_ENCYCLOPEDIA,
			ExternalLinkType.STAR_TREK_COM,
			ExternalLinkType.STAR_TREK_ONLINE_WIKI,
			ExternalLinkType.TRIVIA_TRIBUTE,
			ExternalLinkType.WIKIPEDIA
	);

	private static final List<ExternalLinkTypeVariant> EPISODES_AND_MOVIES_LINK_TYPE_VARIANTS = List.of(
			ExternalLinkTypeVariant.IMDB_TITLE
	);

	public boolean isPersonLink(ExternalLink externalLink) {
		return isLinkMatching(externalLink, REAL_WORLD_PERSON_LINK_TYPES, REAL_WORLD_PERSON_LINK_TYPE_VARIANTS);
	}

	public boolean isEpisodeOrMovieLink(ExternalLink externalLink) {
		return isLinkMatching(externalLink, EPISODES_AND_MOVIES_LINK_TYPES, EPISODES_AND_MOVIES_LINK_TYPE_VARIANTS);
	}

	private boolean isLinkMatching(ExternalLink externalLink, List<ExternalLinkType> externalLinkTypes,
			List<ExternalLinkTypeVariant> externalLinkTypeVarants) {
		if (externalLink == null) {
			return false;
		}
		ExternalLinkType type = externalLink.getType();
		ExternalLinkTypeVariant variant = externalLink.getVariant();
		return type != null && externalLinkTypes.contains(type)
				|| variant != null && externalLinkTypeVarants.contains(variant);
	}

}
