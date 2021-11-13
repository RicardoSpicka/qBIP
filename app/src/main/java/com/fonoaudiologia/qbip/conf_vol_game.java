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

public class conf_vol_game extends AppCompatActivity {

    TextView  volAlto, volBaixo, pausaEntre, duracaoBeep;
    int vlBaixo, vlAlto, tempoSom, pausaIntensidade, pausaBeep, durbeep, frequencia, currentVolume;

    float volumeMaximo, auxvlAlto, auxvlBaixo;

    SeekBar seekBarFreq;
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_vol);

        Intent intent = getIntent();
        String parametro1 = (String) intent.getSerializableExtra("vlBaixo");
        String parametro2 = (String) intent.getSerializableExtra("vlAlto");
        String parametro3 = (String) intent.getSerializableExtra("pausaBeep");
        String parametro4 = (String) intent.getSerializableExtra("durBeep");
        String parametro5 = (String) intent.getSerializableExtra("frequencia");
        vlBaixo = Integer.valueOf(parametro1);
        vlAlto = Integer.valueOf(parametro2);
        pausaBeep = Integer.valueOf(parametro3);
        durbeep = Integer.valueOf(parametro4);
        frequencia = Integer.valueOf(parametro5);

        volAlto = (TextView) findViewById(R.id.volAlto);
        volBaixo = (TextView) findViewById(R.id.volBaixo);
        duracaoBeep = (TextView) findViewById(R.id.duracaoBeep);
        pausaEntre = (TextView) findViewById(R.id.pausaEntre);

        volAlto.setText(String.valueOf(vlAlto) + " %");
        volBaixo.setText(String.valueOf(vlBaixo) + " %");
        duracaoBeep.setText(String.valueOf(durbeep) + " mS");
        pausaEntre.setText(String.valueOf(pausaBeep) + " mS");

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        volumeMaximo = 15;

    }

    // CONSTROI OS SONS DOS BIPs E PAUSA
    //BEEPS
    public void baixo(){
        PerfectTune perfectTune = new PerfectTune();
        perfectTune.setTuneFreq(frequencia);
        auxvlBaixo = (Float.valueOf(vlBaixo) / 100) * volumeMaximo;
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int) auxvlBaixo,0);
        perfectTune.playTune();
        try {
            Thread.sleep (durbeep);
        } catch (InterruptedException e) {}
        perfectTune.stopTune();
    }
    public void alto(){
        PerfectTune perfectTune = new PerfectTune();
        perfectTune.setTuneFreq(frequencia);
        auxvlAlto = (Float.valueOf(vlAlto) / 100) * volumeMaximo;
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int) auxvlAlto,0);
        perfectTune.playTune();
        try {
            Thread.sleep (durbeep);
        } catch (InterruptedException e) {}
        perfectTune.stopTune();
    }
    public void pause(){
        try {
            Thread.sleep (pausaBeep);
        } catch (InterruptedException e) {}
    } // FIM DOS SONS E DA PAUSA

    //Incrementa e decrementa a frequencia do GROSSO
    public void baixoMine(View view) {
        volBaixo = (TextView) findViewById(R.id.volBaixo);
        volBaixo.setText(String.valueOf(vlBaixo) + " %");
        int volMin = 20;
        int aux = vlBaixo;
        if (vlBaixo > volMin) {
            vlBaixo = aux - 5;
        } else {
            Toast.makeText(this, "Mínimo de 20%", Toast.LENGTH_SHORT).show();
        }
        volBaixo.setText(String.valueOf(vlBaixo) + " %");
    }
    public void baixoMore(View view) {
        volBaixo = (TextView) findViewById(R.id.volBaixo);
        volBaixo.setText(String.valueOf(vlBaixo) + " %");
        int volMax = 50;
        int aux = vlBaixo;
        if (vlBaixo < volMax) {
            vlBaixo = aux + 5;
        }else {
            Toast.makeText(this, "Máximo de 50 %", Toast.LENGTH_SHORT).show();
        }
        volBaixo.setText(String.valueOf(vlBaixo) + " %");
    }

    //Incrementa e decrementa a frequencia do FINO
    public void altoMine(View view) {
        volAlto = (TextView) findViewById(R.id.volAlto);
        volAlto.setText(String.valueOf(vlAlto) + " %");
        int volMin = 60;
        int aux = vlAlto;
        if (vlAlto > volMin) {
            vlAlto = aux - 5;
        } else {
            Toast.makeText(this, "Mínimo de 60 %", Toast.LENGTH_SHORT).show();
        }
        volAlto.setText(String.valueOf(vlAlto) + " %");
    }
    public void altoMore(View view) {
        volAlto = (TextView) findViewById(R.id.volAlto);
        volAlto.setText(String.valueOf(vlAlto) + " %");
        int volMax = 100;
        int aux = vlAlto;
        if (vlAlto < volMax) {
            vlAlto = aux + 5;
        } else {
            Toast.makeText(this, "Máximo de 100 %", Toast.LENGTH_SHORT).show();
        }
        volAlto.setText(String.valueOf(vlAlto) + " %");
    }

    //Incrementa e decrementa a duração do BEEP
    public void durMine(View view) {
        duracaoBeep = (TextView) findViewById(R.id.duracaoBeep);
        duracaoBeep.setText(String.valueOf(durbeep) + " mS");
        int durMin = 500;
        int aux = durbeep;
        if (durbeep > durMin) {
            durbeep = aux - 50;
        } else {
            Toast.makeText(this, "Mínimo de 500 mS", Toast.LENGTH_SHORT).show();
        }
        duracaoBeep.setText(String.valueOf(durbeep) + " mS");
    }
    public void durMore(View view) {
        duracaoBeep = (TextView) findViewById(R.id.duracaoBeep);
        duracaoBeep.setText(String.valueOf(durbeep) + " mS");
        int durMax = 2000;
        int aux = durbeep;
        if (durbeep < durMax) {
            durbeep = aux + 50;
        } else {
            Toast.makeText(this, "Máximo de 2000 Hz", Toast.LENGTH_SHORT).show();
        }
        duracaoBeep.setText(String.valueOf(durbeep) + " mS");
    }

    //Incrementa e decrementa o tempo da pausa entre os BIPs
    public void pauseMine(View view) {
        pausaEntre = (TextView) findViewById(R.id.pausaEntre);
        pausaEntre.setText(String.valueOf(pausaBeep) + " mS");
        int durMin = 500;
        int aux = pausaBeep;
        if (pausaBeep > durMin) {
            pausaBeep = aux - 50;
        } else {
            Toast.makeText(this, "Mínimo de 500 mS", Toast.LENGTH_SHORT).show();
        }
        pausaEntre.setText(String.valueOf(pausaBeep) + " mS");
    }
    public void pauseMore(View view) {
        pausaEntre = (TextView) findViewById(R.id.pausaEntre);
        pausaEntre.setText(String.valueOf(pausaBeep) + " mS");
        int durMax = 2000;
        int aux = pausaBeep;
        if (pausaBeep < durMax) {
            pausaBeep = aux + 50;
        } else {
            Toast.makeText(this, "Máximo de 2000 Hz", Toast.LENGTH_SHORT).show();
        }
        pausaEntre.setText(String.valueOf(pausaBeep) + " mS");
    }

    public void testeBaixo(View view) {
        baixo();
    }

    public void testeAlto(View view) {
        alto();
    }

    public void testeDuracao(View view) {
        baixo();
    }

    public void pausaEntre(View view) {
        for (int i = 0; i < 4; i++){
            baixo();
            pause();
        }
    }

    // volta ao JOGO INTENSIDADE
    public void backGameVol(View v){
        Intent intent = new Intent(this, vol_game.class);
        //startActivity(intent);
        intent.putExtra("vlBaixo", String.valueOf(vlBaixo));
        intent.putExtra("vlAlto", String.valueOf(vlAlto));
        intent.putExtra("pausaBeep", String.valueOf(pausaBeep));
        intent.putExtra("durBeep", String.valueOf(durbeep));
        intent.putExtra("frequencia", String.valueOf(frequencia));
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.mover_direita, R.anim.mover_esquerda);
        ActivityCompat.startActivity(this,intent, activityOptionsCompat.toBundle());
    }

}