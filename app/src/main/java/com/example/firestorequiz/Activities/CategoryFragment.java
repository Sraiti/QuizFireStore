package com.example.firestorequiz.Activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firestorequiz.Adapters.CategoryAdapter;
import com.example.firestorequiz.R;

import java.util.List;

public class CategoryFragment extends Fragment {

    // List of Native ads and MenuItems that populate the RecyclerView.
    private List<Object> mRecyclerViewItems;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);

        MainActivity activity = (MainActivity) getActivity();
        mRecyclerViewItems = activity.getRecyclerViewItems();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_category, container, false);
        RecyclerView mRecyclerView = rootView.findViewById(R.id.recycler_view);

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView.
        mRecyclerView.setHasFixedSize(true);

        // Specify a linear layout manager.
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        // Specify an adapter.
        RecyclerView.Adapter<RecyclerView.ViewHolder> adapter = new CategoryAdapter(getActivity(), mRecyclerViewItems);
        mRecyclerView.setAdapter(adapter);

        return rootView;
    }


}
