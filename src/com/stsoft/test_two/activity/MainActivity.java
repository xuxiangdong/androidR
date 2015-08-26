package com.stsoft.test_two.activity;

import com.example.test_two.R;
import com.stsoft.test_two.layout.BidirSlidingLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity
{
    /**
     * 双向滑动菜单布局
     */
    private BidirSlidingLayout bidirSldingLayout;
    
    /**
     * 在内容布局上显示的ListView
     */
    private ListView contentList;
    
    /**
     * ListView的适配器
     */
    private ArrayAdapter<String> contentListAdapter;
    
    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;
    
    /**
     * 当在主界面中单击返回键的时候用的的提示，提示：连续点击2次退出程序
     */
    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            isExit = false;
        }
    };
    
    /**
     * 用于填充contentListAdapter的数据源。
     */
    private String[] contentItems = {"Content Item 1", "Content Item 2", "Content Item 3", "Content Item 4", "Content Item 5", "Content Item 6", "Content Item 7", "Content Item 8", "Content Item 9",
        "Content Item 10", "Content Item 11", "Content Item 12", "Content Item 13", "Content Item 14", "Content Item 15", "Content Item 16"};
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        /**
         * 设置布局文件
         */
        setContentView(R.layout.activity_main);
        
        bidirSldingLayout = (BidirSlidingLayout)findViewById(R.id.bidir_sliding_layout);
        contentList = (ListView)findViewById(R.id.contentList);
        contentListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contentItems);
        contentList.setAdapter(contentListAdapter);
        bidirSldingLayout.setScrollEvent(contentList);
        
        /**
         * 左边按钮
         */
        Button showLeftButton = (Button)findViewById(R.id.show_left_button);
        /**
         * 右边按钮
         */
        Button showRightButton = (Button)findViewById(R.id.show_right_button);
        /**
         * 为左边的按钮设置监听事件
         */
        showLeftButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (bidirSldingLayout.isLeftLayoutVisible())
                {
                    bidirSldingLayout.scrollToContentFromLeftMenu();
                }
                else
                {
                    bidirSldingLayout.initShowLeftState();
                    bidirSldingLayout.scrollToLeftMenu();
                }
            }
        });
        /**
         * 为右边的按钮设置监听事件
         */
        showRightButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (bidirSldingLayout.isRightLayoutVisible())
                {
                    bidirSldingLayout.scrollToContentFromRightMenu();
                }
                else
                {
                    bidirSldingLayout.initShowRightState();
                    bidirSldingLayout.scrollToRightMenu();
                }
            }
        });
        
    }
    
    /**
     * 界面中的返回键事件
     * 重写方法
     * 
     * @param keyCode
     * @param event
     * @return
     * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            exit();
            return false;
        }
        else
        {
            return super.onKeyDown(keyCode, event);
        }
    }
    
    private void exit()
    {
        if (!isExit)
        {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        }
        else
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            System.exit(0);
        }
    }
}
