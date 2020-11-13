package com.example.corecourse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView newText, seekBarText, keepGoingText;
    private SeekBar seekBar;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newText = (TextView) findViewById(R.id.main_text);

        button = (Button) findViewById(R.id.button_id);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                newText.setText("Ahora me siento más liberado");
            }
        });

        seekBarText = (TextView) findViewById(R.id.seekBarText);
        keepGoingText = (TextView) findViewById(R.id.keepGoingText);

        seekBar = (SeekBar) findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarText.setText("" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                keepGoingText.setText("Muy bien, así me gusta");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if (seekBar.getProgress() != seekBar.getMax())
                    keepGoingText.setText("¡¡Pero no pares!!");
                else keepGoingText.setText("Ahí está bien :)");
            }
        });
    }
}