package net.yeoubi.paginator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

/**
 * @author InJung Chung
 */
public class PagingRecyclerView extends FrameLayout {

    public interface OnPaginateListener {
        void onPaginate(int page);
    }

    public int page = 1;
    public int itemsPerPage = 20;

    private OnPaginateListener paginateListener;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private RecyclerView.OnScrollListener pagingScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (!(recyclerView.getLayoutManager() instanceof LinearLayoutManager)) {
                return;
            }

            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int totalItemCount = recyclerView.getAdapter() != null ? recyclerView.getAdapter().getItemCount() : 0;
            int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

            if (lastVisibleItemPosition + 1 >= itemsPerPage * (page + 1)) {
                hideLoading();
                return;
            }

            // Check if needs to paginate
            if (lastVisibleItemPosition + 1 >= itemsPerPage * page) {
                if (paginateListener == null) {
                    return;
                }

                paginateListener.onPaginate(++page);

                // Check if needs to show progress bar
                if (lastVisibleItemPosition == totalItemCount - 1) {
                    showLoading();
                }
            }
        }
    };

    // Hide progress bar if data changed
    private RecyclerView.AdapterDataObserver dataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            hideLoading();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            hideLoading();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
            super.onItemRangeChanged(positionStart, itemCount, payload);
            hideLoading();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            hideLoading();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            hideLoading();
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            hideLoading();
        }
    };

    public PagingRecyclerView(@NonNull Context context) {
        super(context);
        init();
    }

    public PagingRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        if (attrs != null) {
            setAttributes(attrs);
        }
    }

    public PagingRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        if (attrs != null) {
            setAttributes(attrs);
        }
    }

    /**
     * Initialize PagingRecyclerView layout and set references
     */
    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.paging_recycler_view, this, false);
        addView(view);

        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.progress_view);

        bind();
    }

    private void bind() {
        recyclerView.addOnScrollListener(pagingScrollListener);
        if (recyclerView.getAdapter() != null) {
            recyclerView.getAdapter().registerAdapterDataObserver(dataObserver);
        }
    }

    public void unbind() {
        recyclerView.removeOnScrollListener(pagingScrollListener);
        if (recyclerView.getAdapter() != null) {
            recyclerView.getAdapter().unregisterAdapterDataObserver(dataObserver);
        }
    }

    /**
     * Setter for ProgressBar color
     *
     * @param attrs AttributeSet that includes color
     */
    private void setAttributes(@Nullable AttributeSet attrs) {
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.PagingRecyclerView);
        int color = attributes.getColor(R.styleable.PagingRecyclerView_color, 0);
        setColor(color);
        attributes.recycle();
    }

    /**
     * Setter for color attribute
     *
     * @param color ProgressBar color
     */
    public void setColor(int color) {
        progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
    }

    /**
     * Getter for paginateListener
     *
     * @return paginateListener
     */
    public OnPaginateListener getOnPaginateListener() {
        return paginateListener;
    }

    /**
     * Setter for paginateListener attribute
     *
     * @param paginateListener OnPaginateListener instance
     */
    public void setOnPaginateListener(OnPaginateListener paginateListener) {
        this.paginateListener = paginateListener;
        paginateListener.onPaginate(page);
        showLoading();
    }

    /**
     * Getter for RecyclerView.Adapter
     *
     * @return RecyclerView's adapter
     */
    public RecyclerView.Adapter getAdapter() {
        return recyclerView.getAdapter();
    }

    /**
     * Setter for RecyclerView Adapter attribute
     *
     * @param adapter RecyclerView adapter
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }
}
