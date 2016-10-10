package lps.com.br.find_it;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

/**
 * Created by PC.RW on 11/09/2016.
 */
public class ImageAdapter extends BaseAdapter {
    Integer[] imageIDs = {
            R.drawable.foto0,
            R.drawable.foto1,
            R.drawable.foto2,
    };
    private Context context;
    private int itemBackground;
    public ImageAdapter(Context c)
    {
        context = c;
        TypedArray a = c.obtainStyledAttributes(R.styleable.MyGallery);
        itemBackground = a.getResourceId(R.styleable.MyGallery_android_galleryItemBackground, 0);
        a.recycle();
    }
    // returns the number of images
    public int getCount() {
        return imageIDs.length;
    }
    // returns the ID of an item
    public Object getItem(int position) {
        return position;
    }
    // returns the ID of an item
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(imageIDs[position]);
        imageView.setLayoutParams(new Gallery.LayoutParams(100, 100));
        imageView.setBackgroundResource(itemBackground);
        return imageView;
    }
}
