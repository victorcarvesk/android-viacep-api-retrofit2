package com.example.july4r.api;

import com.example.july4r.model.CEP;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CEPService {

    @GET("{cep}/json/")
    Call<CEP> RecuperaCep(@Path("cep") String cep);
}
