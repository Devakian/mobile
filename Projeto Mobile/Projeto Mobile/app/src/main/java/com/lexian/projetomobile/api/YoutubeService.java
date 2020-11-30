package com.lexian.projetomobile.api;

import com.lexian.projetomobile.model.Resultado;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YoutubeService {

    /*
    https://www.googleapis.com/youtube/v3/
    search
    ?part=snippet
    &order=date
    &maxResults=20
    &key=AIzaSyBCmJbLrYGM3NDhqphe54YWHnCNFvC0_qE

    https://www.googleapis.com/youtube/v3/search?part=snippet&order=date&maxResults=20&key=AIzaSyBCmJbLrYGM3NDhqphe54YWHnCNFvC0_qE
     **/
    @GET("search")
    Call<Resultado> recuperarVideos(
                    @Query("part") String part,
                    @Query("order") String order,
                    @Query("maxResults") String maxResults,
                    @Query("key") String key,
                    @Query("q") String q

    );
}
