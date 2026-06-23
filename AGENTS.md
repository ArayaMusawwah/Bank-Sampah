# Bank Sampah - Agent Guide

## Project Overview

Android app for managing waste bank (bank sampah) deposits. CRUD operations on setoran records with SQLite storage.

- **Package**: `com.mogador.banksampah`
- **Language**: Java (not Kotlin)
- **Min SDK**: 29 (Android 10) | **Target SDK**: 36
- **Gradle**: 9.4.1 | **AGP**: 9.2.1

## Architecture

Simple single-module Android app with 3 Activities:

| Activity | Purpose |
|----------|---------|
| `LoginActivity` | Entry point, hardcoded auth (admin/12345) |
| `MainActivity` | List view with search, sort, RecyclerView |
| `AddEditSetoranActivity` | Form for create/edit operations |

**Key classes:**
- `DatabaseHelper` - SQLite operations (raw SQL, no Room)
- `Setoran` - POJO model
- `SetoranAdapter` - RecyclerView adapter

## Build & Test Commands

```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Run unit tests (local JVM)
./gradlew testDebugUnitTest

# Run instrumented tests (requires device/emulator)
./gradlew connectedDebugAndroidTest

# Clean build
./gradlew clean
```

## Database

SQLite database: `db_sampah_231011400285.db`

Single table `setoran` with columns: `id`, `nama_anggota`, `jenis_sampah`, `berat`, `saldo`

Seed data is inserted on first run (8 sample records).

## UI Components

- Material Design 3 (MaterialToolbar, ExtendedFloatingActionButton, Chip, TextInputLayout)
- RecyclerView with custom `SetoranAdapter`
- Search by name, sort by 7 options (nama/berat/saldo asc/desc + newest)

## Testing

Only template tests exist:
- `ExampleUnitTest.java` - trivial JUnit test
- `ExampleInstrumentedTest.java` - basic context verification

No business logic tests. Add tests to `app/src/test/` for unit tests.

## Common Pitfalls

1. **Database version**: If schema changes, increment `DATABASE_VERSION` in `DatabaseHelper` and implement `onUpgrade()`
2. **Seed data**: `insertSeedData()` runs only on first install or DB upgrade
3. **Login state**: Uses `SharedPreferences` with key `is_logged_in`
4. **String resources**: UI text is in Indonesian, stored in `res/values/strings.xml`
