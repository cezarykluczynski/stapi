package com.cezarykluczynski.stapi.model.reference.factory;

import com.cezarykluczynski.stapi.model.reference.entity.Reference;
import com.cezarykluczynski.stapi.model.reference.entity.enums.ReferenceType;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

@Service
public class ReferenceFactory {

	public Reference createFromUid(String uid) {
		Preconditions.checkArgument(uid.length() == 14, "UID has to have 14 characters");
		Reference reference = new Reference();
		reference.setUid(uid);
		Pair<ReferenceType, String> referenceTypeReferenceNumberPair = createTypeNumberPair(uid);
		reference.setReferenceType(referenceTypeReferenceNumberPair.getKey());
		reference.setReferenceNumber(referenceTypeReferenceNumberPair.getValue());
		return reference;
	}

	@SuppressWarnings("ReturnCount")
	private Pair<ReferenceType, String> createTypeNumberPair(String uid) {
		if (uid.startsWith("ISBN")) {
			return Pair.of(ReferenceType.ISBN, uid.substring(4));
		} else if (uid.startsWith("ASIN")) {
			return Pair.of(ReferenceType.ASIN, uid.substring(4));
		} else if (uid.startsWith("IS")) {
			return Pair.of(ReferenceType.ISRC, uid.substring(2));
		} else if (uid.startsWith("I")) {
			return Pair.of(ReferenceType.ISBN, uid.substring(1));
		} else if (uid.startsWith("EAN8")) {
			return Pair.of(ReferenceType.EAN, uid.substring(6));
		} else if (uid.startsWith("E")) {
			return Pair.of(ReferenceType.EAN, uid.substring(1));
		} else {
			throw new StapiRuntimeException(String.format("Could not create Reference from UID %s", uid));
		}
	}

}
