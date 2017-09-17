package com.baidu.android.voicedemo.ui.user;

import android.app.Activity;
import android.content.Intent;
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
import com.baidu.android.voicedemo.db.DbHelper;
import com.baidu.android.voicedemo.ui.HomeActivity;
import com.baidu.android.voicedemo.utils.CommonSpUtil;
import com.baidu.android.voicedemo.utils.StaticUtils;
import com.baidu.speech.recognizerdemo.R;

/**
 * A login screen that offers login via email/password.
 */
public class UserLoginActivity extends Activity {
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mLoginFormView;

//    Spinner spinner_produce_class;
//    Spinner spinner_produce_line;
    private final static String TAG = "login:";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        //线，组
//        spinner_produce_class = (Spinner) findViewById(R.id.spinner_produce_class);
//        spinner_produce_line = (Spinner) findViewById(R.id.spinner_produce_line);


        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailRegisterButton = (Button) findViewById(R.id.email_register_button);
        mEmailRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mItent = new Intent();
                mItent.setClass(UserLoginActivity.this, UserRegisterActivity.class);
                startActivity(mItent);
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);

        mEmailView.setText("B1001");
//        mPasswordView.setText("123456");
    }





    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

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
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            doLogin(email,password);
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


    private void doLogin(final String workNumber, final String password){
        UserInfo mInfo = new UserInfo();
        mInfo.setPassword(password);
        mInfo.setUsername(workNumber);
        mInfo.setRoleType(StaticUtils.Role_User);
        UserInfo resultInfo =  DbHelper.getInstance(this).checkUserValidate(mInfo);
        if (resultInfo!=null) {
            // success
            startActivity(new Intent(UserLoginActivity.this,HomeActivity.class));
            CommonSpUtil.setWorker(resultInfo.getUsername());
            CommonSpUtil.setCurrentRole(resultInfo.getRoleType());
            StaticUtils.currentUser = mInfo;
            Toast.makeText(this,"登陆成功",Toast.LENGTH_LONG).show();

            finish();
        } else {
            // fail
            Toast.makeText(this,"账号或密码不对",Toast.LENGTH_LONG).show();
        }

    }


}

