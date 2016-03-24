package com.huxi.tools;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huxi.toasttools.R;


public class ProgressHUD {

    private static Toast hud;

    public static Dialog show(Context context, CharSequence message) {
        return ProgressDialog.show(context, message, true,  null);
    }

    public static Dialog show(Context context, CharSequence message, @DrawableRes int res) {

        return ProgressDialog.show(context, message, true,  null, res);
    }
    public static Dialog show(Context context,CharSequence message,boolean isCancelable){
        return ProgressDialog.show(context,message,isCancelable,null);
    }

    public static Toast showSuccess(Context context, CharSequence message, int duration) {
        return showToast(context, message, duration, R.drawable.progress_hud_success);
    }

    public static Toast showError(Context context, CharSequence message, int duration) {
        return showToast(context, message, duration, R.drawable.progress_hud_error);
    }
    private static Toast showToast(Context context, CharSequence message,
                                   int duration, int imageResId) {
        if (context == null) return null;
        if (hud == null) {
            hud = new Toast(context);
            View view = ((LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.progress_hud, null);
//            view.findViewById(R.id.spinnerImageView).setVisibility(View.GONE);
            ImageView statusImageView = (ImageView) view.findViewById(R.id.statusImageView);
            statusImageView.setImageDrawable(context.getResources().getDrawable(imageResId));
            statusImageView.setColorFilter(Color.WHITE);
            TextView messageTextView = (TextView) view.findViewById(R.id.message);
            messageTextView.setText(message);
            hud.setView(view);
        } else {
            TextView txtMsg = (TextView) hud.getView().findViewById(R.id.message);
            txtMsg.setText(message);
            ImageView imageView = (ImageView) hud.getView().findViewById(R.id.statusImageView);
            imageView.setImageResource(imageResId);
        }
        hud.setDuration(duration);
        hud.setGravity(Gravity.CENTER, 0, 0);
        hud.show();
        return hud;
    }
}
