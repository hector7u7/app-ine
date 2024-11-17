package com.example.app_para_proyecto;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_para_proyecto.db.DbHelper;
import com.example.app_para_proyecto.db.DbManager;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView btnSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            // Cambiar el color de la barra de estado
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.customColor2));
            // Cambiar el color de la barra de navegación (abajo)
            window.setNavigationBarColor(getResources().getColor(R.color.customColor2));
        }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.button);
        btnSign = findViewById(R.id.textView3);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                DbHelper dbHelper = new DbHelper(LoginActivity.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                DbManager dbManager = new DbManager(LoginActivity.this);

                // Validar si los campos están vacíos
                if(username.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Los campos no pueden estar vacíos", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(dbManager.validarLogin(username,password)){
                    Intent intent = new Intent(LoginActivity.this, VideoActivity.class);
                    startActivity(intent);
                    finish();

                }
                else{
                    Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }


                /*if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Los campos no pueden estar vacíos", Toast.LENGTH_SHORT).show();
                } else {
                    // Comparar los valores ingresados con las credenciales correctas
                    if (username.equals(CORRECT_USERNAME) && password.equals(CORRECT_PASSWORD)) {
                        // Credenciales correctas, proceder a la siguiente pantalla
                        Intent intent = new Intent(LoginActivity.this, VideoActivity.class);
                        startActivity(intent);
                        finish(); // Finalizar la actividad para evitar volver al login
                    } else {
                        // Credenciales incorrectas
                        Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }
                }*/
            }
        });

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Realiza la acción cuando se haga clic en el TextView
                //Toast.makeText(LoginActivity.this, "¡Has clickeado el TextView!", Toast.LENGTH_SHORT).show()
                Intent intent = new Intent(LoginActivity.this, SignActivity.class);
                startActivity(intent);
                finish(); // Finalizar la actividad para evitar volver al login
            }
        });

    }
}