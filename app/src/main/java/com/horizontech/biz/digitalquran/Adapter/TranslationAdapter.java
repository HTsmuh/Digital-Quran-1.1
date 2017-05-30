package com.horizontech.biz.digitalquran.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.horizontech.biz.digitalquran.Database.DbBackend;
import com.horizontech.biz.digitalquran.R;

/**
 * Created by Syed Minhaj ul Hasan on 5/6/2017.
 */

public class TranslationAdapter extends ArrayAdapter<String> {
    public Context context;
    public String[] ayat_translation_array;
    public String[] ayat_arabic_array;
    public Typeface tf;
    public TextView arabic;
    public TextView translation;
    public DbBackend db;

    public TranslationAdapter(Context context, String[] ayat_translation, String[] ayat_arabic) {
        super(context, R.layout.translation_single_row,R.id.translationArabicText,ayat_arabic);
        this.context=context;
        this.ayat_translation_array=ayat_translation;
        this.ayat_arabic_array=ayat_arabic;
        db=new DbBackend(this.getContext());
        tf = Typeface.createFromAsset(context.getAssets(), "fonts/"+db.getScript()+".ttf");

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.translation_single_row,parent,false);
        translation= (TextView) row.findViewById(R.id.translationUrduText);
        arabic= (TextView) row.findViewById(R.id.translationArabicText);if (db.getSize().equals("Small")){
            arabic.setTextSize(15);
            translation.setTextSize(12);
        }else if (db.getSize().equals("Normal")){
            arabic.setTextSize(20);
            translation.setTextSize(17);
        }else if (db.getSize().equals("Large")){
            arabic.setTextSize(25);
            translation.setTextSize(22);
        }else if (db.getSize().equals("Extra Large")){
            arabic.setTextSize(30);
            translation.setTextSize(27);
        }
        arabic.setText(ayat_arabic_array[position]);
        translation.setText(ayat_translation_array[position]);
        arabic.setTypeface(tf);
        return row;
    }
}