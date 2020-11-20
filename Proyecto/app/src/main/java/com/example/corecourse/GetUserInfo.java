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

    private final int EDAD_MINIMA = 3;

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
                // Primero comprobamos que el usuario ha puesto los datos correctamente
                // Sino entramos en los distintos catch
                try {
                    validateUserData();

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

                    // Cambiar actividad con todos los parámetros
                    startActivity(intent);
                } catch (MultipleCheckboxException checkboxException) {
                    popAlert(R.string.checkbox_alert_title, R.string.checkbox_alert);
                } catch (InvalidAgeException invalidAgeException) {
                    popAlert(R.string.age_alert_title, R.string.age_alert);
                } catch (DataException ignored) {

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

    // Función para comprobar los datos de nuestros usuario y ver si son válidos
    private void validateUserData() throws DataException {
        if (maleCheck.isChecked() && (femaleCheck.isChecked() || otherCheck.isChecked())
                || femaleCheck.isChecked() && (maleCheck.isChecked() || otherCheck.isChecked()))
            throw new MultipleCheckboxException();
        else if (edadSeekbar.getProgress() < EDAD_MINIMA)
            throw new InvalidAgeException();
    }

    // Funcion genérica para hacer alerts
    private void popAlert(int title, int msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(GetUserInfo.this);

        builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton(R.string.str_dialog_exit, null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}