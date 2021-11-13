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

public class conf_dur_game extends AppCompatActivity {

    TextView frequenciaBeep, pausaBeep, durCurto, durLongo;
    int tempLongo, tempCurto, freqBeep, pausaTempo;

    SeekBar seekBarVol;
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_dur);

        Intent intent = getIntent();
        String parametro1 = (String) intent.getSerializableExtra("tempCurto");
        String parametro2 = (String) intent.getSerializableExtra("tempLongo");
        String parametro3 = (String) intent.getSerializableExtra("pausaTempo");
        String parametro4 = (String) intent.getSerializableExtra("freqBeep");
        tempCurto = Integer.valueOf(parametro1);
        tempLongo = Integer.valueOf(parametro2);
        pausaTempo = Integer.valueOf(parametro3);
        freqBeep = Integer.valueOf(parametro4);

        frequenciaBeep = (TextView) findViewById(R.id.frequenciaBeep);
        pausaBeep = (TextView) findViewById(R.id.pausaEntre);
        durCurto = (TextView) findViewById(R.id.tempoCurto);
        durLongo = (TextView) findViewById(R.id.tempoLongo);
        durCurto.setText(String.valueOf(tempCurto) + " mS");
        durLongo.setText(String.valueOf(tempLongo) + " mS");
        frequenciaBeep.setText(String.valueOf(freqBeep) + " Hz");
        pausaBeep.setText(String.valueOf(pausaTempo) + " mS");

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
    public void curto(){
        PerfectTune perfectTune = new PerfectTune();
        perfectTune.setTuneFreq(freqBeep);
        perfectTune.playTune();
        try {
            Thread.sleep (tempCurto);
        } catch (InterruptedException e) {}
        perfectTune.stopTune();
    }
    public void longo(){
        PerfectTune perfectTune = new PerfectTune();
        perfectTune.setTuneFreq(freqBeep);
        perfectTune.playTune();
        try {
            Thread.sleep (tempLongo);
        } catch (InterruptedException e) {}
        perfectTune.stopTune();
    }
    public void pause(){
        try {
            Thread.sleep (pausaTempo);
        } catch (InterruptedException e) {}
    } // FIM DOS SONS E DA PAUSA



    //Incrementa e decrementa o tempo do CURTO
    public void curtoMine(View view) {
        durCurto = (TextView) findViewById(R.id.tempoCurto);
        durCurto.setText(String.valueOf(tempCurto) + " mS");
        int durMin = 100;
        int aux = tempCurto;
        if (tempCurto > durMin) {
            tempCurto = aux - 50;
        } else {
            Toast.makeText(this, "Mínimo de 100 mS", Toast.LENGTH_SHORT).show();
        }
        durCurto.setText(String.valueOf(tempCurto) + " mS");    }
    public void curtoMore(View view) {
        durCurto = (TextView) findViewById(R.id.tempoCurto);
        durCurto.setText(String.valueOf(tempCurto) + " mS");
        int durMax = 1000;
        int aux = tempCurto;
        if (tempCurto < durMax) {
            tempCurto = aux + 50;
        }else {
            Toast.makeText(this, "Máximo de 1000 mS", Toast.LENGTH_SHORT).show();
        }
        durCurto.setText(String.valueOf(tempCurto) + " mS");    }

    //Incrementa e decrementa a tempo do GROSSO
    public void longoMine(View view) {
        durLongo = (TextView) findViewById(R.id.tempoLongo);
        durLongo.setText(String.valueOf(tempLongo) + " mS");
        int durMin = 1250;
        int aux = tempLongo;
        if (tempLongo > durMin) {
            tempLongo = aux - 100;
        } else {
            Toast.makeText(this, "Mínimo de 1200 Hz", Toast.LENGTH_SHORT).show();
        }
        durLongo.setText(String.valueOf(tempLongo) + " mS");    }
    public void longoMore(View view) {
        durLongo = (TextView) findViewById(R.id.tempoLongo);
        durLongo.setText(String.valueOf(tempLongo) + " mS");
        int durMax = 2000;
        int aux = tempLongo;
        if (tempLongo < durMax) {
            tempLongo = aux + 100;
        } else {
            Toast.makeText(this, "Máximo de 2000 Hz", Toast.LENGTH_SHORT).show();
        }
        durLongo.setText(String.valueOf(tempLongo) + " mS");    }

    //Incrementa e decrementa a frequencia do BEEP
    public void frqMine(View view) {
        frequenciaBeep = (TextView) findViewById(R.id.frequenciaBeep);
        frequenciaBeep.setText(String.valueOf(freqBeep) + " Hz");
        int durMin = 500;
        int aux = freqBeep;
        if (freqBeep > durMin) {
            freqBeep = aux - 50;
        } else {
            Toast.makeText(this, "Mínimo de 500 Hz", Toast.LENGTH_SHORT).show();
        }
        frequenciaBeep.setText(String.valueOf(freqBeep) + " mS");    }
    public void frqMore(View view) {
        frequenciaBeep = (TextView) findViewById(R.id.frequenciaBeep);
        frequenciaBeep.setText(String.valueOf(freqBeep) + " Hz");
        int durMax = 2000;
        int aux = freqBeep;
        if (freqBeep < durMax) {
            freqBeep = aux + 50;
        } else {
            Toast.makeText(this, "Máximo de 2000 Hz", Toast.LENGTH_SHORT).show();
        }
        frequenciaBeep.setText(String.valueOf(freqBeep) + " Hz");    }

    //Incrementa e decrementa o tempo da pausa entre os BIPs
    public void pauseMine(View view) {
        pausaBeep = (TextView) findViewById(R.id.pausaEntre);
        pausaBeep.setText(String.valueOf(pausaTempo) + " mS");
        int durMin = 500;
        int aux = pausaTempo;
        if (pausaTempo > durMin) {
            pausaTempo = aux - 50;
        } else {
            Toast.makeText(this, "Mínimo de 500 mS", Toast.LENGTH_SHORT).show();
        }
        pausaBeep.setText(String.valueOf(pausaTempo) + " mS");    }
    public void pauseMore(View view) {
        pausaBeep = (TextView) findViewById(R.id.pausaEntre);
        pausaBeep.setText(String.valueOf(pausaTempo) + " mS");
        int durMax = 2000;
        int aux = pausaTempo;
        if (pausaTempo < durMax) {
            pausaTempo = aux + 50;
        } else {
            Toast.makeText(this, "Máximo de 2000 Hz", Toast.LENGTH_SHORT).show();
        }
        pausaBeep.setText(String.valueOf(pausaTempo) + " mS");    }

    public void testeCurto(View view) { curto(); }

    public void testeLongo(View view) { longo(); }

    public void testeFrequencia(View view) { curto();     }

    public void pausaEntre(View view) {
        for (int i = 0; i < 4; i++){
            curto();
            pause(); }  }

    // volta ao JOGO FREQUENCIA
    public void backGameDur(View v){

        Intent intent = new Intent(this, dur_game.class);
        intent.putExtra("tempCurto", String.valueOf(tempCurto));
        intent.putExtra("tempLongo", String.valueOf(tempLongo));
        intent.putExtra("pausaTempo", String.valueOf(pausaTempo));
        intent.putExtra("freqBeep", String.valueOf(freqBeep));
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);
        ActivityCompat.startActivity(this,intent, activityOptionsCompat.toBundle());
    }

}