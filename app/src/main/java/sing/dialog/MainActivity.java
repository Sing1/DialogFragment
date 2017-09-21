package sing.dialog;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity implements BottomDialog.OnClickListener, View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.bt1).setOnClickListener(MainActivity.this);
        findViewById(R.id.bt2).setOnClickListener(MainActivity.this);
        findViewById(R.id.bt3).setOnClickListener(MainActivity.this);
        findViewById(R.id.bt4).setOnClickListener(MainActivity.this);
        findViewById(R.id.bt5).setOnClickListener(MainActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt1://无提示有取消
                new BottomDialog.Builder(MainActivity.this)
                        .setFragmentManager(getSupportFragmentManager())
                        .setCancel("取消")
                        .setCancelTextColor(Color.parseColor("#FF0000"))
                        .setCancelTextSize(16)
                        .setList(new String[]{"拍照", "从相册中选择"})
                        .setListener(MainActivity.this)
                        .build();
                break;
            case R.id.bt2://有提示无取消
                new BottomDialog.Builder(MainActivity.this)
                        .setFragmentManager(getSupportFragmentManager())
                        .setHint("选择性别")
                        .setIsRect(true)
                        .setHintTextSize(12)
                        .setHintTextColor(Color.parseColor("#313131"))
                        .setHintBgColor(Color.parseColor("#E5E5E5"))
                        .setList(new String[]{"男", "女", "保密"})
                        .setListener(MainActivity.this)
                        .build();
                break;
            case R.id.bt3://无提示无取消
                new BottomDialog.Builder(MainActivity.this)
                        .setFragmentManager(getSupportFragmentManager())
                        .setList(new String[]{"第一", "第二", "第三", "第四"})
                        .setListener(MainActivity.this)
                        .build();
                break;
            case R.id.bt4://有提示有取消
                new BottomDialog.Builder(MainActivity.this)
                        .setFragmentManager(getSupportFragmentManager())
                        .setHint("退出后将情况记录")
                        .setHintBgColor(Color.parseColor("#E5E5E5"))
                        .setHintTextSize(12)
                        .setHintTextColor(Color.parseColor("#313131"))
                        .setCancel("取消")
                        .setCancelTextSize(16)
                        .setCancelTextColor(Color.parseColor("#FF0000"))
                        .setIsRect(true)
                        .setList(new String[]{"男", "女", "保密"})
                        .setListener(MainActivity.this)
                        .build();
                break;
            case R.id.bt5:// 添加了自定义View
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_title,null);
                view.findViewById(R.id.cancel).setOnClickListener(MainActivity.this);
                view.findViewById(R.id.ok).setOnClickListener(MainActivity.this);
                new BottomDialog.Builder(MainActivity.this)
                        .setFragmentManager(getSupportFragmentManager())
                        .setView(view,0)
                        .setIsRect(true)
                        .setList(new String[]{"选项一", "选项二", "选项三"})
                        .setListener(MainActivity.this)
                        .build();
                break;
            case R.id.cancel:
                Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ok:
                Toast.makeText(this, "确定", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onClick(AlertDialog dialog, int positing, String str) {
        dialog.dismiss();
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}