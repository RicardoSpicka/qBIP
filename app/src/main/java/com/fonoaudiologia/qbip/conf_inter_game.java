package com.fonoaudiologia.qbip;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import com.karlotoy.perfectune.instance.PerfectTune;

public class conf_inter_game extends AppCompatActivity {

    TextView frequenciaBeep, pausa, durBeepBt;
    int durBeep, frequencia, pausaBeep;

    SeekBar seekBarVol;
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_inter);

        Intent intent = getIntent();
        String parametro1 = (String) intent.getSerializableExtra("pausaBeep");
        String parametro2 = (String) intent.getSerializableExtra("durBeep");
        String parametro3 = (String) intent.getSerializableExtra("frequencia");
        pausaBeep = Integer.valueOf(parametro1);
        durBeep = Integer.valueOf(parametro2);
        frequencia = Integer.valueOf(parametro3);

        frequenciaBeep = (TextView) findViewById(R.id.frequenciaBeep);
        frequenciaBeep.setText(String.valueOf(frequencia) + " Hz");

        pausa = (TextView) findViewById(R.id.pausaEntre);
        pausa.setText(String.valueOf(pausaBeep) + " mS");

        durBeepBt = (TextView) findViewById(R.id.duracaoBeep);
        durBeepBt.setText(String.valueOf(durBeep) + " mS");


    seekBarVol = findViewById(R.id.seekBar);

    audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

    //pega o volume máximo
    int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

    int atualVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        seekBarVol.setMax(maxVolume);

        seekBarVol.setProgress(atualVolume);

        seekBarVol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    });
    }
    // CONSTROI OS SONS DOS BIPs E PAUSA
    //BEEPS
    public void somBeep(){
        PerfectTune perfectTune = new PerfectTune();
        perfectTune.setTuneFreq(frequencia);
        perfectTune.playTune();
        try {
            Thread.sleep (durBeep);
        } catch (InterruptedException e) {}
        perfectTune.stopTune();
    }

    public void pause(){
        try {
            Thread.sleep (pausaBeep);
        } catch (InterruptedException e) {}
    } // FIM DOS SONS E DA PAUSA

    //Incrementa e decrementa a duração do BEEP
    public void durMine(View view) {
        durBeepBt = (TextView) findViewById(R.id.duracaoBeep);
        durBeepBt.setText(String.valueOf(durBeep) + " mS");
        int durMin = 200;
        int aux = durBeep;
        if (durBeep > durMin) {
            durBeep = aux - 50;
        } else {
            Toast.makeText(this, "Mínimo de 200 mS", Toast.LENGTH_SHORT).show();
        }
        durBeepBt.setText(String.valueOf(durBeep) + " mS");
    }
    public void durMore(View view) {
        durBeepBt = (TextView) findViewById(R.id.duracaoBeep);
        durBeepBt.setText(String.valueOf(durBeep) + " mS");
        int durMax = 1000;
        int aux = durBeep;
        if (durBeep < durMax) {
            durBeep = aux + 50;
        } else {
            Toast.makeText(this, "Máximo de 1000 mS", Toast.LENGTH_SHORT).show();
        }
        durBeepBt.setText(String.valueOf(durBeep) + " mS");
    }
    //Incrementa e decrementa a frequencia do BEEP
    public void frqMine(View view) {
        frequenciaBeep = (TextView) findViewById(R.id.frequenciaBeep);
        frequenciaBeep.setText(String.valueOf(frequencia) + " Hz");
        int frqMin = 500;
        int aux = frequencia;
        if (frequencia > frqMin) {
            frequencia = aux - 50;
        } else {
            Toast.makeText(this, "Mínimo de 500 Hz", Toast.LENGTH_SHORT).show();
        }
        frequenciaBeep.setText(String.valueOf(frequencia) + " mS");    }
    public void frqMore(View view) {
        frequenciaBeep = (TextView) findViewById(R.id.frequenciaBeep);
        frequenciaBeep.setText(String.valueOf(frequencia) + " Hz");
        int frqMax = 3000;
        int aux = frequencia;
        if (frequencia < frqMax) {
            frequencia = aux + 50;
        } else {
            Toast.makeText(this, "Máximo de 3000 Hz", Toast.LENGTH_SHORT).show();
        }
        frequenciaBeep.setText(String.valueOf(frequencia) + " Hz");    }

    //Incrementa e decrementa o tempo da pausa entre os BIPs
    public void pauseMine(View view) {
        pausa = (TextView) findViewById(R.id.pausaEntre);
        pausa.setText(String.valueOf(pausaBeep) + " mS");
        int pausaMin = 50;
        int aux = pausaBeep;
        if (pausaBeep > pausaMin) {
            pausaBeep = aux - 50;
        } else {
            Toast.makeText(this, "Mínimo de 50 mS", Toast.LENGTH_SHORT).show();
        }
        pausa.setText(String.valueOf(pausaBeep) + " mS");    }
    public void pauseMore(View view) {
        pausa = (TextView) findViewById(R.id.pausaEntre);
        pausa.setText(String.valueOf(pausaBeep) + " mS");
        int pausaMax = 800;
        int aux = pausaBeep;
        if (pausaBeep < pausaMax) {
            pausaBeep = aux + 50;
        } else {
            Toast.makeText(this, "Máximo de 800 mS", Toast.LENGTH_SHORT).show();
        }
        pausa.setText(String.valueOf(pausaBeep) + " mS");    }

    public void testeFrequencia(View view) { somBeep();     }

    public void testeDuracao(View view) { somBeep();     }

    public void pausaEntre(View view) {
        for (int i = 0; i < 4; i++){
            somBeep();
            pause(); }  }

    // volta ao JOGO FREQUENCIA
    public void backGameDur(View v){

        Intent intent = new Intent(this, inter_game.class);
        intent.putExtra("pausaBeep", String.valueOf(pausaBeep));
        intent.putExtra("durBeep", String.valueOf(durBeep));
        intent.putExtra("frequencia", String.valueOf(frequencia));
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);
        ActivityCompat.startActivity(this,intent, activityOptionsCompat.toBundle());
    }

}