package nuricanozturk.dev.android.payment;

import static java.util.Objects.requireNonNull;
import static nuricanozturk.dev.android.payment.global.BundleKeysKt.LOGIN_INFO;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.karandev.util.data.service.DataServiceException;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import nuricanozturk.dev.android.app.data.service.PaymentAppDataService;
import nuricanozturk.dev.android.app.data.service.dto.LoginInfoDTO;
import nuricanozturk.dev.android.app.data.service.dto.LoginInfoStatusDTO;
import nuricanozturk.dev.android.payment.databinding.ActivityLoginInformationBinding;
import nuricanozturk.dev.android.payment.viewmodel.LoginInformationActivityListener;

@AndroidEntryPoint
public class LoginInformationActivity extends AppCompatActivity
{
    private ActivityLoginInformationBinding m_binding;
    private LoginInfoDTO mLoginInfo;

    @Inject
    PaymentAppDataService m_service;

    private void init()
    {
        initBinding();
        initLoginInfo();
    }

    private void initLoginInfo()
    {
        mLoginInfo = Build.VERSION.SDK_INT < 33 ? (LoginInfoDTO) getIntent().getSerializableExtra(LOGIN_INFO) :
                (LoginInfoDTO) getIntent().getSerializableExtra(LOGIN_INFO, LoginInfoDTO.class);
    }

    private void initBinding()
    {
        m_binding = DataBindingUtil.setContentView(this, R.layout.activity_login_information);
        m_binding.setViewModel(new LoginInformationActivityListener(this));
        m_binding.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<LoginInfoStatusDTO>()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        init();
    }


    public void successLoginButtonClicked()
    {
        try
        {
            var adapter = m_binding.getAdapter();

            adapter.clear();
            var username = mLoginInfo.getUsername();

            var logins = m_service.findSuccessLoginByUsername(username);

            if (!logins.isEmpty())
                logins.forEach(adapter::add);
            else Toast.makeText(this, "No fail login", Toast.LENGTH_LONG).show();

        }
        catch (DataServiceException ignored)
        {
            Toast.makeText(this, "Data Service Error", Toast.LENGTH_LONG).show();
        }
        catch (Throwable ignore)
        {
            Toast.makeText(this, "Other type errors", Toast.LENGTH_LONG).show();
        }
    }

    public void successFailButtonClicked()
    {
        try
        {
            var adapter = m_binding.getAdapter();

            adapter.clear();

            var logins = m_service.findFailLoginByUsername(mLoginInfo.getUsername());

            if (!logins.isEmpty())
                logins.forEach(adapter::add);
            else Toast.makeText(this, "No fail login", Toast.LENGTH_LONG).show();
        }
        catch (DataServiceException ignored)
        {
            Toast.makeText(this, "Data Service Error", Toast.LENGTH_LONG).show();
        }
        catch (Throwable ignore)
        {
            Toast.makeText(this, "Other type errors", Toast.LENGTH_LONG).show();
        }
    }

    public void handleListViewItemClicked(int pos)
    {
        var loginInfoStatusDTO = m_binding.getAdapter().getItem(pos);

        new AlertDialog.Builder(this)
                .setTitle(R.string.alert_dialog_login_info_title_text)
                .setMessage(requireNonNull(loginInfoStatusDTO).toString())
                .setPositiveButton(R.string.alert_dialog_login_info_ok_text, (di, a) -> {})
                .create()
                .show();
    }

    public void closeButtonClicked()
    {
        finish();
    }
}