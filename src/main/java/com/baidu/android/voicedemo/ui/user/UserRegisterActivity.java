package com.baidu.android.voicedemo.ui.user;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.android.voicedemo.bean.UserInfo;
import com.baidu.android.voicedemo.bean.UserManagerEvent;
import com.baidu.android.voicedemo.db.DbHelper;
import com.baidu.android.voicedemo.utils.StaticUtils;
import com.baidu.speech.recognizerdemo.R;

import org.greenrobot.eventbus.EventBus;

/**
 * A login screen that offers login via email/password.
 */
public class UserRegisterActivity extends Activity {

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mRePasswordView;
    private View mLoginFormView;

    private final static String TAG = "register:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mRePasswordView = (EditText) findViewById(R.id.repassword);
        mRePasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptRegister();
                    return true;
                }
                return false;
            }
        });

        Button mSubmitButton = (Button) findViewById(R.id.email_submit_button);
        mSubmitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });
        Button mCancelButton = (Button) findViewById(R.id.email_cancel_button);
        mCancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
    }

    private void doRegister(String workNumber, String password){
        UserInfo mInfo = new UserInfo();
        mInfo.setPassword(password);
        mInfo.setUsername(workNumber);
        mInfo.setRoleType(StaticUtils.Role_User);
        boolean b = DbHelper.getInstance(this).addUserInfo(mInfo);
        if(b){
            Toast.makeText(this,"注册成功",Toast.LENGTH_LONG).show();
            EventBus.getDefault().post(new UserManagerEvent());
            finish();
        }else{
            Toast.makeText(this,"注册失败，请联系管理员",Toast.LENGTH_LONG).show();
        }


    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptRegister(){
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mRePasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String doublepassword = mRePasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }else if (!doublepassword.equals(password)) {
            mPasswordView.setError(getString(R.string.error_different_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            doRegister(email,password);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.startsWith("B");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

}

