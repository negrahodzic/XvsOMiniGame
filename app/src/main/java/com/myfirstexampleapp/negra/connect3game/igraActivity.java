package com.myfirstexampleapp.negra.connect3game;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//Activity na kom se bira ime i lik igraca i zapocinje igra
public class igraActivity extends AppCompatActivity {

    int[] IMAGES={R.drawable.captain,R.drawable.dp,R.drawable.groot,R.drawable.harley,
            R.drawable.hela,R.drawable.ironman,R.drawable.puz,R.drawable.scarlet,
            R.drawable.women};
    String[] NAMES={"1. Captain America","2. Deadpool","3. Groot","4. Harley Quinn","5. Hela","6. Iron Man","7. Mantis",
            "8. Scarlet","9. Wonder Woman"};

    int prviSlika;
    int drugiSlika;
    String prviIme;
    String drugiIme;

    public void proveraPodataka(){
        //Klikom na dugme se proverava ispravnost podataka i pokrece igra u novom activity-u
        Button pokreniBtn = (Button) findViewById(R.id.pokreniBtn);
        pokreniBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText prviTextView = (EditText) findViewById(R.id.igracJedanEditText);
                EditText drugiTextView = (EditText) findViewById(R.id.igracDvaEditText);
                EditText editTextSlika1 = (EditText) findViewById(R.id.editTextSlika1);
                EditText editTextSlika2 = (EditText) findViewById(R.id.editTextSlika2);

                prviIme = prviTextView.getText().toString();
                drugiIme = drugiTextView.getText().toString();
                String prviSlika1 = editTextSlika1.getText().toString();
                String drugiSlika2 = editTextSlika2.getText().toString();

                //proverava da li su svi podaci uneti
                if (prviIme.matches("") || drugiIme.matches("") || prviSlika1.matches("") || drugiSlika2.matches("")) {
                    Toast.makeText(igraActivity.this, "Morate uneti sve podatke!", Toast.LENGTH_SHORT).show();
                } else {
                    prviSlika = Integer.parseInt(prviSlika1);
                    drugiSlika = Integer.parseInt(drugiSlika2);

                    //zabranjuje unosenje nedozvoljenih vrednosti
                    if (prviSlika<1||prviSlika>9|| drugiSlika>9 ||drugiSlika<1 ) {
                        Toast.makeText(igraActivity.this, "Morate uneti broj od 1 do 9.", Toast.LENGTH_SHORT).show();
                    } else if (prviSlika==drugiSlika){
                        Toast.makeText(igraActivity.this, "Morate izabrati razlicite likove.", Toast.LENGTH_SHORT).show();

                    } else {
                        pokreniIgru(prviSlika1, drugiSlika2);
                    }
                }
            }
        });
    }

    public void pokreniIgru(String prviSlika1, String drugiSlika2){

                //prosledjuje unete vrednosti activity-u gde se igra XvsO (igra.java)

                Intent startIntent = new Intent(getApplicationContext(), igra.class);

                startIntent.putExtra("com.myfirstexampleapp.negra.connect3game.IME1", prviIme);
                startIntent.putExtra("com.myfirstexampleapp.negra.connect3game.IME2", drugiIme);

                startIntent.putExtra("com.myfirstexampleapp.negra.connect3game.SLIKA1", prviSlika);
                startIntent.putExtra("com.myfirstexampleapp.negra.connect3game.SLIKA2", drugiSlika);

                startActivity(startIntent);
    }

    //pravljenje liste slika svih likova i njihovih imena
    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return IMAGES.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.customlayout, null); //pozivamo nas custom layout

            ImageView imageView = view.findViewById(R.id.imageView);
            TextView textView_name = view.findViewById(R.id.textView_name);

            imageView.setImageResource(IMAGES[i]);
            textView_name.setText(NAMES[i]);
            return view;
        }
    }

    public void listaLikova(){
        //postavlja scroll listu likova
        final ListView listView= findViewById(R.id.listView);
        final CustomAdapter customAdapter= new CustomAdapter();
        listView.setAdapter(customAdapter);
    }

    public void muzika(){
        //pokrece muziku u pozadini
        MediaPlayer pesma= MediaPlayer.create(igraActivity.this,R.raw.avengers);
        pesma.setLooping(true);
        pesma.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_igra);

        listaLikova();
        proveraPodataka();
        muzika();
    }
}

