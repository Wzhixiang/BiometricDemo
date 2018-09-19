package com.wzx.biometricdemo.biometricPromt

import android.content.Context
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.CancellationSignal
import android.support.annotation.RequiresApi
import android.util.Log

/**
 * 描述：在android version M 23 - android version O_MR1 27间，指纹FingerprintManager
 *
 * 创建人： Administrator
 * 创建时间： 2018/9/19
 * 更新时间：
 * 更新内容：
 */

@RequiresApi(Build.VERSION_CODES.M)
class BiometricPrompt23(var context: Context) : BiometricPromptImpl {

    companion object {
        const val TAG = "BiometricPrompt23"
    }

    private var fingerprintManager: FingerprintManager
    private var cancelSignal: CancellationSignal? = null

    private var callback: OnBiometricCallback? = null

    init {
        fingerprintManager = context.getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager
    }


    override fun authenticate(cancelSignal: CancellationSignal, callback: OnBiometricCallback?) {
        this.cancelSignal = cancelSignal
        this.callback = callback

        this.cancelSignal?.setOnCancelListener {
            this.cancelSignal?.cancel()
        }

        if (!fingerprintManager.isHardwareDetected()) {
            callback?.onError("没有指纹识别模块")
        }

        fingerprintManager.authenticate(null, this.cancelSignal, 0, AuthenticationCallback(callback), null)
    }

    private class AuthenticationCallback(var callback: OnBiometricCallback?) : FingerprintManager.AuthenticationCallback() {
        override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
            super.onAuthenticationError(errorCode, errString)

            Log.i(BiometricPrompt23.TAG, "onAuthenticationError " + errString)
            callback?.onError(errString)
        }

        override fun onAuthenticationSucceeded(result: FingerprintManager.AuthenticationResult?) {
            super.onAuthenticationSucceeded(result)

            Log.i(BiometricPrompt23.TAG, "onAuthenticationSucceeded")
            callback?.onSuccess()
        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()

            Log.i(BiometricPrompt23.TAG, "onAuthenticationFailed")
            callback?.onFail()
        }
    }
}