package ru.nehodov.okhttpexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getName();

    private static final String GIT_URL = "https://api.github.com";
    private static final String EXTRA_CONTENT = "extra_content";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText loginEditText = findViewById(R.id.editTextLogin);
//        EditText passwordEditText = findViewById(R.id.editTextPassword);
        OkHttpClient client = new OkHttpClient();

        Button signInBtn = findViewById(R.id.signInBtn);
        signInBtn.setOnClickListener((v -> {
            String userName = loginEditText.getText().toString();
            Request request = new Request.Builder()
                    .url(GIT_URL)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
//                    Log.d(TAG, response.message() + "\n" + response.body().string());
                    if (response.isSuccessful()) {
                        final String strResponse = response.body().string();
                        LoginActivity.this.runOnUiThread(() -> {
                            Intent intent = new Intent(LoginActivity.this, ContentActivity.class);
                            intent.putExtra(EXTRA_CONTENT, strResponse);
                            startActivity(intent);
                        });
                    }

                }
            });
        }));
    }
}