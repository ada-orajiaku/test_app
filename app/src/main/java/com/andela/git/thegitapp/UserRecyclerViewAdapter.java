//package com.andela.git.thegitapp;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.FragmentManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.andela.git.thegitapp.data.User;
//import com.andela.git.thegitapp.services.LruBitmapCache;
//import com.android.volley.RequestQueue;
//import com.android.volley.toolbox.ImageLoader;
//import com.android.volley.toolbox.NetworkImageView;
//
///**
// * Created by adaobifrank on 3/13/17.
// */
//
//public class UserRecyclerViewAdapter
//        extends RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder> {
//
//
//    private final User[] users;
//    private boolean isTwoPane;
//    private FragmentManager fragmentManager;
//    private RequestQueue requestQueue;
//    private ImageLoader mImageLoader;
//
//    public UserRecyclerViewAdapter(User[] items, boolean isTwoPane, FragmentManager fragmentManager, RequestQueue requestQueue)
//    {
//        this.users = items;
//        this.isTwoPane = isTwoPane;
//        this.fragmentManager = fragmentManager;
//        this.requestQueue = requestQueue;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.developer_list_content, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(final ViewHolder holder, int position) {
//        mImageLoader = new ImageLoader(requestQueue, new LruBitmapCache());
//        holder.user = users[position];
//        holder.username.setText(users[position].login);
//        //   holder.avatar.setim
//
//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isTwoPane) {
//                    Bundle arguments = new Bundle();
//                    arguments.putString(DeveloperDetailFragment.ARG_USERNAME, holder.user.login);
//                    arguments.putString(DeveloperDetailFragment.ARG_USER_URL, holder.user.url);
//                    DeveloperDetailFragment fragment = new DeveloperDetailFragment();
//                    fragment.setArguments(arguments);
//
//                    fragmentManager.beginTransaction()
//                            .replace(R.id.developer_detail_container, fragment)
//                            .commit();
//                } else {
//                    Context context = v.getContext();
//                    Intent intent = new Intent(context, DeveloperDetailActivity.class);
//                    intent.putExtra(DeveloperDetailFragment.ARG_USERNAME, holder.user.login);
//                    intent.putExtra(DeveloperDetailFragment.ARG_USER_URL, holder.user.url);
//
//                    context.startActivity(intent);
//                }
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return users.length;
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public final View mView;
//        public final NetworkImageView avatar;
//        public final TextView username;
//        public User user;
//
//        public ViewHolder(View view) {
//            super(view);
//            mView = view;
//            username = (TextView) view.findViewById(R.id.username);
//            avatar = (NetworkImageView) view.findViewById(R.id.avatar);
//          //  avatar.setImageUrl(user.avatar_url, mImageLoader);
//        }
//
//        @Override
//        public String toString() {
//            return super.toString() + " '" + username.getText() + "'";
//        }
//    }
//}