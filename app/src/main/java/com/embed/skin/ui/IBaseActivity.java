package com.embed.skin.ui;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.embed.skin.R;
import com.embed.skin.custom.LoadingDialog;
import com.embed.skin.presenter.BasePresenter;
import com.embed.skin.util.LogUtils;
import com.embed.skin.util.ToastUtil;
import com.embed.skin.view.BaseView;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

public abstract class IBaseActivity<T extends BasePresenter> extends RxAppCompatActivity implements BaseView {
	protected T mPresenter;
	private LoadingDialog mLoadingDailog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(setLayout());
		EventBus.getDefault().register(this);
		ButterKnife.bind(this);
		mPresenter = createPresenter();
		if (mPresenter != null) {
			mPresenter.attachView(this);
		}
		requestPermissions();
//		ActivityStackUtil.add(this);
		init();
		setListener();
	}

	/**
	 * 显示等待提示框
	 */
	private Dialog showWaitingDialog() {
		mLoadingDailog = new LoadingDialog(this, R.style.CustomDialog);
		return mLoadingDailog;
	}


	/**
	 * 显示等待提示框
	 */
	public Dialog showWaitingDialog( String title) {
		mLoadingDailog = new LoadingDialog(this, R.style.CustomDialog,title);
		return mLoadingDailog;
	}

	@Override
	public void showDialog() {
		if (null == mLoadingDailog) {
			showWaitingDialog();
		}
		if (!mLoadingDailog.isShowing()) {
			mLoadingDailog.show();
		}
	}

	@Override
	public void dismissDialog() {
		if (mLoadingDailog != null && mLoadingDailog.isShowing()) {
			mLoadingDailog.dismiss();
			mLoadingDailog = null;
		}
	}
	/**
	 * 显示字符串类型数据
	 * 
	 * @param msg
	 */
	protected void showToastMsg(String msg) {
		ToastUtil.showToast(this, msg, Toast.LENGTH_SHORT);
	}

	/**
	 * 设置layout
	 * 
	 * @return
	 */
	protected abstract int setLayout();

	/**
	 * 初始化
	 */
	protected abstract void init();

	/**
	 * 设置监听
	 */
	protected abstract void setListener();
	protected abstract T createPresenter();


	@Override
	public Activity getActivity() {
		return this;
	}

	@Override
	public LifecycleTransformer bindLifecycle() {
		LifecycleTransformer objectLifecycleTransformer = bindToLifecycle();
		return objectLifecycleTransformer;
	}

	private void requestPermissions() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			RxPermissions rxPermission = new RxPermissions(this);
			rxPermission
					.requestEach(Manifest.permission.ACCESS_FINE_LOCATION,
							Manifest.permission.WRITE_EXTERNAL_STORAGE,
							Manifest.permission.READ_EXTERNAL_STORAGE,
							Manifest.permission.BLUETOOTH,
							Manifest.permission.ACCESS_COARSE_LOCATION
					)
					.subscribe(new Consumer<Permission>() {
						@Override
						public void accept(Permission permission) throws Exception {
							if (permission.granted) {
								// 用户已经同意该权限
								LogUtils.e("BaseApp", permission.name + " is granted.");
							} else if (permission.shouldShowRequestPermissionRationale) {
								// 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
								LogUtils.e("BaseApp",  permission.name + " is denied. More info should be provided.");
							} else {
								// 用户拒绝了该权限，并且选中『不再询问』
								LogUtils.e("BaseApp",  permission.name + " is denied.");
							}
						}
					});
		}

	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mPresenter != null) {
			mPresenter.detachView();
		}
		EventBus.getDefault().unregister(this);
	}


	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onLoingEvent(int i) {

	}
}
