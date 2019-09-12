package com.example.change4change;

public class CharityEntry {
    private String cardTitle;
    private String cardTitleSecondary;
    private String cardContent;
    private int charityLogo;
    private int charityPicture;

    public CharityEntry(int ceCharityLogo, int ceCharityPicture, String ceCardTitle, String ceCardTitleSecondary,String ceCardContent) {
        cardTitle = ceCardTitle;
        cardTitleSecondary = ceCardTitleSecondary;
        cardContent = ceCardContent;
        charityLogo = ceCharityLogo;
        charityPicture = ceCharityPicture;
    }

    public String getCardTitle() {
        return cardTitle;
    }

    public String getCardTitleSecondary() {
        return cardTitleSecondary;
    }

    public String getCardContent() {
        return cardContent;
    }

    public int getCharityLogo() {
        return charityLogo;
    }

    public int getCharityPicture() {
        return charityPicture;
    }
}
