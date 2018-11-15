package com.littleant.carrepair.activies.main;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Constraints;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.car.AddCarActivity;
import com.littleant.carrepair.fragment.AnnualCheckFragment;
import com.littleant.carrepair.fragment.BaseFragment;
import com.littleant.carrepair.fragment.MainFragment;
import com.littleant.carrepair.fragment.ServiceFragment;
import com.littleant.carrepair.fragment.UserCenterFragment;
import com.littleant.carrepair.request.bean.BaseResponseBean;
import com.littleant.carrepair.request.bean.car.MyCarListBean;
import com.littleant.carrepair.request.bean.system.violation.ViolationBean;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.service.rule.RuleQueryAllCmd;
import com.littleant.carrepair.request.excute.user.car.CarQueryAllCmd;
import com.littleant.carrepair.request.utils.DataHelper;
import com.littleant.carrepair.utils.ProjectUtil;
import com.mh.core.task.MHCommandCallBack;
import com.mh.core.task.MHCommandExecute;
import com.mh.core.task.command.abstracts.MHCommand;
import com.mh.core.tools.MHToast;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 主页
 */
public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener, BaseFragment.OnFragmentInteractionListener{
    /**
     * 高德地图
     */
    private static final int permission_request_code = 10;
    private RadioGroup radioGroup;
    private RadioButton mainBtn, annaulCheckBtn, serviceBtn, userCenterBtn;
    private Context mContext;
//    private RecyclerView mList;
    private boolean isGuest;
    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.BLUETOOTH}, permission_request_code);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.main_fragment, new MainFragment(), MainFragment.class.getSimpleName());
        transaction.commit();

        isGuest = DataHelper.getGuestLogin(this);

        init();

    }

    private void init() {
        radioGroup = findViewById(R.id.linearLayout);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Fragment fragment = null;
                String tag = "";
                switch (i) {
                    case R.id.mainBtn:
                        //首页
                        fragment = new MainFragment();
                        tag = MainFragment.class.getSimpleName();
                        break;

                    case R.id.annualBtn:
                        //年检
                        fragment = new AnnualCheckFragment();
                        tag = AnnualCheckFragment.class.getSimpleName();
                        break;

                    case R.id.serviceBtn:
                        //服务
                        fragment = new ServiceFragment();
                        tag = ServiceFragment.class.getSimpleName();
                        break;

                    case R.id.mineBtn:
                        if(isGuest) {
                            finish();
                            return;
                        }
                        //个人中心
                        fragment = new UserCenterFragment();
                        tag = UserCenterFragment.class.getSimpleName();
                        break;
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_fragment, fragment, tag);
                transaction.commitAllowingStateLoss();
            }
        });

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if(mMapView != null) {
//            mMapView.onResume();
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        if(mMapView != null) {
//            mMapView.onPause();
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if(mMapView != null) {
//            mMapView.onDestroy();
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        if(mMapView != null) {
//            mMapView.onSaveInstanceState(outState);
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == permission_request_code) {
            if(grantResults.length > 0 && permissions.length > 0) {
                //获取定位权限
//                MyLocationStyle myLocationStyle;
//                myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
//                myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
//                aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//                //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
//                aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
            } else {
                //获取定位权限失败
                Toast.makeText(this, R.string.permission_deny_location, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        List<ViolationBean.ViolationInfo> mData;

        public MyAdapter(List<ViolationBean.ViolationInfo> data) {
            this.mData = data;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_violation_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
            ViolationBean.ViolationInfo info = mData.get(position);
            holder.lvi_tv_name.setText(info.getCar_brand());
            holder.lvi_tv_sum.setText(String.format(getResources().getString(R.string.text_violation_sum), info.getAmount() + ""));
            holder.lvi_tv_score.setText(String.format(getResources().getString(R.string.text_violation_score), info.getScore() + ""));
            holder.lvi_tv_fine.setText(String.format(getResources().getString(R.string.text_violation_fine), info.getPrice() + ""));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView lvi_tv_name, lvi_tv_sum, lvi_tv_score, lvi_tv_fine;

            ViewHolder(View itemView) {
                super(itemView);
                lvi_tv_name = itemView.findViewById(R.id.lvi_tv_name);
                lvi_tv_sum = itemView.findViewById(R.id.lvi_tv_sum);
                lvi_tv_score = itemView.findViewById(R.id.lvi_tv_score);
                lvi_tv_fine = itemView.findViewById(R.id.lvi_tv_fine);
            }

        }
    }
}
