package com.baidu.android.voicedemo.ui.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.android.voicedemo.bean.UserInfo;
import com.baidu.android.voicedemo.db.DbHelper;
import com.baidu.android.voicedemo.utils.StaticUtils;
import com.baidu.speech.recognizerdemo.R;

/**
 * 修改个人密码的界面：管理员，和用户均可以进来
 */
public class EditPasswordActivity extends Activity {

    // UI references.
    private EditText mOldPasswordView;
    private EditText mPasswordView;
    private EditText mRePasswordView;

    private final static String TAG = "register:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);
        // Set up the login form.
        mOldPasswordView = (EditText) findViewById(R.id.email);
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

    }

    private void doUpdateUser(final String password){
        UserInfo mInfo = new UserInfo();
        mInfo.setPassword(password);
        mInfo.setUsername(StaticUtils.currentUser.getUsername());
        mInfo.setRoleType(StaticUtils.currentUser.getRoleType());
        boolean b = DbHelper.getInstance(this).updateUserInfo(mInfo);
        if(b){
            Toast.makeText(this,"修改密码成功",Toast.LENGTH_LONG).show();
            StaticUtils.currentUser = mInfo;
            finish();
        }else{
            Toast.makeText(this,"修改密码失败",Toast.LENGTH_LONG).show();
        }


    }

    private void toLogin() {
        startActivity(new Intent(this, AdminLoginActivity.class));
        finish();
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptRegister(){
        // Reset errors.
        mOldPasswordView.setError(null);
        mPasswordView.setError(null);
        mRePasswordView.setError(null);

        // Store values at the time of the login attempt.
        String oldPass = mOldPasswordView.getText().toString();
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
        if (TextUtils.isEmpty(oldPass)) {
            mOldPasswordView.setError(getString(R.string.error_field_required));
            focusView = mOldPasswordView;
            cancel = true;
        } else if (!isEmailValid(oldPass)) {
            mOldPasswordView.setError(getString(R.string.error_invalid_email));
            focusView = mOldPasswordView;
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
            doUpdateUser(password);
        }
    }

    private boolean isEmailValid(String oldpass) {
        //TODO: Replace this with your own logic
        if(StaticUtils.currentUser == null){
            throw new NullPointerException("currentUser == null");
        }
        return StaticUtils.currentUser.getPassword().equals(oldpass);
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

}

