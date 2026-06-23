# UI/UX Revamp Design — Bank Sampah

**Date**: 2026-06-22
**Status**: Approved
**Goal**: Full UI/UX revamp with eco/nature theme, WCAG AA compliance

## Problem Statement

Current app has:
1. Missing color definitions in `colors.xml` causing runtime fallback
2. Hardcoded hex values scattered across layouts
3. WCAG AA violations (contrast ratios below 4.5:1 for text)
4. Inconsistent color naming convention
5. Manual status bar spacer instead of EdgeToEdge API
6. No visual hierarchy — list items lack differentiation

## Design System

### Color Palette (Eco/Nature Theme)

All colors tested for WCAG AA compliance (4.5:1 minimum for normal text).

| Token | Hex | Usage | Contrast on White |
|-------|-----|-------|-------------------|
| `green_900` | `#1B5E20` | Primary, toolbar, status bar | 8.5:1 |
| `green_700` | `#2E7D32` | Primary hover/focus, icons | 5.9:1 |
| `green_500` | `#4CAF50` | FAB, accents, active states | 3.0:1 (large text only) |
| `green_100` | `#E8F5E9` | Chip backgrounds, light tints | N/A (bg) |
| `green_50` | `#F1F8E9` | Surface tint | N/A (bg) |
| `cream_50` | `#FFFDE7` | App background | N/A (bg) |
| `cream_100` | `#FFF9C4` | Card surfaces | N/A (bg) |
| `brown_700` | `#4E342E` | Secondary, earth accent | 9.2:1 |
| `brown_400` | `#8D6E63` | On secondary, subtle text | 3.8:1 |
| `neutral_900` | `#212529` | Primary text | 16.1:1 |
| `neutral_700` | `#495057` | Secondary text | 8.6:1 |
| `neutral_500` | `#6C757D` | Hint text, disabled | 5.7:1 |
| `neutral_300` | `#DEE2E6` | Borders, outlines | N/A (border) |
| `error_700` | `#D32F2F` | Error states | 5.6:1 |
| `error_50` | `#FFEBEE` | Error container | N/A (bg) |

### Typography

- Use Material3 text styles throughout (`TextAppearance.Material3.*`)
- No custom fonts — rely on system Roboto
- Minimum body text: 14sp

### Spacing

- Page padding: 16dp
- Card padding: 20dp
- Between elements: 12dp
- Corner radius: 12dp (cards, buttons, inputs)
- Icon button radius: 18dp

## Changes by Screen

### 1. Colors (`colors.xml`)

Complete rewrite:
- Remove undefined tokens (`on_primary`, `surface_container`, `secondary_500`, etc.)
- Add all eco palette colors
- Remove hardcoded text colors — use `neutral_*` tokens
- Add `colorOnPrimary`, `colorSurface`, `colorOnSurface` properly

### 2. Theme (`values/themes.xml` + `values-night/themes.xml`)

- Map all Material3 color attributes to actual defined colors
- Day: green_900 primary, cream backgrounds
- Night: green_700 primary, neutral_900 backgrounds
- Remove `elevationOverlayEnabled` (deprecated in Material3)

### 3. Login Screen (`activity_login.xml`)

- Replace status bar spacer with `WindowInsetsCompat` handling in Java
- Use `EdgeToEdge` API for immersive status bar
- Green gradient background → solid `green_900`
- White card at bottom with proper rounded corners via `MaterialCardView`
- Button: `green_900` background, white text

### 4. Main Screen (`activity_main.xml`)

- Add summary stat cards below toolbar (Anggota, Berat Total, Saldo Total)
- Each stat card: icon + value + label, `cream_100` background
- Search bar: `MaterialCardView` with search icon, rounded 24dp
- Sort spinner: outlined style matching search bar
- FAB: `green_500` background, white icon/text
- Empty state: leaf icon + message + hint

### 5. Item Card (`item_setoran.xml`)

- Replace `bg_card.xml` drawable with `MaterialCardView`
- Top section: name (bold) + chip (category)
- Middle: divider
- Bottom: berat + saldo labels with values
- Edit/Delete: icon buttons with ripple
- Chip: `green_100` background, `green_700` text

### 6. Add/Edit Screen (`activity_add_edit_setoran.xml`)

- Consistent toolbar with back navigation
- Form card: `MaterialCardView` with proper elevation
- All inputs: outlined style with `green_700` focus color
- Spinner: wrapped in `MaterialCardView` for consistent border
- Save button: `green_900` filled
- Cancel button: outlined, `green_700` stroke

### 7. Drawable Updates

- `bg_card.xml` → Remove (use MaterialCardView instead)
- `bg_chip.xml` → Update colors to `green_100`/`green_700`
- `bg_search.xml` → Update to `cream_100` background
- `bg_spinner.xml` → Update to match search bar style
- `bg_icon_button.xml` → Add ripple, update to `green_100`

### 8. Java Changes

- `LoginActivity.java`: Add EdgeToEdge + WindowInsets handling
- `MainActivity.java`: Add stat card data binding
- `AddEditSetoranActivity.java`: Add EdgeToEdge handling

## WCAG Compliance Checklist

- [ ] All text ≥ 4.5:1 contrast ratio
- [ ] Large text (18sp+) ≥ 3:1 contrast ratio
- [ ] UI component borders ≥ 3:1 contrast ratio
- [ ] Focus indicators visible
- [ ] Touch targets ≥ 48dp
- [ ] Color not sole indicator (icons + text always paired)

## Files to Modify

1. `app/src/main/res/values/colors.xml`
2. `app/src/main/res/values/themes.xml`
3. `app/src/main/res/values-night/themes.xml`
4. `app/src/main/res/layout/activity_login.xml`
5. `app/src/main/res/layout/activity_main.xml`
6. `app/src/main/res/layout/activity_add_edit_setoran.xml`
7. `app/src/main/res/layout/item_setoran.xml`
8. `app/src/main/res/drawable/bg_card.xml`
9. `app/src/main/res/drawable/bg_chip.xml`
10. `app/src/main/res/drawable/bg_search.xml`
11. `app/src/main/res/drawable/bg_spinner.xml`
12. `app/src/main/res/drawable/bg_icon_button.xml`
13. `app/src/main/java/com/mogador/banksampah/LoginActivity.java`
14. `app/src/main/java/com/mogador/banksampah/MainActivity.java`
15. `app/src/main/java/com/mogador/banksampah/AddEditSetoranActivity.java`
