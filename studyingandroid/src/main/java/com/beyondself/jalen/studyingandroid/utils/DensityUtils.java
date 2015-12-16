package com.beyondself.jalen.studyingandroid.utils;

import android.content.Context;

/**
 * 屏幕适配:dp与px的转化的类
 */
public class DensityUtils {

	/**
	 * dp->px
	 * @param ctx 传入的对象
	 * @param dp 要转化的px
	 * @return 返回的是转化后的px
	 */
	public static int dp2px(Context ctx, float dp) {
		float density = ctx.getResources().getDisplayMetrics().density;
		int px = (int) (dp * density + 0.5f);// 4.9->5 4.4->4

		return px;
	}

	/**
	 * px->dp
	 * @param ctx 传入的对象
	 * @param px 要转化的px
	 * @return 返回的是转化后的dp
	 */
	public static float px2dp(Context ctx, int px) {
		float density = ctx.getResources().getDisplayMetrics().density;
		float dp = px / density;

		return dp;
	}
}
