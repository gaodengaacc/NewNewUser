package com.lyun.user.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.lyun.adapter.FragmentPagerBaseAdapter;
import com.lyun.api.response.APIPageResult;
import com.lyun.api.response.APIResult;
import com.lyun.library.mvvm.view.activity.GeneralToolbarActivity;
import com.lyun.library.mvvm.viewmodel.GeneralToolbarViewModel;
import com.lyun.library.mvvm.viewmodel.SimpleDialogViewModel;
import com.lyun.user.BR;
import com.lyun.user.R;
import com.lyun.user.api.response.InvoiceHistoryResponse;
import com.lyun.user.api.response.OrderHistoryResponse;
import com.lyun.user.databinding.ActivityAfterSaleServiceBinding;
import com.lyun.user.databinding.ItemAfterSaleServiceInvoiceHistoryBinding;
import com.lyun.user.databinding.ItemAfterSaleServiceOrderHistoryBinding;
import com.lyun.user.fragment.RecyclerViewFragment;
import com.lyun.user.model.AfterSaleServiceModel;
import com.lyun.user.viewmodel.AfterSaleServiceInvoiceHistoryViewModel;
import com.lyun.user.viewmodel.AfterSaleServiceOrderHistoryViewModel;
import com.lyun.user.viewmodel.AfterSaleServiceViewModel;
import com.lyun.user.viewmodel.RecyclerViewViewModel;
import com.lyun.user.viewmodel.watchdog.IAfterSaleServiceOrderHistoryViewModelCallbacks;
import com.lyun.utils.DisplayUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;


public class AfterSaleServiceActivity extends GeneralToolbarActivity<ActivityAfterSaleServiceBinding,
        AfterSaleServiceViewModel> {

    public final int REQUEST_CODE_APPLY_FOR_INVOICE = 0x001;

    private ViewPager mViewPager;

    public static void start(Context context) {
        context.startActivity(new Intent(context, AfterSaleServiceActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.after_sale_service_tablayout);
        mViewPager = (ViewPager) findViewById(R.id.after_sale_service_viewpager);

        tabLayout.setupWithViewPager(mViewPager);

        initPages();

        setTabIndicatorWidth(tabLayout);
    }

    protected void initPages() {
        List<Fragment> pages = new ArrayList<>();
        pages.add(RecyclerViewFragment.newInstance(new OrderHistoryAdapter(this)));
        pages.add(RecyclerViewFragment.newInstance(new InvoiceHistoryAdapter()));
        List<CharSequence> titles = new ArrayList<>();
        titles.add("交易记录");
        titles.add("开票记录");
        mViewPager.setAdapter(new FragmentPagerBaseAdapter(this, getSupportFragmentManager(), pages, titles));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_APPLY_FOR_INVOICE && resultCode == RESULT_OK) {
            initPages();
        }
    }

    protected void setTabIndicatorWidth(TabLayout tabLayout) {
        Class<?> clazz = tabLayout.getClass();
        try {
            Field tabStrip = clazz.getDeclaredField("mTabStrip");
            tabStrip.setAccessible(true);
            LinearLayout ll_tab = (LinearLayout) tabStrip.get(tabLayout);
            for (int i = 0; i < ll_tab.getChildCount(); i++) {
                View child = ll_tab.getChildAt(i);
                child.setPadding(0, 0, 0, 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                params.leftMargin = DisplayUtil.dip2px(this, 40);
                params.rightMargin = DisplayUtil.dip2px(this, 40);
                child.setLayoutParams(params);
                child.invalidate();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getBodyLayoutId() {
        return R.layout.activity_after_sale_service;
    }

    @NonNull
    @Override
    protected AfterSaleServiceViewModel createBodyViewModel() {
        AfterSaleServiceViewModel viewModel = new AfterSaleServiceViewModel();
        viewModel.setPropertyChangeListener(this);
        return viewModel;
    }

    @NonNull
    @Override
    protected GeneralToolbarViewModel.ToolbarViewModel createTitleViewModel() {
        GeneralToolbarViewModel.ToolbarViewModel toolbarViewModel = super.createTitleViewModel();
        toolbarViewModel.title.set("售后服务");
        toolbarViewModel.onBackClick.set(v -> finish());
        return toolbarViewModel;
    }

    public class OrderHistoryAdapter extends RecyclerViewViewModel.RecyclerViewAdapter<ItemAfterSaleServiceOrderHistoryBinding,
            AfterSaleServiceOrderHistoryViewModel,
            OrderHistoryResponse> implements IAfterSaleServiceOrderHistoryViewModelCallbacks {

        private AfterSaleServiceModel mModel;
        private Activity context;

        public OrderHistoryAdapter(Activity context) {
            super();
            this.context = context;
            mModel = new AfterSaleServiceModel();
        }

        @Override
        protected int getLayoutRes() {
            return R.layout.item_after_sale_service_order_history;
        }

        @Override
        protected int getVariableId() {
            return BR.mvvm;
        }

        @Override
        protected AfterSaleServiceOrderHistoryViewModel createViewModel(OrderHistoryResponse data) {
            return new AfterSaleServiceOrderHistoryViewModel(data).setPropertyChangeListener(this);
        }

        @Override
        protected Observable<APIResult<APIPageResult<List<OrderHistoryResponse>>>> getPage(int page) {
            return mModel.getOrderHistory(page);
        }

        @Override
        public void navigateApplyForInvoice(ObservableField<OrderHistoryResponse> observableField, int fieldId) {
            OrderHistoryResponse data = observableField.get();
            /**
             * 服务卡状态
             * -1 不可用
             * 0 未使用
             * 1 使用中
             * 2 已使用完
             * 3 已过期
             * 4 退款中
             * 5 已退款
             */
            boolean canApply = false;
            String message = "";
            switch (data.getCardState()) {
                case "3":
                    message = "此服务卡已过有效期，不能申请发票。";
                    break;
                case "4":
                    message = "此服务卡已退款成功，不能申请发票。";
                    break;
                case "5":
                    message = "此服务卡已退款成功，不能申请发票。";
                    break;
                default:
                    canApply = true;
                    break;
            }
            if (canApply) {
                ApplyForInvoiceActivity.startForResult(context, data.getOrderNo(), REQUEST_CODE_APPLY_FOR_INVOICE);
            } else {
                SimpleDialogViewModel dialog = new SimpleDialogViewModel(context, message)
                        .setBtnCancelVisibility(View.GONE);
                dialog.setOnItemClickListener(new SimpleDialogViewModel.OnItemClickListener() {
                    @Override
                    public void OnYesClick(View view) {

                    }

                    @Override
                    public void OnCancelClick(View view) {

                    }
                });
                dialog.show();
            }
        }

    }

    public class InvoiceHistoryAdapter extends RecyclerViewViewModel.RecyclerViewAdapter<ItemAfterSaleServiceInvoiceHistoryBinding, AfterSaleServiceInvoiceHistoryViewModel, InvoiceHistoryResponse> {

        private AfterSaleServiceModel mModel;

        public InvoiceHistoryAdapter() {
            super();
            mModel = new AfterSaleServiceModel();
        }

        @Override
        protected int getLayoutRes() {
            return R.layout.item_after_sale_service_invoice_history;
        }

        @Override
        protected int getVariableId() {
            return BR.mvvm;
        }

        @Override
        protected AfterSaleServiceInvoiceHistoryViewModel createViewModel(InvoiceHistoryResponse data) {
            return new AfterSaleServiceInvoiceHistoryViewModel(data);
        }

        @Override
        protected Observable<APIResult<APIPageResult<List<InvoiceHistoryResponse>>>> getPage(int page) {
            return mModel.getInvoiceHistory(page);
        }
    }

}
