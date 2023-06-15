package com.baxom.instastorysegmentbardemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.baxom.instastorysegmentbardemo.databinding.ActivityMainBinding;
import com.bumptech.glide.Glide;

import jp.shts.android.storiesprogressview.StoriesProgressView;

public class MainActivity extends AppCompatActivity implements StoriesProgressView.StoriesListener {

    ActivityMainBinding mBinding;

    private final int[] resources = new int[]{
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_foreground,
    };

    String[] stories = new String[]{
            "https://images.unsplash.com/photo-1620766165457-a8025baa82e0?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8bmF0dXJlJTIwb2YlMjBpbmRpYXxlbnwwfHwwfHw%3D&w=1000&q=80",
            "https://images.pexels.com/photos/1172064/pexels-photo-1172064.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
            "https://wallpaperaccess.com/full/2340965.jpg"
    };

    private int counter = 0;
    long pressTime = 0L;
    long limit = 500L;

    private StoriesProgressView storiesProgressView;
    private ImageView image;

    private final View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // inside on touch method we are
            // getting action on below line.
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    // on action down when we press our screen
                    // the story will pause for specific time.
                    pressTime = System.currentTimeMillis();

                    // on below line we are pausing our indicator.
                    storiesProgressView.pause();
                    return false;
                case MotionEvent.ACTION_UP:

                    // in action up case when user do not touches
                    // screen this method will skip to next image.
                    long now = System.currentTimeMillis();

                    // on below line we are resuming our progress bar for status.
                    storiesProgressView.resume();

                    // on below line we are returning if the limit < now - presstime
                    return limit < now - pressTime;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        /*mBinding = ActivityMainBinding.inflate(LayoutInflater.from(getApplicationContext()), null);
        setContentView(mBinding.getRoot());

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // on below line we are setting the total count for our stories.
        mBinding.stories.setStoriesCount(resources.length);

        // on below line we are setting story duration for each story.
        mBinding.stories.setStoryDuration(3000L);

        // on below line we are calling a method for set
        // on story listener and passing context to it.
        //mBinding.stories.setStoriesListener(this);

        // below line is use to start stories progress bar.
        mBinding.stories.startStories(counter);*/

        // inside in create method below line is use to make a full screen.
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // on below line we are initializing our variables.
        storiesProgressView = (StoriesProgressView) findViewById(R.id.stories);

        // on below line we are setting the total count for our stories.
        storiesProgressView.setStoriesCount(stories.length);

        // on below line we are setting story duration for each story.
        storiesProgressView.setStoryDuration(3000L);

        // on below line we are calling a method for set
        // on story listener and passing context to it.
        storiesProgressView.setStoriesListener(this);

        // below line is use to start stories progress bar.
        storiesProgressView.startStories(counter);

        // initializing our image view.
        image = (ImageView) findViewById(R.id.image);

        // on below line we are setting image to our image view.
        Glide.with(getApplicationContext())
                .load(stories[counter])
                .into(image);
        //image.setImageResource(resources[counter]);

        // below is the view for going to the previous story.
        // initializing our previous view.
        View reverse = findViewById(R.id.iv_reverse);

        // adding on click listener for our reverse view.
        reverse.setOnClickListener(v -> {
            // inside on click we are
            // reversing our progress view.
            storiesProgressView.reverse();
        });

        // on below line we are calling a set on touch
        // listener method to move towards previous image.
        reverse.setOnTouchListener(onTouchListener);

        // on below line we are initializing
        // view to skip a specific story.
        View skip = findViewById(R.id.iv_skip);
        skip.setOnClickListener(v -> {
            // inside on click we are
            // skipping the story progress view.
            storiesProgressView.skip();
        });
        // on below line we are calling a set on touch
        // listener method to move to next story.
        skip.setOnTouchListener(onTouchListener);

    }

    @Override
    public void onNext() {

        Glide.with(getApplicationContext())
                .load(stories[++counter])
                .into(image);

        //image.setImageURI(stories[++counter]);
    }

    @Override
    public void onPrev() {
        if ((counter - 1) < 0) return;

        // on below line we are setting image to image view
        //image.setImageResource(resources[--counter]);

        Glide.with(getApplicationContext())
                .load(stories[--counter])
                .into(image);
    }

    @Override
    public void onComplete() {

    }

    /*public void showStories() {

        final ArrayList<MyStory> myStories = new ArrayList<>();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

        MyStory story1 = new MyStory(
                "https://media.pri.org/s3fs-public/styles/story_main/public/images/2019/09/092419-germany-climate.jpg?itok=P3FbPOp-"
        );
        myStories.add(story1);

        MyStory story2 = new MyStory(
                "http://i.imgur.com/0BfsmUd.jpg"

        );
        myStories.add(story2);


        MyStory story3 = new MyStory(
                "https://mfiles.alphacoders.com/681/681242.jpg"
        );
        myStories.add(story3);

        new StoryView.Builder(getSupportFragmentManager())
                .setStoriesList(myStories)
                .setStoryDuration(5000)
                //.setTitleText("Hamza Al-Omari")
                //.setSubtitleText("Damascus")
                .setStoryClickListeners(new StoryClickListeners() {
                    @Override
                    public void onDescriptionClickListener(int position) {
                        //Toast.makeText(MainActivity.this, "Clicked: " + myStories.get(position).getDescription(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onTitleIconClickListener(int position) {
                    }
                })
                .setOnStoryChangedCallback(position -> {
                    //Toast.makeText(MainActivity.this, position + "", Toast.LENGTH_SHORT).show();
                })
                .setStartingIndex(0)
                //.setRtl(true)
                .build()
                .show();

    }*/
}
