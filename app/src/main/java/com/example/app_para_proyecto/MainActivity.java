package com.example.app_para_proyecto;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView listViewOptions;
    private TextView cerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            // Cambiar el color de la barra de estado
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.customColor));
            // Cambiar el color de la barra de navegación (abajo)
            window.setNavigationBarColor(getResources().getColor(R.color.customColor2));
        }

        listViewOptions = findViewById(R.id.listView1);
        cerrarSesion = findViewById(R.id.cerrarSesion);

        // Definir las opciones para el ListView
        String[] options = {
                "Quiénes somos",
                "Cuándo son las elecciones",
                "Cómo votar",
                "Dónde votar",
                "Por quién votar",
                "Dudas o sugerencias"
        };

        // Crear un ArrayAdapter para llenar el ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, options);
        listViewOptions.setAdapter(adapter);

        // Manejar el clic en cada opción de la lista
        listViewOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = options[position];
                // Mostrar un mensaje cuando se hace clic en una opción
                Toast.makeText(MainActivity.this, "Seleccionaste: " + selectedItem, Toast.LENGTH_SHORT).show();

                // Aquí podrías agregar lógica para navegar a otra pantalla dependiendo de la opción seleccionada
            }
        });

        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Realiza la acción cuando se haga clic en el TextView
                Toast.makeText(MainActivity.this, "¡Has cerrado sesión exitosamente!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Finalizar la actividad para evitar volver al login
            }
        });

    }
}