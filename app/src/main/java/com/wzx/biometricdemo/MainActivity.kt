package com.wzx.biometricdemo

import android.os.Bundle
import android.os.CancellationSignal
import android.support.annotation.MainThread
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.wzx.biometricdemo.biometricPromt.BiometricPromptManager
import com.wzx.biometricdemo.biometricPromt.OnBiometricCallback

/**
 * （待定--之后找时间完善）
 * 指纹技术
 * 在Build.VERSION_CODES.M --23: 指纹识别（FingerprintManager）
 * 在Build.VERSION_CODES.P --28：生物特征（BiometricPrompt）
 * 关于BiometricPrompt
 * https://github.com/gaoyangcr7/BiometricPromptDemo
 */
class MainActivity : AppCompatActivity() {

    private lateinit var mStartBtn: Button

    companion object {
        const val TAG: String = "MainActivity"
    }

    private lateinit var bManager: BiometricPromptManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFinger()

        initView()
    }

    fun initView() {
        mStartBtn = findViewById(R.id.btn_finger)
        mStartBtn.setOnClickListener {
            bManager.authenticate(CancellationSignal())
        }
    }

    fun initFinger() {
        bManager = BiometricPromptManager(this)

        bManager.setOnBiometricCallback(object : OnBiometricCallback {
            override fun onSuccess() {
                Log.i(TAG, "onSuccess")

                showToast("onSuccess")
            }

            override fun onFail() {
                Log.i(TAG, "onFail")
                showToast("onFail")
            }

            override fun onError(msg: CharSequence?) {
                Log.i(TAG, "onError " + msg)
                showToast("onError " + msg)
            }

            override fun onCancel() {
                Log.i(TAG, "onCancel")
                showToast("onCancel")
            }
        })
    }

    @MainThread
    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
