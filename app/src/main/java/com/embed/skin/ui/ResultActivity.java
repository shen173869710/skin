package com.embed.skin.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.embed.skin.R;
import com.embed.skin.adapter.ResultAdapter;
import com.embed.skin.entity.PreviewResult;
import com.embed.skin.entity.ResultMetrics;
import com.embed.skin.model.respone.BaseRespone;
import com.embed.skin.presenter.LoginPresenter;
import com.embed.skin.util.GlideUtils;
import com.embed.skin.util.LogUtils;
import com.embed.skin.view.ILoginView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ResultActivity extends LBaseActivity<LoginPresenter> implements ILoginView {


    @BindView(R.id.result_score)
    TextView resultScore;
    @BindView(R.id.result_head)
    ImageView resultHead;
    @BindView(R.id.result_rank)
    TextView resultRank;
    @BindView(R.id.result_faceStatus)
    TextView resultFaceStatus;
    @BindView(R.id.result_age)
    TextView resultAge;
    @BindView(R.id.result_list)
    RecyclerView resultList;

    private PreviewResult result;
    private ResultAdapter resultAdapter;

    @Override
    protected int setLayout() {
        return R.layout.activity_result;
    }

    @Override
    protected void init() {
        result = (PreviewResult) getIntent().getSerializableExtra("result");

        if (result == null) {
            LogUtils.e(TAG, "reulet = "+result);
            return;
        }
        resultScore.setText("得分 "+result.getScore());

        GlideUtils.loadResource(this, R.mipmap.test, resultHead);

        ResultMetrics resultMetrics = result.getMetrics();
        if (resultMetrics != null) {
            resultRank.setText("排名 "+resultMetrics.getRank());
            resultFaceStatus.setText("状态 "+resultMetrics.getFaceStatus());
            resultAge.setText("年龄 "+resultMetrics.getAge());
        }

        resultList.setLayoutManager(new LinearLayoutManager(this));
        resultAdapter = new ResultAdapter(result.getFeatures());
        resultList.setAdapter(resultAdapter);
    }


    @Override
    protected void setListener() {
//        findViewById(R.id.result_image).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(ResultActivity.this, RecomActivity.class));
//            }
//        });
    }

    @Override
    public void createPresenter() {
        mPresenter = new LoginPresenter();
    }


    @Override
    public void loginSuccess(BaseRespone respone) {

    }

    @Override
    public void loginFail(Throwable error, String msg) {
        startActivity(new Intent(ResultActivity.this, HomeActivity.class));
        finish();
    }

//    public void rightClick(View view) {
//        startActivity(new Intent(ResultActivity.this, HistoryListActivity.class));
//    }

}
