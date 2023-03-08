package com.cezarykluczynski.stapi.model.trading_card_deck.entity;

import com.cezarykluczynski.stapi.model.common.annotation.TrackedEntity;
import com.cezarykluczynski.stapi.model.common.annotation.enums.TrackedEntityType;
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard;
import com.cezarykluczynski.stapi.model.trading_card_deck.repository.TradingCardDeckRepository;
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet;
import com.google.common.collect.Sets;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Data
@Entity
@ToString(exclude = {"tradingCardSet", "tradingCards"})
@EqualsAndHashCode(exclude = {"tradingCardSet", "tradingCards"})
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

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "trading_card_set_id")
	private TradingCardSet tradingCardSet;

	@OneToMany(mappedBy = "tradingCardDeck", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<TradingCard> tradingCards = Sets.newHashSet();

}
