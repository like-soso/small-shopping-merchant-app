package com.sososhopping.merchant.viewmodel;

import android.graphics.Bitmap;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.sososhopping.merchant.model.board.dto.request.BoardUpdateRequestDto;
import com.sososhopping.merchant.model.board.entity.Board;
import com.sososhopping.merchant.model.board.repository.BoardRepository;

public class BoardUpdateViewModel extends ViewModel {
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

    public void requestUpdate(int storeId, int boardId, Runnable onSuccess, Runnable onError) {
        BoardRepository.getInstance().requestBoardUpdate(storeId, boardId, this.toDto(), this.bitmap.get(), onSuccess, onError);
    }

    private BoardUpdateRequestDto toDto() {
        return new BoardUpdateRequestDto(title.get(), description.get(), category.get());
    }

    public void setBoard(Board board) {
        this.category.set(board.getWritingType());
        this.title.set(board.getTitle());
        this.description.set(board.getContent());
    }

    public boolean valid(Runnable onTitleEmpty, Runnable onTitleNotEmpty, Runnable onContentEmpty, Runnable onContentNotEmpty) {
        boolean ret = true;

        if (title.get() == null || title.get().isEmpty()) {
            ret = false;
            onTitleEmpty.run();
        } else onTitleNotEmpty.run();

        if (description.get() == null || description.get().isEmpty()) {
            ret = false;
            onContentEmpty.run();
        } else onContentNotEmpty.run();

        return ret;
    }
}
