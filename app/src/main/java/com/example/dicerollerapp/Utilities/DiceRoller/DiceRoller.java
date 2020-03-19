package com.example.dicerollerapp.Utilities.DiceRoller;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.example.dicerollerapp.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Random;

public class DiceRoller extends AppCompatActivity {

    private ImageView dice;
    private Random rng = new Random();
    private boolean rolling;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_roller);

        loadAd();

        dice = findViewById(R.id.dice);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }

    public void rollDice(View v) {
        rollDiceAnimation();
        int randomNumber = rng.nextInt(6) + 1; //Numero aleatorio de 1 a 6
        switch (randomNumber) {
            case 1:
                dice.setImageResource(R.drawable.dice1);
                break;
            case 2:
                dice.setImageResource(R.drawable.dice2);
                break;
            case 3:
                dice.setImageResource(R.drawable.dice3);
                break;
            case 4:
                dice.setImageResource(R.drawable.dice4);
                break;
            case 5:
                dice.setImageResource(R.drawable.dice5);
                break;
            case 6:
                dice.setImageResource(R.drawable.dice6);
                break;
        }
    }

    public void rollDiceAnimation() {
        if (!rolling) {
            final MediaPlayer mp = MediaPlayer.create(this, R.raw.dice_roll_sfx);
            mp.start();

            float pivotX = dice.getWidth() / 2;
            float pivotY = dice.getHeight() / 2;

            Animation rotate = new RotateAnimation(0, 360, pivotX, pivotY);
            rotate.setDuration(300);
            rotate.setFillAfter(true);

            rotate.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    rolling = true;
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    rolling = false;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });

            dice.startAnimation(rotate);
        }
    }

    private void loadAd() {
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build());
    }
}