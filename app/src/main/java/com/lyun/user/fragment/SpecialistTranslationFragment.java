package com.lyun.user.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lyun.user.R;
import com.lyun.user.activity.ServiceCategoryActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class SpecialistTranslationFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.imageView_change)
    ImageView imageViewChange;
    @BindView(R.id.textView_title_change)
    TextView textViewTitleChange;
    @BindView(R.id.imageView_way_change)
    ImageView imageViewWayChange;
    @BindView(R.id.relativeLayout_category)
    RelativeLayout relativeLayoutCategory;
    @BindView(R.id.textView_serviceCategory)
    TextView textViewServiceCategory;

    private boolean mCommunicationMode = false;
    private int requestCode = 0;
    String stringServiceCategory = "";

    public SpecialistTranslationFragment() {
        // Required empty public constructor
    }

    public static SpecialistTranslationFragment newInstance() {
        SpecialistTranslationFragment fragment = new SpecialistTranslationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_specialist_translation, container, false);
        ButterKnife.bind(this, view);

        imageViewChange.setOnClickListener(this);
        relativeLayoutCategory.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relativeLayout_category:
                Intent intent = new Intent();
                intent.setClass(getActivity(), ServiceCategoryActivity.class);
                startActivityForResult(intent, requestCode);
                break;
            case R.id.imageView_change:
                if (!mCommunicationMode) {
                    imageViewChange.setImageResource(R.mipmap.call_phone);
                    imageViewWayChange.setImageResource(R.drawable.image_call_selector);
                    textViewTitleChange.setText("语音呼叫");
                    mCommunicationMode = true;
                } else {
                    imageViewChange.setImageResource(R.mipmap.call_picture);
                    imageViewWayChange.setImageResource(R.drawable.image_picture_selector);
                    textViewTitleChange.setText("图文翻译");
                    mCommunicationMode = false;
                }
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            if (!(bundle.equals("")) && !(bundle == null)) {
                textViewServiceCategory.setText(bundle.getString("category"));
            }
        }
    }
}
