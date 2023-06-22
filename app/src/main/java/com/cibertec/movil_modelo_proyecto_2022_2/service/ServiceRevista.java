package com.cibertec.movil_modelo_proyecto_2022_2.service;

import com.cibertec.movil_modelo_proyecto_2022_2.entity.Revista;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ServiceRevista {

    @GET("revista")
    public abstract   Call<List<Revista>> listaRevista();

    @POST("revista")
    public Call<Revista> registraRevista(@Body Revista objRevista);



    @PUT("revista")
    public abstract Call<Revista> actualizaRevista(@Body Revista objRevista);
}
