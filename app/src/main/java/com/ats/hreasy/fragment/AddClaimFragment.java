package com.ats.hreasy.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ats.hreasy.R;
import com.ats.hreasy.interfaces.AddClaimInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddClaimFragment extends Fragment implements AddClaimInterface {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_claim, container, false);
        return view;
    }

    @Override
    public void fragmentBecameVisible() {

    }
}
