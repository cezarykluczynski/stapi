package com.cezarykluczynski.stapi.etl.country.service;

import com.cezarykluczynski.stapi.etl.country.dto.CountryDTO;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import liquibase.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CountryDTOProvider {

	private static final String UNITED_KINGDOM_OF_GREAT_BRITAIN_AND_NORTHERN_IRELAND = "United Kingdom of Great Britain and Northern Ireland";
	private static final String UNITED_STATES_OF_AMERICA = "United States of America";
	private static final String US = "US";

	private static final List<CountryDTO> COUNTRIES = Lists.newArrayList();

	private static final Map<String, CountryDTO> NAME_INDEX;

	static {
		COUNTRIES.add(CountryDTO.of("Afghanistan", "AF"));
		COUNTRIES.add(CountryDTO.of("Åland Islands", "AX"));
		COUNTRIES.add(CountryDTO.of("Albania", "AL"));
		COUNTRIES.add(CountryDTO.of("Algeria", "DZ"));
		COUNTRIES.add(CountryDTO.of("American Samoa", "AS"));
		COUNTRIES.add(CountryDTO.of("Andorra", "AD"));
		COUNTRIES.add(CountryDTO.of("Angola", "AO"));
		COUNTRIES.add(CountryDTO.of("Anguilla", "AI"));
		COUNTRIES.add(CountryDTO.of("Antarctica", "AQ"));
		COUNTRIES.add(CountryDTO.of("Antigua and Barbuda", "AG"));
		COUNTRIES.add(CountryDTO.of("Argentina", "AR"));
		COUNTRIES.add(CountryDTO.of("Armenia", "AM"));
		COUNTRIES.add(CountryDTO.of("Aruba", "AW"));
		COUNTRIES.add(CountryDTO.of("Australia", "AU"));
		COUNTRIES.add(CountryDTO.of("Austria", "AT"));
		COUNTRIES.add(CountryDTO.of("Azerbaijan", "AZ"));
		COUNTRIES.add(CountryDTO.of("Bahamas", "BS"));
		COUNTRIES.add(CountryDTO.of("Bahrain", "BH"));
		COUNTRIES.add(CountryDTO.of("Bangladesh", "BD"));
		COUNTRIES.add(CountryDTO.of("Barbados", "BB"));
		COUNTRIES.add(CountryDTO.of("Belarus", "BY"));
		COUNTRIES.add(CountryDTO.of("Belgium", "BE"));
		COUNTRIES.add(CountryDTO.of("Belize", "BZ"));
		COUNTRIES.add(CountryDTO.of("Benin", "BJ"));
		COUNTRIES.add(CountryDTO.of("Bermuda", "BM"));
		COUNTRIES.add(CountryDTO.of("Bhutan", "BT"));
		COUNTRIES.add(CountryDTO.of("Bolivia (Plurinational State of)", "BO"));
		COUNTRIES.add(CountryDTO.of("Bonaire, Sint Eustatius and Saba", "BQ"));
		COUNTRIES.add(CountryDTO.of("Bosnia and Herzegovina", "BA"));
		COUNTRIES.add(CountryDTO.of("Botswana", "BW"));
		COUNTRIES.add(CountryDTO.of("Bouvet Island", "BV"));
		COUNTRIES.add(CountryDTO.of("Brazil", "BR"));
		COUNTRIES.add(CountryDTO.of("British Indian Ocean Territory", "IO"));
		COUNTRIES.add(CountryDTO.of("Brunei Darussalam", "BN"));
		COUNTRIES.add(CountryDTO.of("Bulgaria", "BG"));
		COUNTRIES.add(CountryDTO.of("Burkina Faso", "BF"));
		COUNTRIES.add(CountryDTO.of("Burundi", "BI"));
		COUNTRIES.add(CountryDTO.of("Cambodia", "KH"));
		COUNTRIES.add(CountryDTO.of("Cameroon", "CM"));
		COUNTRIES.add(CountryDTO.of("Canada", "CA"));
		COUNTRIES.add(CountryDTO.of("Cabo Verde", "CV"));
		COUNTRIES.add(CountryDTO.of("Cayman Islands", "KY"));
		COUNTRIES.add(CountryDTO.of("Central African Republic", "CF"));
		COUNTRIES.add(CountryDTO.of("Chad", "TD"));
		COUNTRIES.add(CountryDTO.of("Chile", "CL"));
		COUNTRIES.add(CountryDTO.of("China", "CN"));
		COUNTRIES.add(CountryDTO.of("Christmas Island", "CX"));
		COUNTRIES.add(CountryDTO.of("Cocos (Keeling) Islands", "CC"));
		COUNTRIES.add(CountryDTO.of("Colombia", "CO"));
		COUNTRIES.add(CountryDTO.of("Comoros", "KM"));
		COUNTRIES.add(CountryDTO.of("Congo", "CG"));
		COUNTRIES.add(CountryDTO.of("Congo (Democratic Republic of the)", "CD"));
		COUNTRIES.add(CountryDTO.of("Cook Islands", "CK"));
		COUNTRIES.add(CountryDTO.of("Costa Rica", "CR"));
		COUNTRIES.add(CountryDTO.of("Côte d'Ivoire", "CI"));
		COUNTRIES.add(CountryDTO.of("Croatia", "HR"));
		COUNTRIES.add(CountryDTO.of("Cuba", "CU"));
		COUNTRIES.add(CountryDTO.of("Curaçao", "CW"));
		COUNTRIES.add(CountryDTO.of("Cyprus", "CY"));
		COUNTRIES.add(CountryDTO.of("Czech Republic", "CZ"));
		COUNTRIES.add(CountryDTO.of("Denmark", "DK"));
		COUNTRIES.add(CountryDTO.of("Djibouti", "DJ"));
		COUNTRIES.add(CountryDTO.of("Dominica", "DM"));
		COUNTRIES.add(CountryDTO.of("Dominican Republic", "DO"));
		COUNTRIES.add(CountryDTO.of("Ecuador", "EC"));
		COUNTRIES.add(CountryDTO.of("Egypt", "EG"));
		COUNTRIES.add(CountryDTO.of("El Salvador", "SV"));
		COUNTRIES.add(CountryDTO.of("Equatorial Guinea", "GQ"));
		COUNTRIES.add(CountryDTO.of("Eritrea", "ER"));
		COUNTRIES.add(CountryDTO.of("Estonia", "EE"));
		COUNTRIES.add(CountryDTO.of("Ethiopia", "ET"));
		COUNTRIES.add(CountryDTO.of("Falkland Islands (Malvinas)", "FK"));
		COUNTRIES.add(CountryDTO.of("Faroe Islands", "FO"));
		COUNTRIES.add(CountryDTO.of("Fiji", "FJ"));
		COUNTRIES.add(CountryDTO.of("Finland", "FI"));
		COUNTRIES.add(CountryDTO.of("France", "FR"));
		COUNTRIES.add(CountryDTO.of("French Guiana", "GF"));
		COUNTRIES.add(CountryDTO.of("French Polynesia", "PF"));
		COUNTRIES.add(CountryDTO.of("French Southern Territories", "TF"));
		COUNTRIES.add(CountryDTO.of("Gabon", "GA"));
		COUNTRIES.add(CountryDTO.of("Gambia", "GM"));
		COUNTRIES.add(CountryDTO.of("Georgia", "GE"));
		COUNTRIES.add(CountryDTO.of("Germany", "DE"));
		COUNTRIES.add(CountryDTO.of("Ghana", "GH"));
		COUNTRIES.add(CountryDTO.of("Gibraltar", "GI"));
		COUNTRIES.add(CountryDTO.of("Greece", "GR"));
		COUNTRIES.add(CountryDTO.of("Greenland", "GL"));
		COUNTRIES.add(CountryDTO.of("Grenada", "GD"));
		COUNTRIES.add(CountryDTO.of("Guadeloupe", "GP"));
		COUNTRIES.add(CountryDTO.of("Guam", "GU"));
		COUNTRIES.add(CountryDTO.of("Guatemala", "GT"));
		COUNTRIES.add(CountryDTO.of("Guernsey", "GG"));
		COUNTRIES.add(CountryDTO.of("Guinea", "GN"));
		COUNTRIES.add(CountryDTO.of("Guinea-Bissau", "GW"));
		COUNTRIES.add(CountryDTO.of("Guyana", "GY"));
		COUNTRIES.add(CountryDTO.of("Haiti", "HT"));
		COUNTRIES.add(CountryDTO.of("Heard Island and McDonald Islands", "HM"));
		COUNTRIES.add(CountryDTO.of("Holy See", "VA"));
		COUNTRIES.add(CountryDTO.of("Honduras", "HN"));
		COUNTRIES.add(CountryDTO.of("Hong Kong", "HK"));
		COUNTRIES.add(CountryDTO.of("Hungary", "HU"));
		COUNTRIES.add(CountryDTO.of("Iceland", "IS"));
		COUNTRIES.add(CountryDTO.of("India", "IN"));
		COUNTRIES.add(CountryDTO.of("Indonesia", "ID"));
		COUNTRIES.add(CountryDTO.of("Iran (Islamic Republic of)", "IR"));
		COUNTRIES.add(CountryDTO.of("Iraq", "IQ"));
		COUNTRIES.add(CountryDTO.of("Ireland", "IE"));
		COUNTRIES.add(CountryDTO.of("Isle of Man", "IM"));
		COUNTRIES.add(CountryDTO.of("Israel", "IL"));
		COUNTRIES.add(CountryDTO.of("Italy", "IT"));
		COUNTRIES.add(CountryDTO.of("Jamaica", "JM"));
		COUNTRIES.add(CountryDTO.of("Japan", "JP"));
		COUNTRIES.add(CountryDTO.of("Jersey", "JE"));
		COUNTRIES.add(CountryDTO.of("Jordan", "JO"));
		COUNTRIES.add(CountryDTO.of("Kazakhstan", "KZ"));
		COUNTRIES.add(CountryDTO.of("Kenya", "KE"));
		COUNTRIES.add(CountryDTO.of("Kiribati", "KI"));
		COUNTRIES.add(CountryDTO.of("Korea (Democratic People's Republic of)", "KP"));
		COUNTRIES.add(CountryDTO.of("Korea (Republic of)", "KR"));
		COUNTRIES.add(CountryDTO.of("Kuwait", "KW"));
		COUNTRIES.add(CountryDTO.of("Kyrgyzstan", "KG"));
		COUNTRIES.add(CountryDTO.of("Lao People's Democratic Republic", "LA"));
		COUNTRIES.add(CountryDTO.of("Latvia", "LV"));
		COUNTRIES.add(CountryDTO.of("Lebanon", "LB"));
		COUNTRIES.add(CountryDTO.of("Lesotho", "LS"));
		COUNTRIES.add(CountryDTO.of("Liberia", "LR"));
		COUNTRIES.add(CountryDTO.of("Libya", "LY"));
		COUNTRIES.add(CountryDTO.of("Liechtenstein", "LI"));
		COUNTRIES.add(CountryDTO.of("Lithuania", "LT"));
		COUNTRIES.add(CountryDTO.of("Luxembourg", "LU"));
		COUNTRIES.add(CountryDTO.of("Macao", "MO"));
		COUNTRIES.add(CountryDTO.of("Macedonia (the former Yugoslav Republic of)", "MK"));
		COUNTRIES.add(CountryDTO.of("Madagascar", "MG"));
		COUNTRIES.add(CountryDTO.of("Malawi", "MW"));
		COUNTRIES.add(CountryDTO.of("Malaysia", "MY"));
		COUNTRIES.add(CountryDTO.of("Maldives", "MV"));
		COUNTRIES.add(CountryDTO.of("Mali", "ML"));
		COUNTRIES.add(CountryDTO.of("Malta", "MT"));
		COUNTRIES.add(CountryDTO.of("Marshall Islands", "MH"));
		COUNTRIES.add(CountryDTO.of("Martinique", "MQ"));
		COUNTRIES.add(CountryDTO.of("Mauritania", "MR"));
		COUNTRIES.add(CountryDTO.of("Mauritius", "MU"));
		COUNTRIES.add(CountryDTO.of("Mayotte", "YT"));
		COUNTRIES.add(CountryDTO.of("Mexico", "MX"));
		COUNTRIES.add(CountryDTO.of("Micronesia (Federated States of)", "FM"));
		COUNTRIES.add(CountryDTO.of("Moldova (Republic of)", "MD"));
		COUNTRIES.add(CountryDTO.of("Monaco", "MC"));
		COUNTRIES.add(CountryDTO.of("Mongolia", "MN"));
		COUNTRIES.add(CountryDTO.of("Montenegro", "ME"));
		COUNTRIES.add(CountryDTO.of("Montserrat", "MS"));
		COUNTRIES.add(CountryDTO.of("Morocco", "MA"));
		COUNTRIES.add(CountryDTO.of("Mozambique", "MZ"));
		COUNTRIES.add(CountryDTO.of("Myanmar", "MM"));
		COUNTRIES.add(CountryDTO.of("Namibia", "NA"));
		COUNTRIES.add(CountryDTO.of("Nauru", "NR"));
		COUNTRIES.add(CountryDTO.of("Nepal", "NP"));
		COUNTRIES.add(CountryDTO.of("Netherlands", "NL"));
		COUNTRIES.add(CountryDTO.of("New Caledonia", "NC"));
		COUNTRIES.add(CountryDTO.of("New Zealand", "NZ"));
		COUNTRIES.add(CountryDTO.of("Nicaragua", "NI"));
		COUNTRIES.add(CountryDTO.of("Niger", "NE"));
		COUNTRIES.add(CountryDTO.of("Nigeria", "NG"));
		COUNTRIES.add(CountryDTO.of("Niue", "NU"));
		COUNTRIES.add(CountryDTO.of("Norfolk Island", "NF"));
		COUNTRIES.add(CountryDTO.of("Northern Mariana Islands", "MP"));
		COUNTRIES.add(CountryDTO.of("Norway", "NO"));
		COUNTRIES.add(CountryDTO.of("Oman", "OM"));
		COUNTRIES.add(CountryDTO.of("Pakistan", "PK"));
		COUNTRIES.add(CountryDTO.of("Palau", "PW"));
		COUNTRIES.add(CountryDTO.of("Palestine, State of", "PS"));
		COUNTRIES.add(CountryDTO.of("Panama", "PA"));
		COUNTRIES.add(CountryDTO.of("Papua New Guinea", "PG"));
		COUNTRIES.add(CountryDTO.of("Paraguay", "PY"));
		COUNTRIES.add(CountryDTO.of("Peru", "PE"));
		COUNTRIES.add(CountryDTO.of("Philippines", "PH"));
		COUNTRIES.add(CountryDTO.of("Pitcairn", "PN"));
		COUNTRIES.add(CountryDTO.of("Poland", "PL"));
		COUNTRIES.add(CountryDTO.of("Portugal", "PT"));
		COUNTRIES.add(CountryDTO.of("Puerto Rico", "PR"));
		COUNTRIES.add(CountryDTO.of("Qatar", "QA"));
		COUNTRIES.add(CountryDTO.of("Réunion", "RE"));
		COUNTRIES.add(CountryDTO.of("Romania", "RO"));
		COUNTRIES.add(CountryDTO.of("Russian Federation", "RU"));
		COUNTRIES.add(CountryDTO.of("Rwanda", "RW"));
		COUNTRIES.add(CountryDTO.of("Saint Barthélemy", "BL"));
		COUNTRIES.add(CountryDTO.of("Saint Helena, Ascension and Tristan da Cunha", "SH"));
		COUNTRIES.add(CountryDTO.of("Saint Kitts and Nevis", "KN"));
		COUNTRIES.add(CountryDTO.of("Saint Lucia", "LC"));
		COUNTRIES.add(CountryDTO.of("Saint Martin (French part)", "MF"));
		COUNTRIES.add(CountryDTO.of("Saint Pierre and Miquelon", "PM"));
		COUNTRIES.add(CountryDTO.of("Saint Vincent and the Grenadines", "VC"));
		COUNTRIES.add(CountryDTO.of("Samoa", "WS"));
		COUNTRIES.add(CountryDTO.of("San Marino", "SM"));
		COUNTRIES.add(CountryDTO.of("Sao Tome and Principe", "ST"));
		COUNTRIES.add(CountryDTO.of("Saudi Arabia", "SA"));
		COUNTRIES.add(CountryDTO.of("Senegal", "SN"));
		COUNTRIES.add(CountryDTO.of("Serbia", "RS"));
		COUNTRIES.add(CountryDTO.of("Seychelles", "SC"));
		COUNTRIES.add(CountryDTO.of("Sierra Leone", "SL"));
		COUNTRIES.add(CountryDTO.of("Singapore", "SG"));
		COUNTRIES.add(CountryDTO.of("Sint Maarten (Dutch part)", "SX"));
		COUNTRIES.add(CountryDTO.of("Slovakia", "SK"));
		COUNTRIES.add(CountryDTO.of("Slovenia", "SI"));
		COUNTRIES.add(CountryDTO.of("Solomon Islands", "SB"));
		COUNTRIES.add(CountryDTO.of("Somalia", "SO"));
		COUNTRIES.add(CountryDTO.of("South Africa", "ZA"));
		COUNTRIES.add(CountryDTO.of("South Georgia and the South Sandwich Islands", "GS"));
		COUNTRIES.add(CountryDTO.of("South Sudan", "SS"));
		COUNTRIES.add(CountryDTO.of("Spain", "ES"));
		COUNTRIES.add(CountryDTO.of("Sri Lanka", "LK"));
		COUNTRIES.add(CountryDTO.of("Sudan", "SD"));
		COUNTRIES.add(CountryDTO.of("Suriname", "SR"));
		COUNTRIES.add(CountryDTO.of("Svalbard and Jan Mayen", "SJ"));
		COUNTRIES.add(CountryDTO.of("Swaziland", "SZ"));
		COUNTRIES.add(CountryDTO.of("Sweden", "SE"));
		COUNTRIES.add(CountryDTO.of("Switzerland", "CH"));
		COUNTRIES.add(CountryDTO.of("Syrian Arab Republic", "SY"));
		COUNTRIES.add(CountryDTO.of("Taiwan, Province of China", "TW"));
		COUNTRIES.add(CountryDTO.of("Tajikistan", "TJ"));
		COUNTRIES.add(CountryDTO.of("Tanzania, United Republic of", "TZ"));
		COUNTRIES.add(CountryDTO.of("Thailand", "TH"));
		COUNTRIES.add(CountryDTO.of("Timor-Leste", "TL"));
		COUNTRIES.add(CountryDTO.of("Togo", "TG"));
		COUNTRIES.add(CountryDTO.of("Tokelau", "TK"));
		COUNTRIES.add(CountryDTO.of("Tonga", "TO"));
		COUNTRIES.add(CountryDTO.of("Trinidad and Tobago", "TT"));
		COUNTRIES.add(CountryDTO.of("Tunisia", "TN"));
		COUNTRIES.add(CountryDTO.of("Turkey", "TR"));
		COUNTRIES.add(CountryDTO.of("Turkmenistan", "TM"));
		COUNTRIES.add(CountryDTO.of("Turks and Caicos Islands", "TC"));
		COUNTRIES.add(CountryDTO.of("Tuvalu", "TV"));
		COUNTRIES.add(CountryDTO.of("Uganda", "UG"));
		COUNTRIES.add(CountryDTO.of("Ukraine", "UA"));
		COUNTRIES.add(CountryDTO.of("United Arab Emirates", "AE"));
		COUNTRIES.add(CountryDTO.of(UNITED_KINGDOM_OF_GREAT_BRITAIN_AND_NORTHERN_IRELAND, "GB"));
		COUNTRIES.add(CountryDTO.of(UNITED_STATES_OF_AMERICA, US));
		COUNTRIES.add(CountryDTO.of("United States Minor Outlying Islands", "UM"));
		COUNTRIES.add(CountryDTO.of("Uruguay", "UY"));
		COUNTRIES.add(CountryDTO.of("Uzbekistan", "UZ"));
		COUNTRIES.add(CountryDTO.of("Vanuatu", "VU"));
		COUNTRIES.add(CountryDTO.of("Venezuela (Bolivarian Republic of)", "VE"));
		COUNTRIES.add(CountryDTO.of("Viet Nam", "VN"));
		COUNTRIES.add(CountryDTO.of("Virgin Islands (British)", "VG"));
		COUNTRIES.add(CountryDTO.of("Virgin Islands (U.S.)", "VI"));
		COUNTRIES.add(CountryDTO.of("Wallis and Futuna", "WF"));
		COUNTRIES.add(CountryDTO.of("Western Sahara", "EH"));
		COUNTRIES.add(CountryDTO.of("Yemen", "YE"));
		COUNTRIES.add(CountryDTO.of("Zambia", "ZM"));
		COUNTRIES.add(CountryDTO.of("Zimbabwe", "ZW"));

		NAME_INDEX = COUNTRIES.stream()
				.collect(Collectors.toMap(CountryDTO::getName, Function.identity()));

		NAME_INDEX.put("United States", NAME_INDEX.get(UNITED_STATES_OF_AMERICA));
		NAME_INDEX.put("USA", NAME_INDEX.get(UNITED_STATES_OF_AMERICA));
		NAME_INDEX.put(US, NAME_INDEX.get(UNITED_STATES_OF_AMERICA));
		NAME_INDEX.put("UK", NAME_INDEX.get(UNITED_KINGDOM_OF_GREAT_BRITAIN_AND_NORTHERN_IRELAND));
		NAME_INDEX.put("England", NAME_INDEX.get(UNITED_KINGDOM_OF_GREAT_BRITAIN_AND_NORTHERN_IRELAND));
		NAME_INDEX.put("United Kingdom", NAME_INDEX.get(UNITED_KINGDOM_OF_GREAT_BRITAIN_AND_NORTHERN_IRELAND));
	}

	public Set<CountryDTO> provideFromString(String searchString) {
		Set<CountryDTO> countryDTOSet = Sets.newHashSet();

		if (StringUtils.isEmpty(searchString)) {
			return countryDTOSet;
		}

		if (NAME_INDEX.containsKey(searchString)) {
			countryDTOSet.add(NAME_INDEX.get(searchString));
			return countryDTOSet;
		}

		NAME_INDEX.forEach((key, value) -> {
			if (searchString.contains(key)) {
				countryDTOSet.add(value);
			}
		});

		return countryDTOSet;
	}

}
