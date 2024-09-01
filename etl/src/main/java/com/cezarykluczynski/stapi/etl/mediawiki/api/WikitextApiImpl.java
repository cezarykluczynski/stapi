package com.cezarykluczynski.stapi.etl.mediawiki.api;

import com.cezarykluczynski.stapi.etl.mediawiki.api.dto.PageLink;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.etl.template.common.factory.ExternalLinkFactory;
import com.cezarykluczynski.stapi.model.external_link.entity.ExternalLink;
import com.cezarykluczynski.stapi.model.external_link.entity.enums.ExternalLinkType;
import com.cezarykluczynski.stapi.model.external_link.entity.enums.ExternalLinkTypeVariant;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WikitextApiImpl implements WikitextApi {

	private static final Integer LINK_CONTENTS_GROUP = 1;
	private static final Integer PADDING = 2;
	private static final String PIPE = "|";
	private static final String LEFT_BRACKET = "(";
	private static final String RIGHT_BRACKET = ")";
	private static final String SPACE = " ";
	private static final String UNDERSCORE = "_";
	private static final String ONE = "1";
	private static final String TWO = "2";
	private static final String REGEX_PIPE_MATCH = "\\|";
	private static final String DEAD_LINK_MARKER = "bl=1";

	private static final int CI = Pattern.CASE_INSENSITIVE;

	private static final Pattern LINK = Pattern.compile("\\[\\[(.+?)]]", CI);
	private static final Pattern DIS_LINK = Pattern.compile("\\{\\{dis\\|(.+?)}}", CI);
	private static final Pattern MU_LINK = Pattern.compile("\\{\\{mu\\|(.+?)}}", CI);
	private static final Pattern ALT_LINK = Pattern.compile("\\{\\{alt\\|(.+?)}}", CI);
	private static final Pattern S_LINK = Pattern.compile("\\{\\{s\\|(.+?)}}", CI);
	private static final Pattern E_LINK = Pattern.compile("\\{\\{e\\|(.+?)}}", CI);
	private static final Pattern FEDERATION_LINK = Pattern.compile("\\{\\{federation}}", CI);

	private static final Pattern MULTILINE_WITHOUT_TEMPLATES = Pattern.compile("\\{\\{(.*?)}}", Pattern.DOTALL);
	private static final Pattern NO_INCLUDE_PATTERN = Pattern.compile("<noinclude>(.+?)</noinclude>", Pattern.DOTALL & Pattern.CASE_INSENSITIVE);

	private static final Pattern FACEBOOK_EXTERNAL_LINK = Pattern.compile("\\{\\{facebook\\|(.*?)(\\|.*?){0,}}}", CI);
	private static final Pattern IBDB_PERSON_EXTERNAL_LINK = Pattern.compile("\\{\\{ibdb-link\\|id=(\\d+)(\\|.*?){0,}\\}\\}", CI);
	private static final Pattern IBDB_PERSON_WITH_PARAM_EXTERNAL_LINK = Pattern.compile("\\{\\{ibdb-link\\|type=person\\|id=(\\d+)(\\|.*?){0,}}}",
			Pattern.CASE_INSENSITIVE);
	private static final Pattern IMDB_EPISODE_EXTERNAL_LINK = Pattern.compile("\\{\\{imdb-ep\\|((?!\\|).+)(\\|.*?){0,}}}", CI);
	private static final Pattern IMDB_TITLE_EXTERNAL_LINK = Pattern.compile("\\{\\{imdb-title\\|((?!\\|).+).+?}}", CI);
	private static final Pattern IMDB_NAME_EXTERNAL_LINK = Pattern.compile("\\{\\{imdb\\|name/((?!\\|).+).+?}}", CI);
	private static final Pattern ISFDB_AUTHOR_EXTERNAL_LINK = Pattern.compile("\\{\\{isfdb\\|author\\|(.+?)(\\|.*?){0,}}}", CI);
	private static final Pattern INSTAGRAM_EXTERNAL_LINK = Pattern.compile("\\{\\{instagram\\|(.*?)(\\|.*?){0,}}}", CI);
	private static final Pattern MB_EXTERNAL_LINK_EMPTY = Pattern.compile("\\{\\{mb}}", CI);
	private static final Pattern MBETA_EXTERNAL_LINK_EMPTY = Pattern.compile("\\{\\{mbeta}}", CI);
	private static final Pattern MBETA_QUOTE_EXTERNAL_LINK_EMPTY = Pattern.compile("\\{\\{mbeta-quote}}", CI);
	private static final Pattern MBETA_TITLE_EXTERNAL_LINK_EMPTY = Pattern.compile("\\{\\{mbeta-title}}", CI);
	private static final Pattern MB_EXTERNAL_LINK = Pattern.compile("\\{\\{mb\\|(.+?)(\\|.*?){0,}\\}\\}", CI);
	private static final Pattern MBETA_EXTERNAL_LINK = Pattern.compile("\\{\\{mbeta\\|(.+?)(\\|.*?){0,}\\}\\}", CI);
	private static final Pattern MBETA_QUOTE_EXTERNAL_LINK = Pattern.compile("\\{\\{mbeta-quote\\|(.+?)(\\|.*?){0,}\\}\\}", CI);
	private static final Pattern MBETA_TITLE_EXTERNAL_LINK = Pattern.compile("\\{\\{mbeta-title\\|(.+?)(\\|.*?){0,}\\}\\}", CI);
	private static final Pattern MYSPACE_EXTERNAL_LINK = Pattern.compile("\\{\\{myspace\\|([^=]*?)((\\|).+?){0,2}}}", CI);
	private static final Pattern SF_ENCYCLOPEDIA_EXTERNAL_ENTRY_LINK = Pattern.compile("\\{\\{sf-encyc\\|(.+?)}}", CI);
	private static final Pattern STAR_TREK_COM_EXTERNAL_LINK = Pattern.compile("\\{\\{startrek\\.com\\|(.+?)}}", CI);
	private static final Pattern ST_COM_EXTERNAL_LINK = Pattern.compile("\\{\\{st\\.com\\|(.+?)}}", CI);
	private static final Pattern STAR_TREK_MINUTIAE_EXTERNAL_LINK = Pattern.compile("\\{\\{star\\strek\\sminutiae\\|(.+?)}}", CI);
	private static final Pattern STO_WIKI_EXTERNAL_LINK_EMPTY = Pattern.compile("\\{\\{stowiki}}", CI);
	private static final Pattern STO_WIKI_EXTERNAL_LINK = Pattern.compile("\\{\\{stowiki\\|(.+?)}}", CI);
	private static final Pattern TRIVIA_TRIBUTE_EXTERNAL_LINK = Pattern.compile("\\{\\{triviatribute\\|(.+?)}}", CI);
	private static final Pattern TWITTER_EXTERNAL_LINK = Pattern.compile("\\{\\{twitter\\|(.+?)}}", CI);
	private static final Pattern WIKIPEDIA_EXTERNAL_LINK_EMPTY = Pattern.compile("\\{\\{wikipedia}}", CI);
	private static final Pattern WIKIPEDIA_EXTERNAL_LINK = Pattern.compile("\\{\\{wikipedia\\|(.+?)}}", CI);
	private static final Pattern W_EXTERNAL_LINK = Pattern.compile("\\{\\{w\\|(.+?)}}", CI);
	private static final Pattern WIKIPEDIA_TITLE_EXTERNAL_LINK_EMPTY = Pattern.compile("\\{\\{wikipedia-title}}", CI);
	private static final Pattern WIKIPEDIA_TITLE_EXTERNAL_LINK = Pattern.compile("\\{\\{wikipedia-title\\|(.+?)}}", CI);
	private static final Pattern WIKIPEDIA_QUOTE_EXTERNAL_LINK_EMPTY = Pattern.compile("\\{\\{wikipedia-quote}}", CI);
	private static final Pattern WIKIPEDIA_QUOTE_EXTERNAL_LINK = Pattern.compile("\\{\\{wikipedia-quote\\|(.+?)}}", CI);
	private static final Pattern WT_EXTERNAL_LINK_EMPTY = Pattern.compile("\\{\\{wt}}", CI);
	private static final Pattern WT_EXTERNAL_LINK = Pattern.compile("\\{\\{wt\\|(.+?)}}", CI);
	private static final Pattern WIKIQUOTE_EXTERNAL_LINK_EMPTY = Pattern.compile("\\{\\{wikiquote}}", CI);
	private static final Pattern WIKIQUOTE_EXTERNAL_LINK = Pattern.compile("\\{\\{wikiquote\\|(.+?)}}", CI);
	private static final Pattern YOUTUBE_EXTERNAL_LINK = Pattern.compile("\\{\\{youtube\\|(.+?)}}", CI);

	private static final Map<String, String> VIDEO_FORMATS_TO_PAGE_LINK_SUFFIX_MAP = Maps.newLinkedHashMap();
	private static final Map<Pattern, String> VIDEO_FORMATS_LINKS = Maps.newLinkedHashMap();

	static {
		VIDEO_FORMATS_TO_PAGE_LINK_SUFFIX_MAP.put("bd", " (Blu-ray)");
		VIDEO_FORMATS_TO_PAGE_LINK_SUFFIX_MAP.put("bm", " (Betamax)");
		VIDEO_FORMATS_TO_PAGE_LINK_SUFFIX_MAP.put("df", " (digital)");
		VIDEO_FORMATS_TO_PAGE_LINK_SUFFIX_MAP.put("dvd", " (DVD)");
		VIDEO_FORMATS_TO_PAGE_LINK_SUFFIX_MAP.put("ldisc", " (LaserDisc)");
		VIDEO_FORMATS_TO_PAGE_LINK_SUFFIX_MAP.put("uhd", " (4K Ultra HD)");
		VIDEO_FORMATS_TO_PAGE_LINK_SUFFIX_MAP.put("vcd", " (VCD)");
		VIDEO_FORMATS_TO_PAGE_LINK_SUFFIX_MAP.put("vhd", " (VHD)");
		VIDEO_FORMATS_TO_PAGE_LINK_SUFFIX_MAP.put("vhs", " (VHS)");
		for (Map.Entry<String, String> entry : VIDEO_FORMATS_TO_PAGE_LINK_SUFFIX_MAP.entrySet()) {
			VIDEO_FORMATS_LINKS.put(Pattern.compile(String.format("\\{\\{%s\\|(.+?)}}", entry.getKey()), Pattern.CASE_INSENSITIVE), entry.getValue());
		}
	}

	private final ExternalLinkFactory externalLinkFactory;

	@Override
	public List<String> getPageTitlesFromWikitext(String wikitext) {
		return getPageLinksFromWikitext(wikitext)
				.stream()
				.map(PageLink::getTitle)
				.collect(Collectors.toList());
	}

	@Override
	public List<String> getPageTitlesFromWikitextExcludingNotFound(String wikitext, Page page) {
		final Document htmlDocument = page.getHtmlDocument();
		List<PageLink> nonExistingPageLinks = htmlDocument.select("span.new").stream()
				.map(element -> {
					PageLink pageLink = new PageLink();
					pageLink.setTitle(element.attr("title").replace(" (page does not exist)", ""));
					pageLink.setDescription(element.text());
					return pageLink;
				})
				.toList();

		final List<PageLink> pageLinksFromWikitext = getPageLinksFromWikitext(wikitext);

		return pageLinksFromWikitext
				.stream()
				.filter(pageLink -> {
					return nonExistingPageLinks.stream().noneMatch(nonExistingPageLink ->
							StringUtils.equals(nonExistingPageLink.getTitle(), pageLink.getTitle())
									&& (pageLink.getDescription() == null
									|| StringUtils.equals(nonExistingPageLink.getDescription(), pageLink.getDescription())));
				})
				.map(PageLink::getTitle)
				.collect(Collectors.toList());
	}

	@Override
	public List<PageLink> getPageLinksFromWikitext(String wikitext) {
		if (wikitext == null) {
			return Lists.newArrayList();
		}

		List<PageLink> allMatches = Lists.newArrayList();

		allMatches.addAll(extractLinkMatches(wikitext));
		allMatches.addAll(extractMatches(wikitext, DIS_LINK, 2, templateParts -> {
			return templateParts.get(0) + SPACE + LEFT_BRACKET + templateParts.get(1) + RIGHT_BRACKET;
		}));
		allMatches.addAll(extractMatches(wikitext, MU_LINK, 1, templateParts -> {
			return templateParts.get(0) + SPACE + LEFT_BRACKET + "mirror" + RIGHT_BRACKET;
		}));
		allMatches.addAll(extractMatches(wikitext, ALT_LINK, 1, templateParts -> {
			return templateParts.get(0) + SPACE + LEFT_BRACKET + "alternate reality" + RIGHT_BRACKET;
		}));
		allMatches.addAll(extractMatches(wikitext, S_LINK, 1, templateParts -> {
			return templateParts.get(0);
		}));
		allMatches.addAll(extractMatches(wikitext, E_LINK, 1, templateParts -> {
			return templateParts.get(0);
		}));
		for (Map.Entry<Pattern, String> entry : VIDEO_FORMATS_LINKS.entrySet()) {
			allMatches.addAll(extractMatches(wikitext, entry.getKey(), 1, templateParts -> {
				String templatePartZero = templateParts.get(0);
				// there's always this one special case that complicates everything...
				if ("Star Trek".equals(templatePartZero)) {
					templatePartZero = "Star Trek (film)";
				}
				return templatePartZero + entry.getValue();
			}));
		}

		allMatches.addAll(extractMatches(wikitext, FEDERATION_LINK, 0, templateParts -> "United Federation of Planets"));

		return allMatches
				.stream()
				.sorted(Comparator.comparing(PageLink::getStartPosition))
				.collect(Collectors.toList());
	}

	@Override
	@SuppressWarnings({"NPathComplexity", "MethodLength", "ParameterAssignment", "ReturnCount"})
	public List<ExternalLink> getExternalLinksFromWikitext(String wikitext, Page page) {
		if (wikitext == null) {
			return Lists.newArrayList();
		}

		List<ExternalLink> allMatches = Lists.newArrayList();
		String pageTitleWithUnderscores = page.getTitle().replaceAll(SPACE, UNDERSCORE);

		allMatches.add(externalLinkFactory.ofTypeVariantAndResourceId(ExternalLinkType.MEMORY_ALPHA, null, pageTitleWithUnderscores));
		allMatches.addAll(extractMatches(wikitext, FACEBOOK_EXTERNAL_LINK, resourceId -> {
			return externalLinkFactory.ofTypeVariantAndResourceId(ExternalLinkType.FACEBOOK, null, resourceId);
		}));
		allMatches.addAll(extractMatches(wikitext, IBDB_PERSON_EXTERNAL_LINK, resourceId -> {
			return externalLinkFactory.ofTypeVariantAndResourceId(null, ExternalLinkTypeVariant.IBDB_PERSON, resourceId);
		}));
		allMatches.addAll(extractMatches(wikitext, IBDB_PERSON_WITH_PARAM_EXTERNAL_LINK, resourceId -> {
			return externalLinkFactory.ofTypeVariantAndResourceId(null, ExternalLinkTypeVariant.IBDB_PERSON, resourceId);
		}));
		allMatches.addAll(extractMatches(wikitext, IMDB_EPISODE_EXTERNAL_LINK, resourceId -> {
			return externalLinkFactory.ofTypeVariantAndResourceId(ExternalLinkType.IMDB, ExternalLinkTypeVariant.IMDB_TITLE, resourceId);
		}));
		allMatches.addAll(extractMatches(wikitext, IMDB_TITLE_EXTERNAL_LINK, resourceId -> {
			return externalLinkFactory.ofTypeVariantAndResourceId(ExternalLinkType.IMDB, ExternalLinkTypeVariant.IMDB_TITLE, resourceId);
		}));
		allMatches.addAll(extractMatches(wikitext, IMDB_NAME_EXTERNAL_LINK, resourceId -> {
			return externalLinkFactory.ofTypeVariantAndResourceId(ExternalLinkType.IMDB, ExternalLinkTypeVariant.IMDB_NAME, resourceId);
		}));
		allMatches.addAll(extractMatches(wikitext, ISFDB_AUTHOR_EXTERNAL_LINK, resourceId -> {
			return externalLinkFactory.ofTypeVariantAndResourceId(null, ExternalLinkTypeVariant.ISFDB_AUTHOR, resourceId);
		}));
		allMatches.addAll(extractMatches(wikitext, INSTAGRAM_EXTERNAL_LINK, resourceId -> {
			return externalLinkFactory.ofTypeVariantAndResourceId(ExternalLinkType.INSTAGRAM, null, resourceId);
		}));
		allMatches.addAll(extractMatches(wikitext, MB_EXTERNAL_LINK_EMPTY, resourceId -> {
			return externalLinkFactory.ofTypeVariantAndResourceId(ExternalLinkType.MEMORY_BETA, null, pageTitleWithUnderscores);
		}));
		allMatches.addAll(extractMatches(wikitext, MBETA_EXTERNAL_LINK_EMPTY, resourceId -> {
			return externalLinkFactory.ofTypeVariantAndResourceId(ExternalLinkType.MEMORY_BETA, null, pageTitleWithUnderscores);
		}));
		allMatches.addAll(extractMatches(wikitext, MBETA_QUOTE_EXTERNAL_LINK_EMPTY, resourceId -> {
			return externalLinkFactory.ofTypeVariantAndResourceId(ExternalLinkType.MEMORY_BETA, null, pageTitleWithUnderscores);
		}));
		allMatches.addAll(extractMatches(wikitext, MBETA_TITLE_EXTERNAL_LINK_EMPTY, resourceId -> {
			return externalLinkFactory.ofTypeVariantAndResourceId(ExternalLinkType.MEMORY_BETA, null, pageTitleWithUnderscores);
		}));
		allMatches.addAll(extractMatches(wikitext, MB_EXTERNAL_LINK, resourceId -> {
			return externalLinkFactory.ofTypeVariantAndResourceId(ExternalLinkType.MEMORY_BETA, null, resourceId);
		}));
		allMatches.addAll(extractMatches(wikitext, MBETA_EXTERNAL_LINK, resourceId -> {
			return externalLinkFactory.ofTypeVariantAndResourceId(ExternalLinkType.MEMORY_BETA, null, resourceId);
		}));
		allMatches.addAll(extractMatches(wikitext, MBETA_QUOTE_EXTERNAL_LINK, resourceId -> {
			return externalLinkFactory.ofTypeVariantAndResourceId(ExternalLinkType.MEMORY_BETA, null, resourceId);
		}));
		allMatches.addAll(extractMatches(wikitext, MBETA_TITLE_EXTERNAL_LINK, resourceId -> {
			return externalLinkFactory.ofTypeVariantAndResourceId(ExternalLinkType.MEMORY_BETA, null, resourceId);
		}));
		allMatches.addAll(extractMatches(wikitext, MYSPACE_EXTERNAL_LINK, resourceId -> {
			return externalLinkFactory.ofTypeVariantAndResourceId(ExternalLinkType.MYSPACE, null, resourceId);
		}));
		allMatches.addAll(extractMatches(wikitext, SF_ENCYCLOPEDIA_EXTERNAL_ENTRY_LINK, resourceId -> {
			List<String> parts = Arrays.stream(resourceId.split(REGEX_PIPE_MATCH)).toList();
			if (!parts.isEmpty()) {
				resourceId = parts.get(0);
			}
			ExternalLinkTypeVariant externalLinkTypeVariant = ExternalLinkTypeVariant.SF_ENCYCLOPEDIA_ENTRY;
			if (parts.size() > 1 && "news".equalsIgnoreCase(parts.get(parts.size() - 1))) {
				externalLinkTypeVariant = ExternalLinkTypeVariant.SF_ENCYCLOPEDIA_NEWS;
			}
			return externalLinkFactory.ofTypeVariantAndResourceId(null, externalLinkTypeVariant, resourceId);
		}, false));
		extractForStarTrekCom(wikitext, allMatches, STAR_TREK_COM_EXTERNAL_LINK);
		extractForStarTrekCom(wikitext, allMatches, ST_COM_EXTERNAL_LINK);
		allMatches.addAll(extractMatches(wikitext, STAR_TREK_MINUTIAE_EXTERNAL_LINK, resourceId -> {
			return externalLinkFactory.ofTypeVariantAndResourceId(ExternalLinkType.STAR_TREK_MINUTIAE, null, resourceId);
		}));
		allMatches.addAll(extractMatches(wikitext, STO_WIKI_EXTERNAL_LINK_EMPTY, resourceId -> {
			return externalLinkFactory.ofTypeVariantAndResourceId(ExternalLinkType.STAR_TREK_ONLINE_WIKI, null, pageTitleWithUnderscores);
		}));
		allMatches.addAll(extractMatches(wikitext, STO_WIKI_EXTERNAL_LINK, resourceId -> {
			return externalLinkFactory.ofTypeVariantAndResourceId(ExternalLinkType.STAR_TREK_ONLINE_WIKI, null, resourceId);
		}));
		allMatches.addAll(extractMatches(wikitext, TRIVIA_TRIBUTE_EXTERNAL_LINK, resourceId -> {
			return externalLinkFactory.ofTypeVariantAndResourceId(ExternalLinkType.TRIVIA_TRIBUTE, null, resourceId);
		}));
		allMatches.addAll(extractMatches(wikitext, TWITTER_EXTERNAL_LINK, resourceId -> {
			resourceId = getResourceIdExcludingArchivedPages(resourceId);
			if (resourceId == null) {
				return null;
			}
			return externalLinkFactory.ofTypeVariantAndResourceId(ExternalLinkType.TWITTER, null, resourceId);
		}, false));
		allMatches.addAll(extractMatches(wikitext, WIKIPEDIA_EXTERNAL_LINK_EMPTY, resourceId -> {
			return externalLinkFactory.ofTypeVariantAndResourceId(ExternalLinkType.WIKIPEDIA, null, pageTitleWithUnderscores);
		}));
		allMatches.addAll(extractMatches(wikitext, WIKIPEDIA_EXTERNAL_LINK, resourceId -> {
			return externalLinkFactory.ofTypeVariantAndResourceId(ExternalLinkType.WIKIPEDIA, null, resourceId);
		}));
		allMatches.addAll(extractMatches(wikitext, W_EXTERNAL_LINK, resourceId -> {
			return externalLinkFactory.ofTypeVariantAndResourceId(ExternalLinkType.WIKIPEDIA, null, resourceId);
		}));
		allMatches.addAll(extractMatches(wikitext, WIKIPEDIA_TITLE_EXTERNAL_LINK_EMPTY, resourceId -> {
			return externalLinkFactory.ofTypeVariantAndResourceId(ExternalLinkType.WIKIPEDIA, null, pageTitleWithUnderscores);
		}));
		allMatches.addAll(extractMatches(wikitext, WIKIPEDIA_TITLE_EXTERNAL_LINK, resourceId -> {
			return externalLinkFactory.ofTypeVariantAndResourceId(ExternalLinkType.WIKIPEDIA, null, resourceId);
		}));
		allMatches.addAll(extractMatches(wikitext, WIKIPEDIA_QUOTE_EXTERNAL_LINK_EMPTY, resourceId -> {
			return externalLinkFactory.ofTypeVariantAndResourceId(ExternalLinkType.WIKIPEDIA, null, pageTitleWithUnderscores);
		}));
		allMatches.addAll(extractMatches(wikitext, WIKIPEDIA_QUOTE_EXTERNAL_LINK, resourceId -> {
			return externalLinkFactory.ofTypeVariantAndResourceId(ExternalLinkType.WIKIPEDIA, null, resourceId);
		}));
		allMatches.addAll(extractMatches(wikitext, WT_EXTERNAL_LINK_EMPTY, resourceId -> {
			return externalLinkFactory.ofTypeVariantAndResourceId(ExternalLinkType.WIKIPEDIA, null, pageTitleWithUnderscores);
		}));
		allMatches.addAll(extractMatches(wikitext, WT_EXTERNAL_LINK, resourceId -> {
			return externalLinkFactory.ofTypeVariantAndResourceId(ExternalLinkType.WIKIPEDIA, null, resourceId);
		}));
		allMatches.addAll(extractMatches(wikitext, WIKIQUOTE_EXTERNAL_LINK_EMPTY, resourceId -> {
			return externalLinkFactory.ofTypeVariantAndResourceId(ExternalLinkType.WIKIQUOTE, null, pageTitleWithUnderscores);
		}));
		allMatches.addAll(extractMatches(wikitext, WIKIQUOTE_EXTERNAL_LINK, resourceId -> {
			return externalLinkFactory.ofTypeVariantAndResourceId(ExternalLinkType.WIKIQUOTE, null, resourceId);
		}));
		allMatches.addAll(extractMatches(wikitext, YOUTUBE_EXTERNAL_LINK, resourceId -> {
			List<String> parts = Arrays.stream(resourceId.split(REGEX_PIPE_MATCH)).toList();
			// skip archive links to not complicate further
			if (parts.stream().anyMatch(part -> part.equalsIgnoreCase(DEAD_LINK_MARKER))) {
				return null;
			}
			boolean isVideo = parts.stream().anyMatch(part -> part.equalsIgnoreCase("type=v") || part.equalsIgnoreCase("type=video"));
			boolean isPlaylist = parts.stream().anyMatch(part -> part.equalsIgnoreCase("type=p") || part.equalsIgnoreCase("type=playlist"));
			boolean isUser = parts.stream().anyMatch(part -> part.equalsIgnoreCase("type=u") || part.equalsIgnoreCase("type=user"));
			boolean isChannel = parts.stream().anyMatch(part -> part.equalsIgnoreCase("type=c") || part.equalsIgnoreCase("type=channel"));
			if (!isVideo && !isPlaylist && !isUser && !isChannel) {
				// this is user
				return externalLinkFactory.ofTypeVariantAndResourceId(null, ExternalLinkTypeVariant.YOUTUBE, parts.get(0));
			}
			resourceId = parts.stream().filter(part -> !StringUtils.startsWithIgnoreCase(part, "type=")).findFirst().orElse(null);
			if (resourceId == null) {
				return null;
			}
			if (isVideo) {
				return externalLinkFactory.ofTypeVariantAndResourceId(null, ExternalLinkTypeVariant.YOUTUBE_VIDEO, resourceId);
			} else if (isPlaylist) {
				return externalLinkFactory.ofTypeVariantAndResourceId(null, ExternalLinkTypeVariant.YOUTUBE_PLAYLIST, resourceId);
			} else if (isUser) {
				return externalLinkFactory.ofTypeVariantAndResourceId(null, ExternalLinkTypeVariant.YOUTUBE_USER, resourceId);
			} else {
				return externalLinkFactory.ofTypeVariantAndResourceId(null, ExternalLinkTypeVariant.YOUTUBE_CHANNEL, resourceId);
			}
		}, false));

		return allMatches
				.stream()
				.filter(Objects::nonNull)
				.sorted(Comparator.comparing(externalLink -> {
					if (externalLink.getType() != null) {
						return externalLink.getType().name();
					} else {
						return externalLink.getVariant().name();
					}
				}))
				.collect(Collectors.toList());
	}

	@SuppressWarnings({"ParameterAssignment"})
	private static String getResourceIdExcludingArchivedPages(String resourceId) {
		List<String> parts = Arrays.stream(resourceId.split(REGEX_PIPE_MATCH)).toList();
		// skip archive links to not complicate further
		if (!parts.isEmpty()) {
			resourceId = parts.get(0);
		}
		if (parts.stream().anyMatch(part -> part.equalsIgnoreCase(DEAD_LINK_MARKER))) {
			return null;
		}
		return resourceId;
	}

	@SuppressWarnings("ParameterAssignment")
	private void extractForStarTrekCom(String wikitext, List<ExternalLink> allMatches, Pattern stComExternalLink) {
		allMatches.addAll(extractMatches(wikitext, stComExternalLink, resourceId -> {
			resourceId = getResourceIdExcludingArchivedPages(resourceId);
			if (resourceId == null) {
				return null;
			}
			return externalLinkFactory.ofTypeVariantAndResourceId(ExternalLinkType.STAR_TREK_COM, null, resourceId);
		}, false));
	}

	@Override
	public String getWikitextWithoutTemplates(String wikitext) {
		if (wikitext == null) {
			return null;
		}

		return MULTILINE_WITHOUT_TEMPLATES.matcher(wikitext).replaceAll("");
	}

	@Override
	public String getWikitextWithoutNoInclude(String wikitext) {
		if (wikitext == null) {
			return null;
		}

		return StringUtils.trim(NO_INCLUDE_PATTERN.matcher(wikitext).replaceAll(""));
	}

	@Override
	public String getWikitextWithoutLinks(String wikitext) {
		if (wikitext == null) {
			return null;
		}
		String wikitextWithoutLinks = wikitext;
		List<PageLink> pageLinkList = Lists.reverse(getPageLinksFromWikitext(wikitext));

		for (PageLink pageLink : pageLinkList) {
			String pageLinkDescription = MoreObjects.firstNonNull(pageLink.getDescription(), pageLink.getTitle());
			wikitextWithoutLinks = wikitextWithoutLinks.substring(0,
					pageLink.getStartPosition()) + pageLinkDescription + wikitextWithoutLinks.substring(pageLink.getEndPosition());
		}

		return wikitextWithoutLinks;
	}

	@Override
	public String disTemplateToPageTitle(Template template) {
		if (!TemplateTitle.DIS.equals(template.getTitle())) {
			return null;
		}

		List<Template.Part> templatePartList = template.getParts();
		if (templatePartList.size() == 1) {
			Template.Part templatePart = getTemplatePartByKey(templatePartList, ONE);
			return templatePart == null ? null : templatePart.getValue();
		} else if (templatePartList.size() == 2) {
			Template.Part templatePart1 = getTemplatePartByKey(templatePartList, ONE);
			Template.Part templatePart2 = getTemplatePartByKey(templatePartList, TWO);
			if (templatePart1 != null && templatePart2 == null) {
				log.info("Two template parts were found, but \"{}\" key was not found", TWO);
				return templatePart1.getValue();
			} else if (templatePart1 != null) {
				return templatePart1.getValue() + SPACE + LEFT_BRACKET + templatePart2.getValue() + RIGHT_BRACKET;
			}
		}

		return null;
	}

	@Override
	public List<String> getTemplateNamesFromWikitext(String wikitext) {
		if (wikitext == null) {
			return null;
		}
		String wikitextNoWhiteChars = wikitext.replaceAll("[\\t|\\r\\n]+", PIPE);
		List<String> allMatches = Lists.newArrayList();

		final Matcher matcher = MULTILINE_WITHOUT_TEMPLATES.matcher(wikitextNoWhiteChars);
		while (matcher.find()) {
			allMatches.add(matcher.group());
		}

		allMatches = allMatches.stream()
				.map(s -> {
					String result = s.substring(2, s.length() - 2).trim();
					result = StringUtils.substringBefore(result, PIPE);
					return result;
				})
				.collect(Collectors.toList());

		return allMatches;
	}

	private List<PageLink> extractLinkMatches(String wikitext) {
		List<PageLink> linkMatches = Lists.newArrayList();
		Matcher linkMatcher = LINK.matcher(wikitext);

		while (linkMatcher.find()) {
			String group = linkMatcher.group(LINK_CONTENTS_GROUP);
			PageLink pageLink = new PageLink();
			pageLink.setTitle(StringUtils.trim(StringUtils.substringBefore(group, PIPE)));
			pageLink.setUntrimmedDescription(group.contains(PIPE) ? StringUtils.substringAfter(group, PIPE) : null);
			pageLink.setDescription(StringUtils.trim(pageLink.getUntrimmedDescription()));
			pageLink.setStartPosition(linkMatcher.start(LINK_CONTENTS_GROUP) - PADDING);
			pageLink.setEndPosition(linkMatcher.end(LINK_CONTENTS_GROUP) + PADDING);
			linkMatches.add(pageLink);
		}

		return linkMatches;
	}

	private List<PageLink> extractMatches(String wikitext, Pattern pattern, int minParts, Function<List<String>, String> linkConstructor) {
		List<PageLink> matches = Lists.newArrayList();
		Matcher matcher = pattern.matcher(wikitext);

		while (matcher.find()) {
			PageLink pageLink = new PageLink();
			List<String> templateParts = Lists.newArrayList();
			boolean hasExpectedGroup = matcher.groupCount() > 0 && minParts > 0;
			if (hasExpectedGroup) {
				String group = matcher.group(LINK_CONTENTS_GROUP);
				templateParts = Lists.newArrayList(group.split(REGEX_PIPE_MATCH));

				if (templateParts.size() < minParts) {
					continue;
				}
			}

			String title = linkConstructor.apply(templateParts);
			pageLink.setTitle(title);
			pageLink.setDescription(templateParts.isEmpty() ? title : templateParts.get(0));

			if (templateParts.size() == minParts + 1) {
				pageLink.setDescription(templateParts.get(minParts));
			}

			pageLink.setStartPosition(!hasExpectedGroup ? matcher.toMatchResult().start() : matcher.start(LINK_CONTENTS_GROUP) - PADDING);
			pageLink.setEndPosition(!hasExpectedGroup ? matcher.toMatchResult().end() : matcher.end(LINK_CONTENTS_GROUP) + PADDING);
			matches.add(pageLink);
		}

		return matches;
	}

	private List<ExternalLink> extractMatches(String wikitext, Pattern pattern,
			Function<String, ExternalLink> resourceIdExtractor) {
		return extractMatches(wikitext, pattern, resourceIdExtractor, true);
	}

	private List<ExternalLink> extractMatches(String wikitext, Pattern pattern,
			Function<String, ExternalLink> resourceIdExtractor, boolean getFirstBeforePipe) {
		List<ExternalLink> matches = Lists.newArrayList();
		Matcher matcher = pattern.matcher(wikitext);

		while (matcher.find()) {
			List<String> templateParts = Lists.newArrayList();
			boolean hasExpectedGroup = matcher.groupCount() > 0;
			String group = null;
			if (hasExpectedGroup) {
				group = matcher.group(LINK_CONTENTS_GROUP);
				templateParts = Lists.newArrayList(group.split("\\" + PIPE));
				if (templateParts.size() < 1) {
					continue;
				}
			} else if (!pattern.pattern().contains(PIPE)) {
				ExternalLink externalLink = resourceIdExtractor.apply(null);
				matches.add(externalLink);
				continue;
			}

			ExternalLink externalLink = null;
			if (getFirstBeforePipe) {
				externalLink = resourceIdExtractor.apply(templateParts.get(0));
			} else if (group != null) {
				externalLink = resourceIdExtractor.apply(group);

			}
			if (externalLink != null) {
				matches.add(externalLink);
			}
		}

		return matches;
	}

	private Template.Part getTemplatePartByKey(List<Template.Part> templatePartList, String key) {
		return templatePartList
				.stream()
				.filter(part -> key.equals(part.getKey()))
				.findFirst().orElse(null);
	}

}
