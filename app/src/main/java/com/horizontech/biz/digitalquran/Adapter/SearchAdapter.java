package com.horizontech.biz.digitalquran.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.horizontech.biz.digitalquran.R;

import java.util.Locale;

/**
 * Created by user on 17/08/2017.
 */

public class SearchAdapter extends ArrayAdapter<String> {
    public Context context;
    public String[] searchText_array;
    public String[] searchSuraNo_array;
    public String[] searchSuraName_array;
    public String[] searchAyaNo_array;
    private String mSearchkeyword;

    public SearchAdapter(Context context, String[] search_text,String[] search_SuraName,String[] search_SuraNo,String[] search_AyaNo, String keyword) {
        super(context, R.layout.custom_search_single_row,R.id.searchText, search_text);
        this.context=context;
        this.searchText_array = search_text;
        this.searchSuraNo_array = search_SuraNo;
        this.searchSuraName_array = search_SuraName;
        this.searchAyaNo_array = search_AyaNo;
        mSearchkeyword=keyword;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.custom_search_single_row,parent,false);
        TextView searchText= (TextView) row.findViewById(R.id.searchText);
        TextView searchSurahName= (TextView) row.findViewById(R.id.srchSuraName);
        TextView searchSurahNo= (TextView) row.findViewById(R.id.srchSuraNo);
        TextView searchAyatNo= (TextView) row.findViewById(R.id.srchAyaNo);

        // highlight search text Start
        String fullText = getItem(position);
        if (mSearchkeyword != null && !mSearchkeyword.isEmpty()) {
            int startPos = fullText.toLowerCase(Locale.US).indexOf(mSearchkeyword.toLowerCase(Locale.US));
            int endPos = startPos + mSearchkeyword.length();

            if (startPos != -1) {
                Spannable spannable = new SpannableString(fullText);
                ColorStateList blueColor = new ColorStateList(new int[][]{new int[]{}}, new int[]{context.getResources().getColor(R.color.colorPrimary)});
                TextAppearanceSpan highlightSpan = new TextAppearanceSpan(null, Typeface.BOLD, -1, blueColor, null);
                spannable.setSpan(highlightSpan, startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                searchText.setText(spannable);
            } else {
                searchText.setText(fullText);
            }
        } else {
            searchText.setText(fullText);
        }
        //highlight search text End
        searchSurahName.setText(searchSuraName_array[position]);
        searchSurahNo.setText("Surah No. "+searchSuraNo_array[position]);
        searchAyatNo.setText("Ayat No. "+searchAyaNo_array[position]);
        return row;
    }
}