package com.fonoaudiologia.qbip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.karlotoy.perfectune.instance.PerfectTune;

public class conf_freq_game extends AppCompatActivity {

    TextView durBeep, pausaBeep, freqFino, freqGrosso;
    int fqFino, fqGrosso, tempoSomFreq, pausaFreq;

    SeekBar seekBarVol;
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_freq);

        Intent intent = getIntent();
        String parametro1 = (String) intent.getSerializableExtra("fqGrosso");
        String parametro2 = (String) intent.getSerializableExtra("fqFino");
        String parametro3 = (String) intent.getSerializableExtra("pausaFreq");
        String parametro4 = (String) intent.getSerializableExtra("tempoSomFreq");
        fqGrosso = Integer.valueOf(parametro1);
        fqFino = Integer.valueOf(parametro2);
        pausaFreq = Integer.valueOf(parametro3);
        tempoSomFreq = Integer.valueOf(parametro4);

        durBeep = (TextView) findViewById(R.id.duracaoBeep);
        pausaBeep = (TextView) findViewById(R.id.pausaEntre);
        freqFino = (TextView) findViewById(R.id.freqFino);
        freqGrosso = (TextView) findViewById(R.id.freqGrosso);
        freqFino.setText(String.valueOf(fqFino) + " Hertz");
        freqGrosso.setText(String.valueOf(fqGrosso) + " Hertz");
        durBeep.setText(String.valueOf(tempoSomFreq) + " mS");
        pausaBeep.setText(String.valueOf(pausaFreq) + " mS");

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
    public void grosso (){
        PerfectTune perfectTune = new PerfectTune();
        perfectTune.setTuneFreq(fqGrosso);
        perfectTune.playTune();
        try {
            Thread.sleep (tempoSomFreq);
        } catch (InterruptedException e) {}
        perfectTune.stopTune();
    }
    public void fino(){
        PerfectTune perfectTune = new PerfectTune();
        perfectTune.setTuneFreq(fqFino);
        perfectTune.playTune();
        try {
            Thread.sleep (tempoSomFreq);
        } catch (InterruptedException e) {}
        perfectTune.stopTune();
    }
    public void pause(){
        try {
            Thread.sleep (pausaFreq);
        } catch (InterruptedException e) {}
    } // FIM DOS SONS E DA PAUSA

    //Incrementa e decrementa a frequencia do GROSSO
    public void grossoDown(View view) {
        freqGrosso = (TextView) findViewById(R.id.freqGrosso);
        freqGrosso.setText(String.valueOf(fqGrosso) + " Hertz");
        int freqMin = 500;
        int aux = fqGrosso;
        if (fqGrosso > freqMin) {
            fqGrosso = aux - 50;
        } else {
            Toast.makeText(this, "Mínimo de 500 Hz", Toast.LENGTH_SHORT).show();
        }
        freqGrosso.setText(String.valueOf(fqGrosso) + " Hertz");
    }
    public void grossoUp(View view) {
        freqGrosso = (TextView) findViewById(R.id.freqGrosso);
        freqGrosso.setText(String.valueOf(fqGrosso) + " Hertz");
        int freqMax = 1200;
        int aux = fqGrosso;
        if (fqGrosso < freqMax) {
            fqGrosso = aux + 50;
        }else {
            Toast.makeText(this, "Máximo de 1200 Hz", Toast.LENGTH_SHORT).show();
        }
        freqGrosso.setText(String.valueOf(fqGrosso) + " Hertz");
    }

    //Incrementa e decrementa a frequencia do FINO
    public void finoDown(View view) {
        freqFino = (TextView) findViewById(R.id.freqFino);
        freqFino.setText(String.valueOf(fqFino) + " Hertz");
        int freqMin = 1450;
        int aux = fqFino;
        if (fqFino > freqMin) {
            fqFino = aux - 100;
        } else {
            Toast.makeText(this, "Mínimo de 1400 Hz", Toast.LENGTH_SHORT).show();
        }
        freqFino.setText(String.valueOf(fqFino) + " Hertz");
    }
    public void finoUp(View view) {
        freqFino = (TextView) findViewById(R.id.freqFino);
        freqFino.setText(String.valueOf(fqFino) + " Hertz");
        int freqMax = 5000;
        int aux = fqFino;
        if (fqFino < freqMax) {
            fqFino = aux + 100;
        } else {
            Toast.makeText(this, "Máximo de 5000 Hz", Toast.LENGTH_SHORT).show();
        }
        freqFino.setText(String.valueOf(fqFino) + " Hertz");
    }

    //Incrementa e decrementa a duração do BEEP
    public void durMine(View view) {
        durBeep = (TextView) findViewById(R.id.duracaoBeep);
        durBeep.setText(String.valueOf(tempoSomFreq) + " mS");
        int durMin = 500;
        int aux = tempoSomFreq;
        if (tempoSomFreq > durMin) {
            tempoSomFreq = aux - 50;
        } else {
            Toast.makeText(this, "Mínimo de 500 mS", Toast.LENGTH_SHORT).show();
        }
        durBeep.setText(String.valueOf(tempoSomFreq) + " mS");
    }
    public void durMore(View view) {
        durBeep = (TextView) findViewById(R.id.duracaoBeep);
        durBeep.setText(String.valueOf(tempoSomFreq) + " mS");
        int durMax = 2000;
        int aux = tempoSomFreq;
        if (tempoSomFreq < durMax) {
            tempoSomFreq = aux + 50;
        } else {
            Toast.makeText(this, "Máximo de 2000 Hz", Toast.LENGTH_SHORT).show();
        }
        durBeep.setText(String.valueOf(tempoSomFreq) + " mS");
    }

    //Incrementa e decrementa o tempo da pausa entre os BIPs
    public void pauseMine(View view) {
        pausaBeep = (TextView) findViewById(R.id.pausaEntre);
        pausaBeep.setText(String.valueOf(pausaFreq) + " mS");
        int durMin = 500;
        int aux = pausaFreq;
        if (pausaFreq > durMin) {
            pausaFreq = aux - 50;
        } else {
            Toast.makeText(this, "Mínimo de 500 mS", Toast.LENGTH_SHORT).show();
        }
        pausaBeep.setText(String.valueOf(pausaFreq) + " mS");
    }
    public void pauseMore(View view) {
        pausaBeep = (TextView) findViewById(R.id.pausaEntre);
        pausaBeep.setText(String.valueOf(pausaFreq) + " mS");
        int durMax = 2000;
        int aux = pausaFreq;
        if (pausaFreq < durMax) {
            pausaFreq = aux + 50;
        } else {
            Toast.makeText(this, "Máximo de 2000 Hz", Toast.LENGTH_SHORT).show();
        }
        pausaBeep.setText(String.valueOf(pausaFreq) + " mS");
    }

    public void testeGrosso(View view) {
        grosso();
    }

    public void testeFino(View view) {
        fino();
    }

    public void testeDuracao(View view) {
        grosso();
    }

    public void pausaEntre(View view) {
        for (int i = 0; i < 4; i++){
            grosso();
            pause();
        }
    }

    // volta ao JOGO FREQUENCIA
    public void backGameFreq(View v){
        Intent intent = new Intent(this, freq_game.class);
        //startActivity(intent);
        intent.putExtra("fqGrosso", String.valueOf(fqGrosso));
        intent.putExtra("fqFino", String.valueOf(fqFino));
        intent.putExtra("pausaFreq", String.valueOf(pausaFreq));
        intent.putExtra("tempoSomFreq", String.valueOf(tempoSomFreq));
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.mover_direita, R.anim.mover_esquerda);
        ActivityCompat.startActivity(this,intent, activityOptionsCompat.toBundle());
    }

}