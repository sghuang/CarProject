package com.littleant.carrepair.request.excute.maintain.garage;

import android.content.Context;

import com.littleant.carrepair.request.constant.InterfaceConstant;
import com.littleant.carrepair.request.constant.ParamsConstant;
import com.littleant.carrepair.request.excute.BaseRequestCmd;
import com.mh.core.tools.MHLogUtil;

public class GarageQueryAllCmd extends BaseRequestCmd {
    public GarageQueryAllCmd(Context context, String name, ParamsConstant.OrderRule orderby, double longitude, double latitude) {
        super(context);
        params.put(ParamsConstant.NAME, name);
        params.put(ParamsConstant.ORDERBY, orderby.getDes());
        params.put(ParamsConstant.LONGITUDE, longitude + "");
        params.put(ParamsConstant.LATITUDE, latitude + "");
        MHLogUtil.logI(getClass().getSimpleName() + this.params.toString());
    }

    @Override
    protected String getInterfaceName() {
        return InterfaceConstant.MAINTAIN_GARAGE;
    }

    @Override
    protected RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }
}
