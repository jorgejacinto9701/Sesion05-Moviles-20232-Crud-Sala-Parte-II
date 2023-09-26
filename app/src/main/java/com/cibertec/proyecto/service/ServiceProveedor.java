package com.cibertec.proyecto.service;

import com.cibertec.proyecto.entity.Proveedor;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceProveedor {
    @GET("util/registraProveedor")
    public abstract Call<Proveedor> RegistraProveedor(Proveedor objProveedor);

}
