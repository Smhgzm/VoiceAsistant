package com.example.smhgz.gizem;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private Button openMic;
    private TextView showVoiceText;
    private final int REQ_CODE_SPEECH_OUTPUT = 143;


    MediaPlayer mPlayer = new MediaPlayer();
    public boolean isPlaying = false;
    public boolean isPaused = false;
    int result;
    TextToSpeech t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showVoiceText = (TextView) findViewById(R.id.showVoiceOutput);

        openMic = (Button) findViewById(R.id.button);

        openMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnToOpenMic();
            }
        });

        t1 = new TextToSpeech(getApplicationContext(), new android.speech.tts.TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    result = t1.setLanguage(Locale.getDefault());
                } else {
                    Toast.makeText(getApplicationContext(), "Özellik desteklenmiyor", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void btnToOpenMic() {
        speechStandby();
    }

    void speechStandby() {
        if (isPlaying) {
            mPlayer.pause();
            isPaused = true;
        }
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Şimdi konuşun...");

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_OUTPUT);
        } catch (ActivityNotFoundException tim) {

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_OUTPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList voiceInText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String keyword = voiceInText.get(0).toString().toLowerCase();


                    if (keyword.contains("acil") || keyword.contains("durum") || keyword.contains("başım dertte")) {
                        Toast.makeText(this, "alarm", Toast.LENGTH_LONG).show();
                    }

                    else if (keyword.contains("neredeyim") || keyword.contains("kayboldum")) {
                        Toast.makeText(this, "maps aç", Toast.LENGTH_LONG).show();
                    }

                    else if (keyword.contains("konum gönder") && keyword.contains("kişi bir")) {
                        Toast.makeText(this, "konum bir", Toast.LENGTH_LONG).show();
                    }

                    else if (keyword.contains("konum gönder") && keyword.contains("kişi 2")) {
                        Toast.makeText(this, "konum iki", Toast.LENGTH_LONG).show();
                    }

                    else if (keyword.contains("konum gönder") && keyword.contains("kişi 3")) {
                        Toast.makeText(this, "konum üç", Toast.LENGTH_LONG).show();
                    }

                    else if (keyword.contains("ara") && keyword.contains("bir")) {
                        Toast.makeText(this, "ara bir", Toast.LENGTH_LONG).show();
                    }

                    else if (keyword.contains("ara") && (keyword.contains("2"))) {
                        Toast.makeText(this, "ara iki", Toast.LENGTH_LONG).show();
                    }

                    else if (keyword.contains("ara") && keyword.contains("3")) {
                        Toast.makeText(this, "ara üç", Toast.LENGTH_LONG).show();
                    }

                    else if (keyword.contains("hava")) {
                        Toast.makeText(this, "openweather", Toast.LENGTH_LONG).show();
                    }

                    else if (keyword.contains("saat kaç")) {
                        time();
                    }

                    else if (keyword.contains("gün")) {
                        date();
                    }

                }
            }
        }
    }

   public void toSpeak (String toSpeak){
        Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
        int speak = t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void time () {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm");
        String strTime =mdformat.format(calendar.getTime());
        toSpeak(strTime);
    }


    public void date () {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd-MMM-yyyy EEEE");
        String strDate = "bugün : " + mdformat.format(calendar.getTime());
        toSpeak(strDate);
    }
    }


