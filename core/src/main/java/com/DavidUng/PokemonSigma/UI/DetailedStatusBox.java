package com.DavidUng.PokemonSigma.UI;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class DetailedStatusBox extends StatusBox {

    private Label hpText;

    public DetailedStatusBox(Skin skin) {
        super(skin);

        hpText = new Label("NaN/NaN", skin);
        uiContainer.row();
        this.setBackground(skin.getDrawable("currentPlayerMon"));
        uiContainer.add(hpText);

    }

    public void setHPText(int hpLeft, int hpTotal) {
        hpText.setText(hpLeft+"/"+hpTotal);
    }

}
