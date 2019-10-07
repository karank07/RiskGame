package ca.concordia.risk.model;

/**
 * This is a model class for Card having cardType and CountryCard as its member
 * variables
 * 
 * @author Pranal
 *
 */

public class Card {

	public static final String INFANTRY = "INFANTRY";
	public static final String CAVALRY = "Cavalry";
	public static final String ARTILLERY = "Artillery";

	private String cardType;
	private Country countryCard;

	/**
	 * For creating an object for card of type cardType and for country CountryCard
	 * 
	 * @param cardType
	 * @param countryCard
	 */
	public Card(String cardType, Country countryCard) {
		setCardName(cardType);
		setCountryCard(countryCard);
	}

	/**
	 * @return the cardType
	 */
	public String getCardType() {
		return cardType;
	}

	/**
	 * @param cardType the cardType to set
	 */
	public void setCardName(String cardType) {
		if (!cardType.equalsIgnoreCase(INFANTRY) || !cardType.equalsIgnoreCase(CAVALRY)
				|| !cardType.equalsIgnoreCase(ARTILLERY))
			throw new IllegalArgumentException("The card type doesnot exist");
		else
			this.cardType = cardType;
	}

	/**
	 * @return the countryCard
	 */
	public Country getCountryCard() {
		return countryCard;
	}

	/**
	 * @param countryCard the countryCard to set
	 */
	public void setCountryCard(Country countryCard) {
		this.countryCard = countryCard;
	}

}
