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

public class vol_game extends AppCompatActivity {

    TextView placar2, titulo, config, voltar, anterior, proximo, jogardenovo, repete;
    int vlBaixo, vlAlto, primeiro, segundo, terceiro, quarto, quinto,
                  respPrimeiro, respSegundo, respTerceiro, respQuarto, respQuinto,
                  goals, contador, error, beep, auxResp, auxContador, auxBeep, qtdade, frequencia, durBeep, pausaBeep;
    Button baixo, alto;
    Boolean trava, reiniciar;
    Switch figFundo;
    MediaPlayer mp, certo, errado, fim;
    AudioManager audioManager;

    float volumeMaximo, auxvlAlto, auxvlBaixo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        beep = 2;
        qtdade = 5;

        try {
            Intent intent = getIntent();
            String parametro1 = (String) intent.getSerializableExtra("vlBaixo");
            String parametro2 = (String) intent.getSerializableExtra("vlAlto");
            String parametro3 = (String) intent.getSerializableExtra("pausaBeep");
            String parametro4 = (String) intent.getSerializableExtra("durBeep");
            String parametro5 = (String) intent.getSerializableExtra("frequencia");
            vlBaixo = Integer.valueOf(parametro1);
            vlAlto = Integer.valueOf(parametro2);
            pausaBeep = Integer.valueOf(parametro3);
            durBeep = Integer.valueOf(parametro4);
            frequencia = Integer.valueOf(parametro5);
        } catch (Exception ex) {

            //seta as configurações iniciais
            vlBaixo = 40; //Volume do Beep Baixo
            vlAlto = 80; // Volume do Beep Alto
            pausaBeep = 500; // TEMPO DA PAUSA ENTRE OS BIPS
            durBeep = 800; // DURAÇÃO DO SOM BAIXO E ALTO
            frequencia = 1300;
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            volumeMaximo = 15;
            auxBeep = beep;
        }

        jogo();


    } // fecha o OBCREATE

    public void jogo(){
        setContentView(R.layout.game_vol);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        trava = true; // TRAVA O SONM DOS BOTÕES DE RESPOSTA ENQUANTO NÃO FOI TOCADO O PADRÃO SORTEADO
        goals = 0; // CONTA OS ACERTOS
        error = 0; // CONTA OS ERROS
        contador = 1; // CONTA A QUANTIDADE DE JOGADAS
        auxResp = 0;

        config = findViewById(R.id.configuracoes);
        voltar = findViewById(R.id.voltar);
        anterior = findViewById(R.id.anterior);
        proximo = findViewById(R.id.proximo);
        jogardenovo = findViewById(R.id.denovo);
        repete = findViewById(R.id.repete);
        repete.setAlpha((float) 1);
        repete.setClickable(true);

        titulo = findViewById(R.id.subtitulo);
        titulo.setText("baixo/alto    -     " + String.valueOf(beep) +  " Bips");

        placar2 = findViewById(R.id.placar2);
        placar2.setText(String.valueOf(goals) + "  " + String.valueOf(error));
        placar2.setClickable(false);

        baixo = (Button) findViewById(R.id.game_baixo);
        alto = (Button) findViewById(R.id.game_alto);

        ativarBotoes();

        mp = MediaPlayer.create(this, R.raw.cafeteria);
        figFundo = (Switch) findViewById(R.id.figFundo);

        figFundo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Toast.makeText(getBaseContext(), "NESSA MODALIDADE NÃO TEM FIGURA FUNDO!", Toast.LENGTH_SHORT).show();
                figFundo.setChecked(false);

            }
        });

    }

//**************************************************************************************************************************************************
// BOTÃO ESCUTA OS SONS - É CHAMADO POR UMA VIEW
//**************************************************************************************************************************************************
    public void escutar(View view){

        if(auxContador == contador && auxBeep == beep && !reiniciar){

        } else {

            sortSons();
        }

        desativarBotoes();

        trava = true;

            switch (primeiro){
                case 1:
                    baixo();
                    pause();
                    break;

                case 2:
                    alto();
                    pause();
                    break;
            }

            switch (segundo){
                case 1:
                    baixo();
                    pause();
                    break;

                case 2:
                    alto();
                    pause();
                    break;
            }

            if (beep >= 3){
                switch (terceiro){
                    case 1:
                        baixo();
                        pause();
                        break;

                    case 2:
                        alto();
                        pause();
                        break;
                }
            }

        if (beep >= 4){
            switch (quarto){
                case 1:
                    baixo();
                    pause();
                    break;

                case 2:
                    alto();
                    pause();
                    break;
            }
        }

        if (beep >= 5){
            switch (quinto){
                case 1:
                    baixo();
                    pause();
                    break;

                case 2:
                    alto();
                    pause();
                    break;
            }
        }

        trava = false;
        esperaResposta(); // CHAMA A FUNÇÃO QUE FICA ESPERANDO AS RESPOSTAS

    } // FIM DO BOTÃO ESCUTAR

//**************************************************************************************************************************************************
// SORTEIA OS SONS -  É CHAMADO DENTRO DO ESCUTA SONS
//**************************************************************************************************************************************************
    public void sortSons(){

        primeiro = segundo = terceiro = quarto = quinto = 0;

        if (contador > qtdade){
            Toast.makeText(this, "ACABOU ESSE JOGO", Toast.LENGTH_LONG).show();
            ativarBotoes();
            fim();
            repete.setAlpha((float) 0.3);
            repete.setClickable(false);
        }

        switch (beep) {

            case 2:
                Random rand1 = new Random();
                primeiro = rand1.nextInt(2)+1;

                Random rand2 = new Random();
                segundo = rand2.nextInt(2)+1;
                break;

            case 3:
                Random rand3 = new Random();
                primeiro = rand3.nextInt(2)+1;

                Random rand4 = new Random();
                segundo = rand4.nextInt(2)+1;

                Random rand5 = new Random();
                terceiro = rand5.nextInt(2)+1;
                break;

            case 4:
                Random rand6 = new Random();
                primeiro = rand6.nextInt(2)+1;

                Random rand7 = new Random();
                segundo = rand7.nextInt(2)+1;

                Random rand8 = new Random();
                terceiro = rand8.nextInt(2)+1;

                Random rand9 = new Random();
                quarto = rand9.nextInt(2)+1;
                break;


            case 5:
                Random rand10 = new Random();
                primeiro = rand10.nextInt(2)+1;

                Random rand11 = new Random();
                segundo = rand11.nextInt(2)+1;

                Random rand12 = new Random();
                terceiro = rand12.nextInt(2)+1;

                Random rand13 = new Random();
                quarto = rand13.nextInt(2)+1;

                Random rand14 = new Random();
                quinto = rand14.nextInt(2)+1;
                break;

        }

        auxResp = 0;
        auxBeep = beep;
        reiniciar = false;
        auxContador = contador;
        respPrimeiro = respSegundo = respTerceiro = respQuarto = respQuinto = 0;

    }   // FIM DO SORTEIA OS SONS

//**************************************************************************************************************************************************
// ESPERA A RESPOSTA -  É CHAMADO DENTRO DO ESCUTA SONS
//**************************************************************************************************************************************************
    public void esperaResposta(){

        //sortSons(); // CHAMA O SORTEADOR DO PADRÃO
        baixo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (trava == true) {
                    return;
                } else {
                    baixo();
                    pause();
                    auxResp++;

                    switch (beep){
                        case 2:
                            if (respPrimeiro == 0) {
                                respPrimeiro = 1;
                            } else  {
                                respSegundo = 1;
                            }
                            break;

                        case 3:
                            if (respPrimeiro == 0) {
                                respPrimeiro = 1;
                            } else if (respSegundo == 0) {
                                respSegundo = 1;
                            } else {
                                respTerceiro = 1;
                            }
                            break;

                        case 4:
                            if (respPrimeiro == 0) {
                                respPrimeiro = 1;
                            } else if (respSegundo == 0) {
                                respSegundo = 1;
                            } else if (respTerceiro == 0) {
                                respTerceiro = 1;
                            } else {
                                respQuarto = 1;
                            }
                            break;

                        case 5:
                            if (respPrimeiro == 0) {
                                respPrimeiro = 1;
                            } else if (respSegundo == 0) {
                                respSegundo = 1;
                            } else if (respTerceiro == 0) {
                                respTerceiro = 1;
                            } else if (respQuarto == 0) {
                                respQuarto = 1;
                            } else {
                                respQuinto = 1;
                            }
                            break;

                    }
                    if (auxResp == beep) {
                        trava = true;
                        confereResposta();
                    }
                }

            }
        });


        alto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trava == true) {
                    return;
                } else {
                    alto();
                    pause();
                    auxResp++;

                        switch (beep){
                            case 2:
                                if (respPrimeiro == 0) {
                                respPrimeiro = 2;
                                } else  {
                                    respSegundo = 2;
                                }
                                break;

                            case 3:
                                if (respPrimeiro == 0) {
                                    respPrimeiro = 2;
                                } else if (respSegundo == 0) {
                                    respSegundo = 2;
                                } else {
                                    respTerceiro = 2;
                                }
                                break;

                            case 4:
                                if (respPrimeiro == 0) {
                                    respPrimeiro = 2;
                                } else if (respSegundo == 0) {
                                    respSegundo = 2;
                                } else if (respTerceiro == 0) {
                                    respTerceiro = 2;
                                } else {
                                    respQuarto = 2;
                                }
                                break;

                            case 5:
                                if (respPrimeiro == 0) {
                                    respPrimeiro = 2;
                                } else if (respSegundo == 0) {
                                    respSegundo = 2;
                                } else if (respTerceiro == 0) {
                                    respTerceiro = 2;
                                } else if (respQuarto == 0) {
                                    respQuarto = 2;
                                } else {
                                    respQuinto = 2;
                                }
                                break;

                        }

                    if (auxResp == beep) {
                        trava = true;
                        confereResposta();
                    }

                }

            }
        });

    } //FIM DO ESPERA REPOSTA

//**************************************************************************************************************************************************
// CONFERE A RESPOSTA -  É CHAMADO DENTRO DO ESPERA A RESPOSTA
//**************************************************************************************************************************************************
    public void confereResposta(){

        int aux3 = 0;

        switch (beep) {
            case 2:
                if (respPrimeiro == primeiro && respSegundo == segundo){
                    aux3 = 1;
                } else aux3 = 2;
                break;

            case 3:
                if (respPrimeiro == primeiro && respSegundo == segundo && respTerceiro == terceiro){
                    aux3 = 1;
                } else aux3 = 2;
                break;

            case 4:
                if (respPrimeiro == primeiro && respSegundo == segundo && respTerceiro == terceiro && respQuarto == quarto){
                    aux3 = 1;
                } else aux3 = 2;
                break;

            case 5:
                if (respPrimeiro == primeiro && respSegundo == segundo && respTerceiro == terceiro && respQuarto == quarto && respQuinto == quinto){
                    aux3 = 1;
                } else aux3 = 2;
                break;
        }

        switch (aux3) {

            case 1:
                goals++;
                contador++;
                placar2.setText((String.valueOf(goals)) + "  " + (String.valueOf(error)));
                sortSons();
                certo();
                break;

            case 2:
                error++;
                contador++;
                placar2.setText(String.valueOf(goals) + "  " + String.valueOf(error));
                sortSons();
                errado();
                break;

        }
        trava = true;

    } // FIM DO CONFERE RESPOSTA

//**************************************************************************************************************************************************
// CONSTROI OS SONS DOS BIPS E DA PAUSA
//**************************************************************************************************************************************************
    public void baixo (){
        PerfectTune perfectTune = new PerfectTune();
        perfectTune.setTuneFreq(frequencia);
        auxvlBaixo = (Float.valueOf(vlBaixo) / 100) * volumeMaximo;
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int) auxvlBaixo,0);
        perfectTune.playTune();
        try {
            Thread.sleep (durBeep);
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
    public void configBaixoAlto(View view) {

        Intent intent = new Intent(this, conf_vol_game.class);
        intent.putExtra("vlBaixo", String.valueOf(vlBaixo));
        intent.putExtra("vlAlto", String.valueOf(vlAlto));
        intent.putExtra("pausaBeep", String.valueOf(pausaBeep));
        intent.putExtra("durBeep", String.valueOf(durBeep));
        intent.putExtra("frequencia", String.valueOf(frequencia));
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out);
        ActivityCompat.startActivity(this,intent, activityOptionsCompat.toBundle());
    }


    //Incrementa e decrementa o numero de BIPs
    public void beepmore(View view) {
        if (beep == 4){
            beep++;
            contador = 1;
            jogo();
            proximo.setAlpha((float) 0.3);
            proximo.setClickable(false);
        } else {
            beep++;
            contador = 1;
            jogo();
        }     }
    public void beepmine(View view) {
        if (beep == 3){
            beep--;
            contador = 1;
            jogo();
            anterior.setAlpha((float) 0.3);
            anterior.setClickable(false);
        } else {
            beep--;
            contador = 1;
            jogo();
        }     }

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

        if (beep == 2) {
            anterior.setAlpha((float) 0.3);
            anterior.setClickable(false);
        } else {
            anterior.setAlpha((float) 1.0);
            anterior.setClickable(true);
        }

        if (beep == 5) {
            proximo.setAlpha((float) 0.3);
            proximo.setClickable(false);
        } else {
            proximo.setAlpha((float) 1.0);
            proximo.setClickable(true);
        }
    }

    public void desativarBotoes(){
        config.setAlpha((float) 0.3);
        config.setClickable(false);
        voltar.setAlpha((float) 0.3);
        voltar.setClickable(false);
        anterior.setAlpha((float) 0.3);
        anterior.setClickable(false);
        proximo.setAlpha((float) 0.3);
        proximo.setClickable(false);
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