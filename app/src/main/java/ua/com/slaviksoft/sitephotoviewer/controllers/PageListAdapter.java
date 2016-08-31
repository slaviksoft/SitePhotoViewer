package ua.com.slaviksoft.sitephotoviewer.controllers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ua.com.slaviksoft.sitephotoviewer.R;
import ua.com.slaviksoft.sitephotoviewer.model.PageItem;

/**
 * Created by Slavik on 17.08.2016.
 */

public class PageListAdapter extends RecyclerView.Adapter<PageListAdapter.ViewHolder>{

    private List<PageItem> mList;
    private OnItemClickListener mClickListener;

    public PageListAdapter(List<PageItem> list, OnItemClickListener onClickListener){
        mList = list;
        mClickListener = onClickListener;
    }

    private PageItem getItem(int position){
        return mList.get(position);
    }

    // EVENTS

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.page_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.updateViews(getItem(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    // HOLDER

    public class ViewHolder extends RecyclerView.ViewHolder{

            private Context context;
            public TextView title;
            public ImageView image;
            private PageItem item;

            public ViewHolder(View itemView){
                super(itemView);
                this.title = (TextView) itemView.findViewById(R.id.textViewTitle);
                this.image = (ImageView) itemView.findViewById(R.id.imageViewPromo);
                this.context = itemView.getContext();
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mClickListener.onItemClick(item);
                    }
                });
            }

            public void updateViews(PageItem item){
                this.item = item;
                this.title.setText(item.getTitle());
                Picasso.with(context)
                        .load(item.getUrlImg())
                        .placeholder(context.getResources().getDrawable(R.drawable.ic_gallery))
                        .into(image)
                        ;

            }
    }

    public interface OnItemClickListener{
        public void onItemClick(PageItem item);
    }

}

