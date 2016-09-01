package ua.com.slaviksoft.sitephotoviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

import ua.com.slaviksoft.sitephotoviewer.controllers.PageListAdapter;
import ua.com.slaviksoft.sitephotoviewer.controllers.PageLoadeListener;
import ua.com.slaviksoft.sitephotoviewer.controllers.PageLoader;
import ua.com.slaviksoft.sitephotoviewer.model.FavoritesDB;
import ua.com.slaviksoft.sitephotoviewer.model.ImagesPage;
import ua.com.slaviksoft.sitephotoviewer.model.PageItem;

public class ImagesActivity extends AppCompatActivity implements PageLoadeListener<ImagesPage>, PageListAdapter.OnItemClickListener{

    private RecyclerView recyclerView;
    private ImageView imageViewEmpty;
    private StaggeredGridLayoutManager layoutManager;
    private EditText editTextPage;
    private PageListAdapter pagesListAdapter;
    private PageLoader<ImagesPage> mainPageLoader;
    private ImagesPage page;
    private PageItem pageItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        imageViewEmpty = (ImageView) findViewById(R.id.imageViewEmpty);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewImages);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        if (getIntent().hasExtra(PageItem.EXTRA_LOAD_FROM_DB)){
            loadPageFromDb();
        }else {
            pageItem = getIntent().getParcelableExtra(PageItem.EXTRA_NAME);
            loadPageFromUrl(pageItem);
        }

    }

    private void loadPageFromDb(){
        page = new ImagesPage();
        FavoritesDB db = new FavoritesDB(this);
        page.setItems(db.query());
        pagesListAdapter = new PageListAdapter(page.getItems(), this);
        recyclerView.setAdapter(pagesListAdapter);
        processEmptyView();
    }

    private void loadPageFromUrl(PageItem item){

        page = new ImagesPage();
        page.setUrl(item.getUrl());
        page.setHeader(item.getHeader());

        mainPageLoader = new PageLoader(this);
        mainPageLoader.setListener(this);
        mainPageLoader.execute(page);

    }

    private void processEmptyView(){
        if (pagesListAdapter.getItemCount() == 0)
            imageViewEmpty.setVisibility(View.VISIBLE);
        else
            imageViewEmpty.setVisibility(View.GONE);

    }

    @Override
    public void onPageLoaded(ImagesPage page) {
        Log.d("DEBUG", "event loaded");
        pagesListAdapter = new PageListAdapter(page.getItems(), this);
        recyclerView.setAdapter(pagesListAdapter);
        processEmptyView();
    }

    @Override
    public void onItemClick(PageItem item) {
        Log.d("DEBUG", item.getUrlImg()+" - "+item.getUrl());

        Intent intent = new Intent(this, ViewImageActivity.class);
        intent.putExtra(PageItem.EXTRA_NAME, item); //current item
        intent.putParcelableArrayListExtra(PageItem.EXTRA_LIST_NAME, new ArrayList<PageItem>(page.getItems()));

        startActivity(intent);

    }
}
