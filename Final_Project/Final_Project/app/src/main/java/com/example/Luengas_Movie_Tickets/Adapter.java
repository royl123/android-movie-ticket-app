package com.example.Luengas_Movie_Tickets;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

// File sets up RecyclerView
public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private Context context;
    private List<MovieModel> data;

    public Adapter(Context context, List<MovieModel> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.movie_list, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(data.get(position).getTitle());
        holder.synopsis.setText(data.get(position).getSynopsis());
        holder.rd.setText(data.get(position).getRd());
        String imgUrl = "https://image.tmdb.org/t/p/w500"+data.get(position).getImg();

        // populate RecycleView images with Glide
        Glide.with(context)
                .load(imgUrl)
                .into(holder.img);

        // When clicking on movie title, start next activity ("info" - 2nd screen)
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View itemView) {
                Context context = itemView.getContext();
                Intent intent = new Intent(context, info.class);

                // Transfer over movie title you clicked with poster to next activity
                intent.putExtra("title", data.get(position).getTitle());
                intent.putExtra("poster", imgUrl);
                context.startActivity(intent);
            }
        });
    }

    // implement abstract getItemCount method required for Adapter
    @Override
    public int getItemCount() {
        return data.size();
    }

    // Map MovieModel props to itemView
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView synopsis;
        ImageView img;
        TextView rd;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.titleText);
            synopsis = itemView.findViewById(R.id.overviewText);
            img = itemView.findViewById(R.id.imageView);
            rd = itemView.findViewById(R.id.releaseDate);
        }
    }
}

