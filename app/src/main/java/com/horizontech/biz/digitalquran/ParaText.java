package com.horizontech.biz.digitalquran;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.horizontech.biz.digitalquran.Adapter.TranslationAdapter;
import com.horizontech.biz.digitalquran.Database.DbBackend;

import java.util.Arrays;

public class ParaText extends AppCompatActivity {

    TextView quranText;
    Typeface tf;
    ImageView bismillah;
    ImageView bismillah2;
    ViewGroup header;
    int num;
    int index;
    Button translation;
    ListView ParaTextList;
    boolean isTranslate=false;
    TranslationAdapter listAdapter_Eng;
    TranslationAdapter listAdapter_Urdu;
    ScrollView ParaTextScroll;
    LayoutInflater inflater;
    DbBackend db;
    String[] text;
    final Context context = this;
    Dialog dialog;
    String[] translation_text_Eng;
    String[] translation_text_Urdu;
    String[] arabic_text;
    RadioGroup language_radiogroup;
    Button dialogButton;
    RadioButton radioButton_en;
    RadioButton radioButton_ur;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db=new DbBackend(ParaText.this);
        if (db.getMode().equals("DayMoodFullScreen")) {
            setTheme(R.style.DayMoodFullScreen);
        }else {
            setTheme(R.style.NightMoodFullScreen);
        }
        setContentView(R.layout.activity_para_text);

        translation= (Button) findViewById(R.id.translate);
        ParaTextList= (ListView) findViewById(R.id.paratextlist);
        inflater = getLayoutInflater();
        header = (ViewGroup)inflater.inflate(R.layout.translation_header, ParaTextList , false);
        ParaTextList .addHeaderView(header, null, false);
        ParaTextScroll= (ScrollView) findViewById(R.id.paratextscroll);

        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.activity_para_text);
        Resources res = getResources();
        Drawable portrait = res.getDrawable(R.drawable.portrait);
        Drawable landscape = res.getDrawable(R.drawable.landscape);

        WindowManager window = (WindowManager)getSystemService(WINDOW_SERVICE);
        Display display = window.getDefaultDisplay();
        num = display.getRotation();
        if (num == 0){
            relativeLayout.setBackgroundDrawable(portrait);
        }else if (num == 1 || num == 3){
            relativeLayout.setBackgroundDrawable(landscape);
        }else{
            relativeLayout.setBackgroundDrawable(portrait);
        }
        quranText= (TextView) findViewById(R.id.surah_text);
        if (db.getSize().equals("Small")){
            quranText.setTextSize(15);
        }else if (db.getSize().equals("Normal")){
            quranText.setTextSize(20);
        }else if (db.getSize().equals("Large")){
            quranText.setTextSize(25);
        }else if (db.getSize().equals("Extra Large")){
            quranText.setTextSize(30);
        }
        tf = Typeface.createFromAsset(getAssets(), "fonts/"+db.getScript()+".ttf");
        quranText.setTypeface(tf);
        bismillah= (ImageView) findViewById(R.id.bismillahimage);
        bismillah2= (ImageView) findViewById(R.id.bismillah2);
        if (db.getMode().equals("DayMoodFullScreen")) {
            bismillah.setImageResource(R.drawable.bismillah_daymod);
            bismillah2.setImageResource(R.drawable.bismillah_daymod);
        }else {
            bismillah.setImageResource(R.drawable.bismillah_nightmod);
            bismillah2.setImageResource(R.drawable.bismillah_nightmod);
        }
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        index = bundle.getInt("Para_Number");
            if (db.getScript().equals("pdms")){
                text = db.Para_Text_pdms(index);
            }else {
                text = db.Para_Text_me_quran(index);
            }
        String finalize = Arrays.toString(text).replaceAll(",","");
        String finalize1 = finalize.replaceAll("\\[","");
        String finalize2 = finalize1.replaceAll("\\]","");

        arabic_text = db.Para_Ayat_Text(index);
        translation_text_Eng = db.Para_Translation_Eng(index);
        translation_text_Urdu = db.Para_Translation_Urdu(index);
        listAdapter_Eng = new TranslationAdapter(this,translation_text_Eng,arabic_text);
        listAdapter_Urdu = new TranslationAdapter(this,translation_text_Urdu,arabic_text);
        quranText.setText(finalize2);
        translation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isTranslate){
                    translation.setText("HIDE TRANSLATION");
                    isTranslate=true;
                    dialog = new Dialog(context);
                    dialog.setContentView(R.layout.activity_custom_translation);
                    dialog.setTitle("Select Language");
                    language_radiogroup= (RadioGroup) dialog.findViewById(R.id.language_radio);
                    radioButton_en= (RadioButton) dialog.findViewById(R.id.en_radio);
                    radioButton_ur= (RadioButton) dialog.findViewById(R.id.ur_radio);
                    dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                    language_radiogroup.check(R.id.ur_radio);
                    // if button is clicked, close the custom dialog
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            quranText.setVisibility(View.INVISIBLE);
                            ParaTextScroll.setVisibility(View.INVISIBLE);
                            ParaTextList.setVisibility(View.VISIBLE);
                            if (radioButton_en.isChecked()){
                                ParaTextList.setAdapter(listAdapter_Eng);
                            }else {
                                ParaTextList.setAdapter(listAdapter_Urdu);
                            }
                        }
                    });
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(dialog.getWindow().getAttributes());
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    dialog.show();
                    dialog.getWindow().setAttributes(lp);
                    dialog.show();

                }else{
                    translation.setText("SHOW TRANSLATION");
                    isTranslate=false;
                    quranText.setVisibility(View.VISIBLE);
                    ParaTextScroll.setVisibility(View.VISIBLE);
                    ParaTextList.setVisibility(View.INVISIBLE);
                }
            }


        });
    }
}