package com.horizontech.biz.digitalquran.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.horizontech.biz.digitalquran.Adapter.BookmarkAdapter;
import com.horizontech.biz.digitalquran.Adapter.ParaNameAdapter;
import com.horizontech.biz.digitalquran.Database.DbBackend;
import com.horizontech.biz.digitalquran.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookmarkFragment extends Fragment {

    ListView bookmark_para;
    ListView bookmark_sura;
    BookmarkAdapter listAdapter;

    public BookmarkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView=inflater.inflate(R.layout.fragment_bookmark, container, false);
        bookmark_para = (ListView)myView.findViewById(R.id.bookmark_para);
        bookmark_sura = (ListView)myView.findViewById(R.id.bookmark_sura);
        DbBackend db=new DbBackend(getContext());
        return myView;
    }

}
