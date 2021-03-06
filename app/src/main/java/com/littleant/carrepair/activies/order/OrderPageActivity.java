package com.littleant.carrepair.activies.order;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.Constraints;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.littleant.carrepair.R;
import com.littleant.carrepair.activies.BaseActivity;
import com.littleant.carrepair.activies.address.MyAddressActivity;
import com.littleant.carrepair.activies.pay.PaymentActivity;
import com.littleant.carrepair.request.bean.MyAddressListBean;

import static com.littleant.carrepair.activies.address.MyAddressActivity.USER_ADDRESS_BEAN;

/**
 * 确认订单
 */
public class OrderPageActivity extends BaseActivity {

    private RecyclerView mList;
    private Button mSubmit;
    private TextView op_tv_total_money, btn_add_receive_address;
    private TextView aop_address_name,aop_address_phone, aop_address;
    private View aop_cl;
    public static final String PICK_ADDRESS = "pic_address";
    private static final int REQUEST_ADDRESS = 10;
    private MyAddressListBean.AddressInfo addressInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void init() {
        super.init();

        mList = findViewById(R.id.op_list);
        mList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        op_tv_total_money = findViewById(R.id.op_tv_total_money);
        btn_add_receive_address = findViewById(R.id.btn_add_receive_address);

        mSubmit = findViewById(R.id.op_submit);
        mSubmit.setOnClickListener(this);

        aop_address_name = findViewById(R.id.aop_address_name);
        aop_address_phone = findViewById(R.id.aop_address_phone);
        aop_address = findViewById(R.id.aop_address);
        aop_cl = findViewById(R.id.aop_cl);
        aop_cl.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_page;
    }

    @Override
    protected int getTitleId() {
        return R.string.text_order_page;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.op_submit:
                    Intent intent = new Intent(OrderPageActivity.this, PaymentActivity.class);
                    OrderPageActivity.this.startActivity(intent);
                break;

                case R.id.aop_cl:
                    Intent intent2 = new Intent(OrderPageActivity.this, MyAddressActivity.class);
                    intent2.putExtra(PICK_ADDRESS, true);
                    OrderPageActivity.this.startActivityForResult(intent2, REQUEST_ADDRESS);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_ADDRESS && resultCode == RESULT_OK) {
            btn_add_receive_address.setVisibility(View.INVISIBLE);
            addressInfo = (MyAddressListBean.AddressInfo) data.getSerializableExtra(USER_ADDRESS_BEAN);
            aop_address_name.setText(addressInfo.getName());
            aop_address_phone.setText(addressInfo.getPhone());
            aop_address.setText(addressInfo.getAddress());
        }

    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        public static final int TYPE_HEADER = 0;  //说明是带有Header的
        public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
        public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的
        private View mFooterView;

        public View getFooterView() {
            return mFooterView;
        }
        public void setFooterView(View footerView) {
            mFooterView = footerView;
            notifyItemInserted(getItemCount()-1);
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(viewType == TYPE_FOOTER){
                mFooterView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_order_end_item, parent, false);
                return new MyAdapter.ViewHolder(mFooterView);
            }
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_order_item, parent, false);
            final MyAdapter.ViewHolder viewHolder = new MyAdapter.ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
            if(position == getItemCount() - 1) {
                holder.oei_detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog d = new Dialog(OrderPageActivity.this, R.style.MyTransparentDialog);
                        View contentView = View.inflate(OrderPageActivity.this, R.layout.layout_point, null);
//                        d.setContentView(R.layout.layout_point);
                        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
                        int dialogWidth = (int) (dm.widthPixels * 0.7);
                        int dialogHeight = (int) (dm.heightPixels * 0.35);
                        d.setContentView(contentView, new Constraints.LayoutParams(dialogWidth, dialogHeight));
                        contentView.findViewById(R.id.lp_iv_close).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                d.dismiss();
                            }
                        });
                        d.show();
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return 5;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView loi_product_title, loi_per_price, loi_product_count, loi_totle_count, loi_tv_sum;
            ImageView loi_img, oei_detail;

            ViewHolder(View itemView) {
                super(itemView);
                if(itemView == mFooterView) {
                    oei_detail = itemView.findViewById(R.id.oei_detail);
                }

            }

        }

        @Override
        public int getItemViewType(int position) {

            if (position == getItemCount()-1){
                //最后一个,应该加载Footer
                return TYPE_FOOTER;
            }
            return TYPE_NORMAL;
        }
    }
}
