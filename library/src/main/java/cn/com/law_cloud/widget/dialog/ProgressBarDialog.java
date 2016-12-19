package cn.com.law_cloud.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import cn.com.law_cloud.library.R;


/**
 * 加载中dialog
 */
public class ProgressBarDialog extends Dialog {

    private Context context;
    private String progressText;
    private LoadingCancelCallBack loadingCancel;

    public ProgressBarDialog(Context context) {
        super(context, R.style.dialog_theme);
        this.context = context;
    }

    public ProgressBarDialog(Context context, String progressText) {
        super(context, R.style.dialog_theme);
        this.context = context;
        this.progressText = progressText;
    }


    public void setLoadingCancel(LoadingCancelCallBack loadingCancel) {
        this.loadingCancel = loadingCancel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.include_dialog_progressbar);
        TextView title = (TextView) findViewById(R.id.custom_imageview_progress_title);
        title.setText(progressText == null ? "加载中..." : progressText);
    }

    public void setMessage(String message) {
        TextView title = (TextView) findViewById(R.id.custom_imageview_progress_title);
        title.setText(message);
    }

    @Override
    public void show() {
        if (!isShowing() && context != null) {
            super.show();
        }
    }


    @Override
    public void dismiss() {
        if (loadingCancel != null && isShowing()) {
            loadingCancel.loadCancel();
        }
        super.dismiss();
    }

    public interface LoadingCancelCallBack {
        public void loadCancel();
    }


}
