package com.sundram.wallpaperApp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sundram.wallpaperApp.Adapters.PhotoAdapter;
import com.sundram.wallpaperApp.Modules.Collection;
import com.sundram.wallpaperApp.Modules.Photo;
import com.sundram.wallpaperApp.R;
import com.sundram.wallpaperApp.Utils.GlideApp;
import com.sundram.wallpaperApp.WebServices.ApiInterface;
import com.sundram.wallpaperApp.WebServices.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionFragment extends Fragment {
    private final String TAG =CollectionFragment.class.getSimpleName();
    @BindView(R.id.fragment_collect_progressbar)
    ProgressBar progressBar;
    @BindView(R.id.fragment_collect_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_collect_title)
    TextView title;
    @BindView(R.id.fragment_collect_username)
    TextView username;
    @BindView(R.id.fragment_collect_description)
    TextView description;
    @BindView(R.id.fragment_collect_user_avatar)
    CircleImageView userAvatar;

    private Unbinder unbinder;
    private List<Photo> photoList = new ArrayList<>();
    private PhotoAdapter photoAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        unbinder = ButterKnife.bind(this, view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        photoAdapter = new PhotoAdapter(getActivity(),photoList);
        recyclerView.setAdapter(photoAdapter);
        Bundle bundle = getArguments();
        int collectionId = bundle.getInt("CollectionId");
        showProgressbar(true);
        getInformationOfCollection(collectionId);
        getPhotosOfCollection(collectionId);
        return view;
    }

    private void getInformationOfCollection(int collectionId){
        ApiInterface apiInterface = ServiceGenerator.createServic(ApiInterface.class);
        Call<Collection> call = apiInterface.getInformationOfCollection(collectionId);
        call.enqueue(new Callback<Collection>() {
            @Override
            public void onResponse(Call<Collection> call, Response<Collection> response) {

                if(response.isSuccessful()){
                    Collection collection = response.body();
                    title.setText(collection.getTitle());
                    description.setText(collection.getDescription());
                    username.setText(collection.getUser().getUserName());
                    GlideApp
                            .with(getActivity())
                            .load(collection.getUser().getProfileImage().getSmall())
                            .into(userAvatar);
                }
                else{

                    Log.e(TAG,"Fail" + response.message());
                }
            }

            @Override
            public void onFailure(Call<Collection> call, Throwable t) {

                Log.e(TAG,"FAIL" + t.getMessage());
            }
        });
    }

    private void getPhotosOfCollection(int collectionId){

        ApiInterface apiInterface = ServiceGenerator.createServic(ApiInterface.class);
        Call<List<Photo>> call = apiInterface.getPhotosOfCollection(collectionId);
        call.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {

                if (response.isSuccessful()){

                    photoList.addAll(response.body());
                    photoAdapter.notifyDataSetChanged();


                }else {
                    Log.e(TAG,"Fail" + response.message());
                }
                 showProgressbar(false);
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {

                Log.e(TAG,"Fail" + t.getMessage());
                showProgressbar(false);
            }
        });

    }
    private void showProgressbar(boolean isShow){
        if (isShow){
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        else
        {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
