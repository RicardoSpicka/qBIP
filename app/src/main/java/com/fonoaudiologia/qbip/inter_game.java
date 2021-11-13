package com.fonoaudiologia.qbip;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import com.karlotoy.perfectune.instance.PerfectTune;

import java.util.Random;

public class inter_game extends AppCompatActivity {

    TextView placar2, titulo, config, voltar, anterior, proximo, jogardenovo, repete;
    int volume, respostaescolhida, goals, contador, error, beep, auxResp, auxContador, auxBeep, qtdade, frequencia, durBeep, pausaBeep;
    Button one, two, three, four, five, six;
    Boolean trava, reiniciar;
    Switch figFundo;
    MediaPlayer mp, certo, errado, fim;
    AudioManager audioManager;

    float volumeMaximo, auxvolume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        qtdade = 5; //quantidade de rodadas

        try {
            Intent intent = getIntent();
            String parametro1 = (String) intent.getSerializableExtra("pausaBeep");
            String parametro2 = (String) intent.getSerializableExtra("durBeep");
            String parametro3 = (String) intent.getSerializableExtra("frequencia");
            pausaBeep = Integer.valueOf(parametro1);
            durBeep = Integer.valueOf(parametro2);
            frequencia = Integer.valueOf(parametro3);
        } catch (Exception ex) {

            //seta as configurações iniciais
            volume = 100; //Volume do Beep Baixo
            pausaBeep = 200; // TEMPO DA PAUSA ENTRE OS BIPS
            durBeep = 300; // DURAÇÃO DO SOM BAIXO E ALTO
            frequencia = 1300;
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            auxBeep = beep;
        }

        volumeMaximo = 15;

        jogo();


    } // fecha o OBCREATE

    public void jogo(){
        setContentView(R.layout.game_inter);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        trava = true; // TRAVA O SONM DOS BOTÕES DE RESPOSTA ENQUANTO NÃO FOI TOCADO O PADRÃO SORTEADO
        goals = 0; // CONTA OS ACERTOS
        error = 0; // CONTA OS ERROS
        contador = 1; // CONTA A QUANTIDADE DE JOGADAS
        auxResp = 0;

        config = findViewById(R.id.configuracoes);
        jogardenovo = findViewById(R.id.denovo);
        repete = findViewById(R.id.repete);
        repete.setAlpha((float) 1);
        repete.setClickable(true);
        voltar = findViewById(R.id.voltar);

        titulo = findViewById(R.id.subtitulo);
        titulo.setText("FREQUÊNCIA: " + String.valueOf(frequencia) +  " Hz - INTERVALO: " + String.valueOf(pausaBeep) +  " mS");

        placar2 = findViewById(R.id.placar2);
        placar2.setText(String.valueOf(goals) + "  " + String.valueOf(error));
        placar2.setClickable(false);

        one = (Button) findViewById(R.id.one);
        two = (Button) findViewById(R.id.two);
        three = (Button) findViewById(R.id.three);
        four = (Button) findViewById(R.id.four);
        five = (Button) findViewById(R.id.five);
        six = (Button) findViewById(R.id.six);

        ativarBotoes();

        mp = MediaPlayer.create(this, R.raw.cafeteria);
        figFundo = (Switch) findViewById(R.id.figFundo);

        figFundo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    mp = MediaPlayer.create(getBaseContext(), R.raw.cafeteria);
                    mp.start();
                } else {
                    mp.stop();
                }

            }
        });

    }

//**************************************************************************************************************************************************
// BOTÃO ESCUTA OS SONS - É CHAMADO POR UMA VIEW
//**************************************************************************************************************************************************
    public void escutar(View view){

        if(auxContador == contador && auxBeep == beep && !reiniciar){

        } else {

            sortBeeps();
        }

        desativarBotoes();

        trava = true;

            switch (beep){
                case 1:
                    beep(); // 1
                    break;

                case 2:
                    beep();  //1
                    pause();
                    beep();  //2
                    break;

                case 3:
                    beep();  //1
                    pause();
                    beep();  //2
                    pause();
                    beep();  //3
                    break;

                case 4:
                    beep();  //1
                    pause();
                    beep();  //2
                    pause();
                    beep();  //3
                    pause();
                    beep();  //4
                    break;

                case 5:
                    beep();  //1
                    pause();
                    beep();  //2
                    pause();
                    beep();  //3
                    pause();
                    beep();  //4
                    pause();
                    beep(); //5
                    break;

                case 6:
                    beep();  //1
                    pause();
                    beep();  //2
                    pause();
                    beep();  //3
                    pause();
                    beep();  //4
                    pause();
                    beep(); //5
                    pause();
                    beep(); //6
                    break;
            }

        trava = false;
        esperaResposta(); // CHAMA A FUNÇÃO QUE FICA ESPERANDO AS RESPOSTAS

    } // FIM DO BOTÃO ESCUTAR

//**************************************************************************************************************************************************
// SORTEIA OS BEEpS -  É CHAMADO DENTRO DO ESCUTA SONS
//**************************************************************************************************************************************************
    public void sortBeeps(){

        beep = 0;

        if (contador > qtdade){
            Toast.makeText(this, "ACABOU ESSE JOGO", Toast.LENGTH_LONG).show();
            ativarBotoes();
            fim();
            repete.setAlpha((float) 0.3);
            repete.setClickable(false);
        }

                Random rand1 = new Random();
                beep = rand1.nextInt(5)+1;

        auxResp = 0;
        auxBeep = beep;
        reiniciar = false;
        auxContador = contador;
        respostaescolhida = 0;

    }   // FIM DO SORTEIA OS BEEPS

//**************************************************************************************************************************************************
// ESPERA A RESPOSTA -  É CHAMADO DENTRO DO ESCUTA SONS
//**************************************************************************************************************************************************
    public void esperaResposta(){

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trava == true) {
                    return;
                } else {
                    respostaescolhida = 1;
                    confereResposta();
                }
            }
        });

        two.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (trava == true) {
                    return;
                } else {
                    respostaescolhida = 2;
                    confereResposta();
                }
            }
        });

        three.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (trava == true) {
                    return;
                } else {
                    respostaescolhida = 3;
                    confereResposta();
                }
            }
        });

        four.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (trava == true) {
                    return;
                } else {
                    respostaescolhida = 4;
                    confereResposta();
                };
            }
        });

        five.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (trava == true) {
                    return;
                } else {
                    respostaescolhida = 5;
                    confereResposta();
                };
            }
        });

        six.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (trava == true) {
                    return;
                } else {
                    respostaescolhida = 6;
                    confereResposta();
                }
            }
        });

    } //FIM DO ESPERA REPOSTA

//**************************************************************************************************************************************************
// CONFERE A RESPOSTA -  É CHAMADO DENTRO DO ESPERA A RESPOSTA
//**************************************************************************************************************************************************
    public void confereResposta(){

        int aux3 = 0;

        if (respostaescolhida == beep) aux3 = 1;
        else aux3 = 2;

        switch (aux3) {

            case 1:
                goals++;
                contador++;
                placar2.setText((String.valueOf(goals)) + "  " + (String.valueOf(error)));
                sortBeeps();
                certo();
                break;

            case 2:
                error++;
                contador++;
                placar2.setText(String.valueOf(goals) + "  " + String.valueOf(error));
                sortBeeps();
                errado();
                break;

        }
        trava = true;

    } // FIM DO CONFERE RESPOSTA

//**************************************************************************************************************************************************
// CONSTROI OS SONS DOS BIPS E DA PAUSA
//**************************************************************************************************************************************************
    public void beep (){
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

//**************************************************************************************************************************************************
// CHAMA A TELA DE CONGIFURAÇÃO E ALTERA OS PARÂMETROS
//**************************************************************************************************************************************************
    public void config(View view) {

        Intent intent = new Intent(this, conf_inter_game.class);
        intent.putExtra("pausaBeep", String.valueOf(pausaBeep));
        intent.putExtra("durBeep", String.valueOf(durBeep));
        intent.putExtra("frequencia", String.valueOf(frequencia));
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);
        ActivityCompat.startActivity(this,intent, activityOptionsCompat.toBundle());
    }

    // reinicia o jogo no mesmo numero de beeps
    public void reinicia(View v){
        reiniciar = true;
        mp.release();
        trava = false;
        jogo();

    }

    //volta ao MENU PRINCIPAL
    public void backMain(View view) {
        super.finish();
        Intent intent = new Intent(this, Main_Activity.class);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);
        ActivityCompat.startActivity(this,intent, activityOptionsCompat.toBundle());
    }

    public void ativarBotoes(){
        config.setAlpha((float) 1.0);
        voltar.setAlpha((float) 1.0);
        config.setClickable(true);
        voltar.setClickable(true);
    }

    public void desativarBotoes(){
        config.setAlpha((float) 0.3);
        config.setClickable(false);
        voltar.setAlpha((float) 0.3);
        voltar.setClickable(false);
    }

    public void certo(){
        certo = MediaPlayer.create(this, R.raw.certo);
        certo.start(); // toca o som de acerto
    }

    public void errado(){
        errado = MediaPlayer.create(this, R.raw.errado);
        errado.start(); // toca o som de acerto
    }

    public void fim(){
        fim = MediaPlayer.create(this, R.raw.fim);
        fim.start(); // toca o som de acerto
    }

}