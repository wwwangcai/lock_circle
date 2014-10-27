package com.pinping.lockfun;

import java.util.HashMap;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class GameLayout extends LinearLayout {
	
	Context mContext;
	
	private Integer[] imgs = { 
	            R.drawable.game_icon_blue_rabbit_128,
	            R.drawable.game_icon_red_rabbit_128, 
	    }; 
	    
	private int gameState = -1;
	GridView gameGrid = null;

	private LayoutInflater mInflater;


	private int roundCount;
	private int round = 0;

	private Handler mainHandler;
	
	public GameLayout(Context context) {
		super(context);
		mContext = context;
		roundCount = Integer.parseInt( mContext.getResources().getString(R.string.game_round_count));
		// TODO Auto-generated constructor stub
		initView();
		
	}

	public GameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		// TODO Auto-generated constructor stub
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		mInflater = LayoutInflater.from(mContext);
		//load ganme layout
		mInflater.inflate(R.layout.view_game_fanpaizi,this);
		
        gameGrid = (GridView)findViewById(R.id.game_gridview); 
        //ΪGridView���������� 
        gameGrid.setAdapter(new MyAdapter(mContext)); 
        
      //ע������¼� 
        gameGrid.setOnItemClickListener(new OnItemClickListener() 
        { 		

			public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
            { 
                //reverse ImageResource
            	 ImageView imageView = (ImageView) v;
            	 reverseGameIcon(position,imageView);
            	 
            	 //���ϸ���
            	 imageView.invalidate();
             	//judge if game is over
            	 gameState = judgeGameState();
            	 //Log.e("gameState",gameState+"");
            	 if(gameState == 1){
            		 round += 1;
            		 if(round < roundCount)
            			 resetGame();
            		 else
            			 endGame();
                	 //                   
            		 //gameGrid.requestLayout();
            		 //gameAdapter.notifyDataSetChanged();
            	 }
            	}
            }); 

	}	

	
	protected void resetGame() {
		// TODO Auto-generated method stub
		
		String tag = "resetGame";
		Log.e(tag ,"login");
		
		//reset gameState
		gameState = -1;				
		gameGrid.setAdapter(new MyAdapter(mContext)); 
	}
	
	private void endGame() {
		// TODO Auto-generated method stub
		mainHandler.obtainMessage(MainActivity.MSG_GAME_END).sendToTarget();
	}
	
	private int judgeGameState() {
		// TODO Auto-generated method stub
		String tag = "judgeGameState";
		Log.e(tag ,"login");
		
		MyAdapter gameAdapter = (MyAdapter) gameGrid.getAdapter();
		
		return gameAdapter.getGameState();      
	}
    
	
	private void reverseGameIcon(int position,ImageView imageView){
    	String tag = "reverseGameIcon";
    	
		MyAdapter gameAdapter = (MyAdapter) gameGrid.getAdapter();	 
		
   	 	Integer integer = (Integer) imageView.getTag();
   	 	integer = integer == null ? 0 : integer;
   	 	switch(integer){
   	 		case R.drawable.game_icon_blue_rabbit_128:
   	 			//blue
   	 			imageView.setImageResource(imgs[1]);
   	 			imageView.setTag(imgs[1]);
   	 			//gameAdapter.notifyDataSetChanged();
   	 			Log.e("reverseGameIcon.hashmap","pos:"+position+"|value:"+imgs[1]);   
   	 			gameAdapter.hashmap.put(position, imgs[1]);
   	 			break;
   	 		case R.drawable.game_icon_red_rabbit_128:
   	 			imageView.setImageResource(imgs[0]);
   	 			imageView.setTag(imgs[0]);
   	 		
   	 			Log.e("reverseGameIcon.hashmap","pos:"+position+"|value:"+imgs[0]);  
   	 			gameAdapter.hashmap.put(position, imgs[0]);
	 			break;
   	 		default:	
			//nothing to do
   	 			Log.e(tag ,"getTag() is NULL!");
   	 			break;
   	 	}
    }

	public void setMainHandler(Handler mHandler) {
		// TODO Auto-generated method stub
		mainHandler = mHandler;
		
	}

}

//�Զ��������� 
class MyAdapter extends BaseAdapter{ 
    //�����Ķ��� 
    private Context mContext; 
    
    //ͼƬ���� 
    private Integer[] imgs = { 
            R.drawable.game_icon_blue_rabbit_128,
            R.drawable.game_icon_red_rabbit_128  
    };
    
   //��¼position->data֮���ӳ��        
   public HashMap<Integer , Integer> hashmap = new HashMap<Integer , Integer>();
   
    MyAdapter(Context context){ 
    	Log.e("MyAdapter","MyAdapter construct!");
        mContext = context; 
        hashmap.clear();
        
       // gameGrid = mContext.getResources().getLayout(R.layout)(GridView)findViewById(R.id.game_gridview);
    } 
    
    public int getCount() {
    	int item_count = Integer.parseInt( 
    			mContext.getResources().getString(R.string.game_item_number));
        return item_count; 
    } 

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		
		return hashmap.get(position);
	} 

    public long getItemId(int id) { 
        return id; 
        
    } 
     
   /*
    * RETURN:
    * 0 : blue/red��������;
    * 1 : blue/redֻ��һ��
    * 2 : blue/red������
    * 
    */
    public int getGameState(){
    	
		String tag = "getGameState";
		//ͬʱ��������ͼƬ
		if( hashmap.values().contains(imgs[0] )
				&& hashmap.values().contains(imgs[1]) ){
			Log.e(tag ,hashmap.toString());
			Log.e(tag,"both red/blue");
			return 2;
		}
		
		//ȫ��Ϊ�գ��쳣����
		if(!( hashmap.values().contains(imgs[0]) 
					|| hashmap.values().contains(imgs[1]))){
			Log.e(tag,"none red/blue");
			//empty
			return 0;
		}
		
    	return 1;
    }
    
    //����View���� 
    public View getView(int position, View convertView, ViewGroup parent) { 
    	Log.e("getView","login");
    	
         ImageView imageView; 
            if (convertView == null) { 
                imageView = new ImageView(mContext); 
                imageView.setLayoutParams(new GridView.LayoutParams(128, 128));//����ImageView���󲼾� 
                imageView.setAdjustViewBounds(false);//���ñ߽���� 
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);//���ÿ̶ȵ����� 
                //imageView.setPadding(4, 4, 4, 4);//���ü�� 
                
            }  
            else { 
                imageView = (ImageView) convertView; 
            } 
            
            //GridView��getView��position 0��ε�������
            if(parent.getChildCount() == position)  {              	
				Log.e("getView","1");
				
				//��������ʱ����Ӧ��ε���getView��ʼ��position 0������
				if( 0 == position && hashmap.keySet().contains(position)){
					return imageView;
				}
				
				int game_item_number = Integer.parseInt(
						mContext.getResources().getString(R.string.game_item_number));
				
				//���position����Ƿ����ȫ�ջ���һ����ɫ�����������
				if( (game_item_number -1) == position ){
						int state = getGameState();
						
						if ( 2 == state ){
							//nothing todo
						}
						
						if ( 0 == state){
							imageView.setImageResource(imgs[1]);
	                    	imageView.setTag(imgs[1]);
	                    	hashmap.put(position,imgs[1]);
	                    	return imageView;
						}
						
						if( 1 == state ){
					
							if ( hashmap.containsValue(imgs[0])){
								imageView.setImageResource(imgs[1]);
		                    	imageView.setTag(imgs[1]);
		                    	Log.e("getView.hashmap","pos:"+position+"|value:"+imgs[1]);           	 
		                    	hashmap.put(position,imgs[1]); 	
							}else if ( hashmap.containsValue(imgs[1]) ){
								imageView.setImageResource(imgs[0]);
		                    	imageView.setTag(imgs[0]);
		                    	Log.e("getView.hashmap","pos:"+position+"|value:"+imgs[0]);           	 
		                    	hashmap.put(position,imgs[0]); 	
							}
							imageView.setVisibility(View.VISIBLE);
							return imageView;
						}
					
				}
				
				
                //�������
                int rand_index = (int) ((Math.random()*100));
                
                //��һ��map����¼ÿһ��λ�õ�data
                
                if (rand_index >= 50){
               	 imageView.setVisibility(View.INVISIBLE);
               	 Log.e("getView.hashmap","pos:"+position+"|value:-1");   
               	 hashmap.put(position, -1);             	 

                }else{
                	imageView.setVisibility(View.VISIBLE);
                	//ΪImageView����ͼƬ��Դ ,���뱣֤����һ������ͬ
                	 imageView.setImageResource(imgs[rand_index%(imgs.length)]);
                	 imageView.setTag(imgs[rand_index%(imgs.length)]);
                	 Log.e("getView.hashmap","pos:"+position+"|value:"+imgs[rand_index%(imgs.length)]);           	 
                	 hashmap.put(position,imgs[rand_index%(imgs.length)]);                  	 
                }        
                
                return imageView;   
            }
            else{

            	Log.e("getView","2");
            	//������Ƕ�μ��ص����⣬���Բ������������ ���룬 
            	return imageView;              	
            }                    
    }
}