package com.example.change4change;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CharityInfoFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_charity_info, null);

        // Data could be taken from database
        ArrayList<CharityEntry> charityEntries = new ArrayList<>();

        // Adding a new card
        charityEntries.add(new CharityEntry(
                R.drawable.cerebral_palsy,
                R.drawable.cerebral_palsy_content,
                "Cerebral Palsy Alliance Singapore",
                "CPAS",
                "Empowering persons with cerebral palsy or multiple disabilities to realize their full potential"
        ));

        charityEntries.add(new CharityEntry(
                R.drawable.singapore_association_for_the_deaf,
                R.drawable.singapore_association_for_the_deaf_content,
                "Singapore Association for the Deaf",
                "SAD",
                "Provides services: Counselling/Financial Aid/Employment Support/Learning Support/Speech Therapy/Hearing Test and Aids/Interpretations/Sign Language Courses for deaf or hard-of-hearing clients"
        ));

        charityEntries.add(new CharityEntry(
                R.drawable.thye_hua_kwan,
                R.drawable.thye_hua_kwan_content,
                "Thye Hua Kwan Moral Charities",
                "THKMC",
                "We help anyone  who needs help with full respect to their race, colour, creed, language, culture and religion"
        ));

        charityEntries.add(new CharityEntry(
                R.drawable.food_from_the_heart,
                R.drawable.food_from_the_heart_content,
                "Food from The Heart",
                "FFTH",
                "To reach out to the less-fortunate and brighten their lives by alleviating hunger through food distribution programme and bringing joy through the distribution of toys and birthday celebrations"
        ));


        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new CardRecyclerViewAdapter(charityEntries);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;

    }

}
