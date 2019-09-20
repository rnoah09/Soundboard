package com.example.soundboard;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;

import java.io.File;

public class SoundBoardActivity extends AppCompatActivity {
    private SoundPool soundPool;
    private int soundID;
    boolean loaded = false;
    private Button buttonRight;
    private Button buttonLeft;
    private Button buttonA;
    private Button buttonBFlat;
    private Button buttonB;
    private Button buttonC;
    private Button buttonCSharp;
    private Button buttonD;
    private Button buttonDSharp;
    private Button buttonE;
    private Button buttonF;
    private Button buttonFSharp;
    private Button buttonG;
    private Button buttonGSharp;
    private SeekBar pitchBar;
    private Switch otamatoneSoundBoardSwitch;
    private int seekBarProgress;
    private float volumePlaceholder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wireWidgets();
        setListeners();
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId,
                                       int status) {
                loaded = true;
            }
        });
        soundID = soundPool.load(this, R.raw.beep2, 1);


    }

    private void wireWidgets() {
        buttonLeft = findViewById(R.id.button_buttonLeft_main);
        buttonRight = findViewById(R.id.button_buttonRight_main);
        pitchBar = findViewById(R.id.seekbar_pitch_main);
        otamatoneSoundBoardSwitch = findViewById(R.id.switch_otamatoneboard_main);

        buttonA = findViewById(R.id.button_a_main);
        buttonB = findViewById(R.id.button_b_main);
        buttonBFlat = findViewById(R.id.button_bflat_main);
        buttonC = findViewById(R.id.button_c_main);
        buttonCSharp = findViewById(R.id.button_csharp_main);
        buttonD = findViewById(R.id.button_d_main);
        buttonDSharp = findViewById(R.id.button_dsharp_main);
        buttonE = findViewById(R.id.button_e_main);
        buttonF = findViewById(R.id.button_f_main);
        buttonFSharp = findViewById(R.id.button_fsharp_main);
        buttonG = findViewById(R.id.button_g_main);
        buttonGSharp = findViewById(R.id.button_gsharp_main);

    }

    @SuppressLint("ClickableViewAccessibility")
    private void setListeners() {

        pitchBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekBarProgress = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });

        otamatoneSoundBoardSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    buttonLeft.setVisibility(View.GONE);
                    buttonRight.setVisibility(View.GONE);
                    pitchBar.setVisibility(View.GONE);

                    buttonA.setVisibility(View.VISIBLE);
                    buttonB.setVisibility(View.VISIBLE);
                    buttonBFlat.setVisibility(View.VISIBLE);
                    buttonC.setVisibility(View.VISIBLE);
                    buttonCSharp.setVisibility(View.VISIBLE);
                    buttonD.setVisibility(View.VISIBLE);
                    buttonDSharp.setVisibility(View.VISIBLE);
                    buttonE.setVisibility(View.VISIBLE);
                    buttonF.setVisibility(View.VISIBLE);
                    buttonFSharp.setVisibility(View.VISIBLE);
                    buttonG.setVisibility(View.VISIBLE);
                    buttonGSharp.setVisibility(View.VISIBLE);
                }
                else{
                    buttonLeft.setVisibility(View.VISIBLE);
                    buttonRight.setVisibility(View.VISIBLE);
                    pitchBar.setVisibility(View.VISIBLE);

                    buttonA.setVisibility(View.GONE);
                    buttonB.setVisibility(View.GONE);
                    buttonBFlat.setVisibility(View.GONE);
                    buttonC.setVisibility(View.GONE);
                    buttonCSharp.setVisibility(View.GONE);
                    buttonD.setVisibility(View.GONE);
                    buttonDSharp.setVisibility(View.GONE);
                    buttonE.setVisibility(View.GONE);
                    buttonF.setVisibility(View.GONE);
                    buttonFSharp.setVisibility(View.GONE);
                    buttonG.setVisibility(View.GONE);
                    buttonGSharp.setVisibility(View.GONE);
                }

            }
        });

        buttonLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL || motionEvent.getAction() == MotionEvent.ACTION_BUTTON_RELEASE){

                    soundPool.autoPause();
                    Log.e("Test", "Stopped sound");

                }

                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){

                    AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
                    float actualVolume = (float) audioManager
                            .getStreamVolume(AudioManager.STREAM_MUSIC);
                    float maxVolume = (float) audioManager
                            .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                    float volume = actualVolume / maxVolume;
                    volumePlaceholder = volume;

                    if (loaded) {
                        soundPool.play(soundID, volume, volume, 1, -1, (float) (((1.5 / 100.0) * seekBarProgress) + 0.5));

                        Log.e("Test", "Played sound");
                    }
                }

                return false;
            }
        });

        buttonRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL || motionEvent.getAction() == MotionEvent.ACTION_BUTTON_RELEASE){

                soundPool.autoPause();
                Log.e("Test", "Played sound");

                }

                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){

                    AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
                    float actualVolume = (float) audioManager
                            .getStreamVolume(AudioManager.STREAM_MUSIC);
                    float maxVolume = (float) audioManager
                            .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                    float volume = actualVolume / maxVolume;
                    volumePlaceholder = volume;

                    if (loaded) {
                        soundPool.play(soundID, volume, volume, 1, -1, (float) (((1.5 / 100.0) * seekBarProgress) + 0.5));

                        Log.e("Test", "Played sound");
                    }
                }

                return false;
            }
        });

    }

}