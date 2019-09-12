package com.example.change4change;

public class RewardsProductAllList {
    private int cardLogo;
    private String cardTitle;
    private String cardTitleSecondary;

    public RewardsProductAllList(int cardLogo, String cardTitle, String cardTitleSecondary) {
        this.cardLogo = cardLogo;
        this.cardTitle = cardTitle;
        this.cardTitleSecondary = cardTitleSecondary;
    }

    public int getCardLogo() {
        return cardLogo;
    }

    public String getCardTitleSecondary() {
        return cardTitleSecondary;
    }

    public String getCardTitle() {
        return cardTitle;
    }

}
