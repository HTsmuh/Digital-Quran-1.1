package com.horizontech.biz.digitalquran.Fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.horizontech.biz.digitalquran.Database.DbBackend;
import com.horizontech.biz.digitalquran.ParaText;
import com.horizontech.biz.digitalquran.R;
import com.horizontech.biz.digitalquran.SurahText;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookmarkFragment extends Fragment {

    ListView bookmark_para;
    ListView bookmark_sura;
    TextView no_bookmark;
    BookmarkParaAdapter ParaAdapter;
    BookmarkSurahAdapter SurahAdapter;
    DbBackend db;
    String[] para_date;
    String[] para_arabic;
    String[] para_english;
    String[] para_serial;
    String[] sura_date;
    String[] sura_arabic;
    String[] sura_english;
    String[] sura_serial;
    List<String> para_no;
    List<String> sura_no;
    ViewGroup para_header;
    ViewGroup surah_header;
    public void checkValidation(){
        if (Arrays.toString(para_serial).equals("[]")&&Arrays.toString(sura_serial).equals("[]")){
            para_header.findViewById(R.id.para_header).setVisibility(View.GONE);
            surah_header.findViewById(R.id.surah_header).setVisibility(View.GONE);
            no_bookmark.setVisibility(View.VISIBLE);
        }else if (Arrays.toString(para_serial).equals("[]")){
            para_header.findViewById(R.id.para_header).setVisibility(View.GONE);
        }else if (Arrays.toString(sura_serial).equals("[]")){
            surah_header.findViewById(R.id.surah_header).setVisibility(View.GONE);
        }
    }

    public void refreshFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public  void setDynamicHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
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
        View myView = inflater.inflate(R.layout.fragment_bookmark, container, false);
        bookmark_para = (ListView) myView.findViewById(R.id.bookmark_para);
        bookmark_sura = (ListView) myView.findViewById(R.id.bookmark_sura);
        no_bookmark = (TextView) myView.findViewById(R.id.no_bookmark);
        inflater = getActivity().getLayoutInflater();
        para_header = (ViewGroup) inflater.inflate(R.layout.custom_bookmark_para_header, bookmark_para, false);
        surah_header = (ViewGroup) inflater.inflate(R.layout.custom_bookmark_surah_header, bookmark_sura, false);

        db = new DbBackend(getContext());
        para_date = db.getBookmarkParaDate();
        para_arabic = db.getBookmarkPara_arabic();
        para_english = db.getBookmarkPara_english();
        para_serial = db.getBookmarkPara_serial();
        para_no = db.getBookmarkPara_no();

        sura_date = db.getBookmarksuraDate();
        sura_arabic = db.getBookmarksura_arabic();
        sura_english = db.getBookmarksura_english();
        sura_serial = db.getBookmarksura_serial();
        sura_no = db.getBookmarksura_no();

        SurahAdapter = new BookmarkSurahAdapter(getContext(), sura_serial, sura_arabic, sura_english, sura_no, sura_date);
        ParaAdapter = new BookmarkParaAdapter(getContext(), para_serial, para_arabic, para_english, para_no, para_date);

        bookmark_para.addHeaderView(para_header, null, false);
        bookmark_sura.addHeaderView(surah_header, null, false);

        bookmark_sura.setAdapter(SurahAdapter);
        bookmark_para.setAdapter(ParaAdapter);

        checkValidation();
        setDynamicHeight(bookmark_para);
        setDynamicHeight(bookmark_sura);
        bookmark_para.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int pos=position-1;
                int index= Integer.parseInt(para_no.get(pos));
                Intent intent = new Intent(getActivity(), ParaText.class);
                intent.putExtra("BookmarkFragment", index);
                startActivity(intent);

            }
        });
        bookmark_sura.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int pos=position-1;
                int index= Integer.parseInt(sura_no.get(pos));
                Intent intent = new Intent(getActivity(), SurahText.class);
                intent.putExtra("BookmarkFragment", index);
                startActivity(intent);
            }
        });
        return myView;
    }
   // Adapter class
    private class BookmarkParaAdapter extends ArrayAdapter<String> {
        public Context context;
        private String[] serial_no_array;
        private String[] bookmark_arabic_array;
        private String[] bookmark_english_array;
        private String[] bookmark_verse_array;
        private String[] bookmark_date_array;
        private DbBackend db;
        private BookmarkParaAdapter adapter;
        private List<String> para_items =null;
        private Typeface tf;

        BookmarkParaAdapter(Context context, String[] serial_no, String[] bookamrk_arabic, String[] bookamrk_english, List<String> para_items, String[] bookamrk_date) {
            super(context, R.layout.custom_bookmark,R.id.bookmark_arabic, para_items);
            this.context=context;
            this.serial_no_array=serial_no;
            this.bookmark_arabic_array =bookamrk_arabic;
            this.bookmark_english_array =bookamrk_english;
            //this.bookmark_verse_array =bookamrk_verse;
            this.bookmark_date_array =bookamrk_date;
            this.para_items = para_items;
            this.adapter=this;
            tf = Typeface.createFromAsset(context.getAssets(), "fonts/pdms.ttf");
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=inflater.inflate(R.layout.custom_bookmark,parent,false);
            db=new DbBackend(getContext());
            TextView bookmark_serial= (TextView) row.findViewById(R.id.bookmark_serial);
            TextView bookmark_arabic= (TextView) row.findViewById(R.id.bookmark_arabic);
            TextView bookmark_english= (TextView) row.findViewById(R.id.bookmark_english);
            //TextView bookmark_verse= (TextView) row.findViewById(R.id.bookmark_aya);
            TextView bookmark_date= (TextView) row.findViewById(R.id.bookmark_date);
            ImageView bookmark_image= (ImageView) row.findViewById(R.id.bookmark_remove);
           bookmark_serial.setText(serial_no_array[position]);
           //bookmark_serial.setText(para_items.get(position));
            bookmark_arabic.setText(bookmark_arabic_array[position]);
            bookmark_english.setText(bookmark_english_array[position]);
            //String verse;
            String bookmark;
            //verse = "Verse Number : ";
            bookmark = "Bookmarked ";
            //bookmark_verse.setText(String.format("%s%s", verse, bookmark_verse_array[position]));
            bookmark_date.setText(String.format("%s%s", bookmark, bookmark_date_array[position]));
            bookmark_image.setImageResource(R.drawable.bookmarkremove);
            bookmark_image.setTag(position);
            bookmark_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    para_items.remove(position);
                    db.deleteBookmarkPara(position);
                        //Fragment fragment = null;
                        //fragment = new BookmarkFragment();
                        //replaceFragment(fragment);
                        adapter.notifyDataSetChanged();
                    setDynamicHeight(bookmark_para);
                    Toast.makeText(context, "Bookmark Removed", Toast.LENGTH_SHORT).show();
                }
            });
            bookmark_arabic.setTypeface(tf);
            return row;
        }
    }
    private class BookmarkSurahAdapter extends ArrayAdapter<String> {
        public Context context;
        private String[] serial_no_array;
        private String[] bookmark_arabic_array;
        private String[] bookmark_english_array;
        private String[] bookmark_verse_array;
        private String[] bookmark_date_array;
        private DbBackend db;
        private BookmarkSurahAdapter adapter;
        private List<String> surah_items =null;
        private Typeface tf;

        BookmarkSurahAdapter(Context context, String[] serial_no, String[] bookamrk_arabic, String[] bookamrk_english, List<String> surah_items, String[] bookamrk_date) {
            super(context, R.layout.custom_bookmark,R.id.bookmark_arabic, surah_items);
            this.context=context;
            this.serial_no_array=serial_no;
            this.bookmark_arabic_array =bookamrk_arabic;
            this.bookmark_english_array =bookamrk_english;
            //this.bookmark_verse_array =bookamrk_verse;
            this.bookmark_date_array =bookamrk_date;
            this.surah_items = surah_items;
            this.adapter=this;
            tf = Typeface.createFromAsset(context.getAssets(), "fonts/pdms.ttf");
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=inflater.inflate(R.layout.custom_bookmark,parent,false);
            db=new DbBackend(getContext());
            TextView bookmark_serial= (TextView) row.findViewById(R.id.bookmark_serial);
            TextView bookmark_arabic= (TextView) row.findViewById(R.id.bookmark_arabic);
            TextView bookmark_english= (TextView) row.findViewById(R.id.bookmark_english);
            //TextView bookmark_verse= (TextView) row.findViewById(R.id.bookmark_aya);
            TextView bookmark_date= (TextView) row.findViewById(R.id.bookmark_date);
            ImageView bookmark_image= (ImageView) row.findViewById(R.id.bookmark_remove);
            bookmark_serial.setText(serial_no_array[position]);
            bookmark_arabic.setText(bookmark_arabic_array[position]);
            bookmark_english.setText(bookmark_english_array[position]);
            //String verse;
            String bookmark;
            //verse = "Verse Number : ";
            bookmark = "Bookmarked ";
            //bookmark_verse.setText(String.format("%s%s", verse, bookmark_verse_array[position]));
            bookmark_date.setText(String.format("%s%s", bookmark, bookmark_date_array[position]));
            bookmark_image.setImageResource(R.drawable.bookmarkremove);
            bookmark_image.setTag(position);
            bookmark_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    surah_items.remove(position);
                    db.deleteBookmarkSurah(position);
                    adapter.notifyDataSetChanged();
                    setDynamicHeight(bookmark_sura);
                    Toast.makeText(context, "Bookmark Removed", Toast.LENGTH_SHORT).show();
                }
            });
            bookmark_arabic.setTypeface(tf);
            return row;
        }
    }

}
