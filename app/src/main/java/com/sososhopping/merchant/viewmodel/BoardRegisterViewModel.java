package com.sososhopping.merchant.viewmodel;

import android.graphics.Bitmap;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.sososhopping.merchant.model.board.dto.request.BoardRegisterRequestDto;
import com.sososhopping.merchant.model.board.repository.BoardRepository;

public class BoardRegisterViewModel extends ViewModel {
    ObservableField<Bitmap> bitmap = new ObservableField<>();

    ObservableField<String> category = new ObservableField<>("EVENT");
    ObservableField<String> title = new ObservableField<>();
    ObservableField<String> description = new ObservableField<>();

    public ObservableField<Bitmap> getBitmap() {
        return bitmap;
    }

    public ObservableField<String> getCategory() {
        return category;
    }

    public ObservableField<String> getTitle() {
        return title;
    }

    public ObservableField<String> getDescription() {
        return description;
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap.set(bitmap);
    }

    public void requestRegister(int storeId, Runnable onSuccess, Runnable onError) {
        BoardRepository.getInstance().requestBoardRegister(storeId, this.toDto(), this.bitmap.get(), onSuccess, onError);
    }

    private BoardRegisterRequestDto toDto() {
        return new BoardRegisterRequestDto(title.get(), description.get(), category.get());
    }
}
