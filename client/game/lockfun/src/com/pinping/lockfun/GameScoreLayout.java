package com.pinping.lockfun;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class GameScoreLayout extends LinearLayout {

	private Context mContext;
	private LayoutInflater mInflater;
	private PopupWindow scorePopWin;

	public GameScoreLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
		initView();
		
	}

	private void initView() {
		// TODO Auto-generated method stub
		mInflater = LayoutInflater.from(mContext);
		//mInflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
		//load game_scoreboard view
		View score_layout = mInflater.inflate(R.layout.view_game_scoreboard,this);
		
		//scorePopWin = new PopupWindow(score_layout,LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		//scorePopWin.setBackgroundDrawable();
		//scorePopWin.showAtLocation(parent, gravity, x, y)
		//scorePopWin.update();
		
	}
	
	

}
