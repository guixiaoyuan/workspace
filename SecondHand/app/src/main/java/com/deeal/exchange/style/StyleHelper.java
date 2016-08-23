package com.deeal.exchange.style;

/**
 * Created by Administrator on 2015/8/18.
 */
public class StyleHelper {
    /**
     * 标题颜色
     */
    private int titleColor;
    /**
     * 按钮背景
     */
    private int buttonSeletor;
    /**
     * 底部选择样式
     */
    private int[] bottomIcons;

    /**
     * 构造类
     * @param style 0：default；1：pink；2：dark；
     */
    public StyleHelper(int style){
        switch (style){
            case 0:
                titleColor = TextColor.bluewColor;
                bottomIcons = BottomIcon.BLUE_BOTTOM_ICON;
                break;
            case 1:
                titleColor = TextColor.pinkColor;
                bottomIcons = BottomIcon.PINK_BOTTOM_ICON;
                break;
            case 2:
                titleColor = TextColor.blackColor;
                bottomIcons = BottomIcon.BLACK_BOTTOM_ICON;
                break;
        }
    }

    public int getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }

    public int getButtonSeletor() {
        return buttonSeletor;
    }

    public void setButtonSeletor(int buttonSeletor) {
        this.buttonSeletor = buttonSeletor;
    }

    public int[] getBottomIcons() {
        return bottomIcons;
    }

    public void setBottomIcons(int[] bottomIcons) {
        this.bottomIcons = bottomIcons;
    }
}
