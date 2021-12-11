package com.sososhopping.merchant.view.adapter;

import static android.view.View.GONE;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sososhopping.merchant.R;
import com.sososhopping.merchant.databinding.ItemItemListBinding;
import com.sososhopping.merchant.model.accounting.repository.AccountingRepository;
import com.sososhopping.merchant.model.item.entity.Item;
import com.sososhopping.merchant.model.item.repository.ItemRepository;

import java.util.List;
import java.util.function.Consumer;

public class ItemListRecyclerViewAdapter extends RecyclerView.Adapter<ItemListRecyclerViewAdapter.ViewHolder>{

    private final List<Item> mValues;

    public ItemListRecyclerViewAdapter(List<Item> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(ItemItemListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Consumer<Integer> onSuccess = this::onSuccess;
        holder.mItem = mValues.get(position);
        holder.mTitleView.setText(mValues.get(position).getName());
        holder.mDescriptionView.setText(mValues.get(position).getDescription());
        holder.mUnitView.setText(mValues.get(position).getPurchaseUnit());
        holder.mUnitPriceView.setText(Integer.toString(mValues.get(position).getUnitPrice()));
        holder.mDisabledView.setVisibility(mValues.get(position).isSaleStatus() ? GONE : View.VISIBLE);
        Glide.with(holder.itemView.getContext())
                .load(Uri.parse(mValues.get(position).getImgUrl()))
                .into(holder.mImage);

        holder.mMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), holder.mMore);
                popupMenu.inflate(R.menu.menu_item_popup);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.itemUpdate) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("storeId", mValues.get(position).getStoreId());
                            bundle.putInt("itemId", mValues.get(position).getId());
                            Navigation.findNavController((View) (holder.itemView.getParent().getParent().getParent().getParent())).navigate(R.id.action_itemFragment_to_itemUpdateFragment, bundle);
                        } else {
                            System.out.println(mValues.get(position).getId());
                            System.out.println(mValues.get(position).getStoreId());
                            ItemRepository.getInstance().requestItemDelete(mValues.get(position).getStoreId(), mValues.get(position).getId(), position, onSuccess);
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void onSuccess(int position) {
        mValues.remove(position);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTitleView;
        public final TextView mDescriptionView;
        public final TextView mUnitView;
        public final TextView mUnitPriceView;
        public final ImageView mImage;
        public final TextView mDisabledView;
        public final ImageView mMore;
        public Item mItem;

        public ViewHolder(ItemItemListBinding binding) {
            super(binding.getRoot());
            mTitleView = binding.shopListItemTitle;
            mDescriptionView = binding.shopListItemDescription;
            mImage = binding.shopListItemImage;
            mUnitView = binding.itemListItemUnit;
            mUnitPriceView = binding.itemListItemUnitPrice;
            mDisabledView = binding.itemListDisabled;
            mMore = binding.more;
        }
    }
}
