package it.SimDit.IlRicettarioDiTino.ui;

import static it.SimDit.IlRicettarioDiTino.util.Constants.EMAIL_ADDRESS;
import static it.SimDit.IlRicettarioDiTino.util.Constants.ENCRYPTED_DATA_FILE_NAME;
import static it.SimDit.IlRicettarioDiTino.util.Constants.ENCRYPTED_SHARED_PREFERENCES_FILE_NAME;
import static it.SimDit.IlRicettarioDiTino.util.Constants.PASSWORD;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputLayout;

import org.apache.commons.validator.routines.EmailValidator;

import java.io.IOException;
import java.security.GeneralSecurityException;

import it.SimDit.IlRicettarioDiTino.R;
import it.SimDit.IlRicettarioDiTino.util.DataEncryptionUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    private static final String TAG = "WelcomeActivity";
    private TextInputLayout emailTextInputLayout;
    private TextInputLayout passwordTextInputLayout;
    private DataEncryptionUtil dataEncryptionUtil;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    //Usually we use newInstance and not the constructor when you have arguments in input
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //Method with the inflate of the layout file of the fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emailTextInputLayout=view.findViewById(R.id.textInputLayoutEmail);
        passwordTextInputLayout=view.findViewById(R.id.textInputLayoutPassword);
        dataEncryptionUtil = new DataEncryptionUtil(requireContext());
        MaterialCheckBox rememberMeCheckBox = view.findViewById(R.id.rememberMeCheckBox);

        try{
            String savedEmail = dataEncryptionUtil.readSecretDataWithEncryptedSharedPreferences(
                    ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, EMAIL_ADDRESS);
            String savedPassword = dataEncryptionUtil.readSecretDataWithEncryptedSharedPreferences(
                    ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, PASSWORD);
            if(savedEmail != null && savedPassword != null){
                emailTextInputLayout.getEditText().setText(savedEmail);
                passwordTextInputLayout.getEditText().setText(savedPassword);
                rememberMeCheckBox.setChecked(true);
            } else if (savedEmail != null || savedPassword != null) {
                deleteLoginData();
            }
        }catch(GeneralSecurityException | IOException e){
            Log.e(TAG, "Unable to load saved login data", e);
        }

        try {
            // Remove the legacy encrypted-file copy; login data is stored only in preferences.
            dataEncryptionUtil.deleteSecretDataOnFile(ENCRYPTED_DATA_FILE_NAME);
        } catch (IOException e) {
            Log.e(TAG, "Unable to remove legacy login data", e);
        }

        rememberMeCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                deleteLoginData();
            }
        });

        Button loginButton = view.findViewById(R.id.button2);
        loginButton.setOnClickListener(item -> {
            Log.d(TAG, "Login button clicked");
            String email = emailTextInputLayout.getEditText().getText().toString();
            String password = passwordTextInputLayout.getEditText().getText().toString();
            boolean emailIsValid = validateEmail(email);
            boolean passwordIsValid = validatePassword(password);
            if (emailIsValid && passwordIsValid){
                Log.d(TAG, "Email and password are ok");
                if (rememberMeCheckBox.isChecked()) {
                    saveLoginData(email, password);
                }
            }
        });

        emailTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                emailTextInputLayout.setError(null);
            }
        });
        passwordTextInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                passwordTextInputLayout.setError(null);
            }
        });

        Button signUpButton=view.findViewById(R.id.button4);
        signUpButton.setOnClickListener(v -> {
            Log.d(TAG, "Sign up button clicked");
            Navigation.findNavController(v)
                    .navigate(R.id.action_loginFragment_to_signUpFragment);
        });
    }

    private boolean validateEmail(String email) {
        boolean result= email != null && EmailValidator.getInstance().isValid(email);
        if(result){
            emailTextInputLayout.setError(null);
        }else{
            emailTextInputLayout.setError(getString(R.string.login_error_email));
        }
        return result;
    }
    private boolean validatePassword(String password) {
        boolean result= password != null && password.length() >= 8 &&
                !password.isBlank();
        if(result){
            passwordTextInputLayout.setError(null);
        }else{
            passwordTextInputLayout.setError(getString(R.string.login_error_password));
        }
        return result;
    }

    /**
     * Encrypts login data using DataEncryptionUtil class.
     * @param email The email address to be encrypted and saved
     * @param password The password to be encrypted and saved
     */
    private void saveLoginData(String email, String password) {
        try {
            dataEncryptionUtil.writeSecretDataWithEncryptedSharedPreferences(
                    ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, EMAIL_ADDRESS, email);
            dataEncryptionUtil.writeSecretDataWithEncryptedSharedPreferences(
                    ENCRYPTED_SHARED_PREFERENCES_FILE_NAME, PASSWORD, password);
        } catch (GeneralSecurityException | IOException e) {
            Log.e(TAG, "Unable to save login data", e);
        }
    }

    private void deleteLoginData() {

        try {
            dataEncryptionUtil.deleteSecretDataWithEncryptedSharedPreferences(
                    ENCRYPTED_SHARED_PREFERENCES_FILE_NAME,
                    EMAIL_ADDRESS
            );

            dataEncryptionUtil.deleteSecretDataWithEncryptedSharedPreferences(
                    ENCRYPTED_SHARED_PREFERENCES_FILE_NAME,
                    PASSWORD
            );

        } catch (GeneralSecurityException | IOException e) {
            Log.e(TAG, "Unable to delete saved login data", e);
        }
    }

}