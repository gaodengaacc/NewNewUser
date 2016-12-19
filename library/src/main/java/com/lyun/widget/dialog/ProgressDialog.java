package com.lyun.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.widget.TextView;

import com.lyun.library.R;
import com.lyun.widget.DonutProgress;

/**
 * 进度对话框
 *
 * @author 赵尉尉
 * @since 2015/7/3 18:04
 */
public class ProgressDialog extends Dialog {

    private Context context;

    private DonutProgress mDonutProgress;
    private TextView mTextView;

    private long mMaxProgress = 100;
    private long mProgress = 0;

    private String mText = "正在上传数据...";

    public ProgressDialog(Context context) {
        super(context, R.style.dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置
        setContentView(R.layout.dialog_progress);

        // 点击Dialog外部消失
        setCanceledOnTouchOutside(false);

        mDonutProgress = (DonutProgress) findViewById(R.id.progress_progress);
        mTextView = (TextView) findViewById(R.id.progress_text);

        setProgress(mProgress);
        mTextView.setText(mText);
    }

    public void setMaxProgress(long max) {
        mMaxProgress = max;
    }

    public void setProgress(long progress) {
        if (mDonutProgress != null) {
            mDonutProgress.setProgress((int) (100 * progress / mMaxProgress));
        }
        mProgress = progress;
    }

    public void setText(String text) {
        if (mTextView != null) {
            mTextView.setText(text);
        }
        mText = text;
    }
}
