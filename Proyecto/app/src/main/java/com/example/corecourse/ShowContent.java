package com.example.corecourse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ShowContent extends AppCompatActivity {

    private TextView mainText;
    private String userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_content);

        userData = "¡Buenas ";

        mainText = findViewById(R.id.main_text);

        Intent intent = getIntent();
        userData = userData.concat(intent.getStringExtra("name")) + "!\n" +
                                    "Usted tiene " + intent.getStringExtra("edad") + " años.\n" +
                                    "Su género aunque, francamente, nos da igual, lo usaremos para datos estadísticos.\n" +
                                    "Usted ha determinado su género como " + intent.getStringExtra("gender") + ".";

        mainText.setText(userData);
    }
}