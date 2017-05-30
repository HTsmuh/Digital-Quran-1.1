package com.horizontech.biz.digitalquran.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.horizontech.biz.digitalquran.Adapter.SurahNameAdapter;
import com.horizontech.biz.digitalquran.Database.DbBackend;
import com.horizontech.biz.digitalquran.SurahText;
import com.horizontech.biz.digitalquran.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SurahFragment extends Fragment {
    ListView itemList;
    SurahNameAdapter listAdapter;
    int[] typeimage={R.drawable.makki,R.drawable.madni,R.drawable.madni,R.drawable.madni,R.drawable.madni,R.drawable.makki,R.drawable.makki,R.drawable.madni,R.drawable.madni,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.madni,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.madni,R.drawable.makki,R.drawable.madni,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.madni,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.madni,R.drawable.madni,R.drawable.madni,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.madni,R.drawable.makki,R.drawable.madni,R.drawable.madni,R.drawable.madni,R.drawable.madni,R.drawable.madni,R.drawable.madni,R.drawable.madni,R.drawable.madni,R.drawable.madni,R.drawable.madni,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.madni,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.madni,R.drawable.madni,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.madni,R.drawable.makki,R.drawable.makki,R.drawable.makki,R.drawable.makki};
    public SurahFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView=inflater.inflate(R.layout.fragment_surah, container, false);

        itemList = (ListView)myView.findViewById(R.id.listView);
        DbBackend dbFragment=new DbBackend(getContext());

        String[] surah_numbers = dbFragment.surah_No();
        String[] surah_names = dbFragment.surah_arabic();
        String[] surah_roman_names = dbFragment.surah_roman();
        String[] surah_verse_no = dbFragment.Surah_Verses();

        listAdapter = new SurahNameAdapter(getContext(),surah_numbers,surah_names,surah_roman_names,surah_verse_no,typeimage);

        itemList.setAdapter(listAdapter);
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), SurahText.class);
                int index=position+1;
                intent.putExtra("Surah_Number", index);
                startActivity(intent);
            }
        });
        return myView;

    }

}
