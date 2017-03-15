package com.andela.git.thegitapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.andela.git.thegitapp.data.User;
import com.andela.git.thegitapp.services.HttpsTrustManager;
import com.andela.git.thegitapp.services.LruBitmapCache;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An activity representing a list of Developers. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link DeveloperDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class DeveloperListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    // URL of object to be parsed
    String JsonURL = "https://api.github.com/search/users?q=repos:%3E=1+location:lagos";
    // This string will hold the results
    String data = "";
    // Defining the Volley request queue that handles the URL request concurrently
    public RequestQueue requestQueue;

    View recyclerView;

    int pageNumber;
    private ProgressBar loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        loadingBar = (ProgressBar) findViewById(R.id.loadingBar);

        recyclerView = findViewById(R.id.developer_list);
        assert recyclerView != null;

        if (findViewById(R.id.developer_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }


       HttpsTrustManager.allowAllSSL();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        pageNumber = 1;
        String url = "https://api.github.com/search/users?q=repos:%3E=1+location:lagos&per_page=10&page="+pageNumber;
        loadingBar.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    int resultCount = response.optInt("total_count");
                    if (resultCount > 0) {
                        Gson gson = new Gson();
                        JSONArray jsonArray = response.optJSONArray("items");
                        if (jsonArray != null) {
                            User[] songs = gson.fromJson(jsonArray.toString(), User[].class);
                            loadingBar.setVisibility(View.GONE);
                            setupRecyclerView((RecyclerView) recyclerView, songs);
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                loadingBar.setVisibility(View.GONE);
                Log.e("LOG", error.toString());
                Toast.makeText(getApplication(),error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, User[] users) {
        recyclerView.setAdapter(new UserRecyclerViewAdapter(users));
    }

    public class UserRecyclerViewAdapter
      extends RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder> {

        private User[] users;
        ImageLoader mImageLoader;

        public UserRecyclerViewAdapter(User[] items) {
            this.users = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.developer_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            mImageLoader = AppController.getInstance().getImageLoader();
            holder.user = users[position];
            holder.username.setText(users[position].login);
            if(users[position].avatar_url != null){
                holder.avatar.setImageUrl(users[position].avatar_url, mImageLoader);
            }

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(DeveloperDetailFragment.ARG_USERNAME, holder.user.login);
                        arguments.putString(DeveloperDetailFragment.ARG_USER_URL, holder.user.url);
                        DeveloperDetailFragment fragment = new DeveloperDetailFragment();
                        fragment.setArguments(arguments);

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.developer_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, DeveloperDetailActivity.class);
                        intent.putExtra(DeveloperDetailFragment.ARG_USERNAME, holder.user.login);
                        intent.putExtra(DeveloperDetailFragment.ARG_USER_URL, holder.user.html_url);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return users.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final NetworkImageView avatar;
            public final TextView username;
            public User user;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                username = (TextView) view.findViewById(R.id.username);
                avatar = (NetworkImageView) view.findViewById(R.id.avatar);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + username.getText() + "'";
            }
        }
    }


}
