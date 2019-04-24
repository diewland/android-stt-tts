package com.example.stttts;

import android.app.Activity;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    /*
     * https://www.akexorcist.com/2013/08/android-code-string-speech-recognizer.html
     * https://www.akexorcist.com/2013/08/android-code-text-to-speech.html
     */

    int REQUEST_CODE_VOICE_RECOGNITION = 1001;

    EditText txt_msg;
    Button btn_transcribe;
    Button btn_speak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_msg = (EditText)findViewById(R.id.msg);
        btn_transcribe = (Button)findViewById(R.id.transcribe);
        btn_speak = (Button)findViewById(R.id.speak);

        // bind buttons
        btn_transcribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "th-TH");
                startActivityForResult(intent, REQUEST_CODE_VOICE_RECOGNITION);
            }
        });
        btn_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTTS.getInstance(getApplicationContext())
                    .setEngine("com.google.android.tts")
                    .setLocale(new Locale("th"))
                    .speak(txt_msg.getText().toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_VOICE_RECOGNITION && resultCode == Activity.RESULT_OK){
            List<String> results = data.getStringArrayListExtra (RecognizerIntent.EXTRA_RESULTS);
            String text = results.get(0);
            txt_msg.setText(text);
        }
    }
}
