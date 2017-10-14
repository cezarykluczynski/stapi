package com.cezarykluczynski.stapi.model.trading_card_deck.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard;
import com.cezarykluczynski.stapi.model.trading_card_deck.repository.TradingCardDeckRepository;
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet;
import com.google.common.collect.Sets;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import java.util.Set;

@Data
@Entity
@ToString(exclude = {"tradingCardSet", "tradingCards"})
@EqualsAndHashCode(exclude = {"tradingCardSet", "tradingCards"})
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@TrackedEntity(type = TrackedEntityType.REAL_WORLD_PRIMARY, repository = TradingCardDeckRepository.class, singularName = "trading card deck",
		pluralName = "trading card decks")
public class TradingCardDeck {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trading_card_deck_sequence_generator")
	@SequenceGenerator(name = "trading_card_deck_sequence_generator", sequenceName = "trading_card_deck_sequence", allocationSize = 1)
	private Long id;

	@Column(name = "u_id")
	private String uid;

	private String name;

	private String frequency;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "trading_card_set_id")
	private TradingCardSet tradingCardSet;

	@OneToMany(mappedBy = "tradingCardDeck", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	private Set<TradingCard> tradingCards = Sets.newHashSet();

}
