package com.sososhopping.merchant.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.sososhopping.merchant.model.auth.dto.request.EmailDuplicationCheckRequestDto;
import com.sososhopping.merchant.model.auth.dto.request.SignupRequestDto;
import com.sososhopping.merchant.model.auth.repository.AuthRepository;

public class SignupViewModel extends ViewModel {

    private final ObservableField<String> email = new ObservableField<>();
    private final ObservableField<String> password = new ObservableField<>();
    private final ObservableField<String> passwordCheck = new ObservableField<>();
    private final ObservableField<String> name = new ObservableField<>();
    private final ObservableField<String> phone = new ObservableField<>();

    private boolean emailDupChecked = false;

    public ObservableField<String> getEmail() {
        return email;
    }

    public ObservableField<String> getPassword() {
        return password;
    }

    public ObservableField<String> getPasswordCheck() {
        return passwordCheck;
    }

    public ObservableField<String> getName() {
        return name;
    }

    public ObservableField<String> getPhone() {
        return phone;
    }

    public void requestEmailDupCheck(Runnable onInappropriate,
                                     Runnable onChecked,
                                     Runnable onDuplicated,
                                     Runnable onError) {
        Runnable onNotDuplicated = () -> {
            onChecked.run();
            emailDupChecked = true;
        };
        if (email.get() == null || email.get().isEmpty() || !email.get().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) onInappropriate.run();
        else {
            AuthRepository.getInstance().requestEmailDuplicationCheck(this.toEmailDuplicationCheckRequestDto(), onNotDuplicated, onDuplicated, onError);
        }
    }

    public boolean validateForm(Runnable onPasswordProper,
                                Runnable onPasswordMatch,
                                Runnable onNameProper,
                                Runnable onPhoneProper,
                                Runnable onEmailNotChecked,
                                Runnable onPasswordTooShort,
                                Runnable onPasswordCheckEmpty,
                                Runnable onPasswordNotMatched,
                                Runnable onNameEmpty,
                                Runnable onPhoneEmpty) {
        boolean isValid = true;

        if (!emailDupChecked) {
            onEmailNotChecked.run();
            isValid = false;
        }

        if (password.get() == null || password.get().length() < 8) {
            onPasswordTooShort.run();
            isValid = false;
        } else {
            onPasswordProper.run();
        }

        if (passwordCheck.get() == null || passwordCheck.get().isEmpty()){
            onPasswordCheckEmpty.run();
            isValid = false;
        }
        else if (!passwordCheck.get().equals(password.get())) {
            onPasswordNotMatched.run();
            isValid = false;
        } else {
            onPasswordMatch.run();
        }

        if (name.get() == null || name.get().isEmpty()) {
            onNameEmpty.run();
        } else {
            onNameProper.run();
        }

        if (phone.get() == null || phone.get().isEmpty()) {
            onPhoneEmpty.run();
        } else {
            onPhoneProper.run();
        }

        return isValid;
    }

    public void requestSignup(Runnable onSuccess,
                              Runnable onError) {
        AuthRepository.getInstance().requestSignup(this.toSignupRequestDto(), onSuccess, onError);
    }

    private EmailDuplicationCheckRequestDto toEmailDuplicationCheckRequestDto() {
        return new EmailDuplicationCheckRequestDto(email.get());
    }

    private SignupRequestDto toSignupRequestDto() {
        return new SignupRequestDto(this.email.get(),
                this.password.get(),
                this.name.get(),
                this.phone.get());
    }
}
