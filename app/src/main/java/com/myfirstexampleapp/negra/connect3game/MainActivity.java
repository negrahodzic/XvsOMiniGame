package com.myfirstexampleapp.negra.connect3game;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public void igraj(){
        // Dugme koje prebacuje na activity gde se bira ime i lik igraca
        Button igrajBtn= (Button) findViewById(R.id.igrajBtn);
        igrajBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIntent= new Intent(getApplicationContext(), igraActivity.class);
                startActivity(startIntent);
            }
        });
    }

    public void pravila(){
        //Dugme koje prosledjuje igraca na wikipediju gde moze procitati upustvo za XvsO igru
        Button pravilaBtn= (Button) findViewById(R.id.pravilaBtn);
        pravilaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pravila="https://en.wikipedia.org/wiki/Tic-tac-toe";
                Uri adresa=Uri.parse(pravila);

                Intent posetiPravila= new Intent(Intent.ACTION_VIEW, adresa);

                //Proveri da li postoji aplikacija koja moze da nam ode na internet
                if(posetiPravila.resolveActivity(getPackageManager()) != null) {
                    startActivity(posetiPravila);
                }else {
                    Toast.makeText(MainActivity.this, "Nismo pronasli aplikaciju za pristup internet strani.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void muzika(){
        //pokrece muziku u pozadini
        MediaPlayer pesma= MediaPlayer.create(MainActivity.this,R.raw.wonderwoman);
        pesma.setLooping(true);
        pesma.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        igraj();
        pravila();
        muzika();

    }
}
