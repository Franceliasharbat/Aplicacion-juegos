package com.example.fire;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtener referencia a los botones
        Button retosButton = findViewById(R.id.retosb);
        Button verdadButton = findViewById(R.id.verdadb);

        // Establecer OnClickListener para el botón retosb
        retosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar a ActivityIngresos
                Intent intent = new Intent(MainActivity.this, Ingresos.class);
                startActivity(intent);
            }
        });

        // Establecer OnClickListener para el botón verdadb
        verdadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar a ActivityCompras
                Intent intent = new Intent(MainActivity.this, Compras.class);
                startActivity(intent);
            }
        });
    }
}
