package com.cezarykluczynski.stapi.etl.template.actor.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.cezarykluczynski.stapi.etl.template.actor.dto.LifeRangeDTO;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
public class VideoGamePerformerLifeRangeFixedValueProvider implements FixedValueProvider<String, LifeRangeDTO> {

	private static final String CHICAGO_ILLINOIS_USA = "Chicago, Illinois, USA";
	private static final String LOS_ANGELES_CALIFORNIA_USA = "Los Angeles, California, USA";
	private static final String SAN_FRANCISCO_CALIFORNIA_USA = "San Francisco, California, USA";
	private static final String TORONTO_ONTARIO_CANADA = "Toronto, Ontario, Canada";
	private static final String HOUSTON_TEXAS_USA = "Houston, Texas, USA";
	private static final String USA = "USA";
	private static final String THE_BRONX_NEW_YORK_USA = "The Bronx, New York, USA";
	private static final String NEW_YORK_NEW_YORK_USA = "New York, New York, USA";
	private static final String PHILADELPHIA_PENNSYLVANIA_USA = "Philadelphia, Pennsylvania, USA";
	private static final String ATLANTA_GEORGIA_USA = "Atlanta, Georgia, USA";

	private static final Map<String, LifeRangeDTO> TITLE_TO_LIFE_RANGE_MAP = Maps.newHashMap();

	static {
		TITLE_TO_LIFE_RANGE_MAP.put("Susan Allenback", LifeRangeDTO.of(LocalDate.of(1952, 7, 12), "Ft. Meade, Maryland, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("George Almond", LifeRangeDTO.of(LocalDate.of(1959, 11, 2), "Nova Scotia, Canada", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Dee Bradley Baker", LifeRangeDTO.of(LocalDate.of(1962, 8, 31), "Bloomington, Indiana, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Leigh-Allyn Baker", LifeRangeDTO.of(LocalDate.of(1972, 3, 13), "Murray, Kentucky, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Robert V. Barron", LifeRangeDTO.of(LocalDate.of(1932, 12, 26), "Charleston, West Virginia, USA",
				LocalDate.of(2000, 12, 1), "Salinas, California, USA"));
		TITLE_TO_LIFE_RANGE_MAP.put("Charles Bartlett", LifeRangeDTO.of(LocalDate.of(1941, 8, 18), "San Antonio, Texas, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Jeff Bennett", LifeRangeDTO.of(LocalDate.of(1962, 11, 2), HOUSTON_TEXAS_USA, null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Roy Blumenfeld", LifeRangeDTO.of(LocalDate.of(1944, 5, 11), THE_BRONX_NEW_YORK_USA, null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Jeffrey Nicholas Brown", LifeRangeDTO.of(LocalDate.of(1975, 8, 29), "Evanston, Chicago, Illinois, USA", null,
				null));
		TITLE_TO_LIFE_RANGE_MAP.put("Steve Bulen", LifeRangeDTO.of(LocalDate.of(1949, 8, 1), "California, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Corey Burton", LifeRangeDTO.of(LocalDate.of(1955, 8, 3), "West Los Angeles, California, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Jamie Calvert", LifeRangeDTO.of(LocalDate.of(1967, 11, 9), "South Pasadena, California, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Terrence 'T.C.' Carson", LifeRangeDTO.of(LocalDate.of(1958, 11, 19), CHICAGO_ILLINOIS_USA, null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Megan Cavanagh", LifeRangeDTO.of(LocalDate.of(1960, 11, 8), CHICAGO_ILLINOIS_USA, null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("June Chadwick", LifeRangeDTO.of(LocalDate.of(1951, 11, 30), "Warwickshire, England, UK", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Cam Clarke", LifeRangeDTO.of(LocalDate.of(1957, 11, 6), LOS_ANGELES_CALIFORNIA_USA, null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("James M. Connor", LifeRangeDTO.of(LocalDate.of(1960, 6, 16), "Omaha, Nebraska, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Chris Cote", LifeRangeDTO.of(LocalDate.of(1952, 11, 23), SAN_FRANCISCO_CALIFORNIA_USA, null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Jim Cummings", LifeRangeDTO.of(LocalDate.of(1952, 11, 3), "Youngstown, Ohio, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Leslie Danon", LifeRangeDTO.of(LocalDate.of(1966, 1, 5), LOS_ANGELES_CALIFORNIA_USA, null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Grey DeLisle", LifeRangeDTO.of(LocalDate.of(1973, 8, 24), "Fort Ord, California, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Barry Dennen", LifeRangeDTO.of(LocalDate.of(1938, 2, 22), CHICAGO_ILLINOIS_USA, null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Charles Dennis", LifeRangeDTO.of(LocalDate.of(1946, 12, 16), TORONTO_ONTARIO_CANADA, null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Michael Donovan", LifeRangeDTO.of(LocalDate.of(1953, 6, 12), "Vancouver, British Columbia, Canada", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("D.C. Douglas", LifeRangeDTO.of(LocalDate.of(1966, 2, 2), "Berkeley, California, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Richard Doyle", LifeRangeDTO.of(null, "Brockton, Massachusetts, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Michael Clarke Duncan", LifeRangeDTO.of(LocalDate.of(1957, 12, 10), CHICAGO_ILLINOIS_USA,
				LocalDate.of(2012, 9, 3), LOS_ANGELES_CALIFORNIA_USA));
		TITLE_TO_LIFE_RANGE_MAP.put("Murphy Dunne", LifeRangeDTO.of(LocalDate.of(1942, 6, 22), CHICAGO_ILLINOIS_USA, null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Greg Eagles", LifeRangeDTO.of(LocalDate.of(1970, 10, 28), "Milwaukee, Wisconsin, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Elena Fabri", LifeRangeDTO.of(LocalDate.of(1971, 12, 1), "Geneva, Switzerland", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Joshua Fardon", LifeRangeDTO.of(LocalDate.of(1965, 10, 23), "Kansas City, Missouri, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Tom Farrell", LifeRangeDTO.of(LocalDate.of(1950, 5, 3), "New York City, New York, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Ron Feinberg", LifeRangeDTO.of(LocalDate.of(1932, 10, 10), SAN_FRANCISCO_CALIFORNIA_USA,
				LocalDate.of(2005, 1, 29), LOS_ANGELES_CALIFORNIA_USA));
		TITLE_TO_LIFE_RANGE_MAP.put("Jodie Fisher", LifeRangeDTO.of(LocalDate.of(1960, 7, 17), "Dallas, Texas, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Crispin Freeman", LifeRangeDTO.of(LocalDate.of(1972, 2, 9), CHICAGO_ILLINOIS_USA, null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Eddie Frierson", LifeRangeDTO.of(LocalDate.of(1959, 11, 22), "Akron, Ohio, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Dan Gilvezan", LifeRangeDTO.of(LocalDate.of(1950, 10, 26), "St. Louis, Missouri, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Christopher Gorham", LifeRangeDTO.of(LocalDate.of(1974, 8, 14), "Fresno, California, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Michael Gough", LifeRangeDTO.of(LocalDate.of(1956, 12, 3), "San Jose, California, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Sean G. Griffin", LifeRangeDTO.of(LocalDate.of(1942, 10, 14), "Limerick, Ireland", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Jennifer Hale", LifeRangeDTO.of(LocalDate.of(1972, 1, 1), "Goose Bay, Newfoundland, Canada", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Blake Hammond", LifeRangeDTO.of(LocalDate.of(1963, 6, 21), "Coryell County, Texas, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Jess Harnell", LifeRangeDTO.of(LocalDate.of(1963, 12, 23), "Teaneck, New Jersey, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Tim Harrison", LifeRangeDTO.of(LocalDate.of(1971, 5, 13), "Barre, Vermont, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Melora Harte", LifeRangeDTO.of(LocalDate.of(1943, 10, 29), null, null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Carolyn Hennesy", LifeRangeDTO.of(LocalDate.of(1962, 6, 10), LOS_ANGELES_CALIFORNIA_USA, null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Ravil Isyanov", LifeRangeDTO.of(LocalDate.of(1962, 8, 20), "Voskresensk, Moscow Oblast, Russian SFSR, USSR",
				null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Roger Jackson", LifeRangeDTO.of(LocalDate.of(1958, 7, 13), ATLANTA_GEORGIA_USA, null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Nick Jameson", LifeRangeDTO.of(LocalDate.of(1950, 7, 10), "Columbia, Missouri, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Wes Johnson", LifeRangeDTO.of(LocalDate.of(1961, 6, 6), "Arlington, Virginia, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Tom Kane", LifeRangeDTO.of(LocalDate.of(1962, 4, 15), "Overland Park, Kansas, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Roger Kern", LifeRangeDTO.of(LocalDate.of(1948, 6, 22), "Watsonville, California, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Kathryn Klvana", LifeRangeDTO.of(null, USA, null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Steve Kramer", LifeRangeDTO.of(LocalDate.of(1950, 12, 24), "San Juan Capistrano, California, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Daamen J. Krall", LifeRangeDTO.of(LocalDate.of(1951, 10, 20), LOS_ANGELES_CALIFORNIA_USA, null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Joyce Kurtz", LifeRangeDTO.of(LocalDate.of(1958, 6, 25), "Cleveland, Ohio, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Maurice LaMarche", LifeRangeDTO.of(LocalDate.of(1958, 3, 30), TORONTO_ONTARIO_CANADA, null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Alexis (\"Lex\") Lang", LifeRangeDTO.of(LocalDate.of(1965, 11, 12), "Hollywood, California, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Rodger LaRue", LifeRangeDTO.of(LocalDate.of(1948, 7, 10), LOS_ANGELES_CALIFORNIA_USA, null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Amanda Winn Lee", LifeRangeDTO.of(LocalDate.of(1972, 11, 14), HOUSTON_TEXAS_USA, null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Allan Lewis", LifeRangeDTO.of(LocalDate.of(1929, 6, 17), NEW_YORK_NEW_YORK_USA, null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Janice Lynde", LifeRangeDTO.of(LocalDate.of(1948, 3, 28), HOUSTON_TEXAS_USA, null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Tress MacNeille", LifeRangeDTO.of(LocalDate.of(1951, 6, 20), CHICAGO_ILLINOIS_USA, null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Kerrigan Mahan", LifeRangeDTO.of(LocalDate.of(1955, 1, 27), "Encino, California, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Sunil Malhotra", LifeRangeDTO.of(LocalDate.of(1975, 10, 20), null, null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Dave Mallow", LifeRangeDTO.of(LocalDate.of(1948, 10, 19), "Park Ridge, Illinois, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Michael McConnohie", LifeRangeDTO.of(LocalDate.of(1951, 7, 23), "Mansfield, Ohio, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("David McGrath", LifeRangeDTO.of(LocalDate.of(1967, 2, 28), "Glendale, California, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Michael McKee", LifeRangeDTO.of(LocalDate.of(1971, 6, 16), "Oakland, California, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Scott Menville", LifeRangeDTO.of(LocalDate.of(1971, 2, 12), "Malibu, California, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Paul Mercier", LifeRangeDTO.of(LocalDate.of(1962, 7, 13), USA, null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Edie Mirman", LifeRangeDTO.of(LocalDate.of(1957, 7, 26), THE_BRONX_NEW_YORK_USA, null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Sean Owens", LifeRangeDTO.of(LocalDate.of(1974, 5, 31), "Reading, Pennsylvania, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Joseph Pilato", LifeRangeDTO.of(LocalDate.of(1949, 3, 16), "Boston, Massachusetts, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Simon Prescott", LifeRangeDTO.of(LocalDate.of(1936, 5, 26), "Brooklyn, New York, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Jamieson Price", LifeRangeDTO.of(LocalDate.of(1961, 4, 28), "West Palm Beach, Florida, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Phil Proctor", LifeRangeDTO.of(LocalDate.of(1940, 7, 28), "Goshen, Indiana, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Victor Raider-Wexler", LifeRangeDTO.of(LocalDate.of(1943, 12, 31), "Toledo, Ohio, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Peter Renaday", LifeRangeDTO.of(LocalDate.of(1935, 6, 9), "Louisiana, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Mike Reynolds", LifeRangeDTO.of(LocalDate.of(1929, 11, 21), null, null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Rino Romano", LifeRangeDTO.of(null, TORONTO_ONTARIO_CANADA, null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Neil Ross", LifeRangeDTO.of(LocalDate.of(1944, 12, 31), "London, England, UK", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Murray Rubinstein", LifeRangeDTO.of(LocalDate.of(1956, 5, 26), PHILADELPHIA_PENNSYLVANIA_USA, null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Rodney Saulsberry", LifeRangeDTO.of(LocalDate.of(1956, 7, 11), "Detroit, Michigan, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Michael Sorich", LifeRangeDTO.of(LocalDate.of(1958, 3, 23), "Burbank, California, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Patricia Skeriotis", LifeRangeDTO.of(LocalDate.of(1971, 4, 16), "Pittsburgh, Pennsylvania, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Guy Slater", LifeRangeDTO.of(LocalDate.of(1941, 12, 30), "Lahore, Pakistan", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Doug Stone", LifeRangeDTO.of(LocalDate.of(1950, 12, 27), TORONTO_ONTARIO_CANADA, null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Terrence Stone", LifeRangeDTO.of(LocalDate.of(1955, 3, 2), "Wicklow, Ireland", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Michael Eric Strickland", LifeRangeDTO.of(LocalDate.of(1968, 3, 24), null, null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Tara Strong", LifeRangeDTO.of(LocalDate.of(1973, 2, 12), TORONTO_ONTARIO_CANADA, null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Courtenay Taylor", LifeRangeDTO.of(LocalDate.of(1969, 7, 19), SAN_FRANCISCO_CALIFORNIA_USA, null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("John Vernon", LifeRangeDTO.of(LocalDate.of(1932, 2, 24), "Zehner, Saskatchewan, Canada",
				LocalDate.of(2005, 2, 1), LOS_ANGELES_CALIFORNIA_USA));
		TITLE_TO_LIFE_RANGE_MAP.put("Brian Vouglas", LifeRangeDTO.of(null, SAN_FRANCISCO_CALIFORNIA_USA, null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("B.J. Ward", LifeRangeDTO.of(LocalDate.of(1944, 9, 16), "Wilmington, Delaware, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Jim Ward", LifeRangeDTO.of(LocalDate.of(1959, 5, 19), NEW_YORK_NEW_YORK_USA, null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Andre Ware", LifeRangeDTO.of(null, "Kalamazoo, Michigan, USA", null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Gary Anthony Williams", LifeRangeDTO.of(LocalDate.of(1966, 3, 14), ATLANTA_GEORGIA_USA, null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Thomas F. Wilson", LifeRangeDTO.of(LocalDate.of(1959, 4, 15), PHILADELPHIA_PENNSYLVANIA_USA, null, null));
		TITLE_TO_LIFE_RANGE_MAP.put("Jeff Winkless", LifeRangeDTO.of(LocalDate.of(1941, 6, 2), "Springfield, Massachusetts, USA",
				LocalDate.of(2006, 6, 26), "Evanston, Illinois, USA"));
		TITLE_TO_LIFE_RANGE_MAP.put("Tom Wyner", LifeRangeDTO.of(LocalDate.of(1947, 6, 16), null, null, null));
	}

	@Override
	public FixedValueHolder<LifeRangeDTO> getSearchedValue(String key) {
		return FixedValueHolder.of(TITLE_TO_LIFE_RANGE_MAP.containsKey(key), TITLE_TO_LIFE_RANGE_MAP.get(key));
	}

}
