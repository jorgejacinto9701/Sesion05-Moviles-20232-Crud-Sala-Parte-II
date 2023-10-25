package com.cibertec.proyecto.service;

import com.cibertec.proyecto.entity.Proveedor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ServiceProveedor {
    @GET("util/registraProveedor")
    public abstract Call<Proveedor> RegistraProveedor(Proveedor objProveedor);

    @GET("proveedor/porRazonSocial/{razSoc}")
    public Call<List<Proveedor>> ListaProveedor (@Path("razSoc") String razSoc);
}
