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
     * ˫�򻬶��˵�����
     */
    private BidirSlidingLayout bidirSldingLayout;
    
    /**
     * �����ݲ�������ʾ��ListView
     */
    private ListView contentList;
    
    /**
     * ListView��������
     */
    private ArrayAdapter<String> contentListAdapter;
    
    // ����һ������������ʶ�Ƿ��˳�
    private static boolean isExit = false;
    
    /**
     * �����������е������ؼ���ʱ���õĵ���ʾ����ʾ���������2���˳�����
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
     * �������contentListAdapter������Դ��
     */
    private String[] contentItems = {"Content Item 1", "Content Item 2", "Content Item 3", "Content Item 4", "Content Item 5", "Content Item 6", "Content Item 7", "Content Item 8", "Content Item 9",
        "Content Item 10", "Content Item 11", "Content Item 12", "Content Item 13", "Content Item 14", "Content Item 15", "Content Item 16"};
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        /**
         * ���ò����ļ�
         */
        setContentView(R.layout.activity_main);
        
        bidirSldingLayout = (BidirSlidingLayout)findViewById(R.id.bidir_sliding_layout);
        contentList = (ListView)findViewById(R.id.contentList);
        contentListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contentItems);
        contentList.setAdapter(contentListAdapter);
        bidirSldingLayout.setScrollEvent(contentList);
        
        /**
         * ��߰�ť
         */
        Button showLeftButton = (Button)findViewById(R.id.show_left_button);
        /**
         * �ұ߰�ť
         */
        Button showRightButton = (Button)findViewById(R.id.show_right_button);
        /**
         * Ϊ��ߵİ�ť���ü����¼�
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
         * Ϊ�ұߵİ�ť���ü����¼�
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
     * �����еķ��ؼ��¼�
     * ��д����
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
            Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show();
            // ����handler�ӳٷ��͸���״̬��Ϣ
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
