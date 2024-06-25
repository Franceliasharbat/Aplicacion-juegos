package com.example.fire;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fire.model.Reto;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Compras extends AppCompatActivity {

    private FirebaseFirestore db;
    private List<DocumentSnapshot> documentos;
    private int currentIndex = 0;
    private TextView retoTextView;
    private MediaPlayer mediaPlayer; // Declarar MediaPlayer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compras);

        db = FirebaseFirestore.getInstance();
        documentos = new ArrayList<>();
        retoTextView = findViewById(R.id.datoTextView);

        // Inicializar MediaPlayer con el sonido "reto.mp3"
        mediaPlayer = MediaPlayer.create(this, R.raw.reto);

        Button nextButton = findViewById(R.id.Listo);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reproducir el sonido al hacer clic en "Listo"
                mediaPlayer = MediaPlayer.create(Compras.this, R.raw.reto);
                mediaPlayer.start();
                mostrarSiguienteDato();
            }
        });

        Button irButton = findViewById(R.id.irV);
        irButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reproducir el sonido al hacer clic en "irV"
                mediaPlayer = MediaPlayer.create(Compras.this, R.raw.verdad);
                mediaPlayer.start();

                Intent intent = new Intent(Compras.this, Ingresos.class);
                startActivity(intent);
            }
        });

        cargarDatos();
    }

    private void cargarDatos() {
        db.collection("Reto")
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
            Reto reto = documentSnapshot.toObject(Reto.class);

            if (reto != null) {
                retoTextView.setText(reto.getReto());
            }

            currentIndex++;
        } else {
            currentIndex = 0; // Reiniciar para empezar de nuevo
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Liberar recursos del MediaPlayer
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
