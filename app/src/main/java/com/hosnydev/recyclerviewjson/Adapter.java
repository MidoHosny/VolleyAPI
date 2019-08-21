package com.hosnydev.recyclerviewjson;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {

    private Context context;
    private ArrayList<Model> list;

    Adapter(Context context, ArrayList<Model> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_format, viewGroup, false);
        return new viewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {

        Model model = list.get(i);

        final String image = model.getImageUrl();
        final String name = model.getName();
        final int like = model.getLike();

        viewHolder.setImage(image, context);
        viewHolder.setName(name);
        viewHolder.setLike(like);

        viewHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewImage.class);
                intent.putExtra("name", name);
                intent.putExtra("image", image);
                intent.putExtra("like", String.valueOf(like));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class viewHolder extends RecyclerView.ViewHolder {

        // view
        CardView card;
        ProgressBar progressFormat;
        CircleImageView imageView;
        TextView Name;
        TextView Like;

        viewHolder(@NonNull View itemView) {
            super(itemView);

            // findView
            progressFormat = itemView.findViewById(R.id.progressFormat);
            card = itemView.findViewById(R.id.cardFormat);
            imageView = itemView.findViewById(R.id.imageFormat);
            Name = itemView.findViewById(R.id.nameFormat);
            Like = itemView.findViewById(R.id.likeFormat);

        }


        void setImage(String imageURL, final Context context) {
            Picasso.with(context)
                    .load(imageURL)
                    .fit()
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressFormat.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            progressFormat.setVisibility(View.GONE);
                            imageView.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_launcher));
                        }
                    });
        }

        void setName(String name) {
            Name.setText(name);
        }

        void setLike(int count) {
            String like = "Like: ";
            like += String.valueOf(count);
            Like.setText(like);
        }


    }
}
