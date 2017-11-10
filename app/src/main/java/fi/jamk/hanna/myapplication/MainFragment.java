/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package fi.jamk.hanna.myapplication;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

public class MainFragment extends BrowseFragment {
    private static final int BACKGROUND_UPDATE_DELAY = 300;
    private final Handler handler = new Handler();
    private ArrayObjectAdapter rowsAdapter;
    private DisplayMetrics metrics;
    private Timer backgroundTimer;
    private URI backgroundURI;
    private BackgroundManager backgroundManager;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        prepareBackgroundManager();
        setupUIElements();
        loadRows();
        setupEventListeners();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != backgroundTimer) {
            backgroundTimer.cancel();
        }
    }

    private void loadRows() {
        List<Picture> list = PictureList.setupPictures();

        rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        CardPresenter cardPresenter = new CardPresenter();
        ArrayObjectAdapter catRowAdapter = new ArrayObjectAdapter(cardPresenter);
        ArrayObjectAdapter landscapeRowAdapter = new ArrayObjectAdapter(cardPresenter);
        for(Picture p : list){
            if(p.getCategory() == "Cat"){
                catRowAdapter.add(p);
            }else if(p.getCategory() == "Landscape"){
                landscapeRowAdapter.add(p);
            }
        }
        for (int i = 0; i < PictureList.PICTURE_CATEGORY.length; i++) {
            HeaderItem header = new HeaderItem(i, PictureList.PICTURE_CATEGORY[i]);
            if(PictureList.PICTURE_CATEGORY[i] == "Cat")
                rowsAdapter.add(new ListRow(header, catRowAdapter));
            else
                rowsAdapter.add(new ListRow(header, landscapeRowAdapter));
        }
        setAdapter(rowsAdapter);

    }

    private void prepareBackgroundManager() {

        backgroundManager = BackgroundManager.getInstance(getActivity());
        backgroundManager.attach(getActivity().getWindow());
        metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
    }

    private void setupUIElements() {
        setBrandColor(getResources().getColor(R.color.fastlane_background));
    }

    private void setupEventListeners() {
        setOnItemViewClickedListener(new ItemViewClickedListener());
        setOnItemViewSelectedListener(new ItemViewSelectedListener());
    }

    protected void updateBackground(String uri) {
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        Glide.with(getActivity())
                .load(uri)
                .centerCrop()
                .into(new SimpleTarget<GlideDrawable>(width, height) {
                    @Override
                    public void onResourceReady(GlideDrawable resource,
                                                GlideAnimation<? super GlideDrawable>
                                                        glideAnimation) {
                        backgroundManager.setDrawable(resource);
                    }
                });
        backgroundTimer.cancel();
    }

    private void startBackgroundTimer() {
        if (null != backgroundTimer) {
            backgroundTimer.cancel();
        }
        backgroundTimer = new Timer();
        backgroundTimer.schedule(new UpdateBackgroundTask(), BACKGROUND_UPDATE_DELAY);
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
            Picture pic = (Picture) item;
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra(DetailsActivity.PICTURE, pic);
            Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    getActivity(), ((ImageCardView) itemViewHolder.view).getMainImageView(),
                    DetailsActivity.SHARED_ELEMENT_NAME).toBundle();
            getActivity().startActivity(intent, bundle);
        }
    }

    private final class ItemViewSelectedListener implements OnItemViewSelectedListener {
        @Override
        public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,
                                   RowPresenter.ViewHolder rowViewHolder, Row row) {
            if (item instanceof Picture) {
                backgroundURI = ((Picture) item).getBackgroundImageURI();
                startBackgroundTimer();
            }

        }
    }

    private class UpdateBackgroundTask extends TimerTask {

        @Override
        public void run() {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (backgroundURI != null) {
                        updateBackground(backgroundURI.toString());
                    }
                }
            });

        }
    }
}
