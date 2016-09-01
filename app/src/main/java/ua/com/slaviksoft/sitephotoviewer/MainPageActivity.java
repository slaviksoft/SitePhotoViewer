package ua.com.slaviksoft.sitephotoviewer;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import ua.com.slaviksoft.sitephotoviewer.controllers.PageListAdapter;
import ua.com.slaviksoft.sitephotoviewer.controllers.PageLoadeListener;
import ua.com.slaviksoft.sitephotoviewer.controllers.PageLoader;
import ua.com.slaviksoft.sitephotoviewer.model.MainPage;
import ua.com.slaviksoft.sitephotoviewer.model.PageItem;

public class MainPageActivity extends AppCompatActivity implements PageLoadeListener<MainPage>, PageListAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private ImageView imageViewEmpty;
    private GridLayoutManager layoutManager;
    private Button buttonPage;
    private PageListAdapter pagesListAdapter;
    private PageLoader<MainPage> mainPageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        layoutManager = new GridLayoutManager(this, 3);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        imageViewEmpty = (ImageView) findViewById(R.id.imageViewEmpty);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMainPage);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        buttonPage = (Button) findViewById(R.id.buttonPage);

        loadPage();

        NinePatchDrawable drawable = (NinePatchDrawable) getResources().getDrawable(R.drawable.ic_gallery);



        Log.d("DEBUG", "ICON = "+drawable.getIntrinsicWidth()+"X"+drawable.getIntrinsicHeight());

    }

    @Override
    public void onPageLoaded(MainPage page) {
        Log.d("DEBUG", "event loaded");
        pagesListAdapter = new PageListAdapter(page.getItems(), this);
        recyclerView.setAdapter(pagesListAdapter);
        if (pagesListAdapter.getItemCount() == 0)
            imageViewEmpty.setVisibility(View.VISIBLE);
        else
            imageViewEmpty.setVisibility(View.GONE);
    }

    private void loadPage(){
        int pageNum = getPageNum();

        MainPage page = new MainPage();
        page.setUrl("http://myass.ru/page/"+pageNum);

        mainPageLoader = new PageLoader(this);
        mainPageLoader.setListener(this);
        mainPageLoader.execute(page);

    }

    private int getPageNum(){
        int num = Integer.parseInt(buttonPage.getText().toString());
        return num;
    }

    private void setPageNum(int num){
        buttonPage.setText(String.valueOf(num));
        loadPage();
    }

    public void onRefreshClick(View view) {
        loadPage();
    }

    public void onMinusClick(View view) {

        int num = getPageNum();
        num--;
        if (num < 1) num = 1;

        setPageNum(num);

    }

    public void onPlusClick(View view) {
        int num = getPageNum();
        num++;
        //if (num > 100) num = 100;
        setPageNum(num);
    }

    public void onPageClick(View view) {

        final Dialog d = new Dialog(this);
        d.setTitle("NumberPicker");
        d.setContentView(R.layout.page_dialog);
        Button b1 = (Button) d.findViewById(R.id.button1);
        b1.setText(android.R.string.ok);
        Button b2 = (Button) d.findViewById(R.id.button2);
        b2.setText(android.R.string.cancel);
        final EditText e1 = (EditText) d.findViewById(R.id.editText);

        e1.setText(""+getPageNum());
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                setPageNum(Integer.valueOf(e1.getText().toString()));
                d.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss(); // dismiss the dialog
            }
        });
        d.show();



    }

    @Override
    public void onItemClick(PageItem item) {
        Log.d("DEBUG", item.getUrl());
        Intent intent = new Intent(MainPageActivity.this, ImagesActivity.class);
        intent.putExtra(PageItem.EXTRA_NAME, item);
        startActivity(intent);
    }

    public void onFavoritesClick(View view) {
        Intent intent = new Intent(MainPageActivity.this, ImagesActivity.class);
        intent.putExtra(PageItem.EXTRA_LOAD_FROM_DB, true);
        startActivity(intent);
    }

}
