package cn.nevercode.utils;


import android.content.Context;
import android.view.Gravity;

public class ToastUtil {
    private static ToastView toast;
    /**
     * 弹出toast
     * @param context
     * @param text
     */
    public static void toastShow(Context context, String text) {
        if (null != toast) {
            toast.cancel();
        }
        if(null != text) {
            toast = new ToastView(context.getApplicationContext(), text);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    /**
     * 弹出toast
     * @param context
     * @param text
     */
    public static void toastShow(Context context, int text) {
        if (null != toast) {
            toast.cancel();
        }
        toast = new ToastView(context.getApplicationContext(), text);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void cancel() {
        if (null != toast) {
            toast.cancel();
            toast = null;
        }
    }

}