package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.instagram.databinding.ActivityDetailBinding;
import com.example.instagram.models.Post;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";
    ActivityDetailBinding binding;
    Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String postId = getIntent().getStringExtra("post_id");

        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.include(Post.KEY_DESCRIPTION);
        query.include(Post.KEY_IMAGE);
        // First try to find from the cache and only then go to network
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK); // or CACHE_ONLY
        // Execute the query to find the object with ID
        query.getInBackground(postId, new GetCallback<Post>() {
            public void done(Post item, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                Log.i(TAG, String.valueOf(item));

                binding.tvUsername.setText(item.getUser().getUsername());
                binding.tvDescription.setText(item.getDescription());
                binding.tvTimestamp.setText(String.valueOf(item.getCreatedAt()));
                Glide.with(getApplicationContext())
                        .load(item.getImage().getUrl())
                        .into(binding.ivPostImage);
            }
        });

    }
}