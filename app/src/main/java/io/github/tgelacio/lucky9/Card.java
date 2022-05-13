package io.github.tgelacio.lucky9;

public class Card {

    private int cardValue;
    private String cardName;

    public Card (String cardName, int cardValue) {
        this.setCardName(cardName);
        this.setCardValue(cardValue);
    }

    public int getCardValue() {
        return cardValue;
    }

    public void setCardValue(int cardValue) {
        this.cardValue = cardValue;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

}
