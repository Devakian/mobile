package com.lexian.projetomobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.lexian.projetomobile.R;
import com.lexian.projetomobile.model.Item;
import com.lexian.projetomobile.model.Video;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterVideo extends RecyclerView.Adapter<AdapterVideo.MyViewHolder> {

    private List<Item> videos = new ArrayList<>();
    private Context context;

    public AdapterVideo(List<Item> videos, Context context) {
        this.videos = videos;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_video,parent,false);
        return new AdapterVideo.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Item video = videos.get(position);
        String titulo = video.snippet.title;

        if (titulo.contains("&#39;") || titulo.contains("&quot;") || titulo.contains("&amp;") ){
            titulo = titulo.replaceAll("&#39;","'");
            titulo = titulo.replaceAll("&quot;","''");
            titulo = titulo.replaceAll("&amp;","&`");
        }

        holder.titulo.setText(titulo);

        String url = video.snippet.thumbnails.high.url;
        Picasso.get().load(url).into(holder.capaVideo);
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titulo;
        TextView descricao;
        TextView data;
        ImageView capaVideo;

            public MyViewHolder(View itemView){
                super(itemView);

                titulo = itemView.findViewById(R.id.textTitulo);
                capaVideo = itemView.findViewById(R.id.imageCapa);

            }

    }
}
