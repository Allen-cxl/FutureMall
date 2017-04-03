package com.futuremall.android.ui.adapter;

import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * *************************************************************************
 *
 * @版权所有: 北京云图微动科技有限公司 (C) 2016
 * @创建人: 孙旭光
 * @创建时间: xxx(2016-12-06 20:31)
 * @Email: 410073261@qq.com
 * <p>
 * 描述:com.fotoable.keyboard.emoji.adapter-SectionRecyclerAdapter
 * <p>
 * <p>
 * *************************************************************************
 */

public abstract class SectionRecyclerAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {


    public final static int VIEW_TYPE_HEADER = -2;
    public final static int VIEW_TYPE_FIRST = -4;
    public final static int VIEW_TYPE_ITEM = -1;
    public final static int VIEW_TYPE_FOOTER = -3;

    private final ArrayMap<Integer, Integer> mHeaderLocationMap;

    private final ArrayMap<Integer, Integer> mFooterLocationMap;

    private GridLayoutManager mLayoutManager;

    private boolean mIsFirst;

    public SectionRecyclerAdapter() {
        mHeaderLocationMap = new ArrayMap<>();
        mFooterLocationMap = new ArrayMap<>();
    }

    /**
     *  count how many section in this data set
     * @return the number of sectioned objects in the data set
     */
    public abstract int getSectionCount();

    /**
     *  Count how many items are in each section.
     * @param section the section number
     * @return number of items within each section.
     *
     * @Note: this method will be repected by: nubmer of sections in the data set.
     */
    public abstract int getItemCount(int section);

    public  void onBindFirstViewHolder(VH holder, int section){};

    public abstract void onBindHeaderViewHolder(VH holder, int section);

    public abstract void onBindFooterViewHolder(VH holder, int section);

    /**
     *
     * @param holder the holder to bind
     * @param section what section the item is in, with in the data set
     * @param relativePosition relative position of item within each section.
     * @param absolutePosition  !!!! unused params, need to take into payment footer count
     */
    public abstract void onBindViewHolder(VH holder, int section, int relativePosition, int absolutePosition);


    /**
     * determine which section needs footer, implements can be vary.
     * @param sectionPosition  what section it is giving the item position in the data set.
     * @return
     */
    public abstract boolean isNeedFooter(int sectionPosition);

    private  boolean isFirst() {
        return mIsFirst;
    }

    public  void setIsFirst(boolean isFirst) {
        mIsFirst = isFirst;
    }

    private final boolean isFirst(int position) {
        return position == 0 ? true : false;
    }

    public final boolean isHeader(int position) {
        return mHeaderLocationMap.get(position) != null;
    }

    public final boolean isFooter(int position) {
        return mFooterLocationMap.get(position) != null;
    }


    /**
     * optional mehtod. Can use this the determine the item's SpanCount base on relative position to its own section
     * @param section  whiche section the item is current on
     * @param relativePositonInSection  the relative position of item within each section
     * @return
     */
    public abstract boolean setFullSpanItem(int section, int relativePositonInSection);


    public final void setLayoutManager(@Nullable final GridLayoutManager lm) {
        mLayoutManager = lm;
        if (lm == null) return;
        lm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (isHeader(position) || isFooter(position))
                    return mLayoutManager.getSpanCount();

                final int[] sectionAndPos = getSectionIndexAndRelativePosition(position);
                final int absPos = position - (sectionAndPos[0] + 1);

                /**
                 * this is optional.
                 */
                if(setFullSpanItem(sectionAndPos[0],sectionAndPos[1])){
                    return mLayoutManager.getSpanCount();
                }

                return getRowSpan(mLayoutManager.getSpanCount(),
                        sectionAndPos[0], sectionAndPos[1], absPos);

            }
        });
    }


    @SuppressWarnings("UnusedParameters")
    protected int getRowSpan(int fullSpanSize, int section, int relativePosition, int absolutePosition) {
        return 1;
    }


    /**
     *  returns section along with offsetted position, int[0]: returns which section this itemPosition belongs to;
     * @param itemPosition: the absolute position in adapter.
     * @return int[0] : absolute position's section position
     *          int[1]:  relative item position with its section.
     *
     * @Notice: Each section must been assigned with a header, and header position of each section within the adapter data
     * is the only factor to determine which section all other items belongs to.
     *
     * A Section may not may not have a footer, that is up to the specific implementation to firgure it out.
     */
    private int[] getSectionIndexAndRelativePosition(int itemPosition) {
        synchronized (mHeaderLocationMap) {
            Integer lastSectionIndex = -1;
            for (final Integer sectionIndex : mHeaderLocationMap.keySet()) {
                if (itemPosition > sectionIndex) {
                    lastSectionIndex = sectionIndex;
                } else {
                    break;
                }
            }
            return new int[]{mHeaderLocationMap.get(lastSectionIndex), itemPosition - lastSectionIndex - 1};
        }
    }


    /**
     *  this is most important part, After create new adapter with the data set,
     *  this method will be called next by adapter, going through the data set, and mannually, insert the header before the first item of each section, and
     *  also if it needs to: insert the footer after the last item of each section.
     * @return
     */
    @Override
    public final int getItemCount() {
        int count = 0;
        mHeaderLocationMap.clear();
        mFooterLocationMap.clear();

        if(isFirst() && isFirst()){
            count++;
        }

        for (int s = 0; s < getSectionCount(); s++) {
            int itemCount = getItemCount(s);

            /**
             *  add header position to arrayMap, every section of group has to have a header,
             *  Header is the relative position, all other item need to compare.
             */
                mHeaderLocationMap.put(count, s);
                count += itemCount + 1;


            //增加footer,判断是否需要footer
            if (isNeedFooter(s)) {
                mFooterLocationMap.put(count, s);
                count++;
            }

        }
        return count;
    }


    /** determine the view type for each position,
     * @hide
     * @deprecated
     */
    @Override
    @Deprecated
    public final int getItemViewType(int position) {

        if(isFirst() && isFirst(position)){
            return VIEW_TYPE_FIRST;
        }else if (isHeader(position)) {
//            VIEW_TYPE_HEADER
            return getHeaderViewType(mHeaderLocationMap.get(position));
        } else if (isFooter(position)) {
            return VIEW_TYPE_FOOTER;
        } else {
            final int[] sectionAndPos = getSectionIndexAndRelativePosition(position);
//            VIEW_TYPE_ITEM
            return getItemViewType(sectionAndPos[0],
                    // offset section view positions
                    sectionAndPos[1],
                    position - (sectionAndPos[0] + 1));
        }
    }

    @SuppressWarnings("UnusedParameters")
    @IntRange(from = 0, to = Integer.MAX_VALUE)
    public int getHeaderViewType(int section) {
        //noinspection ResourceType
        return VIEW_TYPE_HEADER;
    }

    @SuppressWarnings("UnusedParameters")
    @IntRange(from = 0, to = Integer.MAX_VALUE)
    public int getItemViewType(int section, int relativePosition, int absolutePosition) {
        //noinspection ResourceType
        return VIEW_TYPE_ITEM;
    }

    /**
     * @hide
     * @deprecated
     */
    @Override
    @Deprecated
    public final void onBindViewHolder(VH holder, int position) {
        StaggeredGridLayoutManager.LayoutParams layoutParams = null;
        if (holder.itemView.getLayoutParams() instanceof GridLayoutManager.LayoutParams)
            layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        else if (holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams)
            layoutParams = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
        if (isFirst() && isFirst(position)) {
            if (layoutParams != null) layoutParams.setFullSpan(true);
            onBindFirstViewHolder(holder, position);
        }else if (isHeader(position)) {
            if (layoutParams != null) layoutParams.setFullSpan(true);
            onBindHeaderViewHolder(holder, mHeaderLocationMap.get(position));
        } else if (isFooter(position)) {
            if (layoutParams != null) layoutParams.setFullSpan(true);
            onBindFooterViewHolder(holder, mFooterLocationMap.get(position));
        } else {
            if (layoutParams != null) layoutParams.setFullSpan(false);
            final int[] sectionAndPos = getSectionIndexAndRelativePosition(position);
            final int absPos = position - (sectionAndPos[0] + 1);

            onBindViewHolder(holder, sectionAndPos[0],
                    // offset section view positions
                    sectionAndPos[1], absPos);
            if (layoutParams != null)
                holder.itemView.setLayoutParams(layoutParams);
        }
    }

    /**
     * @hide
     * @deprecated
     */
    @Deprecated
    @Override
    public final void onBindViewHolder(VH holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }
}
