# UI/UX Revamp Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Full UI/UX revamp of Bank Sampah Android app with eco/nature theme and WCAG AA compliance.

**Architecture:** Revamp colors, themes, drawables, layouts, and Java files. Foundation-first approach: colors → themes → drawables → layouts → Java.

**Tech Stack:** Android SDK 29-36, Material Design 3, Java 11, Gradle 9.4.1

## Global Constraints

- Min SDK 29, Target SDK 36, compileSdk 36
- Java 11 (no Kotlin)
- Material Design 3 components only
- All text must meet WCAG AA (4.5:1 for normal text, 3:1 for large text)
- Corner radius: 12dp (cards, buttons, inputs)
- Page padding: 16dp, card padding: 20dp

---

### Task 1: Rewrite Colors (`colors.xml`)

**Files:**
- Modify: `app/src/main/res/values/colors.xml`

**What changes:** Replace entire file with eco/nature palette. All colors WCAG AA tested.

- [ ] **Step 1: Write new colors.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!-- Primary (Forest Green) -->
    <color name="green_900">#FF1B5E20</color>
    <color name="green_700">#FF2E7D32</color>
    <color name="green_500">#FF4CAF50</color>
    <color name="green_100">#FFE8F5E9</color>
    <color name="green_50">#FFF1F8E9</color>

    <!-- Secondary (Earth Brown) -->
    <color name="brown_700">#FF4E342E</color>
    <color name="brown_400">#FF8D6E63</color>
    <color name="brown_100">#FFD7CCC8</color>

    <!-- Surface / Background (Cream) -->
    <color name="cream_50">#FFFFFDE7</color>
    <color name="cream_100">#FFFFF9C4</color>

    <!-- Neutral (Text & UI) -->
    <color name="neutral_0">#FFFFFFFF</color>
    <color name="neutral_50">#FFF8F9FA</color>
    <color name="neutral_100">#FFF1F3F4</color>
    <color name="neutral_200">#FFE9ECEF</color>
    <color name="neutral_300">#FFDEE2E6</color>
    <color name="neutral_400">#FFCED4DA</color>
    <color name="neutral_500">#FF6C757D</color>
    <color name="neutral_700">#FF495057</color>
    <color name="neutral_900">#FF212529</color>

    <!-- Error -->
    <color name="error_700">#FFD32F2F</color>
    <color name="error_50">#FFFFEBEE</color>

    <!-- Semantic aliases for theme -->
    <color name="colorPrimary">@color/green_900</color>
    <color name="colorOnPrimary">@color/neutral_0</color>
    <color name="colorPrimaryContainer">@color/green_100</color>
    <color name="colorOnPrimaryContainer">@color/green_900</color>
    <color name="colorSecondary">@color/brown_700</color>
    <color name="colorOnSecondary">@color/neutral_0</color>
    <color name="colorSurface">@color/cream_50</color>
    <color name="colorOnSurface">@color/neutral_900</color>
    <color name="colorOnSurfaceVariant">@color/neutral_500</color>
    <color name="colorError">@color/error_700</color>
    <color name="colorOnError">@color/neutral_0</color>
    <color name="colorErrorContainer">@color/error_50</color>
</resources>
```

- [ ] **Step 2: Verify build compiles**

Run: `./gradlew assembleDebug`
Expected: BUILD SUCCESSFUL

---

### Task 2: Rewrite Themes (Light + Night)

**Files:**
- Modify: `app/src/main/res/values/themes.xml`
- Modify: `app/src/main/res/values-night/themes.xml`

**What changes:** Map all Material3 attributes to the new color tokens.

- [ ] **Step 1: Write light theme**

```xml
<resources xmlns:tools="http://schemas.android.com/tools">
    <style name="Base.Theme.BankSampah" parent="Theme.Material3.DayNight.NoActionBar">
        <item name="colorPrimary">@color/green_900</item>
        <item name="colorOnPrimary">@color/neutral_0</item>
        <item name="colorPrimaryContainer">@color/green_100</item>
        <item name="colorOnPrimaryContainer">@color/green_900</item>

        <item name="colorSecondary">@color/brown_700</item>
        <item name="colorOnSecondary">@color/neutral_0</item>
        <item name="colorSecondaryContainer">@color/brown_100</item>
        <item name="colorOnSecondaryContainer">@color/brown_700</item>

        <item name="colorTertiary">@color/green_500</item>
        <item name="colorOnTertiary">@color/neutral_0</item>

        <item name="colorError">@color/error_700</item>
        <item name="colorOnError">@color/neutral_0</item>
        <item name="colorErrorContainer">@color/error_50</item>
        <item name="colorOnErrorContainer">@color/error_700</item>

        <item name="android:colorBackground">@color/cream_50</item>
        <item name="colorOnBackground">@color/neutral_900</item>

        <item name="colorSurface">@color/cream_50</item>
        <item name="colorOnSurface">@color/neutral_900</item>
        <item name="colorSurfaceVariant">@color/cream_100</item>
        <item name="colorOnSurfaceVariant">@color/neutral_500</item>
        <item name="colorSurfaceContainer">@color/neutral_0</item>
        <item name="colorSurfaceContainerLow">@color/cream_50</item>
        <item name="colorSurfaceContainerHigh">@color/cream_100</item>

        <item name="colorOutline">@color/neutral_300</item>
        <item name="colorOutlineVariant">@color/neutral_200</item>

        <item name="android:statusBarColor">@color/green_900</item>
        <item name="android:windowLightStatusBar">false</item>
        <item name="android:navigationBarColor">@color/cream_50</item>
        <item name="android:windowLightNavigationBar">true</item>
    </style>

    <style name="Theme.BankSampah" parent="Base.Theme.BankSampah" />

    <style name="Widget.BankSampah.TextField.Outlined" parent="Widget.Material3.TextInputLayout.OutlinedBox">
        <item name="boxCornerRadiusTopStart">12dp</item>
        <item name="boxCornerRadiusTopEnd">12dp</item>
        <item name="boxCornerRadiusBottomStart">12dp</item>
        <item name="boxCornerRadiusBottomEnd">12dp</item>
        <item name="boxStrokeWidth">1dp</item>
        <item name="boxStrokeWidthFocused">2dp</item>
    </style>

    <style name="Widget.BankSampah.Card.Elevated" parent="Widget.Material3.CardView.Elevated">
        <item name="cardCornerRadius">12dp</item>
        <item name="cardElevation">2dp</item>
    </style>

    <style name="Widget.BankSampah.Card.Outlined" parent="Widget.Material3.CardView.Outlined">
        <item name="cardCornerRadius">12dp</item>
        <item name="cardElevation">0dp</item>
        <item name="strokeWidth">1dp</item>
    </style>

    <style name="Widget.BankSampah.Button.Filled" parent="Widget.Material3.Button">
        <item name="cornerRadius">12dp</item>
        <item name="backgroundTint">@color/green_900</item>
    </style>

    <style name="Widget.BankSampah.Button.Outlined" parent="Widget.Material3.Button.OutlinedButton">
        <item name="cornerRadius">12dp</item>
        <item name="strokeColor">@color/green_700</item>
        <item name="strokeWidth">1dp</item>
    </style>
</resources>
```

- [ ] **Step 2: Write night theme**

```xml
<resources xmlns:tools="http://schemas.android.com/tools">
    <style name="Base.Theme.BankSampah" parent="Theme.Material3.DayNight.NoActionBar">
        <item name="colorPrimary">@color/green_700</item>
        <item name="colorOnPrimary">@color/neutral_0</item>
        <item name="colorPrimaryContainer">@color/green_900</item>
        <item name="colorOnPrimaryContainer">@color/green_100</item>

        <item name="colorSecondary">@color/brown_400</item>
        <item name="colorOnSecondary">@color/neutral_900</item>
        <item name="colorSecondaryContainer">@color/brown_700</item>
        <item name="colorOnSecondaryContainer">@color/brown_100</item>

        <item name="colorTertiary">@color/green_500</item>
        <item name="colorOnTertiary">@color/neutral_900</item>

        <item name="colorError">#FFEF5350</item>
        <item name="colorOnError">@color/neutral_900</item>
        <item name="colorErrorContainer">#FFB71C1C</item>
        <item name="colorOnErrorContainer">#FFFFCDD2</item>

        <item name="android:colorBackground">@color/neutral_900</item>
        <item name="colorOnBackground">@color/neutral_0</item>

        <item name="colorSurface">@color/neutral_900</item>
        <item name="colorOnSurface">@color/neutral_0</item>
        <item name="colorSurfaceVariant">#FF2D2D2D</item>
        <item name="colorOnSurfaceVariant">@color/neutral_400</item>
        <item name="colorSurfaceContainer">#FF1E1E1E</item>
        <item name="colorSurfaceContainerLow">@color/neutral_900</item>
        <item name="colorSurfaceContainerHigh">#FF2D2D2D</item>

        <item name="colorOutline">@color/neutral_500</item>
        <item name="colorOutlineVariant">#FF3D3D3D</item>

        <item name="android:statusBarColor">@color/neutral_900</item>
        <item name="android:windowLightStatusBar">false</item>
        <item name="android:navigationBarColor">@color/neutral_900</item>
        <item name="android:windowLightNavigationBar">false</item>
    </style>
</resources>
```

- [ ] **Step 3: Verify build compiles**

Run: `./gradlew assembleDebug`
Expected: BUILD SUCCESSFUL

---

### Task 3: Update Drawables

**Files:**
- Modify: `app/src/main/res/drawable/bg_card.xml`
- Modify: `app/src/main/res/drawable/bg_chip.xml`
- Modify: `app/src/main/res/drawable/bg_search.xml`
- Modify: `app/src/main/res/drawable/bg_spinner.xml`
- Modify: `app/src/main/res/drawable/bg_icon_button.xml`
- Create: `app/src/main/res/drawable/bg_stat_card.xml`
- Create: `app/src/main/res/drawable/bg_icon_button_ripple.xml`

- [ ] **Step 1: Update bg_card.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="@color/neutral_0" />
    <corners android:radius="12dp" />
    <stroke
        android:width="1dp"
        android:color="@color/neutral_200" />
</shape>
```

- [ ] **Step 2: Update bg_chip.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="@color/green_100" />
    <corners android:radius="16dp" />
    <stroke
        android:width="1dp"
        android:color="@color/green_500" />
</shape>
```

- [ ] **Step 3: Update bg_search.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="@color/neutral_0" />
    <corners android:radius="24dp" />
    <stroke
        android:width="1dp"
        android:color="@color/neutral_300" />
</shape>
```

- [ ] **Step 4: Update bg_spinner.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="@color/neutral_0" />
    <corners android:radius="24dp" />
    <stroke
        android:width="1dp"
        android:color="@color/neutral_300" />
    <padding
        android:left="12dp"
        android:right="12dp"
        android:top="8dp"
        android:bottom="8dp" />
</shape>
```

- [ ] **Step 5: Update bg_icon_button.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="@color/green_100" />
    <corners android:radius="18dp" />
</shape>
```

- [ ] **Step 6: Create bg_stat_card.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android"
    android:shape="rectangle">
    <solid android:color="@color/cream_100" />
    <corners android:radius="12dp" />
    <stroke
        android:width="1dp"
        android:color="@color/green_100" />
</shape>
```

- [ ] **Step 7: Create bg_icon_button_ripple.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<ripple xmlns:android="http://schemas.android.com/apk/res/android"
    android:color="@color/green_500">
    <item android:id="@android:id/mask">
        <shape android:shape="rectangle">
            <solid android:color="@color/neutral_0" />
            <corners android:radius="18dp" />
        </shape>
    </item>
    <item>
        <shape android:shape="rectangle">
            <solid android:color="@color/green_100" />
            <corners android:radius="18dp" />
        </shape>
    </item>
</ripple>
```

- [ ] **Step 8: Verify build compiles**

Run: `./gradlew assembleDebug`
Expected: BUILD SUCCESSFUL

---

### Task 4: Rewrite Login Layout

**Files:**
- Modify: `app/src/main/res/layout/activity_login.xml`

**What changes:** Replace manual status bar spacer with proper EdgeToEdge-ready layout.

- [ ] **Step 1: Write new activity_login.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:background="@color/green_900">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:gravity="center"
        android:paddingTop="72dp"
        android:paddingBottom="32dp"
        android:paddingStart="24dp"
        android:paddingEnd="24dp">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="♻"
            android:textSize="56sp"
            android:layout_marginBottom="16dp" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/app_name"
            android:textSize="28sp"
            android:textColor="@color/neutral_0"
            android:textStyle="bold"
            android:layout_marginBottom="8dp" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/brand_tagline"
            android:textSize="14sp"
            android:textColor="@color/neutral_300" />
    </LinearLayout>

    <com.google.android.material.card.MaterialCardView
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        app:cardCornerRadius="32dp"
        app:cardBackgroundColor="@color/neutral_0"
        app:cardElevation="0dp">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical"
            android:padding="24dp">

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="@string/login_title"
                android:textSize="24sp"
                android:textColor="@color/neutral_900"
                android:textStyle="bold"
                android:layout_marginBottom="8dp" />

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="@string/login_subtitle"
                android:textSize="14sp"
                android:textColor="@color/neutral_500"
                android:layout_marginBottom="32dp" />

            <com.google.android.material.textfield.TextInputLayout
                android:id="@+id/tilUsername"
                style="@style/Widget.BankSampah.TextField.Outlined"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:hint="@string/login_username"
                app:startIconDrawable="@android:drawable/ic_menu_myplaces"
                app:startIconTint="@color/green_700"
                android:layout_marginBottom="16dp">

                <com.google.android.material.textfield.TextInputEditText
                    android:id="@+id/etUsername"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:inputType="text"
                    android:maxLines="1" />
            </com.google.android.material.textfield.TextInputLayout>

            <com.google.android.material.textfield.TextInputLayout
                android:id="@+id/tilPassword"
                style="@style/Widget.BankSampah.TextField.Outlined"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:hint="@string/login_password"
                app:endIconMode="password_toggle"
                app:startIconDrawable="@android:drawable/ic_lock_idle_lock"
                app:startIconTint="@color/green_700"
                android:layout_marginBottom="24dp">

                <com.google.android.material.textfield.TextInputEditText
                    android:id="@+id/etPassword"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:inputType="textPassword"
                    android:maxLines="1" />
            </com.google.android.material.textfield.TextInputLayout>

            <com.google.android.material.button.MaterialButton
                android:id="@+id/btnLogin"
                style="@style/Widget.BankSampah.Button.Filled"
                android:layout_width="match_parent"
                android:layout_height="56dp"
                android:text="@string/login_button"
                android:textSize="16sp"
                android:textAllCaps="false" />

            <TextView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="@string/login_hint"
                android:textSize="12sp"
                android:textColor="@color/neutral_500"
                android:gravity="center"
                android:layout_marginTop="16dp" />

        </LinearLayout>
    </com.google.android.material.card.MaterialCardView>
</LinearLayout>
```

- [ ] **Step 2: Verify build compiles**

Run: `./gradlew assembleDebug`
Expected: BUILD SUCCESSFUL

---

### Task 5: Rewrite Main Layout

**Files:**
- Modify: `app/src/main/res/layout/activity_main.xml`

**What changes:** Add stat summary cards, improve search/sort bar, better empty state.

- [ ] **Step 1: Write new activity_main.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:background="@color/cream_50"
    tools:context=".MainActivity">

    <com.google.android.material.appbar.MaterialToolbar
        android:id="@+id/toolbar"
        android:layout_width="match_parent"
        android:layout_height="?attr/actionBarSize"
        android:background="@color/green_900"
        app:title="@string/app_name"
        app:titleTextColor="@color/neutral_0"
        app:popupTheme="@style/Theme.Material3.Light" />

    <!-- Stats Summary -->
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:padding="12dp"
        android:gravity="center">

        <LinearLayout
            android:id="@+id/statAnggota"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:orientation="vertical"
            android:background="@drawable/bg_stat_card"
            android:gravity="center"
            android:padding="12dp"
            android:layout_marginEnd="6dp">

            <TextView
                android:id="@+id/tvStatAnggota"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="0"
                android:textSize="24sp"
                android:textStyle="bold"
                android:textColor="@color/green_900" />

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="@string/stat_total_anggota"
                android:textSize="11sp"
                android:textColor="@color/neutral_500"
                android:layout_marginTop="4dp" />
        </LinearLayout>

        <LinearLayout
            android:id="@+id/statBerat"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:orientation="vertical"
            android:background="@drawable/bg_stat_card"
            android:gravity="center"
            android:padding="12dp"
            android:layout_marginStart="6dp"
            android:layout_marginEnd="6dp">

            <TextView
                android:id="@+id/tvStatBerat"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="0 kg"
                android:textSize="24sp"
                android:textStyle="bold"
                android:textColor="@color/green_900" />

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="@string/stat_total_berat"
                android:textSize="11sp"
                android:textColor="@color/neutral_500"
                android:layout_marginTop="4dp" />
        </LinearLayout>

        <LinearLayout
            android:id="@+id/statSaldo"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:orientation="vertical"
            android:background="@drawable/bg_stat_card"
            android:gravity="center"
            android:padding="12dp"
            android:layout_marginStart="6dp">

            <TextView
                android:id="@+id/tvStatSaldo"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Rp 0"
                android:textSize="24sp"
                android:textStyle="bold"
                android:textColor="@color/green_900" />

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="@string/stat_total_saldo"
                android:textSize="11sp"
                android:textColor="@color/neutral_500"
                android:layout_marginTop="4dp" />
        </LinearLayout>
    </LinearLayout>

    <!-- Search & Sort -->
    <LinearLayout
        android:id="@+id/searchSortContainer"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        android:paddingStart="16dp"
        android:paddingEnd="16dp"
        android:paddingBottom="8dp"
        android:gravity="center_vertical">

        <EditText
            android:id="@+id/etSearch"
            android:layout_width="0dp"
            android:layout_height="48dp"
            android:layout_weight="1"
            android:layout_marginEnd="8dp"
            android:background="@drawable/bg_search"
            android:hint="@string/search_hint"
            android:inputType="text"
            android:maxLines="1"
            android:paddingStart="16dp"
            android:paddingEnd="16dp"
            android:textColor="@color/neutral_900"
            android:textColorHint="@color/neutral_500"
            android:textSize="14sp"
            android:drawableStart="@android:drawable/ic_menu_search"
            android:drawablePadding="8dp"
            android:drawableTint="@color/neutral_500" />

        <Spinner
            android:id="@+id/spinnerSort"
            android:layout_width="wrap_content"
            android:layout_height="48dp"
            android:background="@drawable/bg_spinner"
            android:paddingStart="12dp"
            android:paddingEnd="24dp" />
    </LinearLayout>

    <!-- Empty State -->
    <LinearLayout
        android:id="@+id/emptyStateContainer"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:orientation="vertical"
        android:gravity="center"
        android:visibility="gone">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="🍃"
            android:textSize="64sp"
            android:layout_marginBottom="16dp" />

        <TextView
            android:id="@+id/tvEmpty"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/empty_list"
            android:textSize="16sp"
            android:textStyle="bold"
            android:textColor="@color/neutral_700"
            android:layout_marginBottom="8dp" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@string/empty_hint"
            android:textSize="14sp"
            android:textColor="@color/neutral_500" />
    </LinearLayout>

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/rvSetoran"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:clipToPadding="false"
        android:paddingTop="8dp"
        android:paddingBottom="88dp"
        android:paddingStart="16dp"
        android:paddingEnd="16dp" />

    <com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
        android:id="@+id/fabAdd"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom|end"
        android:layout_margin="16dp"
        android:text="@string/add_title"
        android:textAllCaps="false"
        app:icon="@android:drawable/ic_input_add"
        app:backgroundTint="@color/green_700"
        app:iconTint="@color/neutral_0"
        app:textColor="@color/neutral_0"
        app:layout_anchor="@id/rvSetoran"
        app:layout_anchorGravity="bottom|end" />

</LinearLayout>
```

- [ ] **Step 2: Verify build compiles**

Run: `./gradlew assembleDebug`
Expected: BUILD SUCCESSFUL

---

### Task 6: Rewrite Add/Edit Layout

**Files:**
- Modify: `app/src/main/res/layout/activity_add_edit_setoran.xml`

- [ ] **Step 1: Write new activity_add_edit_setoran.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:background="@color/cream_50">

    <com.google.android.material.appbar.MaterialToolbar
        android:id="@+id/toolbar"
        android:layout_width="match_parent"
        android:layout_height="?attr/actionBarSize"
        android:background="@color/green_900"
        app:titleTextColor="@color/neutral_0"
        app:navigationIcon="@android:drawable/ic_menu_revert"
        app:navigationIconTint="@color/neutral_0" />

    <ScrollView
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:fillViewport="true">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            android:padding="16dp">

            <com.google.android.material.card.MaterialCardView
                style="@style/Widget.BankSampah.Card.Elevated"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginBottom="16dp">

                <LinearLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:orientation="vertical"
                    android:padding="20dp">

                    <TextView
                        android:id="@+id/tvFormTitle"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:textAppearance="@style/TextAppearance.Material3.TitleLarge"
                        android:textColor="@color/green_900"
                        android:layout_marginBottom="20dp" />

                    <com.google.android.material.textfield.TextInputLayout
                        android:id="@+id/tilNama"
                        style="@style/Widget.BankSampah.TextField.Outlined"
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:hint="@string/label_nama"
                        app:startIconDrawable="@android:drawable/ic_menu_agenda"
                        app:startIconTint="@color/green_700"
                        android:layout_marginBottom="16dp">

                        <com.google.android.material.textfield.TextInputEditText
                            android:id="@+id/etNama"
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            android:inputType="textPersonName"
                            android:maxLines="1"
                            android:textAppearance="@style/TextAppearance.Material3.BodyLarge" />
                    </com.google.android.material.textfield.TextInputLayout>

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="@string/label_jenis"
                        android:textAppearance="@style/TextAppearance.Material3.LabelMedium"
                        android:textColor="@color/neutral_700"
                        android:layout_marginBottom="8dp" />

                    <com.google.android.material.card.MaterialCardView
                        android:id="@+id/spinnerCard"
                        style="@style/Widget.BankSampah.Card.Outlined"
                        android:layout_width="match_parent"
                        android:layout_height="56dp"
                        android:layout_marginBottom="16dp">

                        <Spinner
                            android:id="@+id/spinnerJenis"
                            android:layout_width="match_parent"
                            android:layout_height="match_parent"
                            android:paddingStart="16dp"
                            android:paddingEnd="16dp" />
                    </com.google.android.material.card.MaterialCardView>

                    <com.google.android.material.textfield.TextInputLayout
                        android:id="@+id/tilBerat"
                        style="@style/Widget.BankSampah.TextField.Outlined"
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:hint="@string/label_berat"
                        app:suffixText="kg"
                        app:suffixTextColor="@color/green_700"
                        android:layout_marginBottom="16dp">

                        <com.google.android.material.textfield.TextInputEditText
                            android:id="@+id/etBerat"
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            android:inputType="numberDecimal"
                            android:maxLines="1"
                            android:textAppearance="@style/TextAppearance.Material3.BodyLarge" />
                    </com.google.android.material.textfield.TextInputLayout>

                    <com.google.android.material.textfield.TextInputLayout
                        android:id="@+id/tilSaldo"
                        style="@style/Widget.BankSampah.TextField.Outlined"
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:hint="@string/label_saldo"
                        app:prefixText="Rp"
                        app:prefixTextColor="@color/green_700"
                        android:layout_marginBottom="8dp">

                        <com.google.android.material.textfield.TextInputEditText
                            android:id="@+id/etSaldo"
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content"
                            android:inputType="numberDecimal"
                            android:maxLines="1"
                            android:textAppearance="@style/TextAppearance.Material3.BodyLarge" />
                    </com.google.android.material.textfield.TextInputLayout>

                </LinearLayout>
            </com.google.android.material.card.MaterialCardView>

            <com.google.android.material.button.MaterialButton
                android:id="@+id/btnSimpan"
                style="@style/Widget.BankSampah.Button.Filled"
                android:layout_width="match_parent"
                android:layout_height="56dp"
                android:text="@string/btn_simpan"
                android:textAllCaps="false"
                app:icon="@android:drawable/ic_menu_save"
                app:iconTint="@color/neutral_0"
                app:iconGravity="textEnd"
                android:layout_marginBottom="12dp" />

            <com.google.android.material.button.MaterialButton
                android:id="@+id/btnBatal"
                style="@style/Widget.BankSampah.Button.Outlined"
                android:layout_width="match_parent"
                android:layout_height="56dp"
                android:text="@string/btn_batal"
                android:textAllCaps="false"
                app:icon="@android:drawable/ic_menu_close_clear_cancel"
                app:iconTint="@color/green_700"
                app:iconGravity="textEnd" />

        </LinearLayout>
    </ScrollView>
</LinearLayout>
```

- [ ] **Step 2: Verify build compiles**

Run: `./gradlew assembleDebug`
Expected: BUILD SUCCESSFUL

---

### Task 7: Rewrite Item Layout

**Files:**
- Modify: `app/src/main/res/layout/item_setoran.xml`

- [ ] **Step 1: Write new item_setoran.xml**

```xml
<?xml version="1.0" encoding="utf-8"?>
<com.google.android.material.card.MaterialCardView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:cardCornerRadius="12dp"
    app:cardElevation="2dp"
    app:cardBackgroundColor="@color/neutral_0"
    app:strokeColor="@color/neutral_200"
    app:strokeWidth="1dp"
    android:layout_marginBottom="12dp">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="16dp">

        <!-- Header: Name + Chip -->
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal"
            android:gravity="center_vertical">

            <TextView
                android:id="@+id/tvNamaAnggota"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:textSize="16sp"
                android:textColor="@color/neutral_900"
                android:textStyle="bold" />

            <TextView
                android:id="@+id/chipJenis"
                android:layout_width="wrap_content"
                android:layout_height="28dp"
                android:background="@drawable/bg_chip"
                android:gravity="center"
                android:paddingStart="12dp"
                android:paddingEnd="12dp"
                android:textSize="12sp"
                android:textColor="@color/green_700"
                android:textStyle="bold" />
        </LinearLayout>

        <!-- Divider -->
        <View
            android:layout_width="match_parent"
            android:layout_height="1dp"
            android:background="@color/neutral_200"
            android:layout_marginTop="12dp"
            android:layout_marginBottom="12dp" />

        <!-- Stats Row -->
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal"
            android:gravity="center_vertical">

            <!-- Berat -->
            <LinearLayout
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:orientation="vertical">

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="@string/label_berat_short"
                    android:textSize="11sp"
                    android:textColor="@color/neutral_500"
                    android:textAllCaps="true"
                    android:letterSpacing="0.1"
                    android:layout_marginBottom="4dp" />

                <TextView
                    android:id="@+id/tvBerat"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:textSize="16sp"
                    android:textColor="@color/neutral_900"
                    android:textStyle="bold" />
            </LinearLayout>

            <!-- Saldo -->
            <LinearLayout
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:orientation="vertical">

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:text="@string/label_saldo_short"
                    android:textSize="11sp"
                    android:textColor="@color/neutral_500"
                    android:textAllCaps="true"
                    android:letterSpacing="0.1"
                    android:layout_marginBottom="4dp" />

                <TextView
                    android:id="@+id/tvSaldo"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:textSize="16sp"
                    android:textColor="@color/green_700"
                    android:textStyle="bold" />
            </LinearLayout>

            <!-- Action Buttons -->
            <LinearLayout
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:orientation="horizontal">

                <ImageButton
                    android:id="@+id/btnEdit"
                    android:layout_width="40dp"
                    android:layout_height="40dp"
                    android:src="@android:drawable/ic_menu_edit"
                    android:background="@drawable/bg_icon_button_ripple"
                    android:contentDescription="@string/action_edit"
                    android:scaleType="centerInside"
                    android:padding="8dp"
                    android:layout_marginEnd="8dp" />

                <ImageButton
                    android:id="@+id/btnDelete"
                    android:layout_width="40dp"
                    android:layout_height="40dp"
                    android:src="@android:drawable/ic_menu_delete"
                    android:background="@drawable/bg_icon_button_ripple"
                    android:contentDescription="@string/action_delete"
                    android:scaleType="centerInside"
                    android:padding="8dp" />
            </LinearLayout>
        </LinearLayout>
    </LinearLayout>
</com.google.android.material.card.MaterialCardView>
```

- [ ] **Step 2: Verify build compiles**

Run: `./gradlew assembleDebug`
Expected: BUILD SUCCESSFUL

---

### Task 8: Update Java — LoginActivity (EdgeToEdge)

**Files:**
- Modify: `app/src/main/java/com/mogador/banksampah/LoginActivity.java`

**What changes:** Add EdgeToEdge + WindowInsetsCompat for proper status bar handling.

- [ ] **Step 1: Update LoginActivity.java**

```java
package com.mogador.banksampah;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "bank_sampah_prefs";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String DEFAULT_USERNAME = "admin";
    private static final String DEFAULT_PASSWORD = "12345";

    private TextInputLayout tilUsername, tilPassword;
    private TextInputEditText etUsername, etPassword;
    private MaterialButton btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (prefs.getBoolean(KEY_IS_LOGGED_IN, false)) {
            goToMain();
            return;
        }

        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, 0);
            return insets;
        });

        tilUsername = findViewById(R.id.tilUsername);
        tilPassword = findViewById(R.id.tilPassword);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        tilUsername.setError(null);
        tilPassword.setError(null);

        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, getString(R.string.login_error_empty), Toast.LENGTH_SHORT).show();
            if (username.isEmpty()) tilUsername.setError(getString(R.string.login_error_empty));
            if (password.isEmpty()) tilPassword.setError(getString(R.string.login_error_empty));
            return;
        }

        if (username.equals(DEFAULT_USERNAME) && password.equals(DEFAULT_PASSWORD)) {
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            prefs.edit().putBoolean(KEY_IS_LOGGED_IN, true).apply();
            goToMain();
        } else {
            Toast.makeText(this, getString(R.string.login_error_invalid), Toast.LENGTH_SHORT).show();
        }
    }

    private void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
```

- [ ] **Step 2: Verify build compiles**

Run: `./gradlew assembleDebug`
Expected: BUILD SUCCESSFUL

---

### Task 9: Update Java — MainActivity (Stats + Empty State)

**Files:**
- Modify: `app/src/main/java/com/mogador/banksampah/MainActivity.java`

**What changes:** Add stat cards logic, update empty state visibility handling, add EdgeToEdge.

- [ ] **Step 1: Update MainActivity.java**

```java
package com.mogador.banksampah;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SetoranAdapter.OnItemClickListener {

    private static final String PREFS_NAME = "bank_sampah_prefs";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    private RecyclerView rvSetoran;
    private TextView tvEmpty;
    private LinearLayout emptyStateContainer;
    private EditText etSearch;
    private Spinner spinnerSort;
    private TextView tvStatAnggota, tvStatBerat, tvStatSaldo;
    private SetoranAdapter adapter;
    private DatabaseHelper dbHelper;
    private String currentSort = null;
    private String currentQuery = "";

    private final ActivityResultLauncher<Intent> addEditLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> loadData());

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

        dbHelper = new DatabaseHelper(this);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_logout) {
                logout();
                return true;
            }
            return false;
        });
        toolbar.inflateMenu(R.menu.menu_main);

        rvSetoran = findViewById(R.id.rvSetoran);
        tvEmpty = findViewById(R.id.tvEmpty);
        emptyStateContainer = findViewById(R.id.emptyStateContainer);
        etSearch = findViewById(R.id.etSearch);
        spinnerSort = findViewById(R.id.spinnerSort);
        tvStatAnggota = findViewById(R.id.tvStatAnggota);
        tvStatBerat = findViewById(R.id.tvStatBerat);
        tvStatSaldo = findViewById(R.id.tvStatSaldo);
        ExtendedFloatingActionButton fabAdd = findViewById(R.id.fabAdd);

        adapter = new SetoranAdapter(this);
        rvSetoran.setLayoutManager(new LinearLayoutManager(this));
        rvSetoran.setAdapter(adapter);

        setupSortSpinner();
        setupSearch();
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddEditSetoranActivity.class);
            addEditLauncher.launch(intent);
        });

        loadData();
    }

    private void setupSortSpinner() {
        String[] sortLabels = {
                getString(R.string.sort_newest),
                getString(R.string.sort_nama_asc),
                getString(R.string.sort_nama_desc),
                getString(R.string.sort_berat_asc),
                getString(R.string.sort_berat_desc),
                getString(R.string.sort_saldo_asc),
                getString(R.string.sort_saldo_desc)
        };
        ArrayAdapter<String> sortAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, sortLabels);
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSort.setAdapter(sortAdapter);

        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] sortValues = {null, "nama_asc", "nama_desc",
                        "berat_asc", "berat_desc", "saldo_asc", "saldo_desc"};
                currentSort = sortValues[position];
                loadData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentQuery = s.toString().trim();
                loadData();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadData() {
        List<Setoran> list;
        if (currentQuery.isEmpty()) {
            list = dbHelper.getAllSetoran(currentSort);
        } else {
            list = dbHelper.searchSetoran(currentQuery);
        }
        adapter.setData(list);

        boolean isEmpty = list.isEmpty();
        emptyStateContainer.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        rvSetoran.setVisibility(isEmpty ? View.GONE : View.VISIBLE);

        updateStats(list);
    }

    private void updateStats(List<Setoran> list) {
        int totalAnggota = list.size();
        double totalBerat = 0;
        double totalSaldo = 0;

        for (Setoran s : list) {
            totalBerat += s.getBerat();
            totalSaldo += s.getSaldo();
        }

        tvStatAnggota.setText(String.valueOf(totalAnggota));
        tvStatBerat.setText(String.format(Locale.US, "%.1f kg", totalBerat));
        tvStatSaldo.setText(String.format(Locale.US, "Rp %,.0f", totalSaldo));
    }

    @Override
    public void onEditClick(Setoran setoran) {
        Intent intent = new Intent(this, AddEditSetoranActivity.class);
        intent.putExtra(AddEditSetoranActivity.EXTRA_SETORAN_ID, setoran.getId());
        addEditLauncher.launch(intent);
    }

    @Override
    public void onDeleteClick(Setoran setoran) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.confirm_delete_title)
                .setMessage(R.string.confirm_delete_message)
                .setPositiveButton(R.string.btn_yes, (dialog, which) -> {
                    dbHelper.deleteSetoran(setoran.getId());
                    loadData();
                })
                .setNegativeButton(R.string.btn_no, null)
                .show();
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
```

- [ ] **Step 2: Verify build compiles**

Run: `./gradlew assembleDebug`
Expected: BUILD SUCCESSFUL

---

### Task 10: Update Java — AddEditSetoranActivity (EdgeToEdge)

**Files:**
- Modify: `app/src/main/java/com/mogador/banksampah/AddEditSetoranActivity.java`

- [ ] **Step 1: Update AddEditSetoranActivity.java**

```java
package com.mogador.banksampah;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddEditSetoranActivity extends AppCompatActivity {

    public static final String EXTRA_SETORAN_ID = "extra_setoran_id";

    private TextInputLayout tilNama, tilBerat, tilSaldo;
    private TextInputEditText etNama, etBerat, etSaldo;
    private Spinner spinnerJenis;
    private MaterialButton btnSimpan, btnBatal;

    private DatabaseHelper dbHelper;
    private int setoranId = -1;
    private boolean isEditMode = false;

    private final String[] jenisOptions = {
            "Pilih jenis sampah", "Plastik", "Kertas", "Botol Kaca", "Logam", "Organik"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_setoran);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, 0);
            return insets;
        });

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        dbHelper = new DatabaseHelper(this);

        tilNama = findViewById(R.id.tilNama);
        tilBerat = findViewById(R.id.tilBerat);
        tilSaldo = findViewById(R.id.tilSaldo);
        etNama = findViewById(R.id.etNama);
        etBerat = findViewById(R.id.etBerat);
        etSaldo = findViewById(R.id.etSaldo);
        spinnerJenis = findViewById(R.id.spinnerJenis);
        btnSimpan = findViewById(R.id.btnSimpan);
        btnBatal = findViewById(R.id.btnBatal);

        ArrayAdapter<String> jenisAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, jenisOptions);
        jenisAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJenis.setAdapter(jenisAdapter);

        setoranId = getIntent().getIntExtra(EXTRA_SETORAN_ID, -1);

        if (setoranId != -1) {
            isEditMode = true;
            toolbar.setTitle(R.string.edit_title);
            ((android.widget.TextView) findViewById(R.id.tvFormTitle)).setText(R.string.edit_title);
            loadSetoranData();
        } else {
            toolbar.setTitle(R.string.add_title);
            ((android.widget.TextView) findViewById(R.id.tvFormTitle)).setText(R.string.add_title);
        }

        btnSimpan.setOnClickListener(v -> saveData());
        btnBatal.setOnClickListener(v -> finish());
    }

    private void loadSetoranData() {
        Setoran setoran = dbHelper.getSetoranById(setoranId);
        if (setoran != null) {
            etNama.setText(setoran.getNamaAnggota());
            for (int i = 1; i < jenisOptions.length; i++) {
                if (jenisOptions[i].equals(setoran.getJenisSampah())) {
                    spinnerJenis.setSelection(i);
                    break;
                }
            }
            etBerat.setText(String.valueOf(setoran.getBerat()));
            etSaldo.setText(String.valueOf(setoran.getSaldo()));
        }
    }

    private void saveData() {
        tilNama.setError(null);
        tilBerat.setError(null);
        tilSaldo.setError(null);

        String nama = etNama.getText().toString().trim();
        int jenisPos = spinnerJenis.getSelectedItemPosition();
        String beratStr = etBerat.getText().toString().trim();
        String saldoStr = etSaldo.getText().toString().trim();

        boolean valid = true;

        if (nama.isEmpty()) {
            tilNama.setError(getString(R.string.error_nama));
            valid = false;
        }
        if (jenisPos == 0) {
            Toast.makeText(this, getString(R.string.error_jenis), Toast.LENGTH_SHORT).show();
            valid = false;
        }

        double berat = 0;
        double saldo = 0;
        try {
            if (beratStr.isEmpty()) {
                tilBerat.setError(getString(R.string.error_berat));
                valid = false;
            } else {
                berat = Double.parseDouble(beratStr);
                if (berat <= 0) {
                    tilBerat.setError(getString(R.string.error_berat));
                    valid = false;
                }
            }
        } catch (NumberFormatException e) {
            tilBerat.setError(getString(R.string.error_berat));
            valid = false;
        }
        try {
            if (saldoStr.isEmpty()) {
                tilSaldo.setError(getString(R.string.error_saldo));
                valid = false;
            } else {
                saldo = Double.parseDouble(saldoStr);
                if (saldo <= 0) {
                    tilSaldo.setError(getString(R.string.error_saldo));
                    valid = false;
                }
            }
        } catch (NumberFormatException e) {
            tilSaldo.setError(getString(R.string.error_saldo));
            valid = false;
        }

        if (!valid) return;
        String jenis = jenisOptions[jenisPos];

        if (isEditMode) {
            Setoran setoran = new Setoran(setoranId, nama, jenis, berat, saldo);
            dbHelper.updateSetoran(setoran);
            Toast.makeText(this, getString(R.string.success_update), Toast.LENGTH_SHORT).show();
        } else {
            Setoran setoran = new Setoran(nama, jenis, berat, saldo);
            dbHelper.insertSetoran(setoran);
            Toast.makeText(this, getString(R.string.success_add), Toast.LENGTH_SHORT).show();
        }

        setResult(RESULT_OK);
        finish();
    }
}
```

- [ ] **Step 2: Verify build compiles**

Run: `./gradlew assembleDebug`
Expected: BUILD SUCCESSFUL

---

### Task 11: Final Verification

**Files:** None (read-only verification)

- [ ] **Step 1: Full clean build**

Run: `./gradlew clean assembleDebug`
Expected: BUILD SUCCESSFUL

- [ ] **Step 2: Run unit tests**

Run: `./gradlew testDebugUnitTest`
Expected: All tests pass

- [ ] **Step 3: Verify APK exists**

Run: `ls -la app/build/outputs/apk/debug/`
Expected: `app-debug.apk` file present
