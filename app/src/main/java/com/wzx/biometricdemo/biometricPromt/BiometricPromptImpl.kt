package com.wzx.biometricdemo.biometricPromt

import android.os.CancellationSignal

/**
 * 描述：
 *
 * 创建人： Administrator
 * 创建时间： 2018/9/19
 * 更新时间：
 * 更新内容：
 */
interface BiometricPromptImpl {

//    var cancelSignal: CancellationSignal

    fun authenticate(cancelSignal: CancellationSignal, callback: OnBiometricCallback?)

}