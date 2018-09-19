package com.wzx.biometricdemo.biometricPromt

import android.content.Context
import android.os.Build
import android.os.CancellationSignal
import android.support.annotation.NonNull
import android.support.annotation.Nullable
import android.util.Log

/**
 * 描述：
 *
 * 创建人： Administrator
 * 创建时间： 2018/9/19
 * 更新时间：
 * 更新内容：
 */
class BiometricPromptManager(@NonNull var context: Context) {

    companion object {
        const val TAG = "BiometricPromptManager"
    }

    @Nullable
    private var mImpl: BiometricPromptImpl? = null

    @Nullable
    private var callback: OnBiometricCallback? = null

    fun setOnBiometricCallback(callback: OnBiometricCallback) {
        this.callback = callback
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            mImpl = BiometricPrompt28(context)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            mImpl = BiometricPrompt23(context)
        } else {
            Log.i(TAG, "没有指纹模块")
        }
    }

    fun authenticate(@NonNull cancelSignal: CancellationSignal) {
        mImpl?.authenticate(cancelSignal, callback)
    }

}