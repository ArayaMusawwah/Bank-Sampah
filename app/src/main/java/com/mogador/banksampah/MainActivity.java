package com.mogador.banksampah;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "bank_sampah_prefs";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    private final DashboardFragment dashboardFragment = new DashboardFragment();
    private final SetoranFragment setoranFragment = new SetoranFragment();
    private final AnggotaFragment anggotaFragment = new AnggotaFragment();
    private Fragment activeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, 0);
            return insets;
        });

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_logout) {
                logout();
                return true;
            }
            return false;
        });
        toolbar.inflateMenu(R.menu.menu_main);

        if (savedInstanceState == null) {
            activeFragment = dashboardFragment;
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainer, anggotaFragment, "anggota").hide(anggotaFragment)
                    .add(R.id.fragmentContainer, setoranFragment, "setoran").hide(setoranFragment)
                    .add(R.id.fragmentContainer, dashboardFragment, "dashboard")
                    .commit();
        } else {
            Fragment found = getSupportFragmentManager().findFragmentByTag("dashboard");
            if (found != null) activeFragment = found;
        }

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_dashboard) {
                switchFragment(dashboardFragment);
                toolbar.setTitle(R.string.app_name);
                return true;
            } else if (id == R.id.nav_setoran) {
                switchFragment(setoranFragment);
                toolbar.setTitle(R.string.greeting);
                return true;
            } else if (id == R.id.nav_anggota) {
                switchFragment(anggotaFragment);
                toolbar.setTitle(R.string.nav_anggota);
                return true;
            }
            return false;
        });
    }

    private void switchFragment(Fragment target) {
        if (target == activeFragment) return;
        getSupportFragmentManager().beginTransaction()
                .hide(activeFragment)
                .show(target)
                .commit();
        activeFragment = target;
    }

    private void logout() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.btn_logout)
                .setMessage("Yakin ingin keluar?")
                .setPositiveButton(R.string.btn_yes, (dialog, which) -> {
                    SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                    prefs.edit().putBoolean(KEY_IS_LOGGED_IN, false).apply();
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton(R.string.btn_no, null)
                .show();
    }
}
