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
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.customColor));
            window.setNavigationBarColor(getResources().getColor(R.color.customColor2));
        }

        listViewOptions = findViewById(R.id.listView1);
        cerrarSesion = findViewById(R.id.cerrarSesion);

        // Opciones y textos de información
        String[] options = {
                "Quiénes somos",
                "Cuándo son las elecciones",
                "Cómo votar",
                "Dónde votar",
                "Por quién votar",
                "Dudas o sugerencias"
        };

        String[] infoTexts = {
                "El Instituto Nacional Electoral es el organismo público autónomo encargado de organizar las elecciones federales, es decir, la elección del Presidente de la República, Diputados y Senadores que integran el Congreso de la Unión, así como organizar, en coordinación con los organismos electorales de las entidades federativas, las elecciones locales en los estados de la República y la Ciudad de México.",
                "El Proceso Electoral 2023-2024 será reconocido como el más grande que ha tenido México. Se celebrarán elecciones federales y la concurrencia de las 32 entidades federativas. En este espacio te informaremos de las actividades relevantes que se desarrollan durante todo el proceso, como debates, periodos de campañas y fechas importantes.",
                "Recuerda que tu participación es clave para fortalecer nuestra democracia. Infórmate y participa.",
                "Puedes votar en los siguientes lugares: tu casilla asignada, que puedes consultar en el sitio oficial del INE (https://www.ine.mx) o en nuestra aplicación ingresando tu sección electoral que aparece en tu credencial para votar.",
                "Te recomendamos votar por el candidato o partido que mejor represente tus valores e intereses. Reflexiona sobre las propuestas de cada aspirante antes de tomar tu decisión.",
                "Si tienes dudas o sugerencias, puedes comunicarte al INETEL (800 433 2000), enviar un correo a atencionciudadana@ine.mx, o visitar cualquiera de los módulos de atención ciudadana distribuidos en todo el país."
        };

        // Usar el adaptador personalizado
        CustomAdapter adapter = new CustomAdapter(this, options, infoTexts);
        listViewOptions.setAdapter(adapter);

        cerrarSesion.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "¡Has cerrado sesión exitosamente!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Finalizar la actividad para evitar volver al login
        });
    }
}
