package com.cezarykluczynski.stapi.etl.character.link.relation.dto;

import com.google.common.collect.ImmutableSet;

import java.lang.reflect.Field;
import java.util.Set;

public class CharacterRelationName {

	public static final String FATHER = "Father";
	public static final String FOSTER_FATHER = "Foster father";
	public static final String ADOPTIVE_FATHER = "Adoptive father";
	public static final String MOTHER = "Mother";
	public static final String FOSTER_MOTHER = "Foster mother";
	public static final String ADOPTIVE_MOTHER = "Adoptive mother";
	public static final String SURROGATE_MOTHER = "Surrogate mother";
	public static final String BROTHER = "Brother";
	public static final String ADOPTIVE_BROTHER = "Adoptive brother";
	public static final String HALF_BROTHER = "Half-brother";
	public static final String SON = "Son";
	public static final String SISTER = "Sister";
	public static final String ADOPTIVE_SISTER = "Adoptive sister";
	public static final String HALF_SISTER = "Half-sister";
	public static final String DAUGHTER = "Daughter";
	public static final String OWNER = "Owner";
	public static final String SIBLING = "Sibling";
	public static final String ADOPTIVE_SIBLING = "Adoptive sibling";
	public static final String RELATIVE = "Relative";
	public static final String SPOUSE = "Spouse";
	public static final String EX_SPOUSE = "Ex-spouse";
	public static final String CHILD = "Child";
	public static final String ADOPTIVE_CHILD = "Adoptive child";
	public static final String CREATOR = "Creator";
	public static final String ORIGINAL_CHARACTER = "Original character";
	public static final String PROPERTY = "Property";
	public static final String PARENT = "Parent";
	public static final String CREATION = "Creation";
	public static final String HOLOGRAM = "Hologram";
	public static final String GRANDFATHER = "Grandfather";
	public static final String GRANDMOTHER = "Grandmother";
	public static final String GREAT_GRANDFATHER = "Great-grandfather";
	public static final String GREAT_GREAT_GRANDFATHER = "Great-great-grandfather";
	public static final String GREAT_GRANDMOTHER = "Great-grandmother";
	public static final String MATERNAL_GRANDFATHER = "Maternal grandfather";
	public static final String MATERNAL_GRANDMOTHER = "Maternal grandmother";
	public static final String MATERNAL_GREAT_GRANDFATHER = "Maternal great-grandfather";
	public static final String MATERNAL_GREAT_GRANDMOTHER = "Maternal great-grandmother";
	public static final String PATERNAL_GRANDFATHER = "Paternal grandfather";
	public static final String PATERNAL_GRANDMOTHER = "Paternal grandmother";
	public static final String PATERNAL_GREAT_GRANDFATHER = "Paternal great-grandfather";
	public static final String PATERNAL_GREAT_GRANDMOTHER = "Paternal great-grandmother";
	public static final String GRANDDAUGHTER = "Granddaughter";
	public static final String GRANDSON = "Grandson";
	public static final String GRANDCHILD = "Grandchild";
	public static final String GREAT_GRANDSON = "Great-grandson";
	public static final String GREAT_GRANDDAUGHTER = "Great-granddaughter";
	public static final String GREAT_GREAT_GRANDDAUGHTER = "Great-great-granddaughter";
	public static final String WIFE = "Wife";
	public static final String HUSBAND = "Husband";
	public static final String CONSORT = "Consort";
	public static final String PARTNER = "Partner";
	public static final String FORMER_PARTNER = "Former partner";
	public static final String COUSIN = "Cousin";
	public static final String ANCESTOR = "Ancestor";
	public static final String DESCENDANT = "Descendant";
	public static final String PATERNAL_ANCESTOR = "Paternal ancestor";
	public static final String MATERNAL_ANCESTOR = "Maternal ancestor";
	public static final String NEPHEW = "Nephew";
	public static final String AUNT = "Aunt";
	public static final String UNCLE = "Uncle";
	public static final String FATHER_IN_LAW = "Father in-law";
	public static final String MOTHER_IN_LAW = "Mother in-law";
	public static final String BROTHER_IN_LAW = "Brother in-law";
	public static final String SISTER_IN_LAW = "Sister in-law";
	public static final String DAUGHTER_IN_LAW = "Daughter in-law";
	public static final String SON_IN_LAW = "Son in-law";
	public static final String GRANDSON_IN_LAW = "Grandson in-law";
	public static final String NIECE = "Niece";
	public static final String STEPFATHER = "Stepfather";
	public static final String STEPMOTHER = "Stepmother";
	public static final String STEPSON = "Stepson";
	public static final String EX_STEPFATHER = "Ex-stepfather";
	public static final String ADOPTIVE_GRANDPARENT = "Adoptive grandparent";
	public static final String PATERNAL_UNCLE = "Paternal uncle";
	public static final String PATERNAL_AUNT = "Paternal aunt";
	public static final String ADOPTIVE_UNCLE = "Adoptive uncle";
	public static final String GODSON = "Godson";
	public static final String GODDAUGHTER = "Goddaughter";
	public static final String GODPARENT = "Godparent";
	public static final String FOREMOTHER = "Foremother";
	public static final String THIRD_FOREMOTHER = "Third foremother";
	public static final String DNA_DONOR = "DNA donor";
	public static final String DNA_RECIPIENT = "DNA recipient";
	public static final String DNA_TEMPLATE = "DNA template";
	public static final String TRANSPORTER_DUPLICATE = "Transporter duplicate";
	public static final String CLONE = "Clone";

	public static final Set<String> ALL_RELATIONS;

	static {
		ImmutableSet.Builder<String> builder = ImmutableSet.builder();
		for (Field field : CharacterRelationName.class.getDeclaredFields()) {
			if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
				try {
					if (field.getType() == String.class) {
						builder.add((String) field.get(null));
					}
				} catch (IllegalAccessException e) {
					// do nothing
				}
			}
		}
		ALL_RELATIONS = builder.build();
	}

}
