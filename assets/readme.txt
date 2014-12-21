控件的使用简介

1 CircularProgressButton

<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_margin="32dp"
    android:gravity="center_horizontal"
    android:orientation="vertical">

    <com.dd.CircularProgressButton
        android:id="@+id/circularButton1"
        android:layout_width="196dp"
        android:layout_height="64dp"
        android:layout_marginTop="16dp"
        android:textColor="@color/cpb_white"
        android:textSize="18sp"
        app:cpb_textComplete="@string/Complete"
        app:cpb_textError="@string/Error"
        app:cpb_textIdle="@string/Upload" />

    <com.dd.CircularProgressButton
        android:id="@+id/circularButton2"
        android:layout_width="196dp"
        android:layout_height="64dp"
        android:layout_marginTop="16dp"
        android:textColor="@color/cpb_white"
        android:textSize="18sp"
        app:cpb_textComplete="@string/Complete"
        app:cpb_textError="@string/Error"
        app:cpb_textIdle="@string/Upload" />

</LinearLayout>

 final CircularProgressButton circularButton1 = (CircularProgressButton) findViewById(R.id.circularButton1);
        circularButton1.setIndeterminateProgressMode(true);
        circularButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (circularButton1.getProgress() == 0) {
                    circularButton1.setProgress(50);
                } else if (circularButton1.getProgress() == 100) {
                    circularButton1.setProgress(0);
                } else {
                    circularButton1.setProgress(100);
                }
            }
        });


2 SwipeMenuPullListView

// step 1. create a MenuCreator
		SwipeMenuCreator creator = new SwipeMenuCreator() {
			@Override
			public void create(SwipeMenu menu) {
				// create "open" item
				SwipeMenuItem openItem = new SwipeMenuItem(getApplicationContext());
				// set item background
				openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,0xCE)));
				// set item width
				openItem.setWidth(dp2px(90));
				// set item title
				openItem.setTitle("Open");
				// set item title fontsize
				openItem.setTitleSize(18);
				// set item title font color
				openItem.setTitleColor(Color.WHITE);
				// add to menu
				menu.addMenuItem(openItem);

				// create "delete" item
				SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
				// set item background
				deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,0x3F, 0x25)));
				// set item width
				deleteItem.setWidth(dp2px(90));
				// set a icon
				deleteItem.setIcon(R.drawable.ic_delete);
				// add to menu
				menu.addMenuItem(deleteItem);
			}
		};
		// set creator
		mListView.setMenuCreator(creator);

		// step 2. listener item click event
		mListView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
				ApplicationInfo item = mAppList.get(position);
				switch (index) {
				case 0:
					// open
					open(item);
					break;
				case 1:
					// delete
//					delete(item);
					mAppList.remove(position);
					mAdapter.notifyDataSetChanged();
					break;
				}
				return false;
			}
		});
		
		
// step 1. 不同的列表对应不用的  MenuCreator
		SwipeMenuCreator creator = new SwipeMenuCreator() {
			@Override
			public void create(SwipeMenu menu) {
				// Create different menus depending on the view type
				switch (menu.getViewType()) {
				case 0:
					createMenu1(menu);
					break;
				case 1:
					createMenu2(menu);
					break;
				case 2:
					createMenu3(menu);
					break;
				}
			}

			private void createMenu1(SwipeMenu menu) {
				SwipeMenuItem item1 = new SwipeMenuItem(
						getApplicationContext());
				item1.setBackground(new ColorDrawable(Color.rgb(0xE5, 0x18,
						0x5E)));
				item1.setWidth(dp2px(90));
				item1.setIcon(R.drawable.ic_action_favorite);
				menu.addMenuItem(item1);
				SwipeMenuItem item2 = new SwipeMenuItem(
						getApplicationContext());
				item2.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
						0xCE)));
				item2.setWidth(dp2px(90));
				item2.setIcon(R.drawable.ic_action_good);
				menu.addMenuItem(item2);
			}
			
			private void createMenu2(SwipeMenu menu) {
				SwipeMenuItem item1 = new SwipeMenuItem(
						getApplicationContext());
				item1.setBackground(new ColorDrawable(Color.rgb(0xE5, 0xE0,
						0x3F)));
				item1.setWidth(dp2px(90));
				item1.setIcon(R.drawable.ic_action_important);
				menu.addMenuItem(item1);
				SwipeMenuItem item2 = new SwipeMenuItem(
						getApplicationContext());
				item2.setBackground(new ColorDrawable(Color.rgb(0xF9,
						0x3F, 0x25)));
				item2.setWidth(dp2px(90));
				item2.setIcon(R.drawable.ic_action_discard);
				menu.addMenuItem(item2);
			}
			
			private void createMenu3(SwipeMenu menu) {
				SwipeMenuItem item1 = new SwipeMenuItem(
						getApplicationContext());
				item1.setBackground(new ColorDrawable(Color.rgb(0x30, 0xB1,
						0xF5)));
				item1.setWidth(dp2px(90));
				item1.setIcon(R.drawable.ic_action_about);
				menu.addMenuItem(item1);
				SwipeMenuItem item2 = new SwipeMenuItem(
						getApplicationContext());
				item2.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
						0xCE)));
				item2.setWidth(dp2px(90));
				item2.setIcon(R.drawable.ic_action_share);
				menu.addMenuItem(item2);
			}
		};
		// set creator
		listView.setMenuCreator(creator);
		
		
// step2   重写adapter的时候，需要重写以下两个函数	
		@Override
		public int getViewTypeCount() {
			// menu type count
			return 3;
		}
		
		@Override
		public int getItemViewType(int position) {
			// current menu type
			return position % 3;
		}


3 FancyButton 不同状态的button库

<ScrollView xmlns:tools="http://schemas.android.com/tools"
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:fancy="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    tools:context="mehdi.sakout.fancybuttons.MainActivity$PlaceholderFragment" >

    <LinearLayout
        android:id="@+id/section_facebook"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="#3b5998"
        android:gravity="center_horizontal"
        android:orientation="vertical"
        android:padding="20dp" >

        <mehdi.sakout.fancybuttons.FancyButton
            android:id="@+id/btn_facebook_like"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginBottom="10dp"
            android:padding="10dp"
            fancy:borderColor="#FFFFFF"
            fancy:borderWidth="1dp"
            fancy:defaultColor="#3b5998"
            fancy:focusColor="#5577bd"
            fancy:fontIconResource="@string/icon_like"
            fancy:fontIconSize="10sp"
            fancy:iconPosition="right"
            fancy:radius="30dp"
            fancy:text="Like my facebook page"
            fancy:textColor="#FFFFFF" />
    </LinearLayout>

</ScrollView>		

        FancyButton facebookLoginBtn = new FancyButton(this);
        facebookLoginBtn.setText("Login with Facebook");
        facebookLoginBtn.setBackgroundColor(Color.parseColor("#3b5998"));
        facebookLoginBtn.setFocusBackgroundColor(Color.parseColor("#5474b8"));
        facebookLoginBtn.setTextSize(17);
        facebookLoginBtn.setRadius(5);
        facebookLoginBtn.setIconResource("\uf082");
        facebookLoginBtn.setIconPosition(FancyButton.POSITION_LEFT);
        facebookLoginBtn.setFontIconSize(30);
        
4 ShareImageView  各种形态的ImageView控件
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

  			<com.github.siyamed.shapeimageview.CircularImageView
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:layout_margin="8dp"
                android:layout_weight="1"
                android:src="@drawable/neo"
                app:siBorderWidth="6dp"
                app:siBorderColor="@color/darkgray"/>

            <com.github.siyamed.shapeimageview.RoundedImageView
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:layout_weight="1"
                android:layout_margin="8dp"
                android:src="@drawable/neo"
                app:siRadius="6dp"
                app:siBorderWidth="6dp"
                app:siBorderColor="@color/darkgray"
                app:siSquare="true"/>

            <com.github.siyamed.shapeimageview.BubbleImageView
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:layout_weight="1"
                android:layout_margin="8dp"
                android:src="@drawable/neo"
                app:siSquare="true"/>
</RelativeLayout>



5 SwipeBackActivity 滑动销毁Activity
  1> 如果是Activity可以直接继承SwipeBackActivity即可
  
   mSwipeBackLayout = getSwipeBackLayout();
   mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
   saveTrackingMode(SwipeBackLayout.EDGE_LEFT);
   mSwipeBackLayout.addSwipeListener(new SwipeBackLayout.SwipeListener() {
            @Override
            public void onScrollStateChange(int state, float scrollPercent) {

            }

            @Override
            public void onEdgeTouch(int edgeFlag) {
                vibrate(VIBRATE_DURATION);
            }

            @Override
            public void onScrollOverThreshold() {
                vibrate(VIBRATE_DURATION);
            }
        });
        
      private void vibrate(long duration) {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = { 0, duration};
        vibrator.vibrate(pattern, -1);
    	}
    	
    	
6 NiftyDialogEffect 特效对话框使用
 NiftyDialogBuilder dialogBuilder=NiftyDialogBuilder.getInstance(this);
        dialogBuilder
                .withTitle("Modal Dialog")                                  //.withTitle(null)  no title
                .withTitleColor("#FFFFFF")                                  //def
                .withDividerColor("#11000000")                              //def
                .withMessage("This is a modal Dialog.")                     //.withMessage(null)  no Msg
                .withMessageColor("#FFFFFFFF")                              //def  | withMessageColor(int resid)
                .withDialogColor("#FFE74C3C")                               //def  | withDialogColor(int resid)                               //def
                .withIcon(getResources().getDrawable(R.drawable.icon))
                .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                .withDuration(700)                                          //def
                .withEffect(Effectstype.Fadein)                                         //def Effectstype.Slidetop
                .withButton1Text("OK")                                      //def gone
                .withButton2Text("Cancel")                                  //def gone
                .setCustomView(R.layout.custom_view,v.getContext())         //.setCustomView(View or ResId,context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "i'm btn1", Toast.LENGTH_SHORT).show();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "i'm btn2", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();