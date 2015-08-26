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
    // 用户名(账号)account
    private ClearEditText account;
    
    // 密码
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
		// 判断是不是首次登录，
		if (preferences.getInt("firststart", -1) == -1 || preferences.getInt("firststart", -1) != this.getPackageInfo(LoginActivity.this).versionCode) {
			editor = preferences.edit();
			// 将登录标志位设置为false，下次登录时不在显示首次登录界面
			editor.putInt("firststart", this.getPackageInfo(LoginActivity.this).versionCode);
			editor.commit();
			// Intent intent = new
			// Intent("com.stsoft.activity.ScollerViewActivity");
			Intent intent = new Intent();
			intent.setClass(LoginActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}
        // 初始化用户名、密码、记住密码、自动登录、登录按钮
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
        
        // 如果上次选了记住密码，那进入登录页面也自动勾选记住密码，并填上用户名和密码
        if (choseRemember)
        {
            account.setText(name);
            password.setText(pass);
            remember.setChecked(true);
        }
        // 如果上次登录选了自动登录，那进入登录页面也自动勾选自动登录
        if (choseAutoLogin)
        {
            autologin.setChecked(true);
        }
        
        login.setOnClickListener(new OnClickListener()
        {
            
            // 默认可登录帐号tinyphp,密码123
            @Override
            public void onClick(View arg0)
            {
                userNameValue = account.getText().toString();
                passwordValue = password.getText().toString();
                SharedPreferences.Editor editor = sp.edit();
                
                if (userNameValue.equals("tinyphp") && passwordValue.equals("123"))
                {
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    
                    // 保存用户名和密码
                    editor.putString("USER_NAME", userNameValue);
                    editor.putString("PASSWORD", passwordValue);
                    
                    // 是否记住密码
                    if (remember.isChecked())
                    {
                        editor.putBoolean("remember", true);
                    }
                    else
                    {
                        editor.putBoolean("remember", false);
                    }
                    
                    // 是否自动登录
                    if (autologin.isChecked())
                    {
                        editor.putBoolean("autologin", true);
                    }
                    else
                    {
                        editor.putBoolean("autologin", false);
                    }
                    editor.commit();
                    
                    // 跳转
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
                    Toast.makeText(LoginActivity.this, "用户名或密码错误，请重新登录!", Toast.LENGTH_SHORT).show();
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
