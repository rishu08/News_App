package com.example.rishabh.networking;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
    public void onBindViewHolder(@NonNull final NewsHolder holder, int position) {

        final Article article = articles.get(position);
        holder.author.setText(article.getAuthor());
        holder.description.setText(article.getDescription());
        holder.publishedat.setText(article.getPublishedAt());
        holder.title.setText(article.getTitle());
        Picasso.get()
                .load(article.getUrlToImage())
                .placeholder(R.drawable.ic_launcher_background)
                .resize(500,500)
                .into(holder.image);


        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = article.getUrl();

                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                ctx.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return articles.size();
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
