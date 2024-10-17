package ulbra.saolucas.appcadastro2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrarActivity extends AppCompatActivity {

    EditText edNome, edUser, edPas1, edPas2;
    Button btSalvar;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela2);

        db = new DBHelper(this);
        edNome = findViewById(R.id.edtnome);
        edUser = findViewById(R.id.edtlogin);
        edPas1 = findViewById(R.id.edtsenha);
        edPas2 = findViewById(R.id.edtrepsenha);
        btSalvar = findViewById(R.id.btsalvar);

        setPlaceholderHandling(edNome, "Insira seu nome");
        setPlaceholderHandling(edUser, "Insira o login");
        setPlaceholderHandling(edPas1, "Insira a senha");
        setPlaceholderHandling(edPas2, "Repita sua senha");

        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = edUser.getText().toString();
                String pas1 = edPas1.getText().toString();
                String pas2 = edPas2.getText().toString();

                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(RegistrarActivity.this, "Insira o LOGIN DO USUÁRIO", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(pas1) || TextUtils.isEmpty(pas2)) {
                    Toast.makeText(RegistrarActivity.this, "Insira a SENHA DO USUÁRIO", Toast.LENGTH_SHORT).show();
                } else if (!pas1.equals(pas2)) {
                    Toast.makeText(RegistrarActivity.this, "As senhas não correspondem ao login do usuário", Toast.LENGTH_SHORT).show();
                } else {
                    long res = db.criarUtilizador(userName, pas1);
                    if (res > 0) {
                        // Navigate to the main screen or login screen after successful registration
                        Intent intent = new Intent(RegistrarActivity.this, MainActivity.class); // Assuming MainActivity is the main screen
                        startActivity(intent);
                        Toast.makeText(RegistrarActivity.this, "Registro Concluido!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegistrarActivity.this, "Falha ao registrar. Tente novamente!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    // Method to handle focus changes and placeholder text
    private void setPlaceholderHandling(final EditText editText, final String placeholder) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    // If the EditText gains focus and the text equals the placeholder, clear it
                    if (editText.getText().toString().equals(placeholder)) {
                        editText.setText("");
                    }
                } else {
                    // If the EditText loses focus and no text is entered, reset the placeholder
                    if (editText.getText().toString().isEmpty()) {
                        editText.setText(placeholder);
                    }
                }
            }
        });
    }
}
