package com.lyun.library.mvvm.bindingadapter.recyclerview;

import android.databinding.BindingAdapter;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lyun.adapter.BaseRecyclerAdapter;
import com.lyun.library.mvvm.OnRecycleItemClickListener;
import com.lyun.library.mvvm.command.RelayCommand;
import com.lyun.library.mvvm.viewmodel.ViewModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public class ViewBindingAdapter {

    @BindingAdapter(value = {"onScrollChangeCommand", "onScrollStateChangedCommand"}, requireAll = false)
    public static void onScrollChangeCommand(final RecyclerView recyclerView,
                                             final RelayCommand<ScrollDataWrapper> onScrollChangeCommand,
                                             final RelayCommand<Integer> onScrollStateChangedCommand) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int state;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (onScrollChangeCommand != null) {
                    onScrollChangeCommand.execute(new ScrollDataWrapper(dx, dy, state));
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                state = newState;
                if (onScrollStateChangedCommand != null) {
                    onScrollChangeCommand.equals(newState);
                }
            }
        });

    }

    @SuppressWarnings("unchecked")
    @BindingAdapter({"onLoadMoreCommand"})
    public static void onLoadMoreCommand(final RecyclerView recyclerView, final RelayCommand<Integer> onLoadMoreCommand) {
        RecyclerView.OnScrollListener listener = new OnScrollListener(onLoadMoreCommand);
        recyclerView.addOnScrollListener(listener);
    }

    @Deprecated
    @BindingAdapter({"layoutManager"})
    public static void setLayoutManage(RecyclerView recyclerView, final RelayCommand<RecyclerView> onLoadMoreCommand) {
        onLoadMoreCommand.execute(recyclerView);
    }

    @BindingAdapter({"layoutManager"})
    public static void setLayoutManager(RecyclerView recyclerView, final RecyclerView.LayoutManager manager) {
        recyclerView.setLayoutManager(manager);
    }

    @BindingAdapter({"notifyData"})
    public static void setNotifyData(RecyclerView recyclerView, List<? extends ViewModel> data) {
        BaseRecyclerAdapter adapter = (BaseRecyclerAdapter) recyclerView.getAdapter();
        if (data != null)
            adapter.setListData(data);
    }

    @BindingAdapter("adapter")
    public static void setAdapter(RecyclerView recyclerView, BaseRecyclerAdapter adapter) {
        if (adapter != null)
            recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("isScroll")
    public static void setIsScroll(RecyclerView recyclerView, final boolean isScroll) {
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 1) {
            @Override
            public boolean canScrollVertically() {
                return isScroll;
            }
        });//设置布局管理器,优化scrollview嵌套recyclerview惯性滑动
    }

    @BindingAdapter("header")
    public static void setHeader(RecyclerView recyclerView, @LayoutRes int layoutRes) {
        if (layoutRes > 0 && recyclerView.getAdapter() != null) {
            View view = View.inflate(recyclerView.getContext(), layoutRes, null);
            ((BaseRecyclerAdapter) recyclerView.getAdapter()).setHeaderView(view);
        } else {
            ((BaseRecyclerAdapter) recyclerView.getAdapter()).setHeaderView(null);
        }

    }

    @BindingAdapter("footer")
    public static void setFooter(RecyclerView recyclerView, @LayoutRes int layoutRes) {
        View view = View.inflate(recyclerView.getContext(), layoutRes, null);
        ((BaseRecyclerAdapter) recyclerView.getAdapter()).setFooterView(view);
    }

    @BindingAdapter("onItemClickCommand")
    public static void setOnItemClickListener(RecyclerView recyclerView, final RelayCommand<ClickListenerData> clickCommand) {
        BaseRecyclerAdapter adapter = (BaseRecyclerAdapter) recyclerView.getAdapter();
        if (adapter != null)
            adapter.setItemClickListener(
                    new OnRecycleItemClickListener() {

                        @Override
                        public void onItemClick(View view, List viewModels, int position) {
                            clickCommand.execute(new ClickListenerData(viewModels, position));
                        }
                    }
            );
    }

    @BindingAdapter("onItemLongClickCommand")
    public static void setOnItemLongClickListener(RecyclerView recyclerView, final RelayCommand<ClickListenerData> longClickCommand) {
        BaseRecyclerAdapter adapter = (BaseRecyclerAdapter) recyclerView.getAdapter();
        adapter.setItemLongClickListener(new OnRecycleItemClickListener() {
            @Override
            public void onItemClick(View view, List viewModels, int position) {
                longClickCommand.execute(new ClickListenerData(viewModels, position));
            }
        });

    }

    public static class OnScrollListener extends RecyclerView.OnScrollListener {

        private PublishSubject<Integer> methodInvoke = PublishSubject.create();

        private RelayCommand<Integer> onLoadMoreCommand;

        public OnScrollListener(RelayCommand<Integer> onLoadMoreCommand) {
            this.onLoadMoreCommand = onLoadMoreCommand;
            methodInvoke.throttleFirst(1, TimeUnit.SECONDS)
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            OnScrollListener.this.onLoadMoreCommand.execute(integer);
                        }
                    });
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();
            if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                if (onLoadMoreCommand != null) {
                    methodInvoke.onNext(recyclerView.getAdapter().getItemCount());
                }
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }


    }

    public static class ScrollDataWrapper {
        public float scrollX;
        public float scrollY;
        public int state;

        public ScrollDataWrapper(float scrollX, float scrollY, int state) {
            this.scrollX = scrollX;
            this.scrollY = scrollY;
            this.state = state;
        }
    }

    public static class ClickListenerData {
        public List<ViewModel> list;
        public int position;

        public ClickListenerData(List<ViewModel> list, int position) {
            this.list = list;
            this.position = position;
        }
    }
}
