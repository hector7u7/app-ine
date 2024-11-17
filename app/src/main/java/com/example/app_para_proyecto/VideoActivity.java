package com.example.app_para_proyecto;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.VideoView;
import android.net.Uri;
import android.os.Handler;
import android.view.View;

public class VideoActivity extends AppCompatActivity {

    private VideoView videoView;
    private Button btnSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_video);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        videoView = findViewById(R.id.videoView);
        btnSkip = findViewById(R.id.button2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            // Cambiar el color de la barra de estado
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.customColor));
            // Cambiar el color de la barra de navegación (abajo)
            window.setNavigationBarColor(getResources().getColor(R.color.customColor));
        }

        // Configurar el VideoView para reproducir el video desde raw
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.intro_video;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        videoView.start(); // Iniciar el video automáticamente
        // Mostrar el botón de "Saltar" después de 3 segundos
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                btnSkip.setVisibility(View.VISIBLE);
            }
        }, 7000);

        // Configurar el botón de "Saltar" para ir a la siguiente actividad
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextActivity();
            }
        });

        // Cuando termine el video, ir automáticamente a la siguiente actividad
        videoView.setOnCompletionListener(mp -> goToNextActivity());
    }

    private void goToNextActivity() {
        Intent intent = new Intent(VideoActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Finalizar la actividad para que no vuelva al video al presionar "Atrás"
    }
}