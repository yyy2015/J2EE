package edu.nju.studentgrade.business;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yyy on 2017/1/3.
 */
public class ScoreListBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private List scoreList;

    public List getScoreList() {
        return scoreList;
    }

    public void setScoreList(List scoreList) {
        this.scoreList = scoreList;
    }




}
