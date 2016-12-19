package Atleta;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.io.IOException;
import java.util.List;

public class Synchronous {
    private static Retrofit retrofit;

    // Nos conectamos a la base de datos
    public static void main(String[] args) throws IOException {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Nos conetcamos al servicio
        AtletaService atletaService = retrofit.create(AtletaService.class);

// GET TODOS Atletas
        Call<List<Atleta>> call = atletaService.getAllAtletas();
        Response<List<Atleta>> response= call.execute();
        // Si el servidor contesta OK
        if(response.isSuccessful()) {
            List<Atleta> atletasList = response.body();
            System.out.println("GET Todos los atletas");
            System.out.println("Codigo: " + response.code() + System.lineSeparator() +
                    "   GET todos Atletas: " + atletasList);
        // Si el servidor contesta KO
        } else {
            System.out.println("- Codigo: " + response.code() +
                    " Error: " + response.errorBody());
        }

// Llamamos a un error provocado
        // va a buscar la función getError en AtletaService
        // Esta función va a buscar la url /atletasError, una URL que no existe
        call = atletaService.getError();
        response= call.execute();
        // Si el servidor contesta KO
        if(!response.isSuccessful()) {
            System.out.println("Codigo: " + response.code() +
                    " Error: " + response.raw() );
        }

// POST Atleta
        Atleta atleta = new Atleta();
        atleta.setNombre("Ivan");
        atleta.setApellidos("Mi Apellido");
        atleta.setNacionalidad("Es");
        Call<Atleta> callAtleta = atletaService.createAtleta(atleta);
        Response<Atleta> responseAtleta= callAtleta.execute();
        // Si el servidor contesta OK
        if(responseAtleta.isSuccessful()) {
            Atleta atletaResp = responseAtleta.body();
            System.out.println("Codigo: " + responseAtleta.code() + System.lineSeparator() +
                    "   Atleta añadido: " + atletaResp);


// PUT Atleta --> Actualizamos el atleta creado anteriormente
            atletaResp.setApellidos("Apellidos Nuevos");
            callAtleta = atletaService.updateAtleta(atletaResp);
            responseAtleta= callAtleta.execute();
            // Si el servidor contesta Ok
            System.out.println("- Codigo: " + responseAtleta.code() + System.lineSeparator() +
                    "   Atleta modificado: " + responseAtleta.body());

// GET TODOS Atletas para comprobar que se ha modificado el atleta anterior
            call = atletaService.getAllAtletas();
            response= call.execute();
            System.out.println("Comprobación del PUT " + System.lineSeparator() +
                    "   Codigo: " + response.code() + System.lineSeparator() +
                    "   GET todos atletas: " + response.body());

// DELETE Atleta
            Call<Void> callDelete= atletaService.deleteAtleta(atletaResp.getId());
            Response<Void> responseDelete= callDelete.execute();

            System.out.println("Codigo del borrado: " + responseDelete.code());

// GET TODOS Atletas para comprobar si se ha eliminado
            call = atletaService.getAllAtletas();
            response= call.execute();
            System.out.println("Comprobación del delete " + System.lineSeparator() +
                    "   Codigo: " + response.code() + System.lineSeparator() +
                    "   GET atletas: " + response.body());




        } else {
            System.out.println("Status code: " + responseAtleta.code() +
                    "Message error: " + responseAtleta.errorBody());
        }

        /**
         * Ejemplo GET de un atleta
         */
        callAtleta = atletaService.getAtleta(1L);
        responseAtleta = callAtleta.execute();

        if(responseAtleta.isSuccessful()) {
            System.out.println("GET ONE->Status code: " + responseAtleta.code() +
                    " Atleta: " + responseAtleta.body() );
        }





    }
}
