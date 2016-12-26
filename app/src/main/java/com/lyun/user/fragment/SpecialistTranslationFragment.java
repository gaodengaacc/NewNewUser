package com.lyun.user.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lyun.user.R;
import com.lyun.user.activity.ServiceCategoryActivity;
import com.lyun.user.dialog.LanguageChoicePopupWindow;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.linearLayout_language)
    LinearLayout linearLayoutLanguage;
    @BindView(R.id.textView_language1)
    TextView textViewLanguage1;
    @BindView(R.id.textView_language2)
    TextView textViewLanguage2;
    @BindView(R.id.relativeLayout_title)
    RelativeLayout relativeLayoutTitle;
    @BindView(R.id.textView_remind)
    TextView textViewRemind;
    @BindView(R.id.view_blank)
    View viewBlank;

    private boolean mCommunicationMode = false;
    private int requestCode = 0;

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

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick({R.id.linearLayout_language, R.id.relativeLayout_category, R.id.imageView_change})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linearLayout_language:
                LanguageChoicePopupWindow languageChoicePopupWindow = new LanguageChoicePopupWindow(getActivity(), linearLayoutLanguage.getWidth());//获取控件宽度
                WindowManager.LayoutParams layoutParams = getActivity().getWindow().getAttributes();
                layoutParams.alpha = 0.5f;//透明度
                getActivity().getWindow().setAttributes(layoutParams);

                languageChoicePopupWindow.showAsDropDown(linearLayoutLanguage, 0, 0, Gravity.CENTER_HORIZONTAL);//设置显示在该控件的下方
                languageChoicePopupWindow.setChooseListener(new LanguageChoicePopupWindow.ChooseListener() {
                    @Override
                    public void onClick(String language1, String language2) {
                        if (language1.equals(language2)) {
                            Toast.makeText(getActivity().getApplicationContext(), "母语和目标语言不能相同", Toast.LENGTH_SHORT).show();
                        } else {
                            textViewLanguage1.setText(language1);
                            textViewLanguage2.setText(language2);
                        }

                    }
                });
                languageChoicePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams layoutParams = getActivity().getWindow().getAttributes();
                        layoutParams.alpha = 1f;
                        getActivity().getWindow().setAttributes(layoutParams);
                    }
                });
                break;
            case R.id.relativeLayout_category:
                Intent intent = new Intent();

                intent.setClass(getActivity(), ServiceCategoryActivity.class);
                intent.putExtra("languageCategory", textViewServiceCategory.getText().toString());//传递服务类别
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {//接收从serviceactivity中返回的数据
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            if (!(bundle.equals("")) && !(bundle == null)) {
                textViewServiceCategory.setText(bundle.getString("category"));
            }
        }
    }
}
