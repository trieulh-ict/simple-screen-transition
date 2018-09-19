package io.trieulh.transitionanim.fragments;


import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.trieulh.anim.ScreenAnim;
import io.trieulh.transitionanim.ActivityB;
import io.trieulh.transitionanim.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentA extends Fragment {


    private View mView;

    @BindView(R.id.img_shared_image)
    ImageButton sharedButton;

    public FragmentA() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_a, container, false);
        ButterKnife.bind(this, mView);
        return mView;
    }

    @OnClick(R.id.btn_start_activity_fade_in)
    public void onClickFadeIn(View v) {
        ScreenAnim.startActivity(getActivity(), ActivityB.class, ScreenAnim.AnimType.FADE_IN);
    }

    @OnClick(R.id.btn_start_activity_zoom_in)
    public void onClickZoomIn(View v) {
        ScreenAnim.startActivity(getActivity(), ActivityB.class, ScreenAnim.AnimType.ZOOM_IN);
    }

    @OnClick(R.id.btn_start_activity_slide_in)
    public void onClickSlideIn(View v) {
        ScreenAnim.startActivity(getActivity(), ActivityB.class, ScreenAnim.AnimType.SLIDE_IN);
    }

    @OnClick(R.id.btn_start_activity_slide_up)
    public void onClickSlideUp(View v) {
        ScreenAnim.startActivity(getActivity(), ActivityB.class, ScreenAnim.AnimType.SLIDE_UP);
    }

    @OnClick(R.id.btn_replace_fragment_fade_in)
    public void onReplaceFadeIn(View v) {
        ScreenAnim.replaceFragment(this, FragmentB.class, ScreenAnim.AnimType.FADE_IN);
    }

    @OnClick(R.id.btn_replace_fragment_zoom_in)
    public void onReplaceZoomIn(View v) {
        ScreenAnim.replaceFragment(this, FragmentB.class, ScreenAnim.AnimType.ZOOM_IN);
    }

    @OnClick(R.id.btn_replace_fragment_slide_in)
    public void onReplaceSlideIn(View v) {
        ScreenAnim.replaceFragment(this, FragmentB.class, ScreenAnim.AnimType.SLIDE_IN);
    }

    @OnClick(R.id.btn_replace_fragment_slide_up)
    public void onReplaceSlideUp(View v) {
        ScreenAnim.replaceFragment(this, FragmentB.class, ScreenAnim.AnimType.SLIDE_UP);
    }

    @OnClick(R.id.btn_replace_fragment_with_transition)
    public void onReplaceFragmentWithSharedElement(View v) {
        ScreenAnim.replaceFragmentWithTransition(this, FragmentB.class, ScreenAnim.AnimType.SLIDE_IN, sharedButton, "sharedImage");
    }
}
