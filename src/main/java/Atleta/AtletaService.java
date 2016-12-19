package Atleta;

import retrofit2.Call;
import retrofit2.http.*;
import java.util.List;

public interface AtletaService {
    @GET("/atletas")
    Call<List<Atleta>> getAllAtletas();

    @GET("/atletas/{id}")
    Call<Atleta> getAtleta(@Path("id") Long id);

    @GET("/atletasError")
    Call<List<Atleta>> getError();

    @POST("/atletas")
    Call<Atleta> createAtleta(@Body Atleta atleta);

    @PUT("/atletas")
    Call<Atleta> updateAtleta(@Body Atleta atleta);

    @DELETE("/atletas/{id}")
    Call<Void> deleteAtleta(@Path("id") Long id);
}