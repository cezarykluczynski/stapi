package com.cezarykluczynski.stapi.model.common.service;

import com.cezarykluczynski.stapi.model.character.entity.CharacterSpecies;
import com.cezarykluczynski.stapi.model.comicCollection.entity.ComicCollection;
import com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries;
import com.cezarykluczynski.stapi.model.comicStrip.entity.ComicStrip;
import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.cezarykluczynski.stapi.model.page.entity.Page;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.model.reference.entity.enums.ReferenceType;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Map;
import java.util.regex.Pattern;

@Service
@Slf4j
public class GuidGenerator {

	private static final Long MAX_PAGE_ID = 9999999999L;

	private static final Map<Class, String> CUSTOM_SYMBOL_MAP = Maps.newHashMap();

	private static final Pattern ASIN = Pattern.compile("^[B]{1}[0-9]{2}[A-Z0-9]{7}|[0-9]{9}(X|[0-9])$");
	private static final Pattern ISBN = Pattern.compile("^[0-9\\-\\s]{9,17}[0-9X]?$");

	static {
		CUSTOM_SYMBOL_MAP.put(ComicSeries.class, "CS");
		CUSTOM_SYMBOL_MAP.put(Comics.class, "CC");
		CUSTOM_SYMBOL_MAP.put(ComicStrip.class, "CT");
		CUSTOM_SYMBOL_MAP.put(ComicCollection.class, "CL");
		CUSTOM_SYMBOL_MAP.put(CharacterSpecies.class, "CP");
	}

	private EntityManager entityManager;

	private Map<String, String> canonicalEntityNameToSymbolMap = Maps.newHashMap();

	private Map<MediaWikiSource, String> mediaWikiSourceToSymbolMap = Maps.newHashMap();

	public GuidGenerator(EntityManager entityManager) {
		this.entityManager = entityManager;
		buildMediaWikiSourceToSymbolMap();
		buildClassSymbolMap();
	}

	private void buildMediaWikiSourceToSymbolMap() {
		mediaWikiSourceToSymbolMap.put(MediaWikiSource.MEMORY_ALPHA_EN, "MA");
		mediaWikiSourceToSymbolMap.put(MediaWikiSource.MEMORY_BETA_EN, "MB");
	}

	public String generateFromPage(Page page, Class<? extends PageAware> clazz) {
		Preconditions.checkNotNull(page, "Page cannot be null");
		Preconditions.checkNotNull(page.getPageId(), "Page ID cannot be null");
		Map<String, ClassMetadata> classMetadataMap = getClassMetadata();
		ClassMetadata classMetadata = classMetadataMap.get(clazz.getCanonicalName());
		if (classMetadata == null) {
			throw new RuntimeException(String.format("No class metadata for entity %s.", clazz.getCanonicalName()));
		}

		if (page.getPageId() > MAX_PAGE_ID) {
			throw new RuntimeException(String.format("Page ID %s is greater than allowed, cannot guarantee GUID uniqueness.", page.getPageId()));
		}

		Class mappedClass = classMetadata.getMappedClass();
		String mappedClassCanonicalName = mappedClass.getCanonicalName();
		String entitySymbol = canonicalEntityNameToSymbolMap.get(mappedClassCanonicalName);
		String sourceSymbol = mediaWikiSourceToSymbolMap.get(page.getMediaWikiSource());
		String paddedPageId = StringUtils.leftPad(page.getPageId().toString(), 10, "0");
		return entitySymbol + sourceSymbol + paddedPageId;
	}

	public String generateFromReference(Pair<ReferenceType, String> referenceTypeReferenceNumberPair) {
		ReferenceType referenceType = referenceTypeReferenceNumberPair.getKey();
		String referenceNumber = referenceTypeReferenceNumberPair.getValue();

		if (referenceType == null || referenceNumber == null) {
			log.warn("Pair {} passed for reference GUID generation was not complete", referenceTypeReferenceNumberPair);
			return null;
		}

		if (ReferenceType.ASIN.equals(referenceType) && ASIN.matcher(referenceNumber).matches()) {
			return "ASIN" + referenceNumber;
		} else if (ReferenceType.ISBN.equals(referenceType) && ISBN.matcher(referenceNumber).matches()) {
			String clearedIsbn = referenceNumber.replaceAll("-|\\s", "");
			if (clearedIsbn.length() == 10) {
				return "ISBN" + clearedIsbn;
			} else if (clearedIsbn.length() == 13) {
				return "I" + clearedIsbn;
			}
		}

		return null;
	}

	private void buildClassSymbolMap() {
		Map<String, ClassMetadata> classMetadataMap = getClassMetadata();

		classMetadataMap.entrySet().forEach(stringClassMetadataEntry -> {
			ClassMetadata classMetadata = stringClassMetadataEntry.getValue();
			Class mappedClass = classMetadata.getMappedClass();
			String mappedClassCanonicalName = mappedClass.getCanonicalName();
			String symbol = buildClassSymbol(mappedClass);

			if (canonicalEntityNameToSymbolMap.values().contains(symbol)) {
				throw new RuntimeException(String.format("Entity class collection no longer suitable for symbol generation. Trying to put symbol "
						+ "%s, but symbol already present.", symbol));
			}

			canonicalEntityNameToSymbolMap.put(mappedClassCanonicalName, symbol);
		});
	}

	private Map<String, ClassMetadata> getClassMetadata() {
		SessionFactory sessionFactory = ((Session) entityManager.getDelegate()).getSessionFactory();
		return sessionFactory.getAllClassMetadata();
	}

	private String buildClassSymbol(Class clazz) {
		if (CUSTOM_SYMBOL_MAP.containsKey(clazz)) {
			return CUSTOM_SYMBOL_MAP.get(clazz);
		}

		return clazz.getSimpleName().substring(0, 2).toUpperCase();
	}

}
