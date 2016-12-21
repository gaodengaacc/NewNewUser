package com.lyun.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyun.activity.BaseActivity;
import com.lyun.user.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 郑成裕 on 2016/12/21.
 */

public class ServiceCategoryActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.imageView_back)
    ImageView imageViewBack;
    @BindView(R.id.relativeLayout_normalService)
    RelativeLayout relativeLayoutNormalService;
    @BindView(R.id.relativeLayout_travel)
    RelativeLayout relativeLayoutTravel;
    @BindView(R.id.relativeLayout_hotel)
    RelativeLayout relativeLayoutHotel;
    @BindView(R.id.relativeLayout_shopping)
    RelativeLayout relativeLayoutShopping;
    @BindView(R.id.relativeLayout_eat)
    RelativeLayout relativeLayoutEat;
    @BindView(R.id.relativeLayout_urgency)
    RelativeLayout relativeLayoutUrgency;
    @BindView(R.id.textView_normalService)
    TextView textViewNormalService;
    @BindView(R.id.textView_travel)
    TextView textViewTravel;
    @BindView(R.id.textView_hotel)
    TextView textViewHotel;
    @BindView(R.id.textView_shopping)
    TextView textViewShopping;
    @BindView(R.id.textView_eat)
    TextView textViewEat;
    @BindView(R.id.textView_urgency)
    TextView textViewUrgency;
    @BindView(R.id.imageView_normalService)
    ImageView imageViewNormalService;
    @BindView(R.id.imageView_travel)
    ImageView imageViewTravel;
    @BindView(R.id.imageView_hotel)
    ImageView imageViewHotel;
    @BindView(R.id.imageView_shopping)
    ImageView imageViewShopping;
    @BindView(R.id.imageView_eat)
    ImageView imageViewEat;
    @BindView(R.id.imageView_urgency)
    ImageView imageViewUrgency;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_category);
        ButterKnife.bind(this);

        initImageView();//初始化choise图标

        imageViewBack.setOnClickListener(this);
        relativeLayoutNormalService.setOnClickListener(this);
        relativeLayoutTravel.setOnClickListener(this);
        relativeLayoutHotel.setOnClickListener(this);
        relativeLayoutShopping.setOnClickListener(this);
        relativeLayoutEat.setOnClickListener(this);
        relativeLayoutUrgency.setOnClickListener(this);
    }

    private void initImageView() {
        imageViewNormalService.setVisibility(View.INVISIBLE);
        imageViewTravel.setVisibility(View.INVISIBLE);
        imageViewHotel.setVisibility(View.INVISIBLE);
        imageViewShopping.setVisibility(View.INVISIBLE);
        imageViewEat.setVisibility(View.INVISIBLE);
        imageViewUrgency.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        intent.setClass(ServiceCategoryActivity.this, MainActivity.class);

        switch (view.getId()) {

            case R.id.imageView_back:

                finish();
                break;
            case R.id.relativeLayout_normalService:

                bundle.putString("category", textViewNormalService.getText().toString());
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.relativeLayout_travel:

                bundle.putString("category", textViewTravel.getText().toString());
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.relativeLayout_hotel:
                bundle.putString("category", textViewHotel.getText().toString());
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.relativeLayout_shopping:
                bundle.putString("category", textViewShopping.getText().toString());
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.relativeLayout_eat:
                bundle.putString("category", textViewEat.getText().toString());
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.relativeLayout_urgency:
                bundle.putString("category", textViewUrgency.getText().toString());
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
