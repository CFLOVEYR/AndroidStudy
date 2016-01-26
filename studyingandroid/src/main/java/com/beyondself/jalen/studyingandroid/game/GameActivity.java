package com.beyondself.jalen.studyingandroid.game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.beyondself.jalen.studyingandroid.game.layer.GuideLayer;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

public class GameActivity extends AppCompatActivity {
    private CCDirector director;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CCGLSurfaceView surfaceView = new CCGLSurfaceView(this);// 这个位置必须传递Activity
        setContentView(surfaceView);//添加布局

        director = CCDirector.sharedDirector();
        director.attachInView(surfaceView);// 导演与界面发生联系// 开启线程
        //需要在这设置,还需要在清单文件配置..
        director.setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft);// 设置竖屏显示
        director.setDisplayFPS(true);//设置显示FPS
        //	director.setAnimationInterval(1.0f/30);// 锁定帧率  指定一个帧率  向下锁定
        director.setScreenSize(1280, 720);//设置屏幕的大小   可以自动屏幕适配

        //场景
        CCScene ccScene=CCScene.node();// 为了api 和cocos-iphone 一致

        ccScene.setUserData(this);//把自己放数据中
        GuideLayer guideLayer = new GuideLayer();
        guideLayer.setUserData(this);
        ccScene.addChild(guideLayer);//场景添加了图层
        director.runWithScene(ccScene);//  运行场景
    }


    //与Activity的生命周期绑定到一起
    @Override
    protected void onResume() {
        super.onResume();
        director.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        director.onPause();//暂停
        //director.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        director.end();//当销毁的时候开始结束
    }
}
