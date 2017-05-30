package com.horizontech.biz.digitalquran.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.horizontech.biz.digitalquran.R;

/**
 * Created by HP on 5/5/2017.
 */

public class ParaNameAdapter extends ArrayAdapter<String> {
    public Context context;
    public String[] para_no_array;
    public String[] para_arabic_array;
    public String[] para_roman_array;
    public String[] para_verse_array;
    String abc="Total Verses : ";
    Typeface tf;

    public ParaNameAdapter(Context context, String[] para_no, String[] parah_arabic, String[] para_roman,String[] para_verse) {
        super(context, R.layout.activity_single_row,R.id.translationUrduText,parah_arabic);
        this.context=context;
        this.para_no_array=para_no;
        this.para_arabic_array=parah_arabic;
        this.para_roman_array=para_roman;
        this.para_verse_array=para_verse;
        tf = Typeface.createFromAsset(context.getAssets(), "fonts/pdms.ttf");

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.activity_single_row,parent,false);
        TextView arabic= (TextView) row.findViewById(R.id.translationUrduText);
        TextView roman= (TextView) row.findViewById(R.id.translationArabicText);
        TextView number= (TextView) row.findViewById(R.id.text3);
        TextView verse= (TextView) row.findViewById(R.id.text4);
        ImageView imagetype= (ImageView) row.findViewById(R.id.image1);
        arabic.setText(para_arabic_array[position]);
        roman.setText(para_roman_array[position]);
        verse.setText(String.format("%s%s", abc, para_verse_array[position]));
        number.setText(para_no_array[position]);
        imagetype.setImageResource(R.drawable.logo);
        arabic.setTypeface(tf);
        return row;
    }
}
