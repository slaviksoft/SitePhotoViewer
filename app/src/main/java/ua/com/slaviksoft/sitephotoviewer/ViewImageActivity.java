package ua.com.slaviksoft.sitephotoviewer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

import ua.com.slaviksoft.sitephotoviewer.controllers.HackyViewPager;
import ua.com.slaviksoft.sitephotoviewer.controllers.ImagePagerAdapter;
import ua.com.slaviksoft.sitephotoviewer.model.PageItem;

public class ViewImageActivity extends AppCompatActivity{

    private ArrayList<PageItem> items;
    private ImagePagerAdapter pagerAdapter;
    private PageItem currentItem;
    private HackyViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        items = getIntent().getParcelableArrayListExtra(PageItem.EXTRA_LIST_NAME);
        PageItem currentItem = getIntent().getParcelableExtra(PageItem.EXTRA_NAME);

        int current = 0;
        for (int i = 0; i < items.size(); i++) {
            Log.d("DEBUG", items.get(i).getUrl());
            if (currentItem.getUrl().equals(items.get(i).getUrl())){
                current = i;
                break;
            }
        }

        viewPager = (HackyViewPager) findViewById(R.id.viewPager);
        pagerAdapter = new ImagePagerAdapter(items);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(current);

    }

}
