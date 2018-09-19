package io.trieulh.anim;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.transition.Fade;
import android.view.View;
import android.view.ViewGroup;

public class ScreenAnim {

    public enum AnimType {
        FADE_IN,
        ZOOM_IN,
        SLIDE_IN,
        SLIDE_UP
    }

    public enum AnimOutType {
        FADE_OUT,
        ZOOM_OUT,
        SLIDE_OUT,
        SLIDE_DOWN
    }

    private static void applyInAnim(Activity a, Intent intent, AnimType animType) {
        if (animType == null) {
            slideInAnimation(a, intent);
            return;
        }
        switch (animType) {
            case FADE_IN:
                fadeInAnimation(a, intent);
                break;
            case ZOOM_IN:
                zoomInAnimation(a, intent);
                break;
            case SLIDE_IN:
                slideInAnimation(a, intent);
                break;
            case SLIDE_UP:
                slideUpAnimation(a, intent);
                break;
            default:
                break;
        }
    }

    private static void applyOutAnim(Activity a, AnimOutType animType) {
        if (animType == null) {
            slideOutAnimation(a);
            return;
        }
        switch (animType) {
            case FADE_OUT:
                fadeOutAnimation(a);
                break;
            case ZOOM_OUT:
                zoomOutAnimation(a);
                break;
            case SLIDE_OUT:
                slideOutAnimation(a);
                break;
            case SLIDE_DOWN:
                slideDownAnimation(a);
                break;
            default:
                break;
        }
    }

    private static void applyFragmentAnim(FragmentTransaction ft, AnimType animType) {
        if (animType == null) {
            ft.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in, R.anim.slide_out);
            return;
        }
        switch (animType) {
            case FADE_IN:
                ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
                break;
            case ZOOM_IN:
                ft.setCustomAnimations(R.anim.zoom_in, R.anim.fade_out, R.anim.fade_in, R.anim.zoom_out);
                break;
            case SLIDE_IN:
                ft.setCustomAnimations(R.anim.slide_in, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out);
                break;

            case SLIDE_UP:
                ft.setCustomAnimations(R.anim.slide_up_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_down_out);
                break;
            default:
                break;
        }
    }

    public static void fadeInAnimation(Activity a, Intent intent) {
        intent.putExtra("EXIT_ANIM", AnimOutType.FADE_OUT);
        a.startActivity(intent);
        a.overridePendingTransition(R.anim.fade_in, R.anim.stay);
    }

    public static void fadeOutAnimation(Activity a) {
        a.overridePendingTransition(0, R.anim.fade_out);
    }

    public static void zoomInAnimation(Activity a, Intent intent) {
        intent.putExtra("EXIT_ANIM", AnimOutType.ZOOM_OUT);
        a.startActivity(intent);
        a.overridePendingTransition(R.anim.zoom_in, R.anim.stay);
    }

    public static void zoomOutAnimation(Activity a) {
        a.overridePendingTransition(0, R.anim.zoom_out);
    }

    public static void slideInAnimation(Activity a, Intent intent) {
        intent.putExtra("EXIT_ANIM", AnimOutType.SLIDE_OUT);
        a.startActivity(intent);
        a.overridePendingTransition(R.anim.slide_in, R.anim.stay);
    }

    public static void slideOutAnimation(Activity a) {
        a.overridePendingTransition(0, R.anim.slide_out);
    }

    public static void slideUpAnimation(Activity a, Intent intent) {
        intent.putExtra("EXIT_ANIM", AnimOutType.SLIDE_DOWN);
        a.startActivity(intent);
        a.overridePendingTransition(R.anim.slide_up_in, R.anim.stay);
    }

    public static void slideDownAnimation(Activity a) {
        a.overridePendingTransition(0, R.anim.slide_down_out);
    }

    public static <T extends Activity, T2 extends Activity> void startActivity(T fromActivity, Class<T2> toActivity, AnimType animType) {
        Intent intent = new Intent(fromActivity, toActivity);
        applyInAnim(fromActivity, intent, animType);
    }

    public <T extends Activity, T2 extends Activity> void startActivityWithIntent(T fromActivity, Class<T2> toActivity, Intent intent, AnimType animType) {
        if (intent != null) {
            fromActivity.startActivity(intent);
            applyInAnim(fromActivity, intent, animType);
        }
    }

    public static <T extends Activity> void exitActivity(T activity, AnimOutType animType) {
        activity.finish();
        if (animType != null) {
            applyOutAnim(activity, animType);
        } else {
            AnimOutType exitAnim = (AnimOutType) activity.getIntent().getSerializableExtra("EXIT_ANIM");
            if (exitAnim != null) {
                applyOutAnim(activity, exitAnim);
            } else {
                applyOutAnim(activity, AnimOutType.SLIDE_OUT);
            }
        }
    }

    public static <T extends Fragment, T2 extends Fragment> void replaceFragment(T fromFragment, Class<T2> toFragment, AnimType animType) {
        int parentId = ((ViewGroup) fromFragment.getView().getParent()).getId();
        if (parentId != View.NO_ID) {
            try {
                T2 newFragment = toFragment.newInstance();
                FragmentTransaction ft = fromFragment.getFragmentManager().beginTransaction();
                applyFragmentAnim(ft, animType);
                ft.replace(parentId, newFragment);
                ft.addToBackStack(null);
                ft.commit();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    public static <T extends Fragment, T2 extends Fragment, V extends View> void replaceFragmentWithTransition(T fromFragment, Class<T2> toFragment, AnimType animType,
                                                                                                               V view, String transitionName) {
        int parentId = ((ViewGroup) fromFragment.getView().getParent()).getId();
        if (parentId != View.NO_ID) {
            try {
                T2 newFragment = toFragment.newInstance();

                FragmentTransaction ft = fromFragment.getFragmentManager().beginTransaction();
                applyFragmentAnim(ft, animType);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    newFragment.setSharedElementEnterTransition(new ImageTransitionSet());
                    newFragment.setSharedElementReturnTransition(new ImageTransitionSet());
                    ft.addSharedElement(view, transitionName);
                }

                ft.replace(parentId, newFragment);
                ft.addToBackStack(null);
                ft.commitAllowingStateLoss();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

}
