package com.beyondself.jalen.studyingandroid.game.layer;

import com.beyondself.jalen.studyingandroid.game.gameutils.CommonUtils;

import org.cocos2d.actions.interval.CCRotateTo;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCSprite;

/**
 * 游戏开始界面
 */
public class HomeLayer extends BaseLayer {

    private CCTMXTiledMap map;
    private CCSprite sprite_carpet;
    private CCSprite sprite_bed;

    public HomeLayer() {
        init();
    }

    private void init() {
        loadMap();
        loadButton();
        loadBottom();
        loadFurnitures();
        loadPerson(mWinWidth / 2, mWinHeight / 2);
        //点击事件
        this.setIsTouchEnabled(true);
    }
    /**跳转的逻辑*/
    @Override
    public void judgeSkip() {
        person_x = sprite_person.getPosition().x;
        person_y = sprite_person.getPosition().y;
        float bot = mWinHeight / 2 - sprite_carpet.getContentSize().getHeight() / 2;
        float top = mWinHeight / 2 + sprite_carpet.getContentSize().getHeight() / 2;
        //去城镇的判断
        if (person_x <=sprite_person.getContentSize().getWidth()/2&&person_y<top&&person_y>bot) {
            CommonUtils.changeLayer(new ShowTownLayer());
        }
        float bedwidth = sprite_bed.getContentSize().getWidth();
        float bedheight = sprite_bed.getContentSize().getHeight();
        //去床上休息的判断
        if ((person_x>mWinWidth-bedwidth/2-sprite_person.getContentSize().getWidth()/2)&&(person_y<mWinHeight/2+bedheight/2)&&(person_y>mWinHeight/2-bedheight/2)){
            CommonUtils.changeLayer(new SleepLayer());
        }
    }


    /**
     * 加载家具
     */
    private void loadFurnitures() {
        //床
        sprite_bed = CCSprite.sprite("house/bed.png");
        sprite_bed.setAnchorPoint(1, 0.5f);//设置锚点
        sprite_bed.setScale(1.5f);//扩大两倍数
        sprite_bed.setPosition(mWinWidth, mWinHeight / 2);
        //地毯
        sprite_carpet = CCSprite.sprite("house/carpet.png");
        sprite_carpet.setAnchorPoint(0.5f, 0);//设置锚点
        sprite_carpet.runAction(CCRotateTo.action(0, 90));//旋转90度
        sprite_carpet.setScale(1.5f);//扩大两倍数
        sprite_carpet.setPosition(0, mWinHeight / 2);
        this.addChild(sprite_bed);
        this.addChild(sprite_carpet);
    }

    /**
     * 加载地图
     */
    private void loadMap() {
        map = CCTMXTiledMap.tiledMap("game1.tmx");
        map.setAnchorPoint(0, 0);
        map.setPosition(0, 0);
        this.addChild(map);
    }


//    @Override
//    public boolean ccTouchesBegan(MotionEvent event) {
//        CGPoint point = this.convertTouchToNodeSpace(event);
//        CGRect top = item_top.getBoundingBox();
//        CGRect bottom = item_bottom.getBoundingBox();
//        //如果是点击了向上移动
//        if (CGRect.containsPoint(top,point)) {
//            Log.e("Test", "向上移动.....");
//        }else if(CGRect.containsPoint(bottom,point)){
//            Log.e("Test", "向下移动.....");
//        }
//
//        return super.ccTouchesBegan(event);
//    }
}
