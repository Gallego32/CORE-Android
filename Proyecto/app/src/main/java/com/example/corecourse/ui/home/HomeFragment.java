package com.example.corecourse.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.corecourse.DataException;
import com.example.corecourse.InvalidAgeException;
import com.example.corecourse.MultipleCheckboxException;
import com.example.corecourse.NavigationActivity;
import com.example.corecourse.R;
import com.example.corecourse.ShowContent;
import com.example.corecourse.TinyDB;
import com.example.corecourse.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private Button submitBtn;
    private SeekBar edadSeekbar;
    private TextView edad;
    private CheckBox maleCheck, femaleCheck, otherCheck;

    private final int EDAD_MINIMA = 3;

    private EditText nameET, surnameET;

    private User user;

    private ArrayList<Object> userLinkedList;

    private TinyDB tinyDB;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        /*final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        submitBtn = root.findViewById(R.id.fragment_submit_btn_id);

        edad = root.findViewById(R.id.fragment_edad_numberTxt);
        edadSeekbar = root.findViewById(R.id.fragment_edad_seekBar);

        maleCheck = root.findViewById(R.id.fragment_male_checkbox);
        femaleCheck = root.findViewById(R.id.fragment_female_checkbox);
        otherCheck = root.findViewById(R.id.fragment_other_checkbox);

        nameET = root.findViewById(R.id.fragment_name_editText);
        surnameET = root.findViewById(R.id.fragment_surname_editText);

        tinyDB = new TinyDB(getContext());

        if (tinyDB.getListObject("user_list", User.class) == null)
            userLinkedList = new ArrayList<Object>();
        else
            userLinkedList = tinyDB.getListObject("user_list", User.class);

        // Botón principal que mandará los datos a la actividad dedicada a mostrar los datos del usuario
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Primero comprobamos que el usuario ha puesto los datos correctamente
                // Sino entramos en los distintos catch
                try {
                    validateUserData();

                    String gender;
                    if (maleCheck.isChecked())
                        gender = "hombre";
                    else if (femaleCheck.isChecked())
                        gender = "mujer";
                    else gender = "otro";

                    user = new User(edadSeekbar.getProgress(), gender, nameET.getText().toString(), surnameET.getText().toString());

                    userLinkedList.add(user);

                    tinyDB.putObject("my_user", user);

                    tinyDB.putListObject("user_list", userLinkedList);

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

        return root;
    }

    // Función para comprobar los datos de nuestros usuario y ver si son válidos
    private void validateUserData() throws DataException {
        if (maleCheck.isChecked() && (femaleCheck.isChecked() || otherCheck.isChecked())
                || femaleCheck.isChecked() && (maleCheck.isChecked() || otherCheck.isChecked()))
            throw new MultipleCheckboxException();
        else if (edadSeekbar.getProgress() < EDAD_MINIMA)
            throw new InvalidAgeException();
    }

    //Funcion genérica para hacer alerts
    private void popAlert(int title, int msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton(R.string.str_dialog_exit, null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}