package com.beyondself.jalen.studyingandroid.game.engine;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCSprite;

/**
 * 控制方向的类
 */
public class Direction {
    /**
     * 加载方向键
     */
    private static void loadButton(CCLayer layer) {
        CCSprite normal_top = CCSprite.sprite("button/top_default.png");
        CCSprite select_top = CCSprite.sprite("button/top_press.png");
        //  菜单 参数1 默认显示的精灵  参数2 选中的时候显示的精灵  参数3 对象  参数4  方法
        CCMenuItem item_top = CCMenuItemSprite.item(normal_top, select_top, layer, "topClick");
//        item_top = CCMenuItemSprite.item(normal_top, select_top);
        item_top.setAnchorPoint(0.5f, 0);//设置到锚点为:  最下边的中心

        CCSprite normal_bottom = CCSprite.sprite("button/bottom_default.png");
        CCSprite select_bottom = CCSprite.sprite("button/bottom_press.png");
        //  菜单 参数1 默认显示的精灵  参数2 选中的时候显示的精灵  参数3 对象  参数4  方法
        CCMenuItem item_bottom = CCMenuItemSprite.item(normal_bottom, select_bottom, layer, "bottomClick");
//        item_bottom = CCMenuItemSprite.item(normal_bottom, select_bottom);
        item_bottom.setAnchorPoint(0.5f, 1);//设置到锚点为:  最上边的中心

        CCSprite normal_left = CCSprite.sprite("button/left_default.png");
        CCSprite select_left = CCSprite.sprite("button/left_press.png");
        //  菜单 参数1 默认显示的精灵  参数2 选中的时候显示的精灵  参数3 对象  参数4  方法
        CCMenuItemSprite item_left = CCMenuItemSprite.item(normal_left, select_left, layer, "leftClick");
//        item_bottom = CCMenuItemSprite.item(normal_bottom, select_bottom);
        item_left.setAnchorPoint(1, 0.5f);//设置到锚点为:  最上边的中心

        CCSprite normal_right = CCSprite.sprite("button/right_default.png");
        CCSprite select_right = CCSprite.sprite("button/right_press.png");
        //  菜单 参数1 默认显示的精灵  参数2 选中的时候显示的精灵  参数3 对象  参数4  方法
        CCMenuItemSprite item_right = CCMenuItemSprite.item(normal_right, select_right, layer, "rightClick");
//        item_bottom = CCMenuItemSprite.item(normal_bottom, select_bottom);
        item_right.setAnchorPoint(0, 0.5f);//设置到锚点为:  最上边的中心

        CCMenu menu = CCMenu.menu(item_top, item_bottom, item_left, item_right);
        menu.setScale(0.8f);  // 让菜单 缩放
        menu.setPosition(1000, 80);
        menu.setRotation(4.5f);  // 设置了旋转的角度
        layer.addChild(menu);// 添加菜单
    }

//
//    /**
//     * 向上移动的事件
//     */
//    public void topClick(Object object) {
//        Log.e("Test", "向上移动.....");
//        CCMoveTo ccMoveTo = CCMoveTo.action(0.5f, CCNode.ccp(sprite_person.getPosition().x, sprite_person.getPosition().y + speed));
//        judgeSkip();
//        sprite_person.runAction(ccMoveTo);
//    }
//
//    /**
//     * 向左移动的事件
//     */
//    public void leftClick(Object object) {
//        Log.e("Test", "向左移动.....");
//        CCMoveTo ccMoveTo = CCMoveTo.action(0.5f, CCNode.ccp(sprite_person.getPosition().x - speed, sprite_person.getPosition().y));
//        judgeSkip();
//        sprite_person.runAction(ccMoveTo);
//
//    }
//    /**跳转到下一页面的逻辑*/
//    private void judgeSkip() {
//        person_x = sprite_person.getPosition().x;
//        person_y = sprite_person.getPosition().y;
//        float bot = winSize.getHeight() / 2 - sprite_carpet.getScaleY() / 2;
//        float top = winSize.getHeight() / 2 + sprite_carpet.getScaleY() / 2;
//        if (person_x <=0&&person_y<top&&person_y>bot) {
//            CommonUtils.changeLayer(new ShowTownLayer());
//        }
//    }
//
//    /**
//     * 向右移动的事件
//     */
//    public void rightClick(Object object) {
//        Log.e("Test", "向右移动.....");
//        CCMoveTo ccMoveTo = CCMoveTo.action(0.5f, CCNode.ccp(sprite_person.getPosition().x + speed, sprite_person.getPosition().y));
//        judgeSkip();
//        sprite_person.runAction(ccMoveTo);
//
//    }
//
//    /**
//     * 向下移动的事件
//     */
//    public void bottomClick(Object object) {
//        Log.e("Test", "向下移动.....");
//        CCMoveTo ccMoveTo = CCMoveTo.action(0.5f, CCNode.ccp(sprite_person.getPosition().x, sprite_person.getPosition().y - speed));
//        judgeSkip();
//        sprite_person.runAction(ccMoveTo);
//    }
}
