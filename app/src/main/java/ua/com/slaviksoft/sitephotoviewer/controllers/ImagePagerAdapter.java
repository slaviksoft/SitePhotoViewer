package ua.com.slaviksoft.sitephotoviewer.controllers;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ua.com.slaviksoft.sitephotoviewer.R;
import ua.com.slaviksoft.sitephotoviewer.model.FavoritesDB;
import ua.com.slaviksoft.sitephotoviewer.model.PageItem;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Slavik on 20.08.2016.
 */
public class ImagePagerAdapter extends PagerAdapter{

    private ArrayList<PageItem> urls;
    public ImagePagerAdapter(ArrayList<PageItem> urls){
        this.urls = urls;
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View)object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }



    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View viewItem = inflater.inflate(R.layout.image_view, container, false);
        PhotoView photoView = (PhotoView) viewItem.findViewById(R.id.iv_photo);
        ImageButton buttonFavorite = (ImageButton) viewItem.findViewById(R.id.imageButtonFavorite);

        final PhotoViewAttacher attacher = new PhotoViewAttacher(photoView);

        final PageItem currItem = urls.get(position);
        Picasso.with(container.getContext())
                .load(currItem.getUrl())
                .placeholder(container.getContext().getResources().getDrawable(android.R.drawable.ic_menu_camera))
                .into(photoView, new Callback() {
                    @Override
                    public void onSuccess() {
                        attacher.update();
                    }
                    @Override
                    public void onError() {
                    }
                });

        TextView textView = (TextView) viewItem.findViewById(R.id.textView);
        textView.setText(currItem.getTitle());

        FavoritesDB db = new FavoritesDB(container.getContext());
        if (db.find(currItem))
            buttonFavorite.setBackgroundDrawable(container.getResources().getDrawable(R.drawable.ic_favorite_yellow_checked_24dp));
        else
            buttonFavorite.setBackgroundDrawable(container.getResources().getDrawable(R.drawable.ic_favorite_yellow_unchecked_24dp));

        buttonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = v.getContext();

                FavoritesDB db = new FavoritesDB(context);
                boolean checked = db.find(currItem);

                if (checked) {
                    db.remove(currItem);
                    v.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_yellow_unchecked_24dp));
                }else{
                    db.add(currItem);
                    v.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_favorite_yellow_checked_24dp));
                }
            }
        });

        container.addView(viewItem);
        return viewItem;

    }


}
