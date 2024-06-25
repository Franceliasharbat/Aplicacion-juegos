package com.example.fire;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fire.model.Verdad;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ingresos extends AppCompatActivity {

    private FirebaseFirestore db;
    private List<DocumentSnapshot> documentos;
    private int currentIndex = 0;
    private TextView verdadTextView;
    private MediaPlayer mediaPlayerVerdad; // Declarar MediaPlayer para "verdad.mp3"
    private MediaPlayer mediaPlayerReto;   // Declarar MediaPlayer para "reto.mp3"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresos);

        db = FirebaseFirestore.getInstance();
        documentos = new ArrayList<>();
        verdadTextView = findViewById(R.id.datoTextView);

        // Inicializar MediaPlayer con el sonido "verdad.mp3"
        mediaPlayerVerdad = MediaPlayer.create(this, R.raw.verdad);

        // Inicializar MediaPlayer con el sonido "reto.mp3"
        mediaPlayerReto = MediaPlayer.create(this, R.raw.reto);

        Button nextButton = findViewById(R.id.ListoR);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reproducir el sonido al hacer clic en "ListoR"
                mediaPlayerVerdad.start();
                mostrarSiguienteDato();
            }
        });

        Button irButton = findViewById(R.id.irR);
        irButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reproducir el sonido al hacer clic en "irR"
                mediaPlayerReto.start();

                Intent intent = new Intent(Ingresos.this, Compras.class);
                startActivity(intent);
            }
        });

        cargarDatos();
    }

    private void cargarDatos() {
        db.collection("Verdad")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    documentos = queryDocumentSnapshots.getDocuments();
                    Collections.shuffle(documentos); // Mezclar los documentos aleatoriamente
                    if (!documentos.isEmpty()) {
                        mostrarSiguienteDato();
                    }
                });
    }

    private void mostrarSiguienteDato() {
        if (currentIndex < documentos.size()) {
            DocumentSnapshot documentSnapshot = documentos.get(currentIndex);
            Verdad verdad = documentSnapshot.toObject(Verdad.class);

            if (verdad != null) {
                verdadTextView.setText(verdad.getVerdad());
            }

            currentIndex++;
        } else {
            currentIndex = 0; // Reiniciar para empezar de nuevo
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Liberar recursos de los MediaPlayers
        if (mediaPlayerVerdad != null) {
            mediaPlayerVerdad.release();
            mediaPlayerVerdad = null;
        }
        if (mediaPlayerReto != null) {
            mediaPlayerReto.release();
            mediaPlayerReto = null;
        }
    }
}
