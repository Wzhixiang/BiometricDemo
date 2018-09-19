package com.wzx.biometricdemo.biometricPromt

import android.content.Context
import android.content.DialogInterface
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.CancellationSignal
import android.support.annotation.Nullable
import android.support.annotation.RequiresApi
import android.util.Log

/**
 * 描述：BiometricPrompt 生物特征识别（指纹识别）
 *
 * 创建人： Administrator
 * 创建时间： 2018/9/19
 * 更新时间：
 * 更新内容：
 */

@RequiresApi(Build.VERSION_CODES.P)
class BiometricPrompt28(var context: Context) : BiometricPromptImpl {

    companion object {
        const val TAG = "BiometricPrompt28"
    }

    private var biometricPrompt: BiometricPrompt
    private var cancelSignal: CancellationSignal? = null

    @Nullable
    private var callback: OnBiometricCallback? = null

    init {
        biometricPrompt = BiometricPrompt.Builder(context)
                .setTitle("指纹识别")
                .setDescription("请输入指纹")
                .setNegativeButton("取消", context.mainExecutor, DialogInterface.OnClickListener { dialog, state ->
                    cancelSignal?.cancel()
                    callback?.onCancel()
                    dialog.dismiss()
                })
                .build()
    }

    override fun authenticate(cancelSignal: CancellationSignal, callback: OnBiometricCallback?) {
        this.cancelSignal = cancelSignal
        this.callback = callback

        this.cancelSignal?.setOnCancelListener {
            this.cancelSignal?.cancel()
        }

        biometricPrompt.authenticate(this.cancelSignal, context.mainExecutor, BiometricPromptCallbackImpl(this.callback))
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private class BiometricPromptCallbackImpl(@Nullable val callback: OnBiometricCallback?) : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
            super.onAuthenticationError(errorCode, errString)

            Log.i(BiometricPrompt28.TAG, "onAuthenticationError " + errString)
            callback?.onError(errString)
        }

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
            super.onAuthenticationSucceeded(result)

            Log.i(BiometricPrompt28.TAG, "onAuthenticationSucceeded " + result.toString())
            callback?.onSuccess()
        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()

            Log.i(BiometricPrompt28.TAG, "onAuthenticationFailed ")
            callback?.onFail()
        }
    }
}