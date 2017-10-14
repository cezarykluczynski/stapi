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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.util.Set;

@Data
@Entity
@ToString(exclude = {"manufacturers", "tradingCards", "tradingCardDecks", "countriesOfOrigin"})
@EqualsAndHashCode(exclude = {"manufacturers", "tradingCards", "tradingCardDecks", "countriesOfOrigin"})
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
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
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Company> manufacturers = Sets.newHashSet();

	@OneToMany(mappedBy = "tradingCardSet", fetch = FetchType.LAZY, targetEntity = TradingCard.class, cascade = CascadeType.ALL)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<TradingCard> tradingCards = Sets.newHashSet();

	@OneToMany(mappedBy = "tradingCardSet", fetch = FetchType.LAZY, targetEntity = TradingCardDeck.class, cascade = CascadeType.ALL)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<TradingCardDeck> tradingCardDecks = Sets.newHashSet();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "trading_card_sets_countries",
			joinColumns = @JoinColumn(name = "trading_card_set_id", nullable = false, updatable = false),
			inverseJoinColumns = @JoinColumn(name = "country_id", nullable = false, updatable = false))
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<Country> countriesOfOrigin = Sets.newHashSet();

}
