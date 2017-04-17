package com.example.testgame;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by 王国晟 on 2017/4/17.
 */
public class MainActivity extends Activity{
    Game1 game;
    Button restart;
    Button button[] = new Button[7];
    int bid[] = {R.id.b1,R.id.b2,R.id.b3,R.id.b4,R.id.b5,R.id.b6,R.id.b7};
    int gamer;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        gamer = 1;
        game = (Game1)findViewById(R.id.game);
        for(int i = 0;i < 7;i++){
            button[i] = (Button)findViewById(bid[i]);
        }
        restart = (Button)findViewById(R.id.restart);
        button[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(game.pawnDown(1, gamer))
                gamer = gamer == 1 ? 2 : 1;
            }
        });
        button[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(game.pawnDown(2, gamer))
                gamer = gamer == 1?2:1;
            }
        });
        button[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(game.pawnDown(3, gamer))
                gamer = gamer == 1?2:1;
            }
        });
        button[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (game.pawnDown(4, gamer))
                    gamer = gamer == 1 ? 2 : 1;
            }
        });
        button[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (game.pawnDown(5, gamer))
                    gamer = gamer == 1 ? 2 : 1;
            }
        });
        button[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(game.pawnDown(6,gamer))
                    gamer = gamer == 1?2:1;
            }
        });
        button[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(game.pawnDown(7,gamer))
                    gamer = gamer == 1?2:1;
            }
        });
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.restart();
                gamer = 1;
            }
        });
    }

}
