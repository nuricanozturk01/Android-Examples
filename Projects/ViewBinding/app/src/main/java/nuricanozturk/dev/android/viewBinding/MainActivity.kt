package nuricanozturk.dev.android.viewBinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.children

import nuricanozturk.dev.android.viewBinding.global.alert.promptDecision
import nuricanozturk.dev.android.viewBinding.global.alert.promptNotConfirmedDialog
import nuricanozturk.dev.android.viewBinding.viewmodel.RegisterInfo
import nuricanozturk.dev.android.viewBinding.databinding.ActivityMainBinding
import nuricanozturk.dev.android.viewBinding.global.alert.INPUT_TYPE_TEXT_PASSWORD_HIDE
import nuricanozturk.dev.android.viewBinding.global.alert.INPUT_TYPE_TEXT_PASSWORD_SHOW
import java.time.DateTimeException
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    private lateinit var mViewBinding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
    }


    private fun initBinding() {
        mViewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mViewBinding.root)

    }
    private fun showPasswordButtonClickedCallback()
    {
        with(mViewBinding.mainActivityShowPasswordButton) {
            val show = tag as Boolean
            val resId =
                if (show) R.string.hide_password_button else R.string.show_password_button

            setText(resId)
            mViewBinding.mainActivityPassword.inputType =
                if (show) INPUT_TYPE_TEXT_PASSWORD_SHOW else INPUT_TYPE_TEXT_PASSWORD_HIDE
            mViewBinding.mainActivityPasswordAgain.inputType =
                if (show) INPUT_TYPE_TEXT_PASSWORD_SHOW else INPUT_TYPE_TEXT_PASSWORD_HIDE
            tag = !show
        }
    }

    private fun initShowPasswordButton()
    {
        mViewBinding.mainActivityShowPasswordButton.apply {
            tag = true
            setOnClickListener{showPasswordButtonClickedCallback()}
        }
    }
    private fun registerButtonCallback(): RegisterInfo? {
        val registerInfo: RegisterInfo?
        try {
            val password = mViewBinding.mainActivityPassword.text.toString()
            val passwordAgain = mViewBinding.mainActivityPasswordAgain.text.toString()

            if (!doForConfirmation(password, passwordAgain))
                return null;

            val birthDate = LocalDate.of(
                mViewBinding.mainActivityYear.text.toString().toInt(),
                mViewBinding.mainActivityMonth.text.toString().toInt(),
                mViewBinding.mainActivityDay.text.toString().toInt()
            )
            registerInfo = RegisterInfo(
                mViewBinding.mainActivityEditTextUsername.text.toString(),
                mViewBinding.mainActivityEditTextEmail.text.toString(),
                birthDate
            )
            mViewBinding.mainActivityTextViewResult.text = registerInfo.toString()
            Toast.makeText(this, "Welcome $registerInfo", Toast.LENGTH_LONG).show()
            return registerInfo

        } catch (ignore: NumberFormatException) {
            Toast.makeText(this, "Invalid value format", Toast.LENGTH_LONG).show()
        } catch (ignore: DateTimeException) {
            Toast.makeText(this, "Invalid date format", Toast.LENGTH_LONG).show()
        }
        return null
    }

    private fun doForConfirmation(password: String, passwordAgain: String): Boolean {
        var result = true
        if (password != passwordAgain || (password.isEmpty() || password.isBlank() || passwordAgain.isBlank() || passwordAgain.isEmpty())) {
            promptNotConfirmedDialog(this, "Alert", "Password Not Confirmed",
                "OK", { _, _ -> neutralButtonCallback() });
            Toast.makeText(this, "Passwords are not same!", Toast.LENGTH_LONG).show()
            result = false;
        }
        return result;
    }


    private fun neutralButtonCallback() {
        mViewBinding.mainActivityPassword.setText("")
        mViewBinding.mainActivityPasswordAgain.setText("")
    }

    private fun initialize() {
        initViews()
    }

    private fun initViews() {

        initBinding()
        initClearButton();
        initCloseButton()
        initAcceptCheckbox()
        initRegisterButton()
        initShowPasswordButton()
    }

    private fun initClearButton() {
        mViewBinding.mainActivityClearButton.apply {
            setOnClickListener{clearButtonCallback()}
        }
    }
    private fun clearEditTexts()
    {
        mViewBinding.mainActivityPassword.setText("")
        mViewBinding.mainActivityPasswordAgain.setText("")
        for (view in mViewBinding.mainActivityBirthDateLayout.children)
            (view as EditText).setText("")

        for (view in mViewBinding.mainActivityLayoutMain.children)
        {
            if (view is EditText)
                view.setText("")
        }

        mViewBinding.mainActivityTextViewResult.text = ""
    }
    private fun clearButtonCallback() {
        clearEditTexts()
        mViewBinding.checkBox.isChecked = false
        with(mViewBinding.mainActivityPassword)
        {
            inputType = INPUT_TYPE_TEXT_PASSWORD_HIDE
            mViewBinding.mainActivityShowPasswordButton.setText(R.string.show_password_button)
            tag = false
        }
        mViewBinding.mainActivityEditTextUsername.requestFocus()
    }

    private fun initAcceptCheckbox() {
        findViewById<CheckBox>(R.id.checkBox)
            .apply {
                setOnCheckedChangeListener { _, checked ->
                    mViewBinding.mainActivityRegisterButton.isEnabled = checked
                }
            }
    }

    private fun closeButtonClickCallback() {
        promptDecision(this, "Warning", "Are you sure?", "Yes", "No",
            { _, _ -> positiveButtonCallback() }) { _, _ -> negativeButtonCallback() }
    }

    private fun positiveButtonCallback() {
        finish()
    }

    private fun negativeButtonCallback() {
        Toast.makeText(this, "Continue the program!", Toast.LENGTH_LONG).show()
    }

    private fun initCloseButton() {

        findViewById<Button>(R.id.mainActivityCloseButton).apply { setOnClickListener { closeButtonClickCallback() } }// destroy
    }

    private fun initRegisterButton() {


        findViewById<Button>(R.id.mainActivityRegisterButton).apply { setOnClickListener { registerButtonCallback() } }
    }
}