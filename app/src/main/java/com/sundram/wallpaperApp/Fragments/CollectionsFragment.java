package com.sundram.wallpaperApp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.sundram.wallpaperApp.Adapters.CollectionAdapter;
import com.sundram.wallpaperApp.Modules.Collection;
import com.sundram.wallpaperApp.R;
import com.sundram.wallpaperApp.Utils.Functions;
import com.sundram.wallpaperApp.WebServices.ApiInterface;
import com.sundram.wallpaperApp.WebServices.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionsFragment extends Fragment {
    private final String TAG = Collection.class.getSimpleName();
    @BindView(R.id.fragment_collection_gridView)
    GridView gridView;
    @BindView(R.id.fragment_collection_progressbar)
    ProgressBar progressBar;

    private CollectionAdapter collectionAdapter;
    private List<Collection> collections = new ArrayList<>();

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collections, container, false);

        unbinder = ButterKnife.bind(this, view);
        collectionAdapter = new CollectionAdapter(getActivity(), collections);
        gridView.setAdapter(collectionAdapter);
        showProgressbar(true);
        getCollection();
        return view;
    }

    @OnItemClick(R.id.fragment_collection_gridView)
    public void setGridView(int position){
        Collection collection = collections.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("CollectionId", collection.getId());
        CollectionFragment collectionFragment = new CollectionFragment();
        collectionFragment.setArguments(bundle);
        Functions.changeMainFragmentWithBack(getActivity(),collectionFragment);
    }

    private void getCollection(){
        ApiInterface apiInterface = ServiceGenerator.createServic(ApiInterface.class);
        Call<List<Collection>> call = apiInterface.getCollection();
        call.enqueue(new Callback<List<Collection>>() {
            @Override
            public void onResponse(Call<List<Collection>> call, Response<List<Collection>> response) {
                if (response.isSuccessful()){
                    collections.addAll(response.body());
                    collectionAdapter.notifyDataSetChanged();
                }else {

                    Log.e(TAG,"Fail"+ response.message());
                }
                showProgressbar(false);
            }

            @Override
            public void onFailure(Call<List<Collection>> call, Throwable t) {
                Log.e(TAG,"Fail"+ t.getMessage());
                showProgressbar(false);
            }
        });
        }

    private void showProgressbar(boolean isShow){
        if (isShow){
            progressBar.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.GONE);
        }
        else
        {
            progressBar.setVisibility(View.GONE);
            gridView.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
