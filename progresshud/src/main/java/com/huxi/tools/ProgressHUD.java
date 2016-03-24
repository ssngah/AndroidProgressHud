package com.huxi.tools;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huxi.progresshud.R;


public class ProgressHUD extends Dialog {

    public ImageView animImageView;
    public ImageView statusImageView;
    public TextView messageTextView;
    private static Toast hud;

    public ProgressHUD(Context context) {
        super(context);
    }

    public ProgressHUD(Context context, int theme) {
        super(context, theme);
    }


    public void setMessage(CharSequence message) {
        if (message != null && message.length() > 0) {
            findViewById(R.id.message).setVisibility(View.VISIBLE);
            TextView txt = (TextView) findViewById(R.id.message);
            txt.setText(message);
            txt.invalidate();
        }
    }

    public static ProgressHUD show(Context context, CharSequence message, boolean indeterminate,
                                   boolean cancelable,
                                   OnCancelListener cancelListener) {
        ProgressHUD dialog = showBaseDialog(context, message, cancelable,
                cancelListener);
//        dialog.animImageView.setBackgroundResource(R.drawable.progress_hud_animation);
        dialog.animImageView.setImageResource(R.drawable.loading);
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.rotate_around);
        anim.setDuration(1000);
        dialog.animImageView.startAnimation(anim);
//        AnimationDrawable drawable = (AnimationDrawable) dialog.animImageView.getBackground();
//        drawable.start();
//		dialog.statusImageView.setVisibility(View.GONE);
        return dialog;
    }

    /**
     * @param res Can be a static drawable or animation drawable
     * @return ProgressHUD
     */
    public static ProgressHUD show(Context context, CharSequence message, boolean indeterminate,
                                   boolean cancelable,
                                   OnCancelListener cancelListener, @DrawableRes int res) {
        ProgressHUD dialog = showBaseDialog(context, message, cancelable,
                cancelListener);
        dialog.animImageView.setImageResource(res);
        Drawable drawable = dialog.animImageView.getDrawable();
        if (drawable instanceof AnimationDrawable) {
            ((AnimationDrawable) drawable).start();
            dialog.statusImageView.setVisibility(View.GONE);
        } else {
            Animation anim = AnimationUtils.loadAnimation(context, R.anim.rotate_around);
            anim.setDuration(1000);
            dialog.animImageView.startAnimation(anim);
        }
        return dialog;
    }


    private static ProgressHUD showBaseDialog(Context context,
                                              CharSequence message, boolean cancelable,
                                              OnCancelListener cancelListener) {
        ProgressHUD dialog = new ProgressHUD(context, R.style.ProgressHUD);
        dialog.setTitle("");
        View view = (View) ((LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.progress_hud, null);
        dialog.animImageView = (ImageView) view.findViewById(R.id.spinnerImageView);
        dialog.messageTextView = (TextView) view.findViewById(R.id.message);
        dialog.statusImageView = (ImageView) view.findViewById(R.id.statusImageView);
        dialog.statusImageView.setColorFilter(Color.WHITE);
        dialog.setContentView(view);
        if (message == null || message.length() == 0) {
            dialog.messageTextView.setVisibility(View.GONE);
        } else {
            dialog.messageTextView.setText(message);
        }
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        int size = context.getResources().getDimensionPixelSize(R.dimen.progress_hud_size);
        lp.dimAmount = 0.2f;
        lp.width = size;
        lp.height = size;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        return dialog;
    }

    public static ProgressHUD show(Context context, CharSequence message) {
        return ProgressHUD.show(context, message, true, false, null);
    }

    public static ProgressHUD show(Context context, CharSequence message, @DrawableRes int res) {

        return ProgressHUD.show(context, message, true, false, null, res);
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
            view.findViewById(R.id.spinnerImageView).setVisibility(View.GONE);
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
