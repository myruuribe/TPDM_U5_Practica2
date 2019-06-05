package mx.edu.ittepic.michel.tpdm_practica2_u5;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class Main3Activity extends AppCompatActivity {
    EditText telephone;
    DatabaseReference serviciofirebase;
    Button retar;
    String telefonoactual ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        telephone=findViewById(R.id.telefono2);
        retar=findViewById(R.id.btnretar);

        telefonoactual = getIntent().getStringExtra("telefono");

        serviciofirebase=FirebaseDatabase.getInstance().getReference();
        retocreado();
        retar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarreto();
            }
        });
    }

    public void insertarreto(){
        String phone= telephone.getText().toString();

        Reto reto= new Reto(phone);
        serviciofirebase.child("Reto").removeValue();
        serviciofirebase.child("Reto").push().setValue(phone).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(Main3Activity.this,"SE CREO RETO",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Main3Activity.this,"ERROR",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void retocreado(){
        serviciofirebase.child("Reto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot locationSnapshot : dataSnapshot.getChildren()) {
                    String telefono = locationSnapshot.getValue().toString();
                    if(telefono.equals(telefonoactual)){
                        Intent pantallaRetado = new Intent(Main3Activity.this,Main4Activity.class);
                        Main3Activity.this.startActivity(pantallaRetado);
                        Toast.makeText(Main3Activity.this,"Has sido retado",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
