package com.jaden.htlabel.input;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;

import com.jaden.htlabel.bean.Label;
import com.jaden.htlabel.utils.Slog;

/**
 * Created Date: 2018/12/14
 * Description: 软键盘相关，监听输入删除等
 */
public class HtLayoutInput extends BaseInputConnection {
    private Listener listener;
    private static Label label; //label
    private static String text; //label的内容

    public HtLayoutInput(View targetView, boolean fullEditor) {
        super(targetView, fullEditor);
    }

    public void setLabel(Label label){
        this.label = label;
        if(label != null) {
            text = label.getText();
        }
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    @Override
    public boolean commitText(CharSequence text, int newCursorPosition) {
        if(listener != null){
            commit(text);
            listener.onInputComplete();
        }
        return true;
    }

    @Override
    public boolean sendKeyEvent(KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_UP) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_DEL:
                    if (listener != null) {
                        delete();
                        listener.onDelComplete();
                    }
                    break;
            }
        }
        return true;
    }


    @Override
    public boolean deleteSurroundingText(int beforeLength, int afterLength) {
        Slog.i("deleteSurroundingText");
        return true;
    }

    private void commit(CharSequence c){
        if(label != null && text != null) {
            text = text.concat(c.toString());
            label.setText(text);
        }
    }

    private void delete(){
        if(label != null && text != null) {
            int l = text.length();
            if (l > 0) {
                text = text.substring(0, l - 1);
                label.setText(text);
            }
        }
    }


    public interface Listener{
        void onInputComplete();
        void onDelComplete();
    }
}
