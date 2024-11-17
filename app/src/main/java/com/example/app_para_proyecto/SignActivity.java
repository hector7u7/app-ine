package com.example.app_para_proyecto;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_para_proyecto.db.DbHelper;
import com.example.app_para_proyecto.db.DbManager;

public class SignActivity extends AppCompatActivity {

    private EditText etNombre, etUsuario, etCorreo, etPassword, etPassword2;
    private Button buttonSign;
    private TextView backLogin;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            // Cambiar el color de la barra de estado
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.customColor2));
            // Cambiar el color de la barra de navegación (abajo)
            window.setNavigationBarColor(getResources().getColor(R.color.customColor2));
        }

        etNombre = findViewById(R.id.etNombre);
        etUsuario = findViewById(R.id.etUsuario);
        etCorreo = findViewById(R.id.etCorreo);
        etPassword = findViewById(R.id.etPassword);
        etPassword2 = findViewById(R.id.etPassword2);
        buttonSign = findViewById(R.id.button);
        backLogin = findViewById(R.id.backLogin);

        buttonSign.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String nombre = etNombre.getText().toString().trim();
                String usuario = etUsuario.getText().toString().trim();
                String correo = etCorreo.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String password2 = etPassword2.getText().toString().trim();

                DbHelper dbHelper = new DbHelper(SignActivity.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                DbManager dbManager = new DbManager(SignActivity.this);

                if (nombre.isEmpty() || usuario.isEmpty() || correo.isEmpty() || password.isEmpty()) {
                    //Toast.makeText(SignActivity.this, "Los campos no pueden estar vacíos", Toast.LENGTH_SHORT).show();
                    camposVacios(etNombre);
                    camposVacios(etUsuario);
                    camposVacios(etPassword);
                    camposVacios(etPassword2);
                    camposVacios(etCorreo);
                    return;
                }
                else if(!password.equals(password2)){
                    return;
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                    return;
                }
                else if(dbManager.existeUsuario(usuario)){
                    return;
                }
                else if(usuario.contains(" ")){
                    return;
                }
                else{
                    long id = dbManager.insertar(usuario, correo, password);
                    limpiar();
                    Toast.makeText(SignActivity.this, "Se ha creado exitosamente el usuario", Toast.LENGTH_SHORT).show();
                }





            }


        });



        backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Realiza la acción cuando se haga clic en el TextView
                //Toast.makeText(LoginActivity.this, "¡Has clickeado el TextView!", Toast.LENGTH_SHORT).show()
                Intent intent = new Intent(SignActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Finalizar la actividad para evitar volver al login
            }
        });


        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // No es necesario hacer nada aquí
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                verificarContrasenas();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Aquí tampoco es necesario hacer nada
            }
        });

        etPassword2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // No es necesario hacer nada aquí
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                verificarContrasenas();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Aquí tampoco es necesario hacer nada
            }
        });

        etCorreo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // No es necesario hacer nada aquí
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                verificarCorreo();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No es necesario hacer nada aquí
            }
        });

        etUsuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // No es necesario hacer nada aquí
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                verificarUsuario();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No es necesario hacer nada aquí
            }
        });

    }

    private void limpiar(){
        etNombre.setText("");
        etPassword.setText("");
        etCorreo.setText("");
        etUsuario.setText("");
        etPassword2.setText("");
    }

    private void verificarContrasenas() {
        String password = etPassword.getText().toString().trim();
        String password2 = etPassword2.getText().toString().trim();
        // Expresión regular que verifica si la contraseña contiene al menos un número y al menos 6 caracteres
        String regex = "^(?=.*\\d).{6,}$";


        if(!password.matches(regex) & !password.isEmpty()){
            etPassword.setError("La contraseña debe contener al menos 6 caracteres y un numero");
        }
        else if (!password.equals(password2)) {
            etPassword2.setError("Las contraseñas no coinciden");
        }
        /*else if (password.isEmpty() || password2.isEmpty()){
            etPassword.setError(null);
            etPassword2.setError(null);
        }*/
        else {
            // Limpiar el error si no hay ningun problema
            etPassword2.setError(null);
            etPassword.setError(null);
        }
    }

    private void verificarCorreo() {
        String correo = etCorreo.getText().toString().trim();

        if(correo.isEmpty()){
            etCorreo.setError(null);
        }
        else{
            if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                etCorreo.setError("Correo no válido");
            } else {
                etCorreo.setError(null); // Limpiar el error si el correo es válido
            }
        }

    }

    private void verificarUsuario(){
        String usuario = etUsuario.getText().toString().trim();
        DbHelper dbHelper = new DbHelper(SignActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        DbManager dbManager = new DbManager(SignActivity.this);
        if(usuario.isEmpty()){
            etUsuario.setError(null);
        }
        else if(usuario.contains(" ")){
            etUsuario.setError("El usuario no puede contener espacios");
        }
        else if(dbManager.existeUsuario(usuario)){
            etUsuario.setError("El usuario ya existe");
        }
        else{
            etUsuario.setError(null);
        }
    }

    private void camposVacios(EditText campo){
        String sCampo = campo.getText().toString().trim();
        if(sCampo.isEmpty()){
            campo.setError("El campo no puede estar vacio");
        }
    }


}