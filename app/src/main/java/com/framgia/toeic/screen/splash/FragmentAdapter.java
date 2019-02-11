package com.framgia.toeic.screen.splash;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.framgia.toeic.R;

public class FragmentAdapter extends PagerAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private onNext mOnNext;

    public FragmentAdapter(Context context) {
        this.mContext = context;
    }

    public int[] images = {
            R.drawable.bg_intro_5,
            R.drawable.bg_intro_6,
            R.drawable.bg_intro_7
    };

    public int[] titles = {
            R.string.title_intro_1,
            R.string.title_intro_2,
            R.string.title_intro_3
    };

    public int[] descriptions = {
            R.string.des_intro_1,
            R.string.des_intro_2,
            R.string.des_intro_3
    };

    public int[] backgroundcolors = {
            R.color.material_red_400,
            R.color.material_brown_600,
            R.color.material_cyan_500
    };

    public int[] lst_button = {
            R.string.button_intro_1,
            R.string.button_intro_1,
            R.string.button_intro_2
    };

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        mInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.fragment_intro, container, false);
        ConstraintLayout layoutIntro = view.findViewById(R.id.layout);
        ImageView imageIntro = view.findViewById(R.id.image_intro);
        TextView titleIntro = view.findViewById(R.id.text_title_intro);
        TextView desIntro = view.findViewById(R.id.text_des_intro);
        final Button buttonIntro = view.findViewById(R.id.button_intro);
        mOnNext = (onNext) mContext;
        buttonIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnNext.position(buttonIntro.getText().toString(), position);
            }
        });
        layoutIntro.setBackgroundResource(backgroundcolors[position]);
        imageIntro.setImageResource(images[position]);
        titleIntro.setText(titles[position]);
        desIntro.setText(descriptions[position]);
        buttonIntro.setText(lst_button[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public interface onNext {
        void position(String s, int i);
    }
}
