package com.example.july4r;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.july4r.api.CEPService;
import com.example.july4r.model.CEP;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView resultado;
    private Button enviar;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultado = (TextView) findViewById(R.id.textView);
        enviar = (Button) findViewById(R.id.button);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://viacep.com.br/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Listener do button
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecuperarCEPRetrofit();
            }
        });
    }

    private void RecuperarCEPRetrofit() {
        CEPService cepService = retrofit.create(CEPService.class);

        // o argumento de RecuperaCep pode ser um input do usuário (tipo String)
        Call<CEP> call = cepService.RecuperaCep("40750380");

        call.enqueue(new Callback<CEP>() {
            @Override
            public void onResponse(Call<CEP> call, Response<CEP> response) {
                if(response.isSuccessful()) {
                    CEP cep = response.body();

                    String address;

                    address = String.format(""
                            + "\n CEP: " + cep.getCep()
                            + "\n Logradouro: " + cep.getLogradouro()
                            + "\n Bairro: " + cep.getBairro()
                            + "\n Localidade: " + cep.getLocalidade()
                            + "\n UF: " + cep.getUf()
                            );

                    resultado.setText(address);
                }
            }

            @Override
            public void onFailure(Call<CEP> call, Throwable t) {

            }
        });
    }
}