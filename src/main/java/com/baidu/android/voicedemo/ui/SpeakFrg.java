package com.baidu.android.voicedemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.speech.recognizerdemo.R;

/**
 * Created by Administrator on 2017/2/20.
 */
public class SpeakFrg extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.frg_speak_main,null);
        return view;
    }

}
