package com.cezarykluczynski.stapi.model.common.service;

import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection;
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries;
import com.cezarykluczynski.stapi.model.character.entity.CharacterRelation;
import com.cezarykluczynski.stapi.model.character.entity.CharacterSpecies;
import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection;
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries;
import com.cezarykluczynski.stapi.model.comic_strip.entity.ComicStrip;
import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.cezarykluczynski.stapi.model.conflict.entity.Conflict;
import com.cezarykluczynski.stapi.model.consent.entity.Consent;
import com.cezarykluczynski.stapi.model.content_language.entity.ContentLanguage;
import com.cezarykluczynski.stapi.model.content_rating.entity.ContentRating;
import com.cezarykluczynski.stapi.model.country.entity.Country;
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries;
import com.cezarykluczynski.stapi.model.material.entity.Material;
import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType;
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard;
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet;
import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EntityMetadataProvider {

	private static final Map<Class, String> CUSTOM_SYMBOL_MAP = Maps.newHashMap();

	static {
		CUSTOM_SYMBOL_MAP.put(ComicSeries.class, "CS");
		CUSTOM_SYMBOL_MAP.put(Comics.class, "CC");
		CUSTOM_SYMBOL_MAP.put(ComicStrip.class, "CT");
		CUSTOM_SYMBOL_MAP.put(ComicCollection.class, "CL");
		CUSTOM_SYMBOL_MAP.put(CharacterSpecies.class, "CP");
		CUSTOM_SYMBOL_MAP.put(CharacterRelation.class, "RC");
		CUSTOM_SYMBOL_MAP.put(BookSeries.class, "BS");
		CUSTOM_SYMBOL_MAP.put(BookCollection.class, "BC");
		CUSTOM_SYMBOL_MAP.put(MagazineSeries.class, "MS");
		CUSTOM_SYMBOL_MAP.put(ContentRating.class, "CR");
		CUSTOM_SYMBOL_MAP.put(ContentLanguage.class, "CA");
		CUSTOM_SYMBOL_MAP.put(Season.class, "SA");
		CUSTOM_SYMBOL_MAP.put(TradingCard.class, "TC");
		CUSTOM_SYMBOL_MAP.put(TradingCardSet.class, "TS");
		CUSTOM_SYMBOL_MAP.put(Country.class, "CU");
		CUSTOM_SYMBOL_MAP.put(VideoGame.class, "VG");
		CUSTOM_SYMBOL_MAP.put(SpacecraftClass.class, "SC");
		CUSTOM_SYMBOL_MAP.put(SpacecraftType.class, "SY");
		CUSTOM_SYMBOL_MAP.put(Spacecraft.class, "SR");
		CUSTOM_SYMBOL_MAP.put(Material.class, "MT");
		CUSTOM_SYMBOL_MAP.put(Conflict.class, "CF");
		CUSTOM_SYMBOL_MAP.put(Consent.class, "CN");
	}

	private final EntityManager entityManager;

	private Map<String, String> classNameToSymbolMap;

	private Map<String, ClassMetadata> classNameToMetadataMap;

	private Map<String, Class> classSimpleNameToClassMap;

	public EntityMetadataProvider(EntityManager entityManager) {
		this.entityManager = entityManager;
		buildClassNameToSymbolMap();
		buildClassNameToMetadataMap();
		buildClassSimpleNameToClassMap();
	}

	public Map<String, String> provideClassNameToSymbolMap() {
		return classNameToSymbolMap;
	}

	public Map<String, ClassMetadata> provideClassNameToMetadataMap() {
		return classNameToMetadataMap;
	}

	public Map<String, Class> provideClassSimpleNameToClassMap() {
		return classSimpleNameToClassMap;
	}

	private void buildClassNameToMetadataMap() {
		SessionFactory sessionFactory = ((Session) entityManager.getDelegate()).getSessionFactory();
		classNameToMetadataMap = sessionFactory.getAllClassMetadata();
	}

	private void buildClassNameToSymbolMap() {
		classNameToSymbolMap = Maps.newHashMap();

		Map<String, ClassMetadata> classMetadataMap = getClassMetadata();

		classMetadataMap.entrySet().forEach(stringClassMetadataEntry -> {
			ClassMetadata classMetadata = stringClassMetadataEntry.getValue();
			Class mappedClass = classMetadata.getMappedClass();
			String mappedClassCanonicalName = mappedClass.getCanonicalName();
			String symbol = buildClassSymbol(mappedClass);

			if (classNameToSymbolMap.values().contains(symbol)) {
				throw new StapiRuntimeException(String.format("Entity class collection no longer suitable for symbol generation. "
						+ "Trying to put symbol %s, but symbol already present.", symbol));
			}

			classNameToSymbolMap.put(mappedClassCanonicalName, symbol);
		});
	}

	private void buildClassSimpleNameToClassMap() {
		Map<String, ClassMetadata> classMetadataMap = getClassMetadata();
		classSimpleNameToClassMap = classMetadataMap.entrySet().stream()
				.collect(Collectors.toMap(this::toSimpleName, entry -> entry.getValue().getMappedClass()));
	}

	private String toSimpleName(Map.Entry<String, ClassMetadata> entry) {
		List<String> classNameParts = Lists.newArrayList(entry.getKey().split("\\."));
		return classNameParts.get(classNameParts.size() - 1);
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
