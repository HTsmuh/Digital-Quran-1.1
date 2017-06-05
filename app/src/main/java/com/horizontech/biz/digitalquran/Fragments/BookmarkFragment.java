package com.horizontech.biz.digitalquran.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.horizontech.biz.digitalquran.Adapter.BookmarkAdapter;
import com.horizontech.biz.digitalquran.Database.DbBackend;
import com.horizontech.biz.digitalquran.R;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookmarkFragment extends Fragment {

    ListView bookmark_para;
    ListView bookmark_sura;
    BookmarkAdapter listAdapter;
    DbBackend db;
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
        db=new DbBackend(getContext());
        String[] para_date=db.getBookmarkParaDate();
        String[] para_arabic=db.getBookmarkPara_arabic();
        String[] para_english=db.getBookmarkPara_english();
        String[] para_serial=db.getBookmarkPara_serial();
        List<String> para_no= db.getBookmarkPara_no();
        listAdapter = new BookmarkAdapter(getContext(),para_serial,para_arabic,para_english,para_no,para_date);
        listAdapter.notifyDataSetChanged();
        bookmark_para.setAdapter(listAdapter);
        bookmark_para.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        return myView;
    }

}
