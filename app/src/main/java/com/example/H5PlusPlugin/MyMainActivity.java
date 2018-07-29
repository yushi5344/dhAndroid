package com.example.H5PlusPlugin;

import android.os.Bundle;

import io.dcloud.PandoraEntry;

/**
 * 扫码
 */

public class MyMainActivity extends PandoraEntry{
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Const.context = this;
    }
}
