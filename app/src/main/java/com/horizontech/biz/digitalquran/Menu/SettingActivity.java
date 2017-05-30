package com.horizontech.biz.digitalquran.Menu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.horizontech.biz.digitalquran.Database.DbBackend;
import com.horizontech.biz.digitalquran.R;

public class SettingActivity extends AppCompatActivity {
    Spinner textSize;
    Spinner displayMode;
    Spinner textScript;
    Button setting_button;
    DbBackend db;
    String myscript;
    String mymode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        db=new DbBackend(SettingActivity.this);

        textSize = (Spinner) findViewById(R.id.text_size);
        displayMode= (Spinner) findViewById(R.id.display_mode);
        textScript= (Spinner) findViewById(R.id.text_script);
        setting_button = (Button) findViewById(R.id.setting_btn);
        if (db.getScript().equals("me_quran")){
            textScript.setSelection(1);
        }else if (db.getScript().equals("pdms")){
            textScript.setSelection(2);
        }
        if (db.getMode().equals("DayMoodFullScreen")){
            displayMode.setSelection(1);
        }else if (db.getMode().equals("NightMoodFullScreen")){
            displayMode.setSelection(2);
        }
        if (db.getSize().equals("Small")){
            textSize.setSelection(1);
        }else if (db.getSize().equals("Normal")){
            textSize.setSelection(2);
        }else if (db.getSize().equals("Large")){
            textSize.setSelection(3);
        }else if (db.getSize().equals("Extra Large")){
            textSize.setSelection(4);
        }
        setting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.getScript().equals("me_quran")){
                    myscript="Me Quran";
                }else if (db.getScript().equals("pdms")) {
                    myscript = "PDMS Saleem";
                }
                if (db.getMode().equals("DayMoodFullScreen")){
                    mymode="Day Mode";
                }else if (db.getMode().equals("NightMoodFullScreen")){
                    mymode="Night Mode";
                }
                if (textSize.getSelectedItem().toString().trim().equals(db.getSize())
                        &&textScript.getSelectedItem().toString().trim().equals(myscript)
                        &&displayMode.getSelectedItem().toString().trim().equals(mymode)){
                    Toast.makeText(SettingActivity.this, "Previous Setting", Toast.LENGTH_SHORT).show();
            }
            if(textSize.getSelectedItem().toString().trim().equals("Select Text Size")
                    ||displayMode.getSelectedItem().toString().trim().equals("Select Display Mode")
                    ||textScript.getSelectedItem().toString().trim().equals("Select Script")){
                Toast.makeText(SettingActivity.this, "Please Select Setting", Toast.LENGTH_SHORT).show();
            }else{
               String size=textSize.getSelectedItem().toString().trim();
                    if(size.equals("Select Text Size")){
                        db.setSize(db.getSize());
                    }else{
                        db.setSize(size);
                    }
               String mode=displayMode.getSelectedItem().toString().trim();
                switch (mode) {
                    case "Select Display Mode":
                        db.setMode(db.getMode());
                        break;
                    case "Day Mode":
                        db.setMode("DayMoodFullScreen");
                        break;
                    case "Night Mode":
                        db.setMode("NightMoodFullScreen");
                        break;
                }
               String script=textScript.getSelectedItem().toString().trim();
                switch (script) {
                    case "Select Script":
                        db.setScript(db.getScript());
                        break;
                    case "Me Quran":
                        db.setScript("me_quran");
                        break;
                    default:
                        db.setScript("pdms");
                        break;
                }
                Toast.makeText(SettingActivity.this, "Setting Changed", Toast.LENGTH_SHORT).show();
            }
            }
        });
    }
}