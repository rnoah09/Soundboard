package com.example.soundboard;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.ToggleButton;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SoundBoardActivity extends AppCompatActivity  {
    private SoundPool soundPool;
    private SoundPool otamatoneSoundPool;
    private int soundID;
    boolean loaded = false;
    private Button buttonRight;
    private Button buttonLeft;
    private ToggleButton toggleButtonRecord;
    private Button buttonPlay;
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
    private Map<Integer, Integer> noteMap;
    private ArrayList<Note> noteArrayList;
    private int aNote;
    private int bbNote;
    private int bNote;
    private int cNote;
    private int cSharpNote;
    private int dNote;
    private int dSharpNote;
    private int eNote;
    private int fNote;
    private int fSharpNote;
    private int gNote;
    private int gSharpNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wireWidgets();
        initializeSoundPool();
        setListeners();
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);



    }

    private void delay(int millisDelay){
        try{
            Thread.sleep(millisDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

        buttonPlay = findViewById(R.id.button_play_main);
        toggleButtonRecord = findViewById(R.id.togglebutton_record_main);

    }

    private void initializeSoundPool() {
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {

            }
        });

        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        otamatoneSoundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        otamatoneSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded = true;
            }
        });

        soundID = otamatoneSoundPool.load(this, R.raw.beep2, 1);


        aNote = soundPool.load(this, R.raw.scalea, 1);
        bbNote = soundPool.load(this, R.raw.scalebb, 1);
        bNote = soundPool.load(this, R.raw.scaleb, 1);
        cNote = soundPool.load(this, R.raw.scalec, 1);
        cSharpNote = soundPool.load(this, R.raw.scalecs, 1);
        dNote = soundPool.load(this, R.raw.scaled, 1);
        dSharpNote = soundPool.load(this, R.raw.scaleds, 1);
        eNote = soundPool.load(this, R.raw.scalee, 1);
        fNote = soundPool.load(this, R.raw.scalef, 1);
        fSharpNote = soundPool.load(this, R.raw.scalefs, 1);
        gNote = soundPool.load(this, R.raw.scaleg, 1);
        gSharpNote = soundPool.load(this, R.raw.scalegs, 1);

        noteMap = new HashMap<>();
        noteMap.put(buttonA.getId(), aNote);
        noteMap.put(buttonBFlat.getId(), bbNote);
        noteMap.put(buttonB.getId(), bNote);
        noteMap.put(buttonC.getId(), cNote);
        noteMap.put(buttonCSharp.getId(), cSharpNote);
        noteMap.put(buttonD.getId(), dNote);
        noteMap.put(buttonDSharp.getId(), dSharpNote);
        noteMap.put(buttonE.getId(),eNote);
        noteMap.put(buttonF.getId(), fNote);
        noteMap.put(buttonFSharp.getId(), fSharpNote);
        noteMap.put(buttonG.getId(), gNote);
        noteMap.put(buttonGSharp.getId(),gSharpNote);

    }

    @SuppressLint("ClickableViewAccessibility")
    private void setListeners() {

        KeyboardListener keyboardListener = new KeyboardListener();
        buttonA.setOnClickListener(keyboardListener);
        buttonBFlat.setOnClickListener(keyboardListener);
        buttonB.setOnClickListener(keyboardListener);
        buttonC.setOnClickListener(keyboardListener);
        buttonCSharp.setOnClickListener(keyboardListener);
        buttonD.setOnClickListener(keyboardListener);
        buttonDSharp.setOnClickListener(keyboardListener);
        buttonE.setOnClickListener(keyboardListener);
        buttonF.setOnClickListener(keyboardListener);
        buttonFSharp.setOnClickListener(keyboardListener);
        buttonG.setOnClickListener(keyboardListener);
        buttonGSharp.setOnClickListener(keyboardListener);

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

                    buttonPlay.setVisibility(View.VISIBLE);
                    toggleButtonRecord.setVisibility(View.VISIBLE);
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

                    buttonPlay.setVisibility(View.GONE);
                    toggleButtonRecord.setVisibility(View.GONE);
                }

            }
        });
        final AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        buttonLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL || motionEvent.getAction() == MotionEvent.ACTION_BUTTON_RELEASE){

                    otamatoneSoundPool.autoPause();
                    Log.e("Test", "Stopped sound");

                }

                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_BUTTON_PRESS){

                    float actualVolume = (float) audioManager
                            .getStreamVolume(AudioManager.STREAM_MUSIC);
                    float maxVolume = (float) audioManager
                            .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                    float volume = actualVolume / maxVolume;
                    volumePlaceholder = volume;

                    if (loaded) {
                        otamatoneSoundPool.play(soundID, volume, volume, 1, -1, (float) (((1.5 / 100.0) * seekBarProgress) + 0.5));

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

                otamatoneSoundPool.autoPause();
                Log.e("Test", "Stopped sound");

                }

                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_BUTTON_PRESS){

                    float actualVolume = (float) audioManager
                            .getStreamVolume(AudioManager.STREAM_MUSIC);
                    float maxVolume = (float) audioManager
                            .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                    float volume = actualVolume / maxVolume;
                    volumePlaceholder = volume;

                    if (loaded) {
                        otamatoneSoundPool.play(soundID, volume, volume, 1, -1, (float) (((1.5 / 100.0) * seekBarProgress) + 0.5));

                        Log.e("Test", "Played sound");
                    }
                }

                return false;
            }
        });

    }

//    @Override
//    public void onClick(View view) {


//    }

    private class KeyboardListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            int songId = noteMap.get(view.getId());
            if(songId != 0){
                soundPool.play(songId,1,1,1,0,1);
            }
        }
    }


}