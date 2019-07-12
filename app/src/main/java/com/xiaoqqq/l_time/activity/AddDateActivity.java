package com.xiaoqqq.l_time.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xiaoqqq.l_time.R;
import com.xiaoqqq.l_time.base.BaseActivity;
import com.xiaoqqq.l_time.constants.RouterPath;

/**
 * @author xiaoqqq
 * @package com.xiaoqqq.l_time.activity
 * @date 2019-07-12
 * @describe 添加纪念日 activity
 */
@Route(path = RouterPath.addDateActivity)
public class AddDateActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_date;
    }

    @Override
    protected void initView() {

    }
}
