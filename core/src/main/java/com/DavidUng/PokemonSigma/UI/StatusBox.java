package com.DavidUng.PokemonSigma.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

public class StatusBox extends Table {

    private Label name;
    private HPBar hpbar;
    private Label level;

    protected Table uiContainer;

    public StatusBox(Skin skin) {
        super(skin);
        this.setBackground("currentEnemyMon");
        uiContainer = new Table();
        this.add(uiContainer).pad(0f).expand().fill();

        name = new Label("namenull", skin,"battleBoxes");
        level = new Label("levelnull", skin,"battleBoxes");
        uiContainer.add(name).align(Align.left).padTop(10f).padLeft(10f);
        uiContainer.add(level).align(Align.right).padTop(10f).padRight(30f).row();
        hpbar = new HPBar(skin);
        uiContainer.add(hpbar).expand().fill();
    }

    public void setName(String newname) {
        name.setText(newname);
    }
    public void setLevel(int newlevel) {
        level.setText("Lv " +newlevel);
    }

    public void displayHPLeft(float hp){
        this.hpbar.displayHPLeft(hp);
    };
    public HPBar getHPBar() {
        return hpbar;
    }

}
