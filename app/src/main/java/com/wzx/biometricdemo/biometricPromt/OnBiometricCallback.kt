package com.wzx.biometricdemo.biometricPromt

/**
 * 描述：
 *
 * 创建人： Administrator
 * 创建时间： 2018/9/19
 * 更新时间：
 * 更新内容：
 */
interface OnBiometricCallback {

    fun onSuccess()

    fun onFail()

    fun onError(msg: CharSequence?)

    fun onCancel()
}