package luiz.br.com.movies;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by Luiz on 14/03/2017.
 */

public interface UsuarioInterface {
    String url = "http://www.mocky.io";

    @GET("/v2/58b9b1740f0000b614f09d2f")
    Call<Usuario> getUsuario();

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

}
