package com.example.change4change;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CharityRewardsFragment  extends Fragment {
    private RecyclerView recyclerViewFeatured;
    private RecyclerView recyclerViewAll;
    private RecyclerView.Adapter adapterFeatured;
    private RecyclerView.Adapter adapterAll;
    private RecyclerView.LayoutManager layoutManagerFeatured;
    private RecyclerView.LayoutManager layoutManagerAll;

    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_charity_rewards, null);

        ArrayList<RewardsProductFeatureList> rewardsProductFeatureLists = new ArrayList<>();
        ArrayList<RewardsProductAllList> rewardsProductAllLists = new ArrayList<>();

        // Adding a new card

        rewardsProductFeatureLists.add(new RewardsProductFeatureList(
                R.drawable.thye_hua_kwan,
                "asdf \nLevis Jeans 50% off",
                "100 pts"
        ));
        rewardsProductFeatureLists.add(new RewardsProductFeatureList(
                R.drawable.thye_hua_kwan,
                "asdf2 \nLevis Jeans 50% off",
                "100 pts"
        ));

        rewardsProductFeatureLists.add(new RewardsProductFeatureList(
                R.drawable.thye_hua_kwan,
                "Levis Jeans \nLevis Jeans 50% off",
                "100 pts"
        ));

        rewardsProductFeatureLists.add(new RewardsProductFeatureList(
                R.drawable.thye_hua_kwan,
                "Raybans \nRayban 50% off",
                "100 pts"
        ));

        rewardsProductFeatureLists.add(new RewardsProductFeatureList(
                R.drawable.thye_hua_kwan,
                "Rimowa \nLuggages 50% off",
                "100 pts"
        ));

        rewardsProductAllLists.add(new RewardsProductAllList(
                R.drawable.levis_logo,
                "Levis Jeans \nJeans 50% off",
                "100 pts"
        ));

        rewardsProductAllLists.add(new RewardsProductAllList(
                R.drawable.rayban_logo,
                "Raybans \nSpecs 50% off",
                "100 pts"
        ));

        rewardsProductAllLists.add(new RewardsProductAllList(
                R.drawable.superdry_logo,
                "Superdry \nShirts 50% off",
                "100 pts"
        ));

        rewardsProductAllLists.add(new RewardsProductAllList(
                R.drawable.superdry_logo,
                "asdf \nLuggages 50% off",
                "100 pts"
        ));

        rewardsProductAllLists.add(new RewardsProductAllList(
                R.drawable.superdry_logo,
                "asdf2 \nLuggages 50% off",
                "100 pts"
        ));

        rewardsProductAllLists.add(new RewardsProductAllList(
                R.drawable.levis_logo,
                "Levis Jeans \nJeans 50% off",
                "100 pts"
        ));

        rewardsProductAllLists.add(new RewardsProductAllList(
                R.drawable.rayban_logo,
                "Raybans \nSpecs 50% off",
                "100 pts"
        ));



        recyclerViewFeatured = view.findViewById(R.id.recycler_view_featured);
        recyclerViewFeatured.setHasFixedSize(true);
        layoutManagerFeatured = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        adapterFeatured = new CardRecyclerViewAdapterRewardsFeature(rewardsProductFeatureLists);

        recyclerViewFeatured.setLayoutManager(layoutManagerFeatured);
        recyclerViewFeatured.setAdapter(adapterFeatured);

        recyclerViewAll = view.findViewById(R.id.recycler_view_all);
        recyclerViewAll.setHasFixedSize(true);
        layoutManagerAll = new LinearLayoutManager(getActivity());
        adapterAll = new CardRecyclerViewAdapterAllRewards(rewardsProductAllLists);

        recyclerViewAll.setLayoutManager(layoutManagerAll);
        recyclerViewAll.setAdapter(adapterAll);


        return view;

    }
}
