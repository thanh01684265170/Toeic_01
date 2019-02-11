package com.framgia.toeic.screen.user;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.framgia.toeic.R;
import com.framgia.toeic.screen.base.BaseActivity;

public class UserActivity extends BaseActivity implements View.OnClickListener {
    private static final String PREFNAME = "data_user";
    private static final String NAME = "name";
    private static final String TARGET = "target";
    private Dialog mDialog;
    private ImageView mImageUser;
    private TextView mTextName;
    private TextView mTextTarget;
    private EditText mEditName;
    private EditText mEditTarget;
    private Button mButtonCancel;
    private Button mButtonSave;
    private SharedPreferences mPreferences;

    public static Intent getUserIntent(Context context) {
        return new Intent(context, UserActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restoringPreferences();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_user;
    }

    @Override
    protected void initComponent() {
        mImageUser = findViewById(R.id.image_edit);
        mTextName = findViewById(R.id.text_name_user);
        mTextTarget = findViewById(R.id.text_target_user);
        mDialog = new Dialog(this);
        mDialog.setContentView(R.layout.dialog_user);
        mButtonCancel = mDialog.findViewById(R.id.button_cancel);
        mButtonSave = mDialog.findViewById(R.id.button_save);
        mEditName = mDialog.findViewById(R.id.edit_name);
        mEditTarget = mDialog.findViewById(R.id.edit_target);
        mButtonCancel.setOnClickListener(this);
        mButtonSave.setOnClickListener(this);
        mImageUser.setOnClickListener(this);
        mPreferences = getSharedPreferences(PREFNAME, MODE_PRIVATE);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_edit:
                mDialog.show();
                break;
            case R.id.button_cancel:
                mDialog.dismiss();
                break;
            case R.id.button_save:
                mTextName.setText(mEditName.getText());
                mTextTarget.setText(mEditTarget.getText());
                savingPreferences();
                mDialog.dismiss();
                break;
        }
    }

    public void savingPreferences() {
        SharedPreferences.Editor editor = mPreferences.edit();
        String name = mEditName.getText().toString();
        String target = mEditTarget.getText().toString();
        editor.putString(NAME, name);
        editor.putString(TARGET, target);
        editor.commit();
    }

    public void restoringPreferences() {
        String name = mPreferences.getString(NAME, "");
        String target = mPreferences.getString(TARGET, "");
        mTextName.setText(name);
        mTextTarget.setText(target);
    }
}

