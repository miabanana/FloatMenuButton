package demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.huaying.floatmenu.FloatMenu;
import com.huaying.floatmenu.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        final FloatMenu floatMenu = (FloatMenu) findViewById(R.id.floatMenu);
        floatMenu.setOnItemClickListener(new FloatMenu.OnItemClickListener() {
            @Override
            public void onItemClick(View menuButton) {
                String string = null;
                switch (menuButton.getId()) {
                    case R.id.menu:
                        floatMenu.toggle();
                        string = "menu";
                        break;
                    case R.id.beauty:
                        floatMenu.setMenuButtonBackground(menuButton.getBackground());
                        floatMenu.toggle();
                        string = "beauty";
                        break;
                    case R.id.mask:
                        floatMenu.setMenuButtonBackground(menuButton.getBackground());
                        floatMenu.toggle();
                        string = "mask";
                        break;
                    case R.id.makeup:
                        floatMenu.setMenuButtonBackground(menuButton.getBackground());
                        floatMenu.toggle();
                        string = "makeup";
                        break;
                }

                Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
            }
        });
        floatMenu.setStateUpdateListener(new FloatMenu.OnStateUpdateListener() {
            @Override
            public void onMenuOpened() {
            }

            @Override
            public void onMenuClosed() {
            }
        });
    }
}
