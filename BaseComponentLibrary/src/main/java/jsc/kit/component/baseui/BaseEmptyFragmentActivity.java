package jsc.kit.component.baseui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Empty activity for launching any {@link Fragment}.
 * <p>
 * <p>
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/JSCKit" target="_blank">https://github.com/JustinRoom/JSCKit</a>
 *
 * @author jiangshicheng
 */
public abstract class BaseEmptyFragmentActivity extends AppCompatActivity {

    public final static String EXTRA_FULL_SCREEN = "full_screen";
    public final static String EXTRA_SHOW_ACTION_BAR = "show_action_bar";
    public final static String EXTRA_FRAGMENT_CLASS_NAME = "class_name";
    public final static String EXTRA_TITLE = "title";

    public abstract void initActionBar(ActionBar actionBar);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getBooleanExtra(EXTRA_FULL_SCREEN, false)) {
            //without ActionBar
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            if (getSupportActionBar() != null)
                getSupportActionBar().hide();
            //show full screen
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            if (getIntent().getBooleanExtra(EXTRA_SHOW_ACTION_BAR, true)) {
                initActionBar(getSupportActionBar());
            } else if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
        }

        String className = getIntent().getStringExtra(EXTRA_FRAGMENT_CLASS_NAME);
        if (className != null && className.length() > 0) {
            Class<?> clzz = null;
            Object obj = null;
            try {
                clzz = Class.forName(className);
                obj = clzz.newInstance();
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
            if (obj != null && obj instanceof Fragment) {
                Fragment fragment = (Fragment) obj;
                Bundle arguments = getIntent().getExtras();
                if (arguments != null) {
                    fragment.setArguments(arguments);
                }
                getSupportFragmentManager().beginTransaction().replace(android.R.id.content, fragment).commit();
            }
        }
    }
}