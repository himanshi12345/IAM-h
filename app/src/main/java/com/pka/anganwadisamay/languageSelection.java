package com.pka.anganwadisamay;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.pka.anganwadisamay.Helper.LocaleHelper;

public class languageSelection extends AppCompatActivity {

    Button english;
    Button hindi;
    Button next;

    RadioButton female;
    RadioButton male;

    TextView bacheKaWajan;
    TextView bacheKiJanmtithi;
    Button startbutton;
    TextView wajanLeneKITithi;
    Button chune;
    Button gadna;
    Button poshanstar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);
        english = (Button)findViewById(R.id.english);
        hindi = (Button)findViewById(R.id.hindi);
        next = (Button)findViewById(R.id.con);

        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateViews("en");
                startActivity(new Intent(languageSelection.this,HomeActivity.class));
            }
        });

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
    //language hindi
    private void updateViews(String languageCode) {
        Context context = LocaleHelper.setLocale(this, languageCode);
        Resources resources = context.getResources();
        female.setText(resources.getString(R.string.female));
        male.setText(resources.getString(R.string.male));
        bacheKaWajan.setText(resources.getString(R.string.bacheKaWajan));
        startbutton.setText(resources.getString(R.string.startbutton));
        bacheKiJanmtithi.setText(resources.getString(R.string.bacheKiJanamTithi));
        wajanLeneKITithi.setText(resources.getString(R.string.wajanLeneKITithi));
        chune.setText(resources.getString(R.string.chune));
        gadna.setText(resources.getString(R.string.gadna));
        poshanstar.setText(resources.getString(R.string.poshanstar));

    }
    public void forward(View view){
        Intent intent = new Intent(languageSelection.this, HomeActivity.class);
        startActivity(intent);

    }
}
