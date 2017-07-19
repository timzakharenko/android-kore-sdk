package kore.botssdk.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import kore.botssdk.R;
import kore.botssdk.adapter.BotCarouselItemButtonAdapter;
import kore.botssdk.models.BotCarouselModel;
import kore.botssdk.utils.BundleConstants;

/**
 * Created by Pradeep Mahato on 14-July-17.
 * Copyright (c) 2014 Kore Inc. All rights reserved.
 */
public class CarouselFragment extends Fragment {

    ImageView carouselItemImage;
    TextView carouselItemTitle, carouselItemSubTitle, carousel_textViw;
    ListView carouselButtonListview;
    RelativeLayout carouselItemRoot;

    BotCarouselModel botCarouselModel;
    Gson gson;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.carousel_item_layout, container, false);

        gson = new Gson();
//        extractFromBundle();
        findViews(view);
//        populateView();

        return view;
    }

    void findViews(View view) {
        carouselItemRoot = (RelativeLayout) view.findViewById(R.id.carousel_item_root);
        carouselItemImage = (ImageView) view.findViewById(R.id.carousel_item_image);
        carouselItemTitle = (TextView) view.findViewById(R.id.carousel_item_title);
        carouselItemSubTitle = (TextView) view.findViewById(R.id.carousel_item_subtitle);
        carouselButtonListview = (ListView) view.findViewById(R.id.carousel_button_listview);
        carousel_textViw = (TextView) view.findViewById(R.id.carousel_textViw);
    }

    int carouselPosition;
    void extractFromBundle() {
        Bundle bundle = getArguments();

        String carouselModel = bundle.getString(BundleConstants.CAROUSEL_ITEM, "");
        carouselPosition = bundle.getInt(BundleConstants.CAROUSEL_ITEM_POSITION);

        if (!carouselModel.isEmpty()) {
            botCarouselModel = gson.fromJson(carouselModel, BotCarouselModel.class);
        }
    }

    void populateView() {
        if (botCarouselModel != null) {
            carouselItemTitle.setText(botCarouselModel.getTitle());
            carouselItemSubTitle.setText(botCarouselModel.getSubtitle());

            Picasso.with(getActivity()).load(botCarouselModel.getImage_url()).into(carouselItemImage);

            BotCarouselItemButtonAdapter botCarouselItemButtonAdapter = new BotCarouselItemButtonAdapter(getActivity());
            carouselButtonListview.setAdapter(botCarouselItemButtonAdapter);
            botCarouselItemButtonAdapter.setBotCaourselButtonModels(botCarouselModel.getButtons());

            carousel_textViw.setText(carouselPosition + "");
            switch (carouselPosition) {
                case 0:
                    carouselItemRoot.setBackgroundColor(0xffff0000);
                    break;
                case 1:
                    carouselItemRoot.setBackgroundColor(0xff00ff00);
                    break;
                case 2:
                    carouselItemRoot.setBackgroundColor(0xff0000ff);
                    break;
                default:
                    carouselItemRoot.setBackgroundColor(0xff0f0f0f);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}