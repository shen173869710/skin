package com.embed.skin.custom;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Date： 2016/11/16
 * Description:
 * xml里面定义点击事件
 * @author WangLizhi
 * @version 1.0
 */
public class DeclaredOnClickListener implements View.OnClickListener {
    private final View mHostView;
    private final String mMethodName;

    private Method mMethod;

    public DeclaredOnClickListener(@NonNull View hostView, @NonNull String methodName) {
        mHostView = hostView;
        mMethodName = methodName;
    }

    @Override
    public void onClick(@NonNull View v) {
        if (mMethod == null) {
            mMethod = resolveMethod(mHostView.getContext(), mMethodName);
        }

        try {
            mMethod.invoke(mHostView.getContext(), v);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(
                    "Could not execute non-public method for android:onClick", e);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException(
                    "Could not execute method for android:onClick", e);
        }
    }

    @NonNull
    private Method resolveMethod(@Nullable Context context, @NonNull String name) {
        while (context != null) {
            try {
                if (!context.isRestricted()) {
                    return context.getClass().getMethod(mMethodName, View.class);
                }
            } catch (NoSuchMethodException e) {
                // Failed to find method, keep searching up the hierarchy.
            }

            if (context instanceof ContextWrapper) {
                context = ((ContextWrapper) context).getBaseContext();
            } else {
                // Can't search up the hierarchy, null out and fail.
                context = null;
            }
        }

        final int id = mHostView.getId();
        final String idText = id == View.NO_ID ? "" : " with id '"
                + mHostView.getContext().getResources().getResourceEntryName(id) + "'";
        throw new IllegalStateException("Could not find method " + mMethodName
                + "(View) in a parent or ancestor Context for android:onClick "
                + "attribute defined on view " + mHostView.getClass() + idText);
    }
}
