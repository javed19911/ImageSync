package com.example.imagesynclib.Database.Local;

import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class MigrationDB {

    private MigrationDB() {
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL(
                    "ALTER TABLE GENERIC ADD COLUMN group_id INTEGER NOT NULL DEFAULT " + Integer.MAX_VALUE);
            database.execSQL(
                    "ALTER TABLE GENERIC ADD COLUMN group_priority INTEGER NOT NULL DEFAULT " + Integer.MAX_VALUE);
            database.execSQL(
                    "ALTER TABLE GENERIC ADD COLUMN item_priority INTEGER NOT NULL DEFAULT " + Integer.MAX_VALUE);
            database.execSQL(
                    "ALTER TABLE GENERIC ADD COLUMN allowed_success_code TEXT DEFAULT '[\"200\"]'");
        }
    };
}
