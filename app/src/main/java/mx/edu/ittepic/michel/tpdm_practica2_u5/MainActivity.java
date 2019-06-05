package mx.edu.ittepic.michel.tpdm_practica2_u5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button jugador1,jugador2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jugador1=findViewById(R.id.jugadoruno);
        jugador2=findViewById(R.id.jugadordos);

        jugador1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pantallainicio= new Intent(MainActivity.this,Main2Activity.class);
                pantallainicio.putExtra("jugador",1);
                MainActivity.this.startActivity(pantallainicio);
            }
        });

        jugador2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pantallainicio= new Intent(MainActivity.this,Main2Activity.class);
                pantallainicio.putExtra("jugador",2);
                MainActivity.this.startActivity(pantallainicio);
            }
        });


    }
}
