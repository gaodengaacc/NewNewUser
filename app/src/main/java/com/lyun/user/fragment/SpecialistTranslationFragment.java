package com.lyun.user.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyun.user.R;

import butterknife.BindView;

public class SpecialistTranslationFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.imageView_change)
    ImageView imageViewChange;
    @BindView(R.id.textView_title_change)
    TextView textViewTitleChange;
    @BindView(R.id.imageView_way_change)
    ImageView imageViewWayChange;

    private boolean mCommunicationMode = false;

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
        imageViewChange = (ImageView) view.findViewById(R.id.imageView_change);
        imageViewWayChange = (ImageView) view.findViewById(R.id.imageView_way_change);
        textViewTitleChange = (TextView) view.findViewById(R.id.textView_title_change);
        imageViewChange.setOnClickListener(this);
        imageViewWayChange.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageView_change:
                if (!mCommunicationMode) {
                    imageViewChange.setImageResource(R.mipmap.call_phone);
                    imageViewWayChange.setImageResource(R.drawable.image_call_selector);
                    textViewTitleChange.setText("语音呼叫");
                    mCommunicationMode = true;
                } else {
                    imageViewChange.setImageResource(R.mipmap.call_picture);
                    imageViewWayChange.setImageResource(R.mipmap.picture_dial_normal);
                    textViewTitleChange.setText("图文翻译");
                    mCommunicationMode = false;
                }
                break;
            case R.id.imageView_way_change:
//                ToastUtil.show(getActivity(),"点击");
                break;
        }
    }
}
