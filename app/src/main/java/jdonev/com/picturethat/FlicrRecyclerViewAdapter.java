package jdonev.com.picturethat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

class FlicrRecyclerViewAdapter extends RecyclerView.Adapter<FlicrRecyclerViewAdapter.FlickrImageViewHolder> {
    private static final String TAG = "FlicrRecyclerViewAdapte";

    private List<Photo> photosList;
    private Context context;

    public FlicrRecyclerViewAdapter(Context context, List<Photo> photosList) {
        this.photosList = photosList;
        this.context = context;
    }

    @NonNull
    @Override
    public FlickrImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Called by the layout manager when it need a new view
        Log.d(TAG, "onCreateViewHolder: new view requested");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse, parent, false);

        return new FlickrImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FlickrImageViewHolder holder, int position) {
        //Called by the layout manager when it want new data in an existing row

        if ((photosList == null) || photosList.size() == 0) {
            holder.thumbnail.setImageResource(R.drawable.placeholder);
            holder.title.setText(R.string.empty_photo);
        } else {
            Photo photoItem = photosList.get(position);
            Log.d(TAG, "onBindViewHolder: " + photoItem.getTitle() + " ---> " + position);

            Picasso.get().load(photoItem.getImage())
                    .error(R.drawable.placeholder)
                    .placeholder(R.drawable.placeholder)
                    .into(holder.thumbnail);

            holder.title.setText(photoItem.getTitle());
        }
    }

    @Override
    public int getItemCount() {

        return ((photosList != null) && (photosList.size() != 0) ? photosList.size() : 1);
    }

    void loadNewData(List<Photo> newPhotos) {
        photosList = newPhotos;
        notifyDataSetChanged();
    }

    public Photo getPhoto(int position) {
        return ((photosList != null) && (photosList.size() != 0) ? photosList.get(position) : null);
    }

    static class FlickrImageViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "FlickrImageViewHolder";
        ImageView thumbnail = null;
        TextView title = null;

        public FlickrImageViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "FlickrImageViewHolder: starts");
            this.thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            this.title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
