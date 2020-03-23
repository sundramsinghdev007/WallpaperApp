package com.sundram.wallpaperApp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sundram.wallpaperApp.Modules.Collection;
import com.sundram.wallpaperApp.R;
import com.sundram.wallpaperApp.Utils.GlideApp;
import com.sundram.wallpaperApp.Utils.SquareImage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectionAdapter extends BaseAdapter {

    private Context context;
    private List<Collection> collections;

    public CollectionAdapter(Context context, List<Collection> collections){
        this.context = context;
        this.collections = collections;
    }

    @Override
    public int getCount() {
        return collections.size();
    }

    @Override
    public Object getItem(int position) {
        return collections.get(position);
    }

    @Override
    public long getItemId(int position) {
        return collections.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView==null){

            convertView = LayoutInflater.from(context).inflate(R.layout.item_collection, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else
            {
                viewHolder =(ViewHolder)convertView.getTag();
            }
            ButterKnife.bind(this,convertView);
        Collection collection = collections.get(position);
        if (collection.getTitle()!= null){
            viewHolder.title.setText(collection.getTitle());
        }
        viewHolder.totalPhoto.setText(String.valueOf(collection.getTotalPhotos()) + "Photos");
        GlideApp
                .with(context)
                .load(collection.getCoverPhoto().getUrl().getRegular())
                .into(viewHolder.collectionPhoto);
        return convertView;
    }
    static class ViewHolder{
        @BindView(R.id.item_collection_title)
        TextView title;
        @BindView(R.id.item_collection_total_photos)
        TextView totalPhoto;
        @BindView(R.id.item_collection_photo)
        SquareImage collectionPhoto;

        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }

}
