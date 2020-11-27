package com.example.corecourse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class NavigationActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private Button submitBtn;
    private SeekBar edadSeekbar;
    private TextView edad;
    private CheckBox maleCheck, femaleCheck, otherCheck;

    private final int EDAD_MINIMA = 3;

    private EditText nameET, surnameET;

    private TinyDB tinyDB;
    private ArrayList<Object> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Delete every user", Snackbar.LENGTH_LONG)
                        .setAction("Delete", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                tinyDB = new TinyDB(NavigationActivity.this);
                                userList = tinyDB.getListObject("user_list", User.class);
                                userList.clear();
                                tinyDB.putListObject("user_list", userList);
                            }
                        }).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        submitBtn = findViewById(R.id.fragment_submit_btn_id);

        submitBtn = findViewById(R.id.fragment_submit_btn_id);
        edad = findViewById(R.id.fragment_edad_numberTxt);
        edadSeekbar = findViewById(R.id.fragment_edad_seekBar);

        maleCheck = findViewById(R.id.fragment_male_checkbox);
        femaleCheck = findViewById(R.id.fragment_female_checkbox);
        otherCheck = findViewById(R.id.fragment_other_checkbox);

        nameET = findViewById(R.id.fragment_name_editText);
        surnameET = findViewById(R.id.fragment_surname_editText);

        /*
        // Botón principal que mandará los datos a la actividad dedicada a mostrar los datos del usuario
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Primero comprobamos que el usuario ha puesto los datos correctamente
                // Sino entramos en los distintos catch
                try {
                    validateUserData();

                    Intent intent = new Intent(NavigationActivity.this, ShowContent.class);

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
        });*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(NavigationActivity.this);

        builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton(R.string.str_dialog_exit, null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}