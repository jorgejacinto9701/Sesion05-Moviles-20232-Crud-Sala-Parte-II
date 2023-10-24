package com.cibertec.proyecto.service;

import com.cibertec.proyecto.entity.Editorial;
import com.cibertec.proyecto.entity.Sala;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServiceEditorial {

    @GET("editorial/porNombre/{nombre}")
    public Call<List<Editorial>> listaporEditorial(@Path("nombre") String nombre);
    @POST("editorial")
    public abstract Call<Editorial> insertaEditorial(@Body Editorial objEditorial);
    ;
}
