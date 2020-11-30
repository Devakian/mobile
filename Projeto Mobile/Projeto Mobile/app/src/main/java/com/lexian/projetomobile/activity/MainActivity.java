package com.lexian.projetomobile.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.lexian.projetomobile.R;
import com.lexian.projetomobile.adapter.AdapterVideo;
import com.lexian.projetomobile.api.YoutubeService;
import com.lexian.projetomobile.helper.RetroFitConfig;
import com.lexian.projetomobile.helper.YoutubeConfig;
import com.lexian.projetomobile.listener.RecyclerItemClickListener;
import com.lexian.projetomobile.model.Item;
import com.lexian.projetomobile.model.Resultado;
import com.lexian.projetomobile.model.Video;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    //widgets
    private RecyclerView recyclerVideos;
    private MaterialSearchView searchView;


    private List<Item> videos = new ArrayList<>();
    private Resultado resultados;
    private AdapterVideo adapterVideo;

    //Retrofit
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializar Componentes
        recyclerVideos = findViewById(R.id.recyclerVideos);
        searchView = findViewById(R.id.searchView);

        //Configuracoes inicias
        retrofit = RetroFitConfig.getRetrofit();

        //Configura Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("LexianApp");
        setSupportActionBar(toolbar);


        //Recupera videos
        recuperaVideo("");

        //Metodos pesquisa SearchView
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recuperaVideo(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                recuperaVideo("");
            }
        });
    }

    private void recuperaVideo(String pesquisa){

        String trataPesquisa = pesquisa.replaceAll(" ","+");
        YoutubeService youtubeService = retrofit.create(YoutubeService.class);

        youtubeService.recuperarVideos(
                "snippet","relevance", "20",
                YoutubeConfig.CHAVE_YOUTUBE_API,trataPesquisa
        ).enqueue(new Callback<Resultado>() {
            @Override
            public void onResponse(Call<Resultado> call, Response<Resultado> response) {
                Log.d("resultado", "resultado: "+response.toString());
                if(response.isSuccessful()){
                    Resultado resultado = response.body();
                    videos = resultado.items;
                    configuraRecylcerVideo();
                    Log.d("resultado", "onResponse: " + resultado.items.get(0).id.videoId);
                }
            }

            @Override
            public void onFailure(Call<Resultado> call, Throwable t) {

            }
        });
    }

    public void configuraRecylcerVideo(){
        adapterVideo = new AdapterVideo(videos,this);
        recyclerVideos.setHasFixedSize(true);
        recyclerVideos.setLayoutManager(new LinearLayoutManager(this));
        recyclerVideos.setAdapter(adapterVideo);

        //Evento clique
        recyclerVideos.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        recyclerVideos,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Item video = videos.get(position);
                                String idVideo = video.id.videoId;

                                Intent i = new Intent(MainActivity.this,PlayerActiviy.class);
                                i.putExtra("idVideo",idVideo);
                                i.putExtra("titulo",video.snippet.title);
                                i.putExtra("descricao",video.snippet.description);
                                i.putExtra("canal",video.snippet.channelTitle);
                                startActivity(i);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                ));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_player_activity,menu);

        MenuItem item = menu.findItem(R.id.menu_search);
        searchView.setMenuItem(item);

        return true;
    }
}