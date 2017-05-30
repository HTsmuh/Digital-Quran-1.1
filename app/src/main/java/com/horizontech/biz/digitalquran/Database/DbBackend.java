package com.horizontech.biz.digitalquran.Database;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

public class DbBackend extends DbObject {

    private String size;
    private String mode;
    private String script;
    public DbBackend(Context context) {
        super(context);
    }
//Surah names
    public String[] surah_arabic() {
        String query = "Select * from Surah_Names";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        ArrayList<String> surah_names_array = new ArrayList<String>();
        if (cursor.moveToFirst()) {
            do {
                String surah_name = cursor.getString(cursor.getColumnIndexOrThrow("names"));
                surah_names_array.add(surah_name);
            } while (cursor.moveToNext());
        }
        cursor.close();
        String[] surah_arabic_names = new String[surah_names_array.size()];
        surah_arabic_names = surah_names_array.toArray(surah_arabic_names);
        return surah_arabic_names;
    }
    //Surah Roman Names
    public String[] surah_roman() {
        String query = "Select * from Surah_Names";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        ArrayList<String> surah_roman_array = new ArrayList<String>();
        if (cursor.moveToFirst()) {
            do {
                String roman = cursor.getString(cursor.getColumnIndexOrThrow("roman"));
                surah_roman_array.add(roman);
            } while (cursor.moveToNext());
        }
        cursor.close();
        String[] surah_roman = new String[surah_roman_array.size()];
        surah_roman = surah_roman_array.toArray(surah_roman);
        return surah_roman;
    }
    //Surah Numbers
    public String[] surah_No() {
        String query = "Select * from Surah_Names";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        ArrayList<String> surah_number_array = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String no = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                surah_number_array.add(no);
            } while (cursor.moveToNext());
        }
        cursor.close();
        String[] surah_number = new String[surah_number_array.size()];
        surah_number = surah_number_array.toArray(surah_number);
        return surah_number;
    }
    //Surah Verses Quantity
    public String[] Surah_Verses() {
        String query = "Select * from Surah_Names";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        ArrayList<String> surah_verses_array = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String verse = cursor.getString(cursor.getColumnIndexOrThrow("verses"));
                surah_verses_array.add(verse);
            } while (cursor.moveToNext());
        }
        cursor.close();
        String[] surah_verses = new String[surah_verses_array.size()];
        surah_verses = surah_verses_array.toArray(surah_verses);
        return surah_verses;
    }
    //Para Arabic Names
    public String[] para_arabic() {
        String query = "Select * from Parah_Names";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        ArrayList<String> para_names_array = new ArrayList<String>();
        if (cursor.moveToFirst()) {
            do {
                String para_name = cursor.getString(cursor.getColumnIndexOrThrow("arabic_name"));
                para_names_array.add(para_name);
            } while (cursor.moveToNext());
        }
        cursor.close();
        String[] para_arabic_names = new String[para_names_array.size()];
        para_arabic_names = para_names_array.toArray(para_arabic_names);
        return para_arabic_names;
    }
    //Para Roman Names
    public String[] para_roman() {
        String query = "Select * from Parah_Names";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        ArrayList<String> para_roman_array = new ArrayList<String>();
        if (cursor.moveToFirst()) {
            do {
                String roman = cursor.getString(cursor.getColumnIndexOrThrow("roman_name"));
                para_roman_array.add(roman);
            } while (cursor.moveToNext());
        }
        cursor.close();
        String[] para_roman = new String[para_roman_array.size()];
        para_roman = para_roman_array.toArray(para_roman);
        return para_roman;
    }
    //Para Serial Number
    public String[] para_No() {
        String query = "Select * from Parah_Names";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        ArrayList<String> para_number_array = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String no = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                para_number_array.add(no);
            } while (cursor.moveToNext());
        }
        cursor.close();
        String[] para_number = new String[para_number_array.size()];
        para_number = para_number_array.toArray(para_number);
        return para_number;
    }
    //Para verses Quantity
    public String[] para_Verses() {
        String query = "Select * from Parah_Names";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        ArrayList<String> para_verses_array = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String total_verse = cursor.getString(cursor.getColumnIndexOrThrow("verses"));
                para_verses_array.add(total_verse);
            } while (cursor.moveToNext());
        }
        cursor.close();
        String[] verse_total = new String[para_verses_array.size()];
        verse_total = para_verses_array.toArray(verse_total);
        return verse_total;
    }
    //Full Surah Arabic Text
    public String[] Surah_Text_pdms(int index) {
        String query = "Select * from quran_text where sura="+index;
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        ArrayList<String> quran_text_array = new ArrayList<>();
        ArrayList<String> second = new ArrayList<>();
        ArrayList<String> first = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String text = cursor.getString(cursor.getColumnIndexOrThrow("text"));
                String num = cursor.getString(cursor.getColumnIndexOrThrow("aya_PDMS"));
                first.add(text);
                second.add(num);
            } while (cursor.moveToNext());

            for(int i = 0; i < first.size(); i++) {
                quran_text_array.add(first.get(i));
                quran_text_array.add(second.get(i));
            }
        }
        cursor.close();
        String[] quran_text = new String[quran_text_array.size()];
        quran_text = quran_text_array.toArray(quran_text);
        return quran_text;
    }public String[] Surah_Text_me_quran(int index) {
        String query = "Select * from quran_text where sura="+index;
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        ArrayList<String> quran_text_array = new ArrayList<>();
        ArrayList<String> second = new ArrayList<>();
        ArrayList<String> first = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String text = cursor.getString(cursor.getColumnIndexOrThrow("text"));
                String num = cursor.getString(cursor.getColumnIndexOrThrow("aya_me_quran"));
                first.add(text);
                second.add(num);
            } while (cursor.moveToNext());

            for(int i = 0; i < first.size(); i++) {
                quran_text_array.add(first.get(i));
                quran_text_array.add(second.get(i));
            }
        }
        cursor.close();
        String[] quran_text = new String[quran_text_array.size()];
        quran_text = quran_text_array.toArray(quran_text);
        return quran_text;
    }
    //On Click translation Button Surah Ayat
    public String[] Surah_Ayat_Text(int index) {
        String query = "Select * from quran_text where sura="+index;
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        ArrayList<String> ayat_text_array = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String ayat_text = cursor.getString(cursor.getColumnIndexOrThrow("text"));
                ayat_text_array.add(ayat_text);
            } while (cursor.moveToNext());
        }
        cursor.close();
        String[] ayat_array = new String[ayat_text_array.size()];
        ayat_array = ayat_text_array.toArray(ayat_array);
        return ayat_array;
    }
    //On Click translation Button translation of Surah Ayat
    public String[] Surah_Translation_Urdu(int index) {
        String query = "Select * from ur_kanzuliman where sura="+index;
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        ArrayList<String> translation_text_array = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String translation_text = cursor.getString(cursor.getColumnIndexOrThrow("text"));
                translation_text_array.add(translation_text);
            } while (cursor.moveToNext());
        }
        cursor.close();
        String[] translation_array = new String[translation_text_array.size()];
        translation_array = translation_text_array.toArray(translation_array);
        return translation_array;
    }
    public String[] Surah_Translation_Eng(int index) {
        String query = "Select * from en_kanzuliman where sura="+index;
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        ArrayList<String> translation_text_array = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String translation_text = cursor.getString(cursor.getColumnIndexOrThrow("text"));
                translation_text_array.add(translation_text);
            } while (cursor.moveToNext());
        }
        cursor.close();
        String[] translation_array = new String[translation_text_array.size()];
        translation_array = translation_text_array.toArray(translation_array);
        return translation_array;
    }// Full Para Arabic text
    public String[] Para_Text_pdms(int index) {
        String query = "Select * from quran_text where para="+index;
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        ArrayList<String> quran_text_array = new ArrayList<>();
        ArrayList<String> second = new ArrayList<>();
        ArrayList<String> first = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String text = cursor.getString(cursor.getColumnIndexOrThrow("text"));
                String num = cursor.getString(cursor.getColumnIndexOrThrow("aya_PDMS"));
                first.add(text);
                second.add(num);
            } while (cursor.moveToNext());

            for(int i = 0; i < first.size(); i++) {
                quran_text_array.add(first.get(i));
                quran_text_array.add(second.get(i));
            }
        }
        cursor.close();
        String[] quran_text = new String[quran_text_array.size()];
        quran_text = quran_text_array.toArray(quran_text);
        return quran_text;
    }public String[] Para_Text_me_quran(int index) {
        String query = "Select * from quran_text where para="+index;
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        ArrayList<String> quran_text_array = new ArrayList<>();
        ArrayList<String> second = new ArrayList<>();
        ArrayList<String> first = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String text = cursor.getString(cursor.getColumnIndexOrThrow("text"));
                String num = cursor.getString(cursor.getColumnIndexOrThrow("aya_me_quran"));
                first.add(text);
                second.add(num);
            } while (cursor.moveToNext());

            for(int i = 0; i < first.size(); i++) {
                quran_text_array.add(first.get(i));
                quran_text_array.add(second.get(i));
            }
        }
        cursor.close();
        String[] quran_text = new String[quran_text_array.size()];
        quran_text = quran_text_array.toArray(quran_text);
        return quran_text;
    }
    //On Click translation Button Para Ayat
    public String[] Para_Ayat_Text(int index) {
        String query = "Select * from quran_text where para="+index;
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        ArrayList<String> ayat_text_array = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String ayat_text = cursor.getString(cursor.getColumnIndexOrThrow("text"));
                ayat_text_array.add(ayat_text);
            } while (cursor.moveToNext());
        }
        cursor.close();
        String[] ayat_array = new String[ayat_text_array.size()];
        ayat_array = ayat_text_array.toArray(ayat_array);
        return ayat_array;
    }
    //On Click translation Button translation of Para Ayat
    public String[] Para_Translation_Urdu(int index) {
        String query = "Select * from ur_kanzuliman where para="+index;
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        ArrayList<String> translation_text_array = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String translation_text = cursor.getString(cursor.getColumnIndexOrThrow("text"));
                translation_text_array.add(translation_text);
            } while (cursor.moveToNext());
        }
        cursor.close();
        String[] translation_array = new String[translation_text_array.size()];
        translation_array = translation_text_array.toArray(translation_array);
        return translation_array;
    }
    public String[] Para_Translation_Eng(int index) {
        String query = "Select * from en_kanzuliman where para="+index;
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        ArrayList<String> translation_text_array = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String translation_text = cursor.getString(cursor.getColumnIndexOrThrow("text"));
                translation_text_array.add(translation_text);
            } while (cursor.moveToNext());
        }
        cursor.close();
        String[] translation_array = new String[translation_text_array.size()];
        translation_array = translation_text_array.toArray(translation_array);
        return translation_array;
    }
    public String getSize() {
        String query = "Select * from User_Setting where _id=1";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                size = cursor.getString(cursor.getColumnIndexOrThrow("size_value"));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return size;
    }

    public void setSize(String size) {
        String query = "update User_Setting set size_value ='"+size+"' where _id=1";
        db.execSQL(query);
        this.size = size;
    }

    public String getMode() {
        String query = "Select * from User_Setting where _id=1";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                mode = cursor.getString(cursor.getColumnIndexOrThrow("display_mode"));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return mode;
    }

    public void setMode(String mode) {
        String query = "update User_Setting set display_mode='"+mode+"' where _id=1";
        db.execSQL(query);
        this.mode = mode;
    }

    public String getScript() {
        String query = "Select * from User_Setting where _id=1";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                script = cursor.getString(cursor.getColumnIndexOrThrow("text_script"));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return script;
    }

    public void setScript(String script) {
        String query = "update User_Setting set text_script='"+script+"' where _id=1";
        db.execSQL(query);
        this.script = script;
    }
}