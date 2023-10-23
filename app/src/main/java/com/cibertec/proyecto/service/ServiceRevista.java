package com.cibertec.proyecto.service;

import com.cibertec.proyecto.entity.Revista;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServiceRevista {


    @GET("revista/porNombre/{nombre}")
    public Call<List<Revista>> listaporRevista(@Path("nombre") String nombre);

    @POST("revista")
    public abstract Call<Revista> insertaRevista(@Body Revista objRevista);
}
