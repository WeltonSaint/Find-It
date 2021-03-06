package lps.com.br.find_it;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Classe Adapter de Galeria
 *
 * @author PC.RW
 * @version 1.0 - 03/10/2016.
 *
 */

class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
        private ArrayList<String> gallery;
        private Context context;

        GalleryAdapter(Context context, ArrayList<String> gallery) {
            this.gallery = gallery;
            this.context = context;
        }

        @Override
        public GalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gallery_item, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(GalleryAdapter.ViewHolder viewHolder, int i) {
            viewHolder.img.setImageResource(context.getResources().getIdentifier("drawable/" + gallery.get(i), null, context.getPackageName()));
            //Picasso.with(context).load(gallery.get(i).getAndroid_image_url()).resize(240, 120).into(viewHolder.img);
        }

        @Override
        public int getItemCount() {
            return gallery.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            private ImageView img;
            ViewHolder(View view) {
                super(view);
                img = (ImageView) view.findViewById(R.id.img);
            }
        }



}
