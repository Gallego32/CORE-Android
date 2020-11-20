package com.example.corecourse;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class GetUserInfo extends AppCompatActivity {

    private Button submitBtn;
    private SeekBar edadSeekbar;
    private TextView edad;
    private CheckBox maleCheck, femaleCheck, otherCheck;

    private EditText nameET, surnameET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_user_info);

        submitBtn = findViewById(R.id.submit_btn_id);
        edad = findViewById(R.id.edad_numberTxt);
        edadSeekbar = findViewById(R.id.edad_seekBar);

        maleCheck = findViewById(R.id.male_checkbox);
        femaleCheck = findViewById(R.id.female_checkbox);
        otherCheck = findViewById(R.id.other_checkbox);

        nameET = findViewById(R.id.name_editText);
        surnameET = findViewById(R.id.surname_editText);

        // Botón principal que mandará los datos a la actividad dedicada a mostrar los datos del usuario
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Primero comprobamos que el usuario no ha marcado las casillas de género de forma incoherente
                // Si es así hacemos un alert
                if (maleCheck.isChecked() && (femaleCheck.isChecked() || otherCheck.isChecked())
                        || femaleCheck.isChecked() && (maleCheck.isChecked() || otherCheck.isChecked())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(GetUserInfo.this);

                    builder.setTitle(R.string.checkbox_alert_title)
                            .setMessage(R.string.checkbox_alert)
                            .setPositiveButton(R.string.str_dialog_exit, null);

                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    Intent intent = new Intent(GetUserInfo.this, ShowContent.class);

                    intent.putExtra("name", nameET.getText().toString() + " " + surnameET.getText().toString());
                    intent.putExtra("edad", "" + edadSeekbar.getProgress());

                    String gender;

                    if (maleCheck.isChecked())
                        gender = "hombre";
                    else if (femaleCheck.isChecked())
                        gender = "mujer";
                    else gender = "otro";
                    intent.putExtra("gender", gender);

                    startActivity(intent);
                }
            }
        });

        edadSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                edad.setText("" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}