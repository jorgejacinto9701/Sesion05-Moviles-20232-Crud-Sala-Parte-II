package com.cibertec.proyecto.service;

import com.cibertec.proyecto.entity.Sala;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceSala {


    @GET("sala")
    public abstract Call<List<Sala>> listaSala();


}
