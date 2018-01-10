package com.huxi.tools;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huxi.toasttools.R;


/**
 * Created by Phyllis on 16/3/24.
 * Custom Progress dialog.
 */
public class ProgressDialog extends Dialog {
    public ImageView animImageView;
    public TextView messageTextView;
    public ProgressDialog(Context context) {
        super(context);
    }

    public ProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static ProgressDialog show(Context context, CharSequence message,
                                   boolean cancelable,
                                   OnCancelListener cancelListener) {
        ProgressDialog dialog = showBaseDialog(context, message, cancelable,
                cancelListener);
        dialog.animImageView.setImageResource(R.drawable.loading);
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.rotate_around);
        anim.setDuration(1000);
        dialog.animImageView.startAnimation(anim);
        return dialog;
    }

    /**
     * @param res Custom animation drawables resourse id
     * @return ProgressDialog
     */
    public static ProgressDialog show(Context context, CharSequence message,
                                   boolean cancelable,
                                   OnCancelListener cancelListener, @DrawableRes int res) {
        ProgressDialog dialog = showBaseDialog(context, message, cancelable,
                cancelListener);
        dialog.animImageView.setImageResource(res);
        Drawable drawable = dialog.animImageView.getDrawable();
        if (drawable instanceof AnimationDrawable) {
            ((AnimationDrawable) drawable).start();
        } else {
            Animation anim = AnimationUtils.loadAnimation(context, R.anim.rotate_around);
            anim.setDuration(1000);
            dialog.animImageView.startAnimation(anim);
        }

        return dialog;
    }
    private static ProgressDialog showBaseDialog(Context context,
                                                 CharSequence message, boolean cancelable,
                                                 OnCancelListener cancelListener) {
        ProgressDialog dialog = new ProgressDialog(context, R.style.ProgressHUD);
        dialog.setTitle("");
        View view =  ((LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.progress_hud, null);
        dialog.animImageView = (ImageView) view.findViewById(R.id.statusImageView);
        dialog.messageTextView = (TextView) view.findViewById(R.id.message);
        int animImageViewSize = context.getResources().getDimensionPixelSize(R.dimen.progress_animation_view_size);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(animImageViewSize,animImageViewSize);
        dialog.animImageView.setLayoutParams(layoutParams);

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
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        return dialog;
    }


}
