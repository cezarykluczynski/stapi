package com.cezarykluczynski.stapi.model.reference.factory;

import com.cezarykluczynski.stapi.model.reference.entity.Reference;
import com.cezarykluczynski.stapi.model.reference.entity.enums.ReferenceType;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

@Service
public class ReferenceFactory {

	public Reference createFromGuid(String guid) {
		Preconditions.checkArgument(guid.length() == 14, "GUID has to have 14 characters");
		Reference reference = new Reference();
		reference.setGuid(guid);
		Pair<ReferenceType, String> referenceTypeReferenceNumberPair = createTypeNumberPair(guid);
		reference.setReferenceType(referenceTypeReferenceNumberPair.getKey());
		reference.setReferenceNumber(referenceTypeReferenceNumberPair.getValue());
		return reference;
	}

	private Pair<ReferenceType, String> createTypeNumberPair(String guid) {
		if (guid.startsWith("ISBN")) {
			return Pair.of(ReferenceType.ISBN, guid.substring(4));
		} else if (guid.startsWith("ASIN")) {
			return Pair.of(ReferenceType.ASIN, guid.substring(4));
		} else if (guid.startsWith("I")) {
			return Pair.of(ReferenceType.ISBN, guid.substring(1));
		} else {
			throw new RuntimeException(String.format("Could not create Reference from GUID %s", guid));
		}
	}

}
