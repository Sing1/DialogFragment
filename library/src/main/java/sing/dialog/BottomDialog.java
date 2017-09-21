package sing.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sing.bottom.dialog.R;

/**
 * @author: LiangYX
 * @ClassName: ButtomDialog
 * @date: 2017/9/18 下午12:08
 * @Description: 底部弹窗
 */
public class BottomDialog extends DialogFragment {

    private Activity context;
    private LinearLayout parent;
    private AlertDialog dialog;

    private String cancel = "";// 添加取消按钮
    private String hint = "";// 添加提示头部
    private List<String> list = new ArrayList<>();
    private boolean isRect = false;// true,代表没有圆角
    private int radius = 8;// 圆角弧度，isRect为true时无效
    private int hintBgColor = Color.parseColor("#FFFFFF");// 提示部分的颜色
    private int hintTextColor = Color.parseColor("#000000");// 提示部分的文字颜色
    private int hintTextSize = 14;
    private int fillColor = Color.parseColor("#FFFFFF");// 填充颜色
    private int pressColor = Color.parseColor("#DCDCDC");// 按下颜色
    private int strokeColor = Color.parseColor("#DCDCDC");//分割线颜色
    private int cancelTextColor = Color.parseColor("#000000");
    private int cancelTextSize = 14;
    private int textColor = Color.parseColor("#000000");
    private int textSize = 14;
    private OnClickListener listener;

    /**
     * START build
     **********************************************************************/
    public static class Builder {
        private Activity context;
        private FragmentManager f;
        private String cancel = "";// 添加取消按钮
        private String hint = "";// 添加提示头部
        private List<String> list = new ArrayList<>();
        private int radius = 8;// 圆角弧度，isRect为true时无效
        private boolean isRect = false;// true,代表没有圆角
        private int hintBgColor = Color.parseColor("#FFFFFF");// 提示部分的背景颜色
        private int hintTextColor = Color.parseColor("#000000");// 提示部分的文字颜色
        private int hintTextSize = 14;
        private int fillColor = Color.parseColor("#FFFFFF");// 填充颜色
        private int pressColor = Color.parseColor("#DCDCDC");// 按下颜色
        private int strokeColor = Color.parseColor("#DCDCDC");//分割线颜色
        private int cancelTextColor = Color.parseColor("#000000");
        private int cancelTextSize = 14;
        private int textSize = 14;
        private int textColor = Color.parseColor("#000000");
        private View view;// 自定义的View
        private int position;// 添加自定义的View的位置
        private OnClickListener listener;

        public Builder(Activity context) {
            this.context = context;
        }

        // 设置取消
        public Builder setCancel(String cancel) {
            this.cancel = cancel;
            return this;
        }

        // 设置提示
        public Builder setHint(String hint) {
            this.hint = hint;
            return this;
        }

        // 设置List数据
        public Builder setList(List<String> list) {
            this.list.clear();
            this.list = list;
            return this;
        }

        public Builder setList(String[] strs) {
            this.list.clear();
            for (int i = 0; i < strs.length; i++) {
                this.list.add(strs[i]);
            }
            return this;
        }

        // 设置弧度
        public Builder setRadius(int radius) {
            this.radius = radius;
            return this;
        }

        // 设置是否为方角
        public Builder setIsRect(boolean isRect) {
            this.isRect = isRect;
            return this;
        }

        // 设置提示部分的背景颜色
        public Builder setHintBgColor(int hintBgColor) {
            this.hintBgColor = hintBgColor;
            return this;
        }

        // 设置提示部分的文字颜色
        public Builder setHintTextColor(int hintTextColor) {
            this.hintTextColor = hintTextColor;
            return this;
        }

        // 设置提示文字的大小
        public Builder setHintTextSize(int hintTextSize) {
            this.hintTextSize = hintTextSize;
            return this;
        }

        // 设置中间内容填充的颜色
        public Builder setFillColor(int fillColor) {
            this.fillColor = fillColor;
            return this;
        }

        // 设置中间内容按下的颜色
        public Builder setPressColor(int pressColor) {
            this.pressColor = pressColor;
            return this;
        }

        // 设置分割线的颜色
        public Builder setStrokeColor(int strokeColor) {
            this.strokeColor = strokeColor;
            return this;
        }

        // 设置取消的文字颜色
        public Builder setCancelTextColor(int cancelTextColor) {
            this.cancelTextColor = cancelTextColor;
            return this;
        }

        // 设置取消的文字大小
        public Builder setCancelTextSize(int cancelTextSize) {
            this.cancelTextSize = cancelTextSize;
            return this;
        }

        // 设置普通的文字颜色
        public Builder setTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        // 设置普通的文字大小
        public Builder setTextSize(int textColor) {
            this.textColor = textColor;
            return this;
        }

        // 设置点击监听
        public Builder setListener(OnClickListener listener) {
            this.listener = listener;
            return this;
        }

        // 添加自定义的View
        public Builder setView(View view, int position) {
            this.view = view;
            this.position = position;
            return this;
        }

        // 设置点击监听
        public Builder setFragmentManager(FragmentManager f) {
            this.f = f;
            return this;
        }

        public BottomDialog build() { // 构建，返回一个新对象
            return new BottomDialog(this);
        }
    }

    /**
     * END build
     **********************************************************************/

    public BottomDialog(Builder b) {
        this.context = b.context;

        parent = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        parent.setLayoutParams(params);
        parent.setBackgroundColor(Color.parseColor("#00000000"));
        parent.setOrientation(LinearLayout.VERTICAL);

        this.cancel = b.cancel;
        this.hint = b.hint;
        this.list = b.list;
        this.isRect = b.isRect;
        this.radius = b.radius;
        this.hintBgColor = b.hintBgColor;
        this.hintTextColor = b.hintTextColor;
        this.hintTextSize = b.hintTextSize;
        this.fillColor = b.fillColor;
        this.pressColor = b.pressColor;
        this.strokeColor = b.strokeColor;
        this.cancelTextColor = b.cancelTextColor;
        this.cancelTextSize = b.cancelTextSize;
        this.textColor = b.textColor;
        this.textSize = b.textSize;
        this.listener = b.listener;

        show(b);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = new AlertDialog.Builder(getActivity(), R.style.BottomDialog).setView(parent).create();
        dialog.setCanceledOnTouchOutside(true);

        // 设置宽度为屏宽、靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        window.setAttributes(wlp);

        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (null != dialog) {
            dialog.getWindow().setLayout(-1, -2);
        }
    }

    private void show(Builder b) {
        if (isRect) {// 直接写在这里，省的每次都判断
            setHintView1();
            setCenterView1();
            setCancelView1();
        } else {
            parent.setPadding(20, 20, 20, 20);// 圆角的时候有边距
            setHintView();
            setCenterView();
            setCancelView();
        }
        if (b.view != null) {
            try {
                parent.addView(b.view, b.position);
            } catch (Exception e) {
                Log.e("","添加自定义View" + e.getMessage());
            }
        }
        show(b.f, "BottomDialog");
    }

    // 设置提示部分
    private void setHintView() {
        if (!TextUtils.isEmpty(hint)) {
            TextView tv = new TextView(context);
            tv.setGravity(Gravity.CENTER);
            tv.setText(hint);
            tv.setTextColor(hintTextColor);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, hintTextSize);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 70);
            tv.setLayoutParams(params);
            tv.setBackground(getTopBackground(hintBgColor));
            parent.addView(tv);

            View line = new View(context);
            line.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
            line.setBackgroundColor(strokeColor);
            parent.addView(line);
        }
    }

    // 设置中间部分
    private void setCenterView() {
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                TextView tv = new TextView(context);
                tv.setGravity(Gravity.CENTER);
                tv.setText(list.get(i));
                tv.setTextColor(textColor);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 90);
                if (i == 0 && TextUtils.isEmpty(hint)) {
                    tv.setBackground(getTopBackground(fillColor));
                } else if (i == list.size() - 1) {
                    tv.setBackground(getBottomBackground());
                } else {
                    tv.setBackground(getCenterBackground(fillColor));
                }
                tv.setLayoutParams(params);
                final int finalI = i;
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onClick(dialog, finalI, list.get(finalI));
                    }
                });
                parent.addView(tv);

                if (i != list.size() - 1) {
                    View view = new View(context);
                    view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
                    view.setBackgroundColor(strokeColor);
                    parent.addView(view);
                }
            }
        }
    }

    // 设置底部取消部分
    private void setCancelView() {
        if (!TextUtils.isEmpty(cancel)) {
            TextView tv = new TextView(context);
            tv.setGravity(Gravity.CENTER);
            tv.setText(cancel);
            tv.setTextColor(cancelTextColor);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, cancelTextSize);
            tv.setBackground(getAllBackground());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 90);
            params.setMargins(0, 20, 0, 0);
            tv.setLayoutParams(params);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(dialog, -1, "取消");
                }
            });
            parent.addView(tv);
        }
    }


    // 设置提示部分
    private void setHintView1() {
        if (!TextUtils.isEmpty(hint)) {
            TextView tv = new TextView(context);
            tv.setGravity(Gravity.CENTER);
            tv.setText(hint);
            tv.setTextColor(hintTextColor);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, hintTextSize);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 70);
            tv.setLayoutParams(params);
            tv.setBackground(getCenterBackground(hintBgColor));
            parent.addView(tv);

            View line = new View(context);
            line.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
            line.setBackgroundColor(strokeColor);
            parent.addView(line);
        }
    }

    // 设置中间部分
    private void setCenterView1() {
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                TextView tv = new TextView(context);
                tv.setGravity(Gravity.CENTER);
                tv.setText(list.get(i));
                tv.setTextColor(textColor);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 90);
                tv.setBackground(getCenterBackground(fillColor));
                tv.setLayoutParams(params);
                final int finalI = i;
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onClick(dialog, finalI, list.get(finalI));
                    }
                });
                parent.addView(tv);

                if (i != list.size() - 1) {
                    View view = new View(context);
                    view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
                    view.setBackgroundColor(strokeColor);
                    parent.addView(view);
                }
            }
        }
    }

    // 设置底部取消部分
    private void setCancelView1() {
        if (!TextUtils.isEmpty(cancel)) {
            TextView tv = new TextView(context);
            tv.setGravity(Gravity.CENTER);
            tv.setText(cancel);
            tv.setTextColor(cancelTextColor);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, cancelTextSize);
            tv.setBackground(getCenterBackground(fillColor));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 90);
            params.setMargins(0, 20, 0, 0);
            tv.setLayoutParams(params);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(dialog, -1, "取消");
                }
            });
            parent.addView(tv);
        }
    }

    // 上面圆角的背景
    private StateListDrawable getTopBackground(int color) {
        GradientDrawable background = new GradientDrawable();//创建drawable
        background.setColor(color);
        background.setCornerRadii(new float[]{radius, radius, radius, radius, 0, 0, 0, 0});
        background.setStroke(0, strokeColor);

        GradientDrawable backgroundPress = new GradientDrawable();//创建drawable
        backgroundPress.setColor(pressColor);
        backgroundPress.setCornerRadii(new float[]{radius, radius, radius, radius, 0, 0, 0, 0});
        backgroundPress.setStroke(0, strokeColor);

        StateListDrawable topBackground = new StateListDrawable();
        topBackground.addState(new int[]{android.R.attr.state_pressed}, backgroundPress);
        topBackground.addState(new int[]{android.R.attr.state_selected}, backgroundPress);
        topBackground.addState(new int[]{-android.R.attr.state_selected}, background);
        topBackground.addState(new int[]{-android.R.attr.state_pressed}, background);
        return topBackground;
    }

    // 下面圆角的背景
    private StateListDrawable getBottomBackground() {
        GradientDrawable background = new GradientDrawable();//创建drawable
        background.setColor(fillColor);
        background.setCornerRadii(new float[]{0, 0, 0, 0, radius, radius, radius, radius});
        background.setStroke(0, strokeColor);

        GradientDrawable backgroundPress = new GradientDrawable();//创建drawable
        backgroundPress.setColor(pressColor);
        backgroundPress.setCornerRadii(new float[]{0, 0, radius, radius, radius, radius, 0, 0});
        backgroundPress.setStroke(0, strokeColor);

        StateListDrawable bottomBackground = new StateListDrawable();
        bottomBackground.addState(new int[]{android.R.attr.state_pressed}, backgroundPress);
        bottomBackground.addState(new int[]{android.R.attr.state_selected}, backgroundPress);
        bottomBackground.addState(new int[]{-android.R.attr.state_selected}, background);
        bottomBackground.addState(new int[]{-android.R.attr.state_pressed}, background);
        return bottomBackground;
    }

    // 四周无圆角的背景
    private StateListDrawable getCenterBackground(int color) {
        GradientDrawable background = new GradientDrawable();//创建drawable
        background.setColor(color);
        background.setCornerRadii(new float[]{0, 0, 0, 0, 0, 0, 0, 0});
        background.setStroke(0, strokeColor);

        GradientDrawable backgroundPress = new GradientDrawable();//创建drawable
        backgroundPress.setColor(pressColor);
        backgroundPress.setCornerRadii(new float[]{0, 0, 0, 0, 0, 0, 0, 0});
        backgroundPress.setStroke(0, strokeColor);

        StateListDrawable centerBackground = new StateListDrawable();
        centerBackground.addState(new int[]{android.R.attr.state_pressed}, backgroundPress);
        centerBackground.addState(new int[]{android.R.attr.state_selected}, backgroundPress);
        centerBackground.addState(new int[]{-android.R.attr.state_selected}, background);
        centerBackground.addState(new int[]{-android.R.attr.state_pressed}, background);
        return centerBackground;
    }

    // 四周圆角的背景
    private StateListDrawable getAllBackground() {
        GradientDrawable background = new GradientDrawable();//创建drawable
        background.setColor(fillColor);
        background.setCornerRadii(new float[]{radius, radius, radius, radius, radius, radius, radius, radius});
        background.setStroke(0, strokeColor);

        GradientDrawable backgroundPress = new GradientDrawable();//创建drawable
        backgroundPress.setColor(pressColor);
        backgroundPress.setCornerRadii(new float[]{radius, radius, radius, radius, radius, radius, radius, radius});
        backgroundPress.setStroke(0, strokeColor);

        StateListDrawable centerBackground = new StateListDrawable();
        centerBackground.addState(new int[]{android.R.attr.state_pressed}, backgroundPress);
        centerBackground.addState(new int[]{android.R.attr.state_selected}, backgroundPress);
        centerBackground.addState(new int[]{-android.R.attr.state_selected}, background);
        centerBackground.addState(new int[]{-android.R.attr.state_pressed}, background);
        return centerBackground;
    }

    // 点击监听
    public interface OnClickListener {
        void onClick(AlertDialog dialog, int pos, String str);
    }
}