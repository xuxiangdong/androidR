package com.stsoft.test_two.activity;

import java.io.OutputStream;

import com.example.test_two.R;
import com.stsoft.test_two.ui.widget.ClearEditText;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity
{
    // �û���(�˺�)account
    private ClearEditText account;
    
    // ����
    private ClearEditText password;
    
    private CheckBox remember;
    
    private CheckBox autologin;
    
    private Button login;
    
    private SharedPreferences sp;
    
    private String userNameValue, passwordValue;
    
    private SharedPreferences preferences;
	private Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
		// �ж��ǲ����״ε�¼��
		if (preferences.getInt("firststart", -1) == -1 || preferences.getInt("firststart", -1) != this.getPackageInfo(LoginActivity.this).versionCode) {
			editor = preferences.edit();
			// ����¼��־λ����Ϊfalse���´ε�¼ʱ������ʾ�״ε�¼����
			editor.putInt("firststart", this.getPackageInfo(LoginActivity.this).versionCode);
			editor.commit();
			// Intent intent = new
			// Intent("com.stsoft.activity.ScollerViewActivity");
			Intent intent = new Intent();
			intent.setClass(LoginActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}
        // ��ʼ���û��������롢��ס���롢�Զ���¼����¼��ť
        account = (ClearEditText)findViewById(R.id.username);
        password = (ClearEditText)findViewById(R.id.userpassword);
        remember = (CheckBox)findViewById(R.id.remember);
        autologin = (CheckBox)findViewById(R.id.autologin);
        login = (Button)findViewById(R.id.login);
        
        sp = getSharedPreferences("userInfo", 0);
        String name = sp.getString("USER_NAME", "");
        String pass = sp.getString("PASSWORD", "");
        
        boolean choseRemember = sp.getBoolean("remember", false);
        boolean choseAutoLogin = sp.getBoolean("autologin", false);
        // Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        
        // ����ϴ�ѡ�˼�ס���룬�ǽ����¼ҳ��Ҳ�Զ���ѡ��ס���룬�������û���������
        if (choseRemember)
        {
            account.setText(name);
            password.setText(pass);
            remember.setChecked(true);
        }
        // ����ϴε�¼ѡ���Զ���¼���ǽ����¼ҳ��Ҳ�Զ���ѡ�Զ���¼
        if (choseAutoLogin)
        {
            autologin.setChecked(true);
        }
        
        login.setOnClickListener(new OnClickListener()
        {
            
            // Ĭ�Ͽɵ�¼�ʺ�tinyphp,����123
            @Override
            public void onClick(View arg0)
            {
                userNameValue = account.getText().toString();
                passwordValue = password.getText().toString();
                SharedPreferences.Editor editor = sp.edit();
                
                if (userNameValue.equals("tinyphp") && passwordValue.equals("123"))
                {
                    Toast.makeText(LoginActivity.this, "��¼�ɹ�", Toast.LENGTH_SHORT).show();
                    
                    // �����û���������
                    editor.putString("USER_NAME", userNameValue);
                    editor.putString("PASSWORD", passwordValue);
                    
                    // �Ƿ��ס����
                    if (remember.isChecked())
                    {
                        editor.putBoolean("remember", true);
                    }
                    else
                    {
                        editor.putBoolean("remember", false);
                    }
                    
                    // �Ƿ��Զ���¼
                    if (autologin.isChecked())
                    {
                        editor.putBoolean("autologin", true);
                    }
                    else
                    {
                        editor.putBoolean("autologin", false);
                    }
                    editor.commit();
                    
                    // ��ת
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Animation animation = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.myanim);
                    if(TextUtils.isEmpty(userNameValue))
                    {
                        account.startAnimation(animation); 
                    }
                    if(TextUtils.isEmpty(passwordValue))
                    {
                        password.startAnimation(animation); 
                    }
                    Toast.makeText(LoginActivity.this, "�û�����������������µ�¼!", Toast.LENGTH_SHORT).show();
                }
                
            }
            
        });
        
    }
    private static PackageInfo getPackageInfo(Context context) {
	    PackageInfo pi = null;
	 
	    try {
	        PackageManager pm = context.getPackageManager();
	        pi = pm.getPackageInfo(context.getPackageName(),
	                PackageManager.GET_CONFIGURATIONS);
	 
	        return pi;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	 
	    return pi;
	}
}
