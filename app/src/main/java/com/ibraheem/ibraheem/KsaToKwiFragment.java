package com.ibraheem.ibraheem;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ibraheem.ibraheem.widget.AdsRecyclerViewAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class KsaToKwiFragment extends Fragment {


    @Bind(R.id.adsRecyclerView)
    RecyclerView adsRecyclerView;
    LinearLayoutManager linearLayoutManager;

    AdsRecyclerViewAdapter adsRecyclerViewAdapter;

    public KsaToKwiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ksa_to_kwi, container, false);
        ButterKnife.bind(this, view);

        linearLayoutManager = new LinearLayoutManager(getContext());
        adsRecyclerView.setLayoutManager(linearLayoutManager);

        adsRecyclerViewAdapter = new AdsRecyclerViewAdapter("ksa", getContext());
        adsRecyclerView.setAdapter(adsRecyclerViewAdapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}