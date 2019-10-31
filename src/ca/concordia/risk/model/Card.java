package ca.concordia.risk.model;

/**
 * This is a model class for Card having cardType and CountryCard as its member variables
 * @author rohan
 * @author pranal
 */

public class Card {

	public static final String INFANTRY = "Infantry";
	public static final String CAVALRY = "Cavalry";	
	public static final String ARTILLERY = "Artillery";
	
	private String cardType;
	
	/**
	 * For creating an object for card of type cardType 
	 * @param cardType
	 */
	public Card(String cardType)
	{
		setCardName(cardType);
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
		if(!cardType.equalsIgnoreCase(INFANTRY) && !cardType.equalsIgnoreCase(CAVALRY) && !cardType.equalsIgnoreCase(ARTILLERY))
			throw new IllegalArgumentException("The card type doesnot exist");
		else
		 this.cardType = cardType;
	}

	
	
	
	
	

}
