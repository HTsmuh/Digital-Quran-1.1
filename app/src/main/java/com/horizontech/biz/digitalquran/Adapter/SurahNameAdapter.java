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
 * Created by HP on 5/3/2017.
 */

public class SurahNameAdapter extends ArrayAdapter<String> {
    public Context context;
    public String[] surah_no_array;
    public String[] surah_arabic_array;
    public String[] surah_roman_array;
    public String[] surah_verse_array;
    public int[] surah_type_images;
    String abc="Total Verses : ";
    Typeface tf;

    public SurahNameAdapter(Context context, String[] surah_no, String[] surah_arabic, String[] surah_roman,String[] surah_verse, int[] surah_type) {
        super(context, R.layout.activity_single_row,R.id.translationUrduText,surah_arabic);
        this.context=context;
        this.surah_no_array=surah_no;
        this.surah_arabic_array=surah_arabic;
        this.surah_roman_array=surah_roman;
        this.surah_verse_array=surah_verse;
        this.surah_type_images=surah_type;

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
        arabic.setText(surah_arabic_array[position]);
        roman.setText(surah_roman_array[position]);
        verse.setText(String.format("%s%s", abc, surah_verse_array[position]));
        number.setText(surah_no_array[position]);
        imagetype.setImageResource(surah_type_images[position]);
        arabic.setTypeface(tf);
        return row;
    }
}
