package com.sundram.wallpaperApp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sundram.wallpaperApp.Adapters.PhotoAdapter;
import com.sundram.wallpaperApp.Modules.Photo;
import com.sundram.wallpaperApp.R;
import com.sundram.wallpaperApp.Utils.RealmController;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FavouriteFragment extends Fragment {

    @BindView(R.id.fragment_favorite_notification)
    TextView notification;
    @BindView(R.id.fragment_favorite_recyclerView)
    RecyclerView recyclerView;
    private PhotoAdapter photoAdapter;
    private Unbinder unbinder;
    private List<Photo> photoList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        unbinder = ButterKnife.bind(this, view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        photoAdapter = new PhotoAdapter(getActivity(),photoList);
        recyclerView.setAdapter(photoAdapter);
        getPhotos();
        return view;
    }

    private void getPhotos(){
        RealmController realmController = new RealmController();
        photoList.addAll(realmController.getPhoto());
        if (photoList.size() == 0){
            notification.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else{
            photoAdapter.notifyDataSetChanged();

            notification.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
