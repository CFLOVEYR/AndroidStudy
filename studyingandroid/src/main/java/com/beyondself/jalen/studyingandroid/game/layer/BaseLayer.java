package com.beyondself.jalen.studyingandroid.game.layer;


import com.beyondself.jalen.studyingandroid.game.gameutils.CommonUtils;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGSize;

public abstract class BaseLayer extends CCLayer {
    protected CCSprite sprite_person;
    protected float person_x;
    protected float person_y;
    int speed = 100;//移动速度为100
    protected final float mWinHeight;
    protected final float mWinWidth;
    private CCMenuItem item_top;
    protected CCMenuItemSprite item_right;
    private float change_x;
    private float change_y;
    private float personWidth;
    private float personHeight;
    private CCSprite sprite_bottom;

    public BaseLayer() {
        CGSize winSize = CCDirector.sharedDirector().getWinSize();
        mWinHeight = winSize.getHeight();
        mWinWidth = winSize.getWidth();
    }


    /**
     * 血条
     */
    protected void loadBottom() {
        sprite_bottom = CCSprite.sprite("lifebar.png");
        sprite_bottom.setAnchorPoint(0, 0);
        sprite_bottom.setPosition(0, 0);
        this.addChild(sprite_bottom, 1000);
    }

    /**
     * 加载方向键
     */
    protected void loadButton() {
        CCSprite normal_top = CCSprite.sprite("button/top_default.png");
        CCSprite select_top = CCSprite.sprite("button/top_press.png");
        //  菜单 参数1 默认显示的精灵  参数2 选中的时候显示的精灵  参数3 对象  参数4  方法
        CCMenuItem item_top = CCMenuItemSprite.item(normal_top, select_top, this, "topClick");
//        item_top = CCMenuItemSprite.item(normal_top, select_top);
        item_top.setAnchorPoint(0.5f, 0);//设置到锚点为:  最下边的中心

        CCSprite normal_bottom = CCSprite.sprite("button/bottom_default.png");
        CCSprite select_bottom = CCSprite.sprite("button/bottom_press.png");
        //  菜单 参数1 默认显示的精灵  参数2 选中的时候显示的精灵  参数3 对象  参数4  方法
        CCMenuItem item_bottom = CCMenuItemSprite.item(normal_bottom, select_bottom, this, "bottomClick");
//        item_bottom = CCMenuItemSprite.item(normal_bottom, select_bottom);
        item_bottom.setAnchorPoint(0.5f, 1);//设置到锚点为:  最上边的中心

        CCSprite normal_left = CCSprite.sprite("button/left_default.png");
        CCSprite select_left = CCSprite.sprite("button/left_press.png");
        //  菜单 参数1 默认显示的精灵  参数2 选中的时候显示的精灵  参数3 对象  参数4  方法
        CCMenuItemSprite item_left = CCMenuItemSprite.item(normal_left, select_left, this, "leftClick");
//        item_bottom = CCMenuItemSprite.item(normal_bottom, select_bottom);
        item_left.setAnchorPoint(1, 0.5f);//设置到锚点为:  最上边的中心

        CCSprite normal_right = CCSprite.sprite("button/right_default.png");
        CCSprite select_right = CCSprite.sprite("button/right_press.png");
        //  菜单 参数1 默认显示的精灵  参数2 选中的时候显示的精灵  参数3 对象  参数4  方法
        item_right = CCMenuItemSprite.item(normal_right, select_right, this, "rightClick");
//        item_bottom = CCMenuItemSprite.item(normal_bottom, select_bottom);
        item_right.setAnchorPoint(0, 0.5f);//设置到锚点为:  最上边的中心

        CCMenu menu = CCMenu.menu(item_top, item_bottom, item_left, item_right);
        menu.setScale(0.8f);  // 让菜单 缩放
        menu.setPosition(1000, 80);
        menu.setRotation(4.5f);  // 设置了旋转的角度
        this.addChild(menu, 1);// 添加菜单

    }

    /**
     * 向上移动的事件
     */
    public void topClick(Object object) {
        change_y = sprite_person.getPosition().y + speed;
        change_x = sprite_person.getPosition().x;
        judgeEvents();
        CCMoveTo ccMoveTo = CCMoveTo.action(0.5f, CCNode.ccp(change_x, change_y));
        judgeSkip();
        sprite_person.runAction(ccMoveTo);
    }

    /**
     * 向下移动的事件
     */
    public void bottomClick(Object object) {
        change_y = sprite_person.getPosition().y - speed;
        change_x = sprite_person.getPosition().x;
        judgeEvents();
        CCMoveTo ccMoveTo = CCMoveTo.action(0.5f, CCNode.ccp(change_x, change_y));
        judgeSkip();
        sprite_person.runAction(ccMoveTo);
    }

    /**
     * 向左移动的事件
     */
    public void leftClick(Object object) {
        change_y = sprite_person.getPosition().y;
        change_x = sprite_person.getPosition().x - speed;
        sprite_person.setFlipX(false);//旋转
        judgeEvents();
        CCMoveTo ccMoveTo = CCMoveTo.action(0.5f, CCNode.ccp(change_x, change_y));
        judgeSkip();
        sprite_person.runAction(ccMoveTo);
    }


    /**
     * 向右移动的事件
     */
    public void rightClick(Object object) {
        change_y = sprite_person.getPosition().y;
        change_x = sprite_person.getPosition().x + speed;
        sprite_person.setFlipX(true);//旋转
        judgeEvents();
        CCMoveTo ccMoveTo = CCMoveTo.action(0.5f, CCNode.ccp(change_x, change_y));
        judgeSkip();
        sprite_person.runAction(ccMoveTo);

    }

    /**
     * 判断事件
     */
    private void judgeEvents() {
        outOfBoundsProblems();
        changePersonState();
    }

    /**
     * 越界问题的解决
     */
    private void outOfBoundsProblems() {
        //不能越界的判断
        if (change_x < personWidth / 2) {
            change_x = personWidth / 2;
        }
        if (change_x >= mWinWidth - personWidth / 2) {
            change_x = mWinWidth - personWidth / 2;
        }
        if (change_y >= mWinHeight - personHeight) {
            change_y = mWinHeight - personHeight;
        }
        if (change_y < sprite_bottom.getContentSize().getHeight()) {
            change_y = sprite_bottom.getContentSize().getHeight();
        }
    }

    /**
     * 改变人物状态
     */
    private void changePersonState() {
        CCAction animate = CommonUtils.getAnimate("person/person%02d.png", 8, false);
        sprite_person.runAction(animate);
    }

    /**
     * 加载主角
     */
    protected void loadPerson(float x, float y) {
        sprite_person = new CCSprite("person/person01.png");
        sprite_person.setAnchorPoint(0.5f, 0);//设置锚点
        sprite_person.setScale(1.3f);//扩大两倍数
        sprite_person.setPosition(x, y);
        personWidth = sprite_person.getContentSize().getWidth();
        personHeight = sprite_person.getContentSize().getHeight();
        this.addChild(sprite_person, 1);
    }

    /**
     * 跳转到下一页面的逻辑,让子类自己去实现
     */
    public abstract void judgeSkip();


}
