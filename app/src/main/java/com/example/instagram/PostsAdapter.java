package com.example.instagram;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagram.databinding.ItemPostBinding;
import com.example.instagram.models.Post;
import com.parse.ParseFile;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemPostBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return this.posts.size();
    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Post> list) {
        this.posts.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ItemPostBinding binding;

        public ViewHolder(ItemPostBinding b) {
            super(b.getRoot());
            binding = b;
            itemView.setOnClickListener(this);
        }

        public void bind(Post post) {
            binding.tvDescription.setText(post.getDescription());
            binding.tvUser.setText(post.getUser().getUsername());
            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context)
                        .load(image.getUrl())
                        .into(binding.ivPostImage);
            }
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(context, "Clicked post!", Toast.LENGTH_SHORT).show();
            Log.i("PostsAdapter", "clicked post");
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Intent i = new Intent(context, DetailActivity.class);
                Post post = posts.get(position);
                i.putExtra("post_id", post.getObjectId());
                context.startActivity(i);
            }

        }
    }
}
