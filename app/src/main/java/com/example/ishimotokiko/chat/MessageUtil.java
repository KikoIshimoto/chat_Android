package com.example.ishimotokiko.chat;

import com.github.bassaer.chatmessageview.views.MessageView;

/**
 * Created by abedaigorou on 2017/08/08.
 */

public class MessageUtil
{
    public static int[] getLastMessageLocation(MessageView messageView){
        int[] a=new int[2];
        int ccount=messageView.getChildCount();
        if(ccount<1)
            return new int[]{0,0};
        messageView.getChildAt(ccount-1).getLocationOnScreen(a);
        return a;
    }
}
