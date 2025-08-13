package com.DavidUng.PokemonSigma.UI;

import com.DavidUng.PokemonSigma.battleLogic.BattleMoveModel;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MoveButton extends TextButton {
    private BattleMoveModel moveModel;
    public MoveButton (String text, Skin skin, String style, BattleMoveModel moveModel){
        super(text,skin,style);
        this.moveModel = moveModel;
    }
    public BattleMoveModel getMoveModel() {
        return moveModel;
    }
}
