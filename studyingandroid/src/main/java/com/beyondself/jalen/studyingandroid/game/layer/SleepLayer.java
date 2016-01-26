package com.beyondself.jalen.studyingandroid.game.layer;

import com.beyondself.jalen.studyingandroid.game.gameutils.CommonUtils;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCTintBy;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.types.ccColor3B;

/**
 * 睡觉的图层
 */
public class SleepLayer extends BaseLayer {
    public SleepLayer() {
        init();
    }

    @Override
    public void judgeSkip() {

    }

    private void init() {
        // 专门显示文字的精灵
        //  参数1  显示的内容 参数2 字体的样式  3 字体的大小
        CCLabel label = CCLabel.labelWithString("深深的沉睡了.....", "hkbd.ttf", 50);
        label.setColor(ccc3(50, 0, 255));
        label.setAnchorPoint(0.5f, 0.5f);
        label.setPosition(mWinWidth / 2, mWinHeight / 2);
        ccColor3B c = ccc3(100, 255, -100);
        // 参数1 时间 参数2 变化后的颜色
        CCTintBy by = CCTintBy.action(1, c);
        CCTintBy reverse = by.reverse();
        CCSequence actions = CCSequence.actions(by, reverse);
        CCDelayTime delayTime = CCDelayTime.action(3);//停留一秒钟
        CCSequence ccSequence = CCSequence.actions(actions, delayTime, CCCallFunc.action(this, "goBack"));
        label.runAction(ccSequence);
        this.addChild(label);
    }
    public void goBack(){
        CommonUtils.changeLayer(new HomeLayer());
    }

}
