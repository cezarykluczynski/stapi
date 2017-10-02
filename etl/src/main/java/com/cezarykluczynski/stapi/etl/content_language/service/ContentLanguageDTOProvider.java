package com.cezarykluczynski.stapi.etl.content_language.service;

import com.cezarykluczynski.stapi.etl.content_language.dto.ContentLanguageDTO;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ContentLanguageDTOProvider {

	private static final String SLOVENE = "Slovene";
	private static final String SPANISH = "Spanish";

	private static final List<ContentLanguageDTO> LANGUAGES = Lists.newArrayList();

	private static final Map<String, ContentLanguageDTO> NAME_INDEX;

	static {
		LANGUAGES.add(ContentLanguageDTO.of("Abkhaz", "ab"));
		LANGUAGES.add(ContentLanguageDTO.of("Afar", "aa"));
		LANGUAGES.add(ContentLanguageDTO.of("Afrikaans", "af"));
		LANGUAGES.add(ContentLanguageDTO.of("Akan", "ak"));
		LANGUAGES.add(ContentLanguageDTO.of("Albanian", "sq"));
		LANGUAGES.add(ContentLanguageDTO.of("Amharic", "am"));
		LANGUAGES.add(ContentLanguageDTO.of("Arabic", "ar"));
		LANGUAGES.add(ContentLanguageDTO.of("Aragonese", "an"));
		LANGUAGES.add(ContentLanguageDTO.of("Armenian", "hy"));
		LANGUAGES.add(ContentLanguageDTO.of("Assamese", "as"));
		LANGUAGES.add(ContentLanguageDTO.of("Avaric", "av"));
		LANGUAGES.add(ContentLanguageDTO.of("Avestan", "ae"));
		LANGUAGES.add(ContentLanguageDTO.of("Aymara", "ay"));
		LANGUAGES.add(ContentLanguageDTO.of("Azerbaijani", "az"));
		LANGUAGES.add(ContentLanguageDTO.of("Bambara", "bm"));
		LANGUAGES.add(ContentLanguageDTO.of("Bashkir", "ba"));
		LANGUAGES.add(ContentLanguageDTO.of("Basque", "eu"));
		LANGUAGES.add(ContentLanguageDTO.of("Belarusian", "be"));
		LANGUAGES.add(ContentLanguageDTO.of("Bengali", "bn"));
		LANGUAGES.add(ContentLanguageDTO.of("Bihari", "bh"));
		LANGUAGES.add(ContentLanguageDTO.of("Bislama", "bi"));
		LANGUAGES.add(ContentLanguageDTO.of("Bosnian", "bs"));
		LANGUAGES.add(ContentLanguageDTO.of("Breton", "br"));
		LANGUAGES.add(ContentLanguageDTO.of("Bulgarian", "bg"));
		LANGUAGES.add(ContentLanguageDTO.of("Burmese", "my"));
		LANGUAGES.add(ContentLanguageDTO.of("Catalan", "ca"));
		LANGUAGES.add(ContentLanguageDTO.of("Valencian", "ca"));
		LANGUAGES.add(ContentLanguageDTO.of("Chamorro", "ch"));
		LANGUAGES.add(ContentLanguageDTO.of("Chechen", "ce"));
		LANGUAGES.add(ContentLanguageDTO.of("Chichewa", "ny"));
		LANGUAGES.add(ContentLanguageDTO.of("Chewa", "ny"));
		LANGUAGES.add(ContentLanguageDTO.of("Nyanja", "ny"));
		LANGUAGES.add(ContentLanguageDTO.of("Chinese", "zh"));
		LANGUAGES.add(ContentLanguageDTO.of("Chuvash", "cv"));
		LANGUAGES.add(ContentLanguageDTO.of("Cornish", "kw"));
		LANGUAGES.add(ContentLanguageDTO.of("Corsican", "co"));
		LANGUAGES.add(ContentLanguageDTO.of("Cree", "cr"));
		LANGUAGES.add(ContentLanguageDTO.of("Croatian", "hr"));
		LANGUAGES.add(ContentLanguageDTO.of("Czech", "cs"));
		LANGUAGES.add(ContentLanguageDTO.of("Danish", "da"));
		LANGUAGES.add(ContentLanguageDTO.of("Divehi", "dv"));
		LANGUAGES.add(ContentLanguageDTO.of("Dhivehi", "dv"));
		LANGUAGES.add(ContentLanguageDTO.of("Maldivian", "dv"));
		LANGUAGES.add(ContentLanguageDTO.of("Dutch", "nl"));
		LANGUAGES.add(ContentLanguageDTO.of("English", "en"));
		LANGUAGES.add(ContentLanguageDTO.of("Esperanto", "eo"));
		LANGUAGES.add(ContentLanguageDTO.of("Estonian", "et"));
		LANGUAGES.add(ContentLanguageDTO.of("Ewe", "ee"));
		LANGUAGES.add(ContentLanguageDTO.of("Faroese", "fo"));
		LANGUAGES.add(ContentLanguageDTO.of("Fijian", "fj"));
		LANGUAGES.add(ContentLanguageDTO.of("Finnish", "fi"));
		LANGUAGES.add(ContentLanguageDTO.of("French", "fr"));
		LANGUAGES.add(ContentLanguageDTO.of("Fula", "ff"));
		LANGUAGES.add(ContentLanguageDTO.of("Fulah", "ff"));
		LANGUAGES.add(ContentLanguageDTO.of("Pulaar", "ff"));
		LANGUAGES.add(ContentLanguageDTO.of("Pular", "ff"));
		LANGUAGES.add(ContentLanguageDTO.of("Galician", "gl"));
		LANGUAGES.add(ContentLanguageDTO.of("Georgian", "ka"));
		LANGUAGES.add(ContentLanguageDTO.of("German", "de"));
		LANGUAGES.add(ContentLanguageDTO.of("Greek", "el"));
		LANGUAGES.add(ContentLanguageDTO.of("Guaraní", "gn"));
		LANGUAGES.add(ContentLanguageDTO.of("Gujarati", "gu"));
		LANGUAGES.add(ContentLanguageDTO.of("Haitian", "ht"));
		LANGUAGES.add(ContentLanguageDTO.of("Haitian Creole", "ht"));
		LANGUAGES.add(ContentLanguageDTO.of("Hausa", "ha"));
		LANGUAGES.add(ContentLanguageDTO.of("Hebrew", "he"));
		LANGUAGES.add(ContentLanguageDTO.of("Herero", "hz"));
		LANGUAGES.add(ContentLanguageDTO.of("Hindi", "hi"));
		LANGUAGES.add(ContentLanguageDTO.of("Hiri Motu", "ho"));
		LANGUAGES.add(ContentLanguageDTO.of("Hungarian", "hu"));
		LANGUAGES.add(ContentLanguageDTO.of("Interlingua", "ia"));
		LANGUAGES.add(ContentLanguageDTO.of("Indonesian", "id"));
		LANGUAGES.add(ContentLanguageDTO.of("Interlingue", "ie"));
		LANGUAGES.add(ContentLanguageDTO.of("Irish", "ga"));
		LANGUAGES.add(ContentLanguageDTO.of("Igbo", "ig"));
		LANGUAGES.add(ContentLanguageDTO.of("Inupiaq", "ik"));
		LANGUAGES.add(ContentLanguageDTO.of("Ido", "io"));
		LANGUAGES.add(ContentLanguageDTO.of("Icelandic", "is"));
		LANGUAGES.add(ContentLanguageDTO.of("Italian", "it"));
		LANGUAGES.add(ContentLanguageDTO.of("Inuktitut", "iu"));
		LANGUAGES.add(ContentLanguageDTO.of("Japanese", "ja"));
		LANGUAGES.add(ContentLanguageDTO.of("Javanese", "jv"));
		LANGUAGES.add(ContentLanguageDTO.of("Kalaallisut", "kl"));
		LANGUAGES.add(ContentLanguageDTO.of("Greenlandic", "kl"));
		LANGUAGES.add(ContentLanguageDTO.of("Kannada", "kn"));
		LANGUAGES.add(ContentLanguageDTO.of("Kanuri", "kr"));
		LANGUAGES.add(ContentLanguageDTO.of("Kashmiri", "ks"));
		LANGUAGES.add(ContentLanguageDTO.of("Kazakh", "kk"));
		LANGUAGES.add(ContentLanguageDTO.of("Khmer", "km"));
		LANGUAGES.add(ContentLanguageDTO.of("Kikuyu", "ki"));
		LANGUAGES.add(ContentLanguageDTO.of("Gikuyu", "ki"));
		LANGUAGES.add(ContentLanguageDTO.of("Kinyarwanda", "rw"));
		LANGUAGES.add(ContentLanguageDTO.of("Kirghiz", "ky"));
		LANGUAGES.add(ContentLanguageDTO.of("Kyrgyz", "ky"));
		LANGUAGES.add(ContentLanguageDTO.of("Komi", "kv"));
		LANGUAGES.add(ContentLanguageDTO.of("Kongo", "kg"));
		LANGUAGES.add(ContentLanguageDTO.of("Korean", "ko"));
		LANGUAGES.add(ContentLanguageDTO.of("Kurdish", "ku"));
		LANGUAGES.add(ContentLanguageDTO.of("Kwanyama", "kj"));
		LANGUAGES.add(ContentLanguageDTO.of("Kuanyama", "kj"));
		LANGUAGES.add(ContentLanguageDTO.of("Latin", "la"));
		LANGUAGES.add(ContentLanguageDTO.of("Luxembourgish", "lb"));
		LANGUAGES.add(ContentLanguageDTO.of("Letzeburgesch", "lb"));
		LANGUAGES.add(ContentLanguageDTO.of("Luganda", "lg"));
		LANGUAGES.add(ContentLanguageDTO.of("Limburgish", "li"));
		LANGUAGES.add(ContentLanguageDTO.of("Limburgan", "li"));
		LANGUAGES.add(ContentLanguageDTO.of("Limburger", "li"));
		LANGUAGES.add(ContentLanguageDTO.of("Lingala", "ln"));
		LANGUAGES.add(ContentLanguageDTO.of("Lao", "lo"));
		LANGUAGES.add(ContentLanguageDTO.of("Lithuanian", "lt"));
		LANGUAGES.add(ContentLanguageDTO.of("Luba-Katanga", "lu"));
		LANGUAGES.add(ContentLanguageDTO.of("Latvian", "lv"));
		LANGUAGES.add(ContentLanguageDTO.of("Manx", "gv"));
		LANGUAGES.add(ContentLanguageDTO.of("Macedonian", "mk"));
		LANGUAGES.add(ContentLanguageDTO.of("Malagasy", "mg"));
		LANGUAGES.add(ContentLanguageDTO.of("Malay", "ms"));
		LANGUAGES.add(ContentLanguageDTO.of("Malayalam", "ml"));
		LANGUAGES.add(ContentLanguageDTO.of("Maltese", "mt"));
		LANGUAGES.add(ContentLanguageDTO.of("Māori", "mi"));
		LANGUAGES.add(ContentLanguageDTO.of("Marathi", "mr"));
		LANGUAGES.add(ContentLanguageDTO.of("Marāṭhī", "mr"));
		LANGUAGES.add(ContentLanguageDTO.of("Marshallese", "mh"));
		LANGUAGES.add(ContentLanguageDTO.of("Mongolian", "mn"));
		LANGUAGES.add(ContentLanguageDTO.of("Nauru", "na"));
		LANGUAGES.add(ContentLanguageDTO.of("Navajo", "nv"));
		LANGUAGES.add(ContentLanguageDTO.of("Navaho", "nv"));
		LANGUAGES.add(ContentLanguageDTO.of("Norwegian Bokmål", "nb"));
		LANGUAGES.add(ContentLanguageDTO.of("North Ndebele", "nd"));
		LANGUAGES.add(ContentLanguageDTO.of("Nepali", "ne"));
		LANGUAGES.add(ContentLanguageDTO.of("Ndonga", "ng"));
		LANGUAGES.add(ContentLanguageDTO.of("Norwegian Nynorsk", "nn"));
		LANGUAGES.add(ContentLanguageDTO.of("Norwegian", "no"));
		LANGUAGES.add(ContentLanguageDTO.of("Nuosu", "ii"));
		LANGUAGES.add(ContentLanguageDTO.of("South Ndebele", "nr"));
		LANGUAGES.add(ContentLanguageDTO.of("Occitan", "oc"));
		LANGUAGES.add(ContentLanguageDTO.of("Ojibwe", "oj"));
		LANGUAGES.add(ContentLanguageDTO.of("Ojibwa", "oj"));
		LANGUAGES.add(ContentLanguageDTO.of("Old Church Slavonic", "cu"));
		LANGUAGES.add(ContentLanguageDTO.of("Church Slavic", "cu"));
		LANGUAGES.add(ContentLanguageDTO.of("Church Slavonic", "cu"));
		LANGUAGES.add(ContentLanguageDTO.of("Old Bulgarian", "cu"));
		LANGUAGES.add(ContentLanguageDTO.of("Old Slavonic", "cu"));
		LANGUAGES.add(ContentLanguageDTO.of("Oromo", "om"));
		LANGUAGES.add(ContentLanguageDTO.of("Oriya", "or"));
		LANGUAGES.add(ContentLanguageDTO.of("Ossetian", "os"));
		LANGUAGES.add(ContentLanguageDTO.of("Ossetic", "os"));
		LANGUAGES.add(ContentLanguageDTO.of("Panjabi", "pa"));
		LANGUAGES.add(ContentLanguageDTO.of("Punjabi", "pa"));
		LANGUAGES.add(ContentLanguageDTO.of("Pāli", "pi"));
		LANGUAGES.add(ContentLanguageDTO.of("Persian", "fa"));
		LANGUAGES.add(ContentLanguageDTO.of("Polish", "pl"));
		LANGUAGES.add(ContentLanguageDTO.of("Pashto", "ps"));
		LANGUAGES.add(ContentLanguageDTO.of("Pushto", "ps"));
		LANGUAGES.add(ContentLanguageDTO.of("Portuguese", "pt"));
		LANGUAGES.add(ContentLanguageDTO.of("Quechua", "qu"));
		LANGUAGES.add(ContentLanguageDTO.of("Romansh", "rm"));
		LANGUAGES.add(ContentLanguageDTO.of("Kirundi", "rn"));
		LANGUAGES.add(ContentLanguageDTO.of("Romanian", "ro"));
		LANGUAGES.add(ContentLanguageDTO.of("Moldavian", "ro"));
		LANGUAGES.add(ContentLanguageDTO.of("Moldovan", "ro"));
		LANGUAGES.add(ContentLanguageDTO.of("Russian", "ru"));
		LANGUAGES.add(ContentLanguageDTO.of("Sanskrit", "sa"));
		LANGUAGES.add(ContentLanguageDTO.of("Sardinian", "sc"));
		LANGUAGES.add(ContentLanguageDTO.of("Sindhi", "sd"));
		LANGUAGES.add(ContentLanguageDTO.of("Northern Sami", "se"));
		LANGUAGES.add(ContentLanguageDTO.of("Samoan", "sm"));
		LANGUAGES.add(ContentLanguageDTO.of("Sango", "sg"));
		LANGUAGES.add(ContentLanguageDTO.of("Serbian", "sr"));
		LANGUAGES.add(ContentLanguageDTO.of("Scottish Gaelic", "gd"));
		LANGUAGES.add(ContentLanguageDTO.of("Gaelic", "gd"));
		LANGUAGES.add(ContentLanguageDTO.of("Shona", "sn"));
		LANGUAGES.add(ContentLanguageDTO.of("Sinhala", "si"));
		LANGUAGES.add(ContentLanguageDTO.of("Sinhalese", "si"));
		LANGUAGES.add(ContentLanguageDTO.of("Slovak", "sk"));
		LANGUAGES.add(ContentLanguageDTO.of(SLOVENE, "sl"));
		LANGUAGES.add(ContentLanguageDTO.of("Somali", "so"));
		LANGUAGES.add(ContentLanguageDTO.of("Southern Sotho", "st"));
		LANGUAGES.add(ContentLanguageDTO.of(SPANISH, "es"));
		LANGUAGES.add(ContentLanguageDTO.of("Sundanese", "su"));
		LANGUAGES.add(ContentLanguageDTO.of("Swahili", "sw"));
		LANGUAGES.add(ContentLanguageDTO.of("Swati", "ss"));
		LANGUAGES.add(ContentLanguageDTO.of("Swedish", "sv"));
		LANGUAGES.add(ContentLanguageDTO.of("Tamil", "ta"));
		LANGUAGES.add(ContentLanguageDTO.of("Telugu", "te"));
		LANGUAGES.add(ContentLanguageDTO.of("Tajik", "tg"));
		LANGUAGES.add(ContentLanguageDTO.of("Thai", "th"));
		LANGUAGES.add(ContentLanguageDTO.of("Tigrinya", "ti"));
		LANGUAGES.add(ContentLanguageDTO.of("Tibetan Standard", "bo"));
		LANGUAGES.add(ContentLanguageDTO.of("Tibetan, Central", "bo"));
		LANGUAGES.add(ContentLanguageDTO.of("Turkmen", "tk"));
		LANGUAGES.add(ContentLanguageDTO.of("Tagalog", "tl"));
		LANGUAGES.add(ContentLanguageDTO.of("Tswana", "tn"));
		LANGUAGES.add(ContentLanguageDTO.of("Tonga", "to"));
		LANGUAGES.add(ContentLanguageDTO.of("Turkish", "tr"));
		LANGUAGES.add(ContentLanguageDTO.of("Tsonga", "ts"));
		LANGUAGES.add(ContentLanguageDTO.of("Tatar", "tt"));
		LANGUAGES.add(ContentLanguageDTO.of("Twi", "tw"));
		LANGUAGES.add(ContentLanguageDTO.of("Tahitian", "ty"));
		LANGUAGES.add(ContentLanguageDTO.of("Uighur", "ug"));
		LANGUAGES.add(ContentLanguageDTO.of("Uyghur", "ug"));
		LANGUAGES.add(ContentLanguageDTO.of("Ukrainian", "uk"));
		LANGUAGES.add(ContentLanguageDTO.of("Urdu", "ur"));
		LANGUAGES.add(ContentLanguageDTO.of("Uzbek", "uz"));
		LANGUAGES.add(ContentLanguageDTO.of("Venda", "ve"));
		LANGUAGES.add(ContentLanguageDTO.of("Vietnamese", "vi"));
		LANGUAGES.add(ContentLanguageDTO.of("Volapük", "vo"));
		LANGUAGES.add(ContentLanguageDTO.of("Walloon", "wa"));
		LANGUAGES.add(ContentLanguageDTO.of("Welsh", "cy"));
		LANGUAGES.add(ContentLanguageDTO.of("Wolof", "wo"));
		LANGUAGES.add(ContentLanguageDTO.of("Western Frisian", "fy"));
		LANGUAGES.add(ContentLanguageDTO.of("Xhosa", "xh"));
		LANGUAGES.add(ContentLanguageDTO.of("Yiddish", "yi"));
		LANGUAGES.add(ContentLanguageDTO.of("Yoruba", "yo"));
		LANGUAGES.add(ContentLanguageDTO.of("Zhuang", "za"));
		LANGUAGES.add(ContentLanguageDTO.of("Chuang", "za"));

		NAME_INDEX = LANGUAGES.stream()
				.collect(Collectors.toMap(ContentLanguageDTO::getName, Function.identity()));

		NAME_INDEX.put("Castillian", NAME_INDEX.get(SPANISH));
		NAME_INDEX.put("Castilian", NAME_INDEX.get(SPANISH));
		NAME_INDEX.put("Slovenian", NAME_INDEX.get(SLOVENE));
	}

	public Optional<ContentLanguageDTO> getByName(String name) {
		if (StringUtils.isBlank(name)) {
			return Optional.empty();
		}

		if (NAME_INDEX.containsKey(name)) {
			return Optional.of(NAME_INDEX.get(name));
		}

		String nameWithoutBrackets = StringUtils.trim(name.replaceAll("\\(.*?\\) ?", ""));
		if (NAME_INDEX.containsKey(nameWithoutBrackets)) {
			return Optional.of(NAME_INDEX.get(nameWithoutBrackets));
		}

		List<String> words = createWordsList(name);

		if (!words.isEmpty()) {
			Optional<ContentLanguageDTO> contentLanguageDTOOptional = tryCreateFromWords(words);

			if (contentLanguageDTOOptional.isPresent()) {
				return contentLanguageDTOOptional;
			}
		}

		log.warn("Could not get language by name \"{}\"", name);
		return Optional.empty();
	}

	private static List<String> createWordsList(String wordListCandiate) {
		return Lists.newArrayList(wordListCandiate.split(" "))
				.stream()
				.filter(StringUtils::isNotBlank)
				.map(word -> word.replaceAll("[\\[\\]+{}()\\\\/-;,.]", ""))
				.collect(Collectors.toList());
	}

	private Optional<ContentLanguageDTO> tryCreateFromWords(List<String> words) {
		for (int i = 0; i < words.size(); i++) {
			String singleWord = words.get(i);
			if (NAME_INDEX.containsKey(singleWord)) {
				return Optional.of(NAME_INDEX.get(singleWord));
			}

			if (words.size() > i + 1) {
				String firstWords = singleWord + " " + words.get(i + 1);

				if (NAME_INDEX.containsKey(firstWords)) {
					return Optional.of(NAME_INDEX.get(firstWords));
				}
			}
		}

		return Optional.empty();
	}

}
