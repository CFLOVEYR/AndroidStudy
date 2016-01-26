package com.beyondself.jalen.studyingandroid.game.layer;

import android.util.Log;

import com.beyondself.jalen.studyingandroid.game.gameutils.CommonUtils;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.instant.CCHide;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCSprite;

/**
 * 故事介绍的界面
 */
public class GuideLayer extends BaseLayer {
    public GuideLayer() {
        init();
    }

    @Override
    public void judgeSkip() {

    }

    private void init() {
        //故事剧情介绍
        showStory();
        Log.e("Test",this.getUserData()+"");
    }

    private void showStory() {
        CCSprite logo1 = CCSprite.sprite("guide/guide01.jpg");
        logo1.setPosition(mWinWidth / 2, mWinHeight / 2);
        this.addChild(logo1);//添加到图层
        // 让logo执行动作
        CCHide ccHide = CCHide.action();// 隐藏
        CCDelayTime delayTime = CCDelayTime.action(1);//停留一秒钟
        CCSequence ccSequence = CCSequence.actions(delayTime, ccHide, CCCallFunc.action(this, "jump2"));
        logo1.runAction(ccSequence);
    }

    // 当第一个页面完成后跳转到第二个页面
    public void jump2() {
        CCSprite logo2 = CCSprite.sprite("guide/guide02.jpg");
        logo2.setPosition(mWinWidth / 2, mWinHeight / 2);
        this.addChild(logo2);//添加到图层
        // 让logo执行动作
        CCHide ccHide = CCHide.action();// 隐藏
        CCDelayTime delayTime = CCDelayTime.action(1);//停留一秒钟
        CCSequence ccSequence = CCSequence.actions(delayTime, ccHide, CCCallFunc.action(this, "jump3"));
        logo2.runAction(ccSequence);
    }

    // 当第二个页面完成后跳转到第三个页面
    public void jump3() {
        CCSprite logo2 = CCSprite.sprite("guide/guide03.jpg");
        logo2.setPosition(mWinWidth / 2, mWinHeight / 2);
        this.addChild(logo2);//添加到图层
        // 让logo执行动作
        CCHide ccHide = CCHide.action();// 隐藏
        CCDelayTime delayTime = CCDelayTime.action(1);//停留一秒钟
        CCSequence ccSequence = CCSequence.actions(delayTime, ccHide, CCCallFunc.action(this, "jumpMain"));
        logo2.runAction(ccSequence);
    }

    // 当第三个页面完成后跳转到游戏开始页面
    public void jumpMain() {
        CommonUtils.changeLayer(new HomeLayer());
    }
}
