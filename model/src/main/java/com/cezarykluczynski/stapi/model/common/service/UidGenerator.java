package com.cezarykluczynski.stapi.model.common.service;

import com.cezarykluczynski.stapi.model.page.entity.Page;
import com.cezarykluczynski.stapi.model.page.entity.PageAware;
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.model.reference.entity.enums.ReferenceType;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.regex.Pattern;

@Service
@Slf4j
public class UidGenerator {

	private static final Long MAX_PAGE_ID = 9999999999L;
	private static final Pattern ASIN = Pattern.compile("^[B]{1}[0-9]{2}[A-Z0-9]{7}|[0-9]{9}(X|[0-9])$");
	private static final Pattern ISBN = Pattern.compile("^[0-9\\-\\s]{9,17}[0-9X]?$");

	private final Map<MediaWikiSource, String> mediaWikiSourceToSymbolMap = Maps.newHashMap();

	private final EntityMatadataProvider entityMatadataProvider;

	public UidGenerator(EntityMatadataProvider entityMatadataProvider) {
		this.entityMatadataProvider = entityMatadataProvider;
		buildMediaWikiSourceToSymbolMap();
	}

	private void buildMediaWikiSourceToSymbolMap() {
		mediaWikiSourceToSymbolMap.put(MediaWikiSource.MEMORY_ALPHA_EN, "MA");
		mediaWikiSourceToSymbolMap.put(MediaWikiSource.MEMORY_BETA_EN, "MB");
	}

	public String generateFromPage(Page page, Class<? extends PageAware> clazz) {
		Preconditions.checkNotNull(page, "Page cannot be null");
		Preconditions.checkNotNull(page.getPageId(), "Page ID cannot be null");
		Map<String, ClassMetadata> classMetadataMap = entityMatadataProvider.provideClassNameToMetadataMap();
		ClassMetadata classMetadata = classMetadataMap.get(clazz.getCanonicalName());
		if (classMetadata == null) {
			throw new StapiRuntimeException(String.format("No class metadata for entity %s.", clazz.getCanonicalName()));
		}

		if (page.getPageId() > MAX_PAGE_ID) {
			throw new StapiRuntimeException(String.format("Page ID %s is greater than allowed, cannot guarantee UID uniqueness.", page.getPageId()));
		}

		Class mappedClass = classMetadata.getMappedClass();
		String mappedClassCanonicalName = mappedClass.getCanonicalName();
		String entitySymbol = entityMatadataProvider.provideClassNameToSymbolMap().get(mappedClassCanonicalName);
		String sourceSymbol = mediaWikiSourceToSymbolMap.get(page.getMediaWikiSource());
		String paddedPageId = StringUtils.leftPad(page.getPageId().toString(), 10, "0");
		return entitySymbol + sourceSymbol + paddedPageId;
	}

	public String generateFromReference(Pair<ReferenceType, String> referenceTypeReferenceNumberPair) {
		ReferenceType referenceType = referenceTypeReferenceNumberPair.getKey();
		String referenceNumber = referenceTypeReferenceNumberPair.getValue();

		if (referenceType == null || referenceNumber == null) {
			log.warn("Pair {} passed for reference UID generation was not complete", referenceTypeReferenceNumberPair);
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

}
