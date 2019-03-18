package com.msjo.uicomponentlib.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class MSLoopViewPagerAdapter<T> extends PagerAdapter {

    protected abstract View inflateView(int viewType, ViewGroup container, int position);
    protected abstract void bindView(View convertView, int position, int viewType);
    protected int getItemViewType(int position) {
        return position;
    }

    protected List<T> itemList;

    private Context context;
    private SparseArray<View> viewCache = new SparseArray<>();

    private boolean isInfinite;
    private boolean dataSetChangeLock;


    public MSLoopViewPagerAdapter(final Context context, final List<T> itemList) {
        this.context = context;
        setItemList(itemList);
    }

    public MSLoopViewPagerAdapter(final Context context, final List<T> itemList, final boolean isInfinite) {
        this.context = context;
        this.isInfinite = isInfinite;
        setItemList(itemList);
    }


    public void setItemList(List<T> itemList) {
        viewCache = new SparseArray<>();
        this.itemList = itemList;
        notifyDataSetChanged();
    }


    public T getItem(int listPosition) {
        if (listPosition >= 0 && listPosition < itemList.size()) {
            return itemList.get(listPosition);
        } else {
            return null;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int listPosition = isInfinite() ? getListPosition(position) : position;
        int viewType = getItemViewType(listPosition);

        View convertView;

        if (viewCache.get(viewType, null) == null) {
            convertView = inflateView(viewType, container, listPosition);
        } else {
            convertView = viewCache.get(viewType);
            viewCache.remove(viewType);
        }

        bindView(convertView, listPosition, viewType);

        container.addView(convertView);

        return convertView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        int listPosition = isInfinite() ? getListPosition(position) : position;

        container.removeView((View) object);
        if (!dataSetChangeLock) {
            viewCache.put(getItemViewType(listPosition), (View) object);
        }
    }

    @Override
    public void notifyDataSetChanged() {
        dataSetChangeLock = true;
        super.notifyDataSetChanged();
        dataSetChangeLock = false;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        int count = 0;
        if (itemList != null) {
            count = itemList.size();
        }
        if (isInfinite()) {
            return count + 2;
        } else {
            return count;
        }
    }

    public int getListCount() {
        return itemList == null ? 0 : itemList.size();
    }

    private int getListPosition(int position) {
        if (!isInfinite()) {
            return position;
        }

        int listPosition;
        if (position == 0) {
            listPosition = getCount() - 1 - 2;
        } else if (position > getCount() - 2) {
            listPosition = 0;
        } else {
            listPosition = position - 1;
        }
        return listPosition;
    }

    public int getLastItemPosition() {
        if (isInfinite) {
            return itemList == null ? 0 : itemList.size();
        } else {
            return itemList == null ? 0 : itemList.size() - 1;
        }
    }

    public boolean isInfinite() {
        return (isInfinite && (itemList != null && itemList.size() > 1));
    }

}
