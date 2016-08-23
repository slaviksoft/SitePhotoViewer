package ua.com.slaviksoft.sitephotoviewer.controllers;

import android.support.annotation.DrawableRes;
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

        // checked favorite if used in db or unchecked in not used
        FavoritesDB db = new FavoritesDB(container.getContext());
        if (db.find(currItem))
            setFavoriteIcon(buttonFavorite, true);
        else
            setFavoriteIcon(buttonFavorite, false);

        // favorite button toggle OnClick
        buttonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavoritesDB db = new FavoritesDB(v.getContext());
                if (db.find(currItem)) {
                    db.remove(currItem);
                    setFavoriteIcon(v, false);
                }else{
                    db.add(currItem);
                    setFavoriteIcon(v, true);
                }
            }
        });

        container.addView(viewItem);
        return viewItem;

    }

    private void setFavoriteIcon(View v, boolean checked){
        if (checked)
            setBackground(v, R.drawable.ic_favorite_yellow_checked_24dp);
        else
            setBackground(v, R.drawable.ic_favorite_yellow_unchecked_24dp);
    }


    private void setBackground(View v, @DrawableRes int id){
        v.setBackgroundDrawable(v.getContext().getResources().getDrawable(id));
    }

}
