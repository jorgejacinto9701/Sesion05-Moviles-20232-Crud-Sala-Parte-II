package com.cibertec.movil_modelo_proyecto_2022_2.service;

import com.cibertec.movil_modelo_proyecto_2022_2.entity.Sala;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServiceSala {
    @POST("sala")
    public abstract Call<Sala> insertaSala(@Body Sala objSala);

    @GET("sala")
    public abstract  Call<List<Sala>> listaSala();
}
