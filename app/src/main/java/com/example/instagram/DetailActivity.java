package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.instagram.databinding.ActivityDetailBinding;
import com.example.instagram.models.Post;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
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
        // Execute the query to find the object with ID
        query.getInBackground(postId, new GetCallback<Post>() {
            public void done(final Post item, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                Log.i(TAG, String.valueOf(item));

                binding.tvUsername.setText(item.getUser().getUsername());
                binding.tvDescription.setText(item.getDescription());
                binding.tvTimestamp.setText(String.valueOf(item.getCreatedAt()));
                binding.tvLikesCount.setText(String.valueOf(item.getLikesCount()) + " likes");
                Glide.with(getApplicationContext())
                        .load(item.getImage().getUrl())
                        .into(binding.ivPostImage);

                ParseFile profile = item.getUser().getParseFile("profile");
                if (profile != null) {
                    Glide.with(getApplicationContext())
                            .load(profile.getUrl())
                            .circleCrop()
                            .into(binding.ivProfile);
                } else {
                    binding.ivProfile.setImageResource(R.drawable.instagram_user_outline_24);
                }


                binding.ivLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (view.isActivated()) {
                            view.setActivated(false);
                            item.decrementLikesCount();
                            binding.tvLikesCount.setText(String.valueOf(item.getLikesCount()) + " likes");
                        }
                        else {
                            view.setActivated(true);
                            item.incrementLikesCount();
                            binding.tvLikesCount.setText(String.valueOf(item.getLikesCount()) + " likes");
                        }
                    }
                });
            }
        });

    }
}