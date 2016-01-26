package com.beyondself.jalen.studyingandroid.game.layer;

import org.cocos2d.layers.CCTMXTiledMap;

/**
 * =========================================================
 *
 *  版权: 个人开发Mr.Jalen  版权所有(c) ${YEAR}

 *  作者:${USER}

 *  版本: 1.0
 *
 *
 *  创建日期 : ${DATE}  ${HOUR}:${MINUTE}
 *
 *
 *  邮箱：Studylifetime@sina.com
 *
 *  描述:
 *          答题的图层
 *
 *  修订历史:
 *
 * =========================================================
 */
public class AnswerLayer extends BaseLayer {

    private CCTMXTiledMap map;

    public AnswerLayer(){
        init();
    }

    /**
     * 初始化图层
     */
    private void init() {
        loadMap();
    }
    /**
     * 加载地图
     */
    private void loadMap() {
        map = CCTMXTiledMap.tiledMap("tower/tower.tmx");
        map.setAnchorPoint(0, 0);
        map.setPosition(0, 0);
        this.addChild(map);
    }
    @Override
    public void judgeSkip() {

    }
}
