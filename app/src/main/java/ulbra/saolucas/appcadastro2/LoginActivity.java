package ulbra.saolucas.appcadastro2;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText edLogin, edPass;
    Button btLogin;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela3);

        db = new DBHelper(this);
        edLogin = findViewById(R.id.edlogin);
        edPass = findViewById(R.id.edsenha);
        btLogin = findViewById(R.id.btentrar);

        // Set placeholder handling
        setPlaceholderHandling(edLogin, "Insira o login");
        setPlaceholderHandling(edPass, "Insira a senha");

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edLogin.getText().toString();
                String password = edPass.getText().toString();

                // Using TextUtils.isEmpty() for better validation
                if (TextUtils.isEmpty(username) || username.equals("Insira o login")) {
                    Toast.makeText(LoginActivity.this, "Usuário não inserido, tente novamente", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password) || password.equals("Insira a senha")) {
                    Toast.makeText(LoginActivity.this, "Senha não inserida, tente novamente", Toast.LENGTH_SHORT).show();
                } else {
                    String res = db.validarLogin(username, password);
                    if (res.equals("OK")) {
                        Toast.makeText(LoginActivity.this, "Login CONCLUIDO!!", Toast.LENGTH_SHORT).show();
                        // Navigate to next screen here if needed
                    } else {
                        Toast.makeText(LoginActivity.this, "Login ou Senha errados!", Toast.LENGTH_SHORT).show();
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
