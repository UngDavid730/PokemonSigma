package com.DavidUng.PokemonSigma.UI;


import com.DavidUng.PokemonSigma.battleLogic.BattleLogic;
import com.DavidUng.PokemonSigma.battleLogic.BattleMoveModel;
import com.DavidUng.PokemonSigma.screens.BattleScene;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.JsonValue;

public class BattleUI extends Group {
    private Table rootTable;
    private Label dialogLabel;
    private Table moveInfo;
    private Label movePP;
    private Label moveDamage;
    private Label moveAccuracy;
    private ImageButton fightButton, pokemonButton, bagButton, runButton;
    private MoveButton m1,m2,m3,m4;
    private Table dialogTable;
    private Table pokemonTable;
    private Table moveTable;
    private Skin skin;
    private Table menuTable;
    public BattleUI(Skin skin) {
        this.skin = skin;
        rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.setBackground(skin.getDrawable("dialogBackground"));
        this.addActor(rootTable);
        //dialog box
        dialogTable = new Table(skin);
        dialogLabel = new Label("", skin);
        dialogTable.setBackground("battleinfobox");
        dialogTable.pad(10f).align(Align.topLeft);
        dialogLabel.setWrap(true);
        dialogTable.add(dialogLabel).width(640-240).height(40);
        //command buttoins
        menuTable = new Table(skin);
        menuTable.defaults().width(110).height(45).padBottom(2f);
        fightButton = new ImageButton(skin, "fight");
        bagButton = new ImageButton(skin, "bag");
        pokemonButton = new ImageButton(skin, "pokemon");
        runButton = new ImageButton(skin, "run");
        menuTable.add(fightButton);
        menuTable.add(bagButton).row();
        menuTable.add(pokemonButton);
        menuTable.add(runButton);
        rootTable.align(Align.bottomLeft);
        rootTable.add(dialogTable);
        rootTable.add(menuTable).right();
        addListeners();
    }

    public void setDialogText(String text) {
        dialogLabel.setText(text);
    }
    private void showCommandTable(){
        rootTable.clear();
        rootTable.add(dialogTable);
        rootTable.add(menuTable).right();
    }
    private void showMoveSelectionTable() {
        moveTable = new Table();
        moveTable.defaults().width(175).height(37.5f).padBottom(1f);
        BattleMoveModel[] moves = BattleLogic.getCurrentPokemon().getMoveset();
        //dialog box
        MoveButton[] buttons = new MoveButton[moves.length];
        int i = 0;
        for (BattleMoveModel move : moves) {
            if(move == null){
                buttons[i] = new MoveButton("", skin, "null",null);
            }
            else{
                buttons[i] = new MoveButton(move.getName(), skin, move.getType().toLowerCase(),move);
            }
            i++;

        }
        int row = 0;
        moveInfo = new Table(skin);
        moveInfo.defaults().width(130).padLeft(10f);
        moveInfo.setBackground("battleinfobox");
        moveInfo.setSize(200, 150);
        moveInfo.setWidth(200);
        moveInfo.setHeight(150);
        Label moveInfoLabelHolder = new Label("",skin);
        moveInfo.add(moveInfoLabelHolder).width(270);
        for(MoveButton button : buttons) {
            System.out.println(button.getMoveModel()==null);
            if(row == 2) {
                moveTable.row();
            }
            moveTable.add(button).padTop(4f);

            if(button.getMoveModel() == null) {
                button.addListener(new InputListener() {
                    @Override
                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                        moveInfo.clear();
                        moveInfo.add(moveInfoLabelHolder).width(270);
                    }

                });
            }else {
                button.addListener(new InputListener() {
                    @Override
                    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                        moveInfo.clear();
                        moveDamage = new Label("POWER: " + button.getMoveModel().getBasePower(), skin);
                        Label moveType = new Label("TYPE: " + button.getMoveModel().getType(), skin);
                        movePP = new Label("PP:" + (button.getMoveModel().getCurrentPp()) + "/" + button.getMoveModel().getPp(), skin);
                        moveAccuracy = new Label("ACCURACY: " + button.getMoveModel().getAccuracy(), skin);
                        moveInfo.add(moveType).padTop(4f).left();
                        moveInfo.add(moveDamage).padTop(4f).right().row();
                        moveInfo.add(movePP).padTop(4f).left();
                        moveInfo.add(moveAccuracy).padTop(4f).right().row();
                    }

                    @Override
                    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                        moveInfo.clear();
                        moveInfo.add(moveInfoLabelHolder).width(270);
                    }

                });
                button.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        BattleScene.getBattleLogic().setPokemonMove(button.getMoveModel());
                        BattleScene.getBattleLogic().setPlayerAction(BattleLogic.Action.MOVE);
                        showCommandTable();

                    }
                });
            }
            row++;
        }
        rootTable.add(moveTable);
        rootTable.add(moveInfo).right();
        rootTable.align(Align.bottomLeft);
    }
    private void addListeners() {
        fightButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                rootTable.clear();
                showMoveSelectionTable();
            }
        });
        bagButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                dialogLabel.setText("You opened your bag.");
            }
        });
        pokemonButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                dialogLabel.setText("Choose another POKEMON.");
            }
        });
        runButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                BattleScene.setbattleOver(true);
                dialogLabel.setText("You ran away safely!");
            }
        });





    }








}
