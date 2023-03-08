package com.cezarykluczynski.stapi.model.trading_card_set.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.country.entity.Country;
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard;
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck;
import com.cezarykluczynski.stapi.model.trading_card_set.entity.enums.ProductionRunUnit;
import com.cezarykluczynski.stapi.model.trading_card_set.repository.TradingCardSetRepository;
import com.google.common.collect.Sets;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Data
@Entity
@ToString(exclude = {"manufacturers", "tradingCards", "tradingCardDecks", "countriesOfOrigin"})
@EqualsAndHashCode(exclude = {"manufacturers", "tradingCards", "tradingCardDecks", "countriesOfOrigin"})
@TrackedEntity(type = TrackedEntityType.REAL_WORLD_PRIMARY, repository = TradingCardSetRepository.class, singularName = "trading card set",
		pluralName = "trading card sets")
public class TradingCardSet {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trading_card_set_sequence_generator")
	@SequenceGenerator(name = "trading_card_set_sequence_generator", sequenceName = "trading_card_set_sequence", allocationSize = 1)
	private Long id;

	@Column(name = "u_id")
	private String uid;

	private String name;

	private Integer releaseYear;

	private Integer releaseMonth;

	private Integer releaseDay;

	private Integer cardsPerPack;

	private Integer packsPerBox;

	private Integer boxesPerCase;

	private Integer productionRun;

	@Enumerated(EnumType.STRING)
	private ProductionRunUnit productionRunUnit;

	private Double cardWidth;

	private Double cardHeight;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "trading_card_set_manufacturers",
			joinColumns = @JoinColumn(name = "trading_card_set_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "company_id", nullable = false, updatable = false))
	private Set<Company> manufacturers = Sets.newHashSet();

	@OneToMany(mappedBy = "tradingCardSet", fetch = FetchType.LAZY, targetEntity = TradingCard.class, cascade = CascadeType.ALL)
	private Set<TradingCard> tradingCards = Sets.newHashSet();

	@OneToMany(mappedBy = "tradingCardSet", fetch = FetchType.LAZY, targetEntity = TradingCardDeck.class, cascade = CascadeType.ALL)
	private Set<TradingCardDeck> tradingCardDecks = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "trading_card_sets_countries",
			joinColumns = @JoinColumn(name = "trading_card_set_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "country_id", nullable = false, updatable = false))
	private Set<Country> countriesOfOrigin = Sets.newHashSet();

}
