package com.lexian.projetomobile.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.lexian.projetomobile.R;
import com.lexian.projetomobile.adapter.AdapterVideo;
import com.lexian.projetomobile.helper.YoutubeConfig;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class PlayerActiviy extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private YouTubePlayerFragment youTubePlayerFragment;
    private YouTubePlayerView youTubePlayerView;
    private String idVideo;
    private String titulo;
    private String descricao;
    private String canal;

    private String ActionBarTitle ;

    private MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_activiy);

        searchView = findViewById(R.id.searchView);


        //Configura Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("LexianApp");

        //Configura informacoes da view
        TextView textTitulo = findViewById(R.id.Titulo);
        TextView textDesc = findViewById(R.id.desc);
        TextView textCanal = findViewById(R.id.canal);

        //Configura componentes
        youTubePlayerView = findViewById(R.id.playerVideo);
        Bundle bundle = getIntent().getExtras();



        if (bundle!=null){
            idVideo= bundle.getString("idVideo");
            titulo=bundle.getString("titulo");
            descricao = bundle.getString("descricao");
            canal = bundle.getString("canal");

            String trataTitulo = titulo;
            String tratadescricao = descricao;
            String tratacanal = canal;

            if (titulo.contains("&#39;") || titulo.contains("&quot;") || titulo.contains("&amp;") ){
                trataTitulo = titulo.replaceAll("&#39;","'");
                trataTitulo = titulo.replaceAll("&quot;","'");
                trataTitulo = titulo.replaceAll("&amp;","'");
            }

            if (descricao.contains("&#39;") || descricao.contains("&quot;") || descricao.contains("&amp;") ){
                tratadescricao = descricao.replaceAll("&#39;","'");
                tratadescricao = descricao.replaceAll("&quot;","'");
                tratadescricao = descricao.replaceAll("&amp;","'");
            }

            if (canal.contains("&#39;") || canal.contains("&quot;") || canal.contains("&amp;") ){
                tratacanal = canal.replaceAll("&#39;","'");
                tratacanal = canal.replaceAll("&quot;","''");
                tratacanal = canal.replaceAll("&amp;","&`");
            }

            textTitulo.setText(trataTitulo);
            textDesc.setText("Descrição: "+ tratadescricao);
            textCanal.setText("Canal: "+tratacanal);

            youTubePlayerView.initialize(YoutubeConfig.CHAVE_YOUTUBE_API,this);
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        youTubePlayer.setShowFullscreenButton(true);
        youTubePlayer.loadVideo(idVideo);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

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