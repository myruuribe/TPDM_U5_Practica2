package mx.edu.ittepic.michel.tpdm_practica2_u5;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main2Activity extends AppCompatActivity {
    EditText nombre, telefono;
    Button btnentrar;
    DatabaseReference serviciofirebase;
    String telefonoactual="";
    int jugadoractual= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final int jugador = getIntent().getIntExtra("jugador",1);
        jugadoractual= jugador;
        btnentrar = findViewById(R.id.btnentrar);

        nombre=findViewById(R.id.nombre);
        telefono=findViewById(R.id.telefono);


        serviciofirebase=FirebaseDatabase.getInstance().getReference();

        btnentrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(jugador == 1){
                    insertarjugador1();
                }
                else{
                    insertarjugador2();
                }
            }
        });
    }

    public void insertarjugador1(){
        String name= nombre.getText().toString();
        final String phone= telefono.getText().toString();

        Jugador jugador= new Jugador(name,phone);
        serviciofirebase.child("Jugador1").removeValue();
        serviciofirebase.child("Jugador1").push().setValue(jugador).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                telefonoactual=phone;
                jugadorcreado();
                Toast.makeText(Main2Activity.this,"SE CREO JUGADOR",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Main2Activity.this, "ERROR",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void insertarjugador2(){
        String name= nombre.getText().toString();
        final String phone= telefono.getText().toString();

        Jugador jugador= new Jugador(name,phone);
        serviciofirebase.child("Jugador2").removeValue();
        serviciofirebase.child("Jugador2").push().setValue(jugador).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                telefonoactual=phone;
                jugadorcreado();
                Toast.makeText(Main2Activity.this,"SE CREO JUGADOR",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Main2Activity.this, "ERROR",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void jugadorcreado(){
        serviciofirebase.child("Jugador1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Jugador jugador = dataSnapshot.getValue(Jugador.class);
                if (jugadoractual==1){
                    Intent pantallareto = new Intent(Main2Activity.this,Main3Activity.class);
                    pantallareto.putExtra("telefono",telefonoactual);
                    Main2Activity.this.startActivity(pantallareto);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        serviciofirebase.child("Jugador2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Jugador jugador= dataSnapshot.getValue(Jugador.class);
                if (jugadoractual==2){
                    Intent pantallareto = new Intent(Main2Activity.this,Main3Activity.class);
                    pantallareto.putExtra("telefono",telefonoactual);
                    Main2Activity.this.startActivity(pantallareto);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
