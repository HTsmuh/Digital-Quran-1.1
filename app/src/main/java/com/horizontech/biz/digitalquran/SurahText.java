package com.horizontech.biz.digitalquran;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.horizontech.biz.digitalquran.Adapter.TranslationAdapter;
import com.horizontech.biz.digitalquran.Database.DbBackend;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class SurahText extends AppCompatActivity {
    TextView quranText;
    ImageView bismillah;
    ImageView bismillah2;
    Typeface tf;
    ViewGroup header;
    int num;
    int index;
    Button bookmark;
    Button show_translation_btn;
    ImageView hide_translation_btn;
    ImageView urdu_translation_btn;
    ImageView english_translation_btn;
    ImageView bookmark_icon;
    ListView SurahTextList;
    boolean isTranslate=false;
    TranslationAdapter listAdapter_Eng;
    TranslationAdapter listAdapter_Urdu;
    ScrollView SurahTextScroll;
    LayoutInflater inflater;
    DbBackend db;
    String[] Surah_text;
    final Context context = this;
    String[] translation_text_Eng;
    String[] translation_text_Urdu;
    String[] arabic_text;
    RelativeLayout relativeLayout;
    LinearLayout show_translate;
    LinearLayout hide_translate;
    int lastViewedPosition =0;
    int topOffset ;
    int position=0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db=new DbBackend(SurahText.this);
        if (db.getMode().equals("DayMoodFullScreen")) {
            setTheme(R.style.DayMoodFullScreen);
        }else {
            setTheme(R.style.NightMoodFullScreen);
        }
        setContentView(R.layout.activity_surah_text);

        hide_translation_btn= (ImageView) findViewById(R.id.hide_translate);
        show_translate= (LinearLayout) findViewById(R.id.para_bottom);
        hide_translate= (LinearLayout) findViewById(R.id.para_translation);
        urdu_translation_btn= (ImageView) findViewById(R.id.trnslate_urdu);
        english_translation_btn= (ImageView) findViewById(R.id.trnslate_english);
        bookmark_icon= (ImageView) findViewById(R.id.bookmark_para2);
        show_translation_btn = (Button) findViewById(R.id.translate);
        SurahTextList= (ListView) findViewById(R.id.surahtextlist);
        bookmark= (Button) findViewById(R.id.bookmark_surah);
        inflater = getLayoutInflater();
        header = (ViewGroup)inflater.inflate(R.layout.custom_translation_header, SurahTextList , false);
        SurahTextList .addHeaderView(header, null, false);
        SurahTextScroll= (ScrollView) findViewById(R.id.surahtextscroll);
        setBackgroungImage();
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
        int index1 = bundle.getInt("Surah_Number");
        int index2 = bundle.getInt("BookmarkFragment");
        int position2 = bundle.getInt("SurahScrollPosition");
        int item2 = bundle.getInt("SurahListItem");
        boolean check = bundle.getBoolean("checkSurahTranslation");
        lastViewedPosition=lastViewedPosition+item2;
        position=position+position2;
        index=index1+index2;
        db=new DbBackend(SurahText.this);
        if (index==9){
            bismillah.setVisibility(View.INVISIBLE);
        }
        if (db.getScript().equals("pdms")) {
            Surah_text = db.Surah_Text_pdms(index);
        }else {
            Surah_text = db.Surah_Text_me_quran(index);
        }
        String finalize = Arrays.toString(Surah_text).replaceAll(",","");
        String finalize1 = finalize.replaceAll("\\[","");
        String finalize2 = finalize1.replaceAll("\\]","");

        arabic_text = db.Surah_Ayat_Text(index);
        translation_text_Eng = db.Surah_Translation_Eng(index);
        translation_text_Urdu = db.Surah_Translation_Urdu(index);
        listAdapter_Eng = new TranslationAdapter(this,Surah_text,translation_text_Eng);
        listAdapter_Urdu = new TranslationAdapter(this,Surah_text,translation_text_Urdu);
        quranText.setText(finalize2);

        //Toast.makeText(this, ""+position+","+position2+","+lastViewedPosition+","+item2+","+check, Toast.LENGTH_SHORT).show();
        if (check == true){
            showTranslation();
            setListScroll(lastViewedPosition);
        }
        SurahTextScroll.post(new Runnable() {
            public void run() {
                setTextScroll(position);
            }
        });
        show_translation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { if(!isTranslate){ showTranslation(); }
            }
        });
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBookmark();
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setBackgroungImage();
    }
    public void setBackgroungImage(){
        relativeLayout = (RelativeLayout)findViewById(R.id.activity_surah_text);
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
    }
    public void setBookmark(){
        int scrollY = SurahTextScroll.getScrollY();

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MMM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
        String currentDateandTime1 = sdf1.format(new Date());
        String currentDateandTime2 = sdf2.format(new Date());
        String currentDateandTime = " on "+currentDateandTime1+" at "+currentDateandTime2;
        db.setBookmark_sura_no(index);
        db.insertINTObookmarkSurah(db.getBookmark_sura_no(),db.bookmarkSurahArabic,db.bookmarkSurahEnglish,scrollY,currentDateandTime);
        Toast.makeText(context, "Bookmarked", Toast.LENGTH_SHORT).show();
    }
    public void setTranslationBookmark(){
        lastViewedPosition = SurahTextList.getFirstVisiblePosition();

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MMM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
        String currentDateandTime1 = sdf1.format(new Date());
        String currentDateandTime2 = sdf2.format(new Date());
        String currentDateandTime = " on "+currentDateandTime1+" at "+currentDateandTime2;
        db.setBookmark_sura_no(index);
        db.insertINTObookmarkSurahTranslation(db.getBookmark_sura_no(),db.bookmarkSurahArabic,db.bookmarkSurahEnglish+"*",lastViewedPosition,currentDateandTime);
        Toast.makeText(context, "Bookmarked", Toast.LENGTH_SHORT).show();
    }
    private void setTextScroll(float spot) {
        int scroll = (int) spot;
        SurahTextScroll.scrollTo(0, scroll);
    }
    private void showTranslation() {
        isTranslate=true;
        show_translate.setVisibility(View.GONE);
        hide_translate.setVisibility(View.VISIBLE);
        quranText.setVisibility(View.INVISIBLE);
        SurahTextScroll.setVisibility(View.INVISIBLE);
        SurahTextList.setVisibility(View.VISIBLE);
        SurahTextList.setAdapter(listAdapter_Urdu);
        if (index==9){
            header.findViewById(R.id.bismillah2).setVisibility(View.INVISIBLE);
        }
        hide_translation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { hideTranslation();

            }
        });
        urdu_translation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {setAdapterScroll(); SurahTextList.setAdapter(listAdapter_Urdu); getAdapterScroll(lastViewedPosition);} });
        english_translation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {setAdapterScroll(); SurahTextList.setAdapter(listAdapter_Eng);getAdapterScroll(lastViewedPosition); }
        });
        bookmark_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTranslationBookmark();
            }
        });
    }
    private void hideTranslation() {
        isTranslate=false;
        show_translate.setVisibility(View.VISIBLE);
        hide_translate.setVisibility(View.GONE);
        quranText.setVisibility(View.VISIBLE);
        SurahTextScroll.setVisibility(View.VISIBLE);
        SurahTextList.setVisibility(View.INVISIBLE);
    }
    public void setListScroll(int pos){
        SurahTextList.setSelection(pos);
    }
    public void setAdapterScroll(){
        lastViewedPosition = SurahTextList.getFirstVisiblePosition();
        View v = SurahTextList.getChildAt(0);
        topOffset = (v == null) ? 0 : v.getTop();
    }
    public void getAdapterScroll(int last){
        SurahTextList.setSelection(last);
    }
}