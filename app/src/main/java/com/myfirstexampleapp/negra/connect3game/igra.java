package com.myfirstexampleapp.negra.connect3game;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

//Glavni activty. Logika cele igre.
public class igra extends AppCompatActivity {
    int[] IMAGES=new int[]{R.drawable.captain,R.drawable.dp,R.drawable.groot,R.drawable.harley,
            R.drawable.hela,R.drawable.ironman,R.drawable.puz,R.drawable.scarlet,
            R.drawable.women};
    int index1;
    int index2;
    // 0 - negra, 1 - ivona, 2-empty
     int  activePlayer=0;
     int[] gameState = {2,2,2,
                        2,2,2,
                        2,2,2};
     int[][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},
                                 {0,3,6},{1,4,7},{2,5,8},
                                 {0,4,8},{2,4,6}};
     boolean gameActive = true;
    String ime1;
    String ime2;

    public void dropIn(View view){
        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[tappedCounter]==2 && gameActive==true) {
                gameState[tappedCounter] = activePlayer;
                if (activePlayer == 0) {  //Postavlja sliku lika na kliknuto polje, sa leva na desno
                    counter.setImageResource(IMAGES[index1 - 1]);
                    counter.setTranslationX(-1100);
                    counter.animate().translationXBy(1100).rotation(36000).setDuration(800);
                    activePlayer = 1;
                } else {  //Postavlja sliku lika na kliknuto polje, sa desna na levo
                    counter.setImageResource(IMAGES[index2 - 1]);
                    counter.setTranslationX(1100);
                    counter.animate().translationXBy(-1100).rotation(36000).setDuration(800);
                    activePlayer = 0;
                }

                TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
                Button tryAgainButton = (Button) findViewById(R.id.tryAgainButton);
                String winner = "";

                for (int[] winningPosition : winningPositions) {
                    //Proverava da li je neki igrac postigao pobednicku kombinaciju
                    if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {
                        //Zaustavlja igru, proverava ko je pobednik i ispisuje to
                        gameActive = false;
                        if (activePlayer == 1) {
                            winner = ime1;
                        } else {
                            winner = ime2;
                        }
                    }
                }

                //prebrojava poteze, kako bi zaustavio igru kad je nereseno
                int brojPoteza = 0;
                for (int i = 0; i < 9; i++) {
                    if (gameState[i] != 2) {
                        brojPoteza++;
                    }
                }

                //Kraj igre. Prikazuje ko je pobedio i postavlja dugme za novu igru
                if (winner != "") {
                    winnerTextView.setText("KRAJ! \n" + winner + " je pobednik! ");
                    tryAgainButton.setVisibility(View.VISIBLE);
                    winnerTextView.setVisibility(View.VISIBLE);
                } else if (brojPoteza == 9 && winner == "") {
                    gameActive = false;
                    winnerTextView.setText("KRAJ! \n Nema pobednika! ");
                    tryAgainButton.setVisibility(View.VISIBLE);
                    winnerTextView.setVisibility(View.VISIBLE);
                }
        }
    }

    //Dugme koje pokrece igru ponovo
    public void tryAgain(View view){
        Button tryAgainButton = (Button) findViewById(R.id.tryAgainButton);
        TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
        tryAgainButton.setVisibility(View.INVISIBLE);
        winnerTextView.setVisibility(View.INVISIBLE);

        //Sklanja slike sa polja, brise stare poteze
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount();i++){
            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            counter.setImageDrawable(null);
        }

        //Postavlja sve na pocetno stanje
        activePlayer=0;
        for (int i=0; i<gameState.length;i++){
            gameState[i]=2;
        }
        gameActive = true;
    }

    public void preuzmiOdIgraActivity(){
        //Preuzima podatke koje su igraci uneli na predhodnom activity
        //Ime i lik prvog igraca, Ime i lik drugog igraca
        if (getIntent().hasExtra("com.myfirstexampleapp.negra.connect3game.IME1")){
            TextView textViewIme1=(TextView)findViewById(R.id.textViewIme1);
            ime1=getIntent().getExtras().getString("com.myfirstexampleapp.negra.connect3game.IME1");
            textViewIme1.setText(ime1);
        }
        if (getIntent().hasExtra("com.myfirstexampleapp.negra.connect3game.IME2")){
            TextView textViewIme2=(TextView)findViewById(R.id.textViewIme2);
            ime2=getIntent().getExtras().getString("com.myfirstexampleapp.negra.connect3game.IME2");
            textViewIme2.setText(ime2);
        }
        if (getIntent().hasExtra("com.myfirstexampleapp.negra.connect3game.SLIKA1")){
            ImageView imageViewSlika1=(ImageView)findViewById(R.id.imageViewSlika1);
            index1=getIntent().getExtras().getInt("com.myfirstexampleapp.negra.connect3game.SLIKA1");
            imageViewSlika1.setImageResource(IMAGES[index1-1]);
        }
        if (getIntent().hasExtra("com.myfirstexampleapp.negra.connect3game.SLIKA2")){
            ImageView imageViewSlika2=(ImageView)findViewById(R.id.imageViewSlika2);
            index2=getIntent().getExtras().getInt("com.myfirstexampleapp.negra.connect3game.SLIKA2");
            imageViewSlika2.setImageResource(IMAGES[index2-1]);
        }
    }

    public void muzika(){
        //pokrece muziku u pozadini
        MediaPlayer pesma= MediaPlayer.create(igra.this,R.raw.avengers);
        pesma.setLooping(true);
        pesma.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.igra);

        preuzmiOdIgraActivity();
        muzika();
    }
}
