package com.example.change4change;

public class RewardsProductFeatureList {
    private int rewardImg;
    private String rewardTitle;
    private String rewardDescription;

    public RewardsProductFeatureList(int rewardImg, String rewardTitle, String rewardDescription) {
        this.rewardImg = rewardImg;
        this. rewardTitle = rewardTitle;
        this.rewardDescription = rewardDescription;
    }

    public int getRewardImg() { return rewardImg; }

    public String getRewardDescription() { return rewardDescription; }

    public String getRewardTitle() { return  rewardTitle; }
}
