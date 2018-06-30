package com.example.rishabh.networking;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {

    Context ctx;
    ArrayList<Article> articles;

    public NewsAdapter(Context ctx, ArrayList<Article> articles) {
        this.ctx = ctx;
        this.articles = articles;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(ctx);
        View inflatedview = li.inflate(R.layout.item_list,parent , false);
        NewsHolder newsHolder = new NewsHolder(inflatedview);
        return newsHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {

        Article article = articles.get(position);
        holder.author.setText(article.getAuthor());
        holder.description.setText(article.getDescription());
        holder.publishedat.setText(article.getPublishedAt());
        holder.title.setText(article.getTitle());
        Picasso.get()
                .load(article.getUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .resize(50,50)
                .into(holder.image);


    }

    @Override
    public int getItemCount() {
        return 5;
    }


    class NewsHolder extends RecyclerView.ViewHolder {

        TextView author,publishedat,title,description;
        ImageView image;


        public NewsHolder(View itemView) {
            super(itemView);

            author = itemView.findViewById(R.id.author);
            publishedat = itemView.findViewById(R.id.publishedAt);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            image = itemView.findViewById(R.id.image);


        }
    }
}
