package upm.cabd.mssde_pas.DatosAbiertosParques;
import java.util.List;

import upm.cabd.mssde_pas.DatosAbiertosParques.DatosAbiertosParques;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import upm.cabd.mssde_pas.DatosAbiertosParques.Graph;


@SuppressWarnings("Unused")
public
interface IDatosAbiertosParquesRESTAPIService {

    @GET("200761-0-parques-jardines.json")
    Call<DatosAbiertosParques> getAllRegisteredParks();
}