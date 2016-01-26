package com.beyondself.jalen.studyingandroid.game.layer;

import com.beyondself.jalen.studyingandroid.game.gameutils.CommonUtils;

import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCSprite;

/**
 * 展示城镇的图层
 */
public class ShowTownLayer extends BaseLayer {

    private CCSprite house1;

    public ShowTownLayer() {
        init();
    }

    /**
     * 初始化组件
     */
    private void init() {
        loadMap();
        loadButton();
        loadBottom();
        loadHouses();
        loadPerson(50, mWinHeight / 2);
    }

    private void loadHouses() {
        //房子1
        house1 = CCSprite.sprite("house/house01.png");
        house1.setAnchorPoint(0, 0);
        house1.setScale(0.7f);
        house1.setPosition(mWinWidth / 5, mWinHeight * 3 / 4);
        this.addChild(house1);
        //房子2
        CCSprite house2 = CCSprite.sprite("house/house02.png");
        house2.setAnchorPoint(0,0);
        house2.setScale(0.7f);
        house2.setPosition(mWinWidth*2/3,mWinHeight*3/4);
        this.addChild(house2);

        //房子3
        CCSprite house3 = CCSprite.sprite("house/tent.png");
        house3.setAnchorPoint(0,0);
        house3.setScale(1.5f);
        house3.setPosition(mWinWidth*2/3,mWinHeight/3);
        this.addChild(house3);
    }

    @Override
    public void judgeSkip() {
        person_x = sprite_person.getPosition().x;
        person_y = sprite_person.getPosition().y;
        float bot = mWinHeight  - house1.getContentSize().getHeight() ;
        float left =  house1.getPosition().x;
        float right=left+house1.getContentSize().getWidth();
        //去答题的界面
        if ((person_x>left&&person_x<right)&&(person_y<mWinHeight&&person_y>bot)){
            CommonUtils.changeLayer(new AnswerLayer());
        }

    }


    /**
     * 初始化背景
     */
    private void loadMap() {
        CCTMXTiledMap map = CCTMXTiledMap.tiledMap("town/zhuan2.tmx");
        map.setAnchorPoint(0, 0);
        map.setPosition(0, 0);
        this.addChild(map);
    }

}
