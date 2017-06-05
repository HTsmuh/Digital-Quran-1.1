package com.horizontech.biz.digitalquran.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.horizontech.biz.digitalquran.Adapter.BookmarkAdapter;
import com.horizontech.biz.digitalquran.Database.DbBackend;
import com.horizontech.biz.digitalquran.R;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookmarkFragment extends Fragment {

    public ListView bookmark_para;
    public ListView bookmark_sura;
    BookmarkAdapter listAdapter;
    DbBackend db;
    ViewGroup header;
    public  void setDynamicHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
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
        inflater = getActivity().getLayoutInflater();
        header = (ViewGroup)inflater.inflate(R.layout.custom_bookmark_header, bookmark_para , false);

        db=new DbBackend(getContext());
        String[] para_date=db.getBookmarkParaDate();
        String[] para_arabic=db.getBookmarkPara_arabic();
        String[] para_english=db.getBookmarkPara_english();
        String[] para_serial=db.getBookmarkPara_serial();
        List<String> para_no= db.getBookmarkPara_no();

        listAdapter = new BookmarkAdapter(getContext(),para_serial,para_arabic,para_english,para_no,para_date);
        listAdapter.notifyDataSetChanged();

        bookmark_sura.setAdapter(listAdapter);
        bookmark_para.setAdapter(listAdapter);
        setDynamicHeight(bookmark_para);
        setDynamicHeight(bookmark_sura);
        listAdapter.notifyDataSetChanged();
        bookmark_para.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        return myView;
    }

}
