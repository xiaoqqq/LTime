package com.xiaoqqq.l_time.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;

/**
 * <pre>
 *     @author : xiaoqing
 *     e-mail : qing.xiao@getech.cn
 *     time   : 2018-6-7
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class DatabaseMigration {
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `local_image` (`imagePath` TEXT  NOT NULL, `timeStamp` TEXT  NOT NULL, PRIMARY KEY(`imagePath`))");
        }
    };

    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("delete from date_content");
            database.execSQL("ALTER TABLE date_content ADD COLUMN desktop_word TEXT  DEFAULT \"\"");
        }
    };

    public static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("delete from group_info");
            database.execSQL("delete from group_user");
            database.execSQL("ALTER TABLE group_user ADD COLUMN email TEXT  DEFAULT \"\"");
        }
    };
    public static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `template_user_info` (`appId` TEXT, `name` TEXT, `headUrl` TEXT, `uid` TEXT NOT NULL, `pluginId` TEXT, PRIMARY KEY(`uid`))");
        }
    };
    public static final Migration MIGRATION_5_6 = new Migration(5, 6) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("delete from conversation");
            database.execSQL(
                    "ALTER TABLE conversation ADD COLUMN conversation_name TEXT  DEFAULT \"\"");
        }
    };
    public static final Migration MIGRATION_6_7 = new Migration(6, 7) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS session_top (`session_id` TEXT NOT NULL, `chat_type` INTEGER NOT NULL, PRIMARY KEY(`session_id`, `chat_type`))");
        }
    };
    public static final Migration MIGRATION_7_8 = new Migration(7, 8) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("drop table msg_sync");
            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS msg_not_break_point (`msgId` TEXT NOT NULL, PRIMARY KEY(`msgId`))");
        }
    };
    public static final Migration MIGRATION_8_9 = new Migration(8, 9) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `home_card` (`homeCardId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `targetUrl` TEXT, `summary` TEXT, `titleInfo` TEXT, `btnInfo` TEXT, `createTime` TEXT, `serId` TEXT, `pluginId` TEXT, `openType` INTEGER NOT NULL, `itemsInfo` TEXT)");
        }
    };
    public static final Migration MIGRATION_9_12 = new Migration(9, 12) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            try {
                database.execSQL("drop table message_flow");
            } catch (Throwable e) {
            }
            database.execSQL("drop table group_user");
            database.execSQL("delete from group_info");
            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `${group_user}` (`gid` TEXT NOT NULL, `uid` TEXT NOT NULL, `name` TEXT, `avatar` TEXT, `isAdmin` INTEGER NOT NULL, `email` TEXT, `orgName` TEXT, PRIMARY KEY(`gid`, `uid`))");
        }
    };

    public static final Migration MIGRATION_12_13 = new Migration(12, 13) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            try {
                database.execSQL("drop table message_flow");
                database.execSQL("drop table group_user");
            } catch (Throwable e) {
            }
            database.execSQL("delete from group_info");
            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS group_user (`gid` TEXT NOT NULL, `uid` TEXT NOT NULL, `name` TEXT, `avatar` TEXT, `isAdmin` INTEGER NOT NULL, `email` TEXT, `orgName` TEXT, PRIMARY KEY(`gid`, `uid`))");
        }
    };

    public static final Migration MIGRATION_13_14 = new Migration(13, 14) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            try {
                database.execSQL("delete from msg_not_break_point");
                database.execSQL("delete from chat_record");
                database.execSQL("delete from conversation");
            } catch (Throwable e) {
            }
        }
    };

    public static final Migration MIGRATION_14_15 = new Migration(14, 15) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            try {
                database.execSQL("delete from chat_record");
                database.execSQL("delete from conversation");
                database.execSQL("drop table template_user_info");
                String sql =
                        "CREATE TABLE IF NOT EXISTS template_user_info (`appId` TEXT, `name` TEXT, `headUrl` TEXT, `uid` TEXT NOT NULL, `pluginId` TEXT, `save` TEXT, PRIMARY KEY(`uid`))";
                database.execSQL(sql);
            } catch (Throwable e) {
            }
        }
    };

    public static final Migration MIGRATION_15_16 = new Migration(15, 16) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            try {
                database.execSQL(
                        "ALTER TABLE chat_record ADD COLUMN unRead INTEGER NOT NULL DEFAULT -2");
            } catch (Throwable e) {
            }
        }
    };

    public static final Migration MIGRATION_16_17 = new Migration(16, 17) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            try {
                database.execSQL("drop table org");
                String sql =
                        "CREATE TABLE IF NOT EXISTS `org` (`id` TEXT NOT NULL, `parent_id` TEXT, `name` TEXT, `avatar` TEXT, `isLeaf` INTEGER NOT NULL, `type` INTEGER NOT NULL, `position_name` TEXT, `user_email` TEXT, PRIMARY KEY(`id`))";
                database.execSQL(sql);
            } catch (Throwable e) {
            }
        }
    };

    public static final Migration MIGRATION_17_18 = new Migration(17, 18) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            try {
                database.execSQL("delete from user_info");
                database.execSQL("ALTER TABLE user_info ADD COLUMN orgName TEXT  DEFAULT \"\"");


            } catch (Throwable e) {
            }
        }
    };

    public static final Migration MIGRATION_18_19 = new Migration(18, 19) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            try {
                database.execSQL(
                        "ALTER TABLE group_info ADD COLUMN totalUser INTEGER NOT NULL DEFAULT 0 ");
                database.execSQL("ALTER TABLE group_info ADD COLUMN adminUid TEXT  DEFAULT \"\"");
                database.execSQL("ALTER TABLE group_info ADD COLUMN tagName TEXT  DEFAULT \"\"");
                database.execSQL("ALTER TABLE group_info ADD COLUMN type TEXT  DEFAULT \"\"");

                database.execSQL("ALTER TABLE user_info ADD COLUMN gid TEXT  DEFAULT \"\"");

                String sql1 =
                        "CREATE TABLE IF NOT EXISTS group_member (`id` TEXT NOT NULL,`gid` TEXT NOT NULL, `uid` TEXT NOT NULL, PRIMARY KEY(`id`))";
                database.execSQL(sql1);

                database.execSQL(
                        "ALTER TABLE group_info ADD COLUMN memberVer INTEGER NOT NULL DEFAULT 0 ");

                database.execSQL(
                        "ALTER TABLE group_info ADD COLUMN showRead INTEGER NOT NULL DEFAULT 0 ");
            } catch (Throwable e) {
            }
        }
    };

    public static final Migration MIGRATION_19_20 = new Migration(19, 20) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            try {
                //user_info表中新增字段gid 表示用户所在的群，如果有多个群，gid中的string用逗号分隔

                database.execSQL("ALTER TABLE template_user_info ADD COLUMN open INTEGER  NOT NULL DEFAULT 0");
            } catch (Throwable e) {
            }
        }
    };
    //public static final Migration MIGRATION_20_21 = new Migration(20, 21) {
    //    @Override
    //    public void migrate(@NonNull SupportSQLiteDatabase database) {
    //        try {
    //            String sql =
    //                    "CREATE TABLE IF NOT EXISTS group_member (`id` TEXT NOT NULL,`gid` TEXT NOT NULL, `uid` TEXT NOT NULL, PRIMARY KEY(`id`))";
    //            database.execSQL(sql);
    //        } catch (Throwable e) {
    //        }
    //    }
    //};
    //public static final Migration MIGRATION_21_22 = new Migration(21, 22) {
    //    @Override
    //    public void migrate(@NonNull SupportSQLiteDatabase database) {
    //        try {
    //            database.execSQL(
    //                    "ALTER TABLE group_info ADD COLUMN memberVer INTEGER NOT NULL DEFAULT 0 ");
    //        } catch (Throwable e) {
    //        }
    //    }
    //};

    //public static final Migration MIGRATION_22_23  = new Migration(22, 23) {
    //    @Override
    //    public void migrate(@NonNull SupportSQLiteDatabase database) {
    //        try {
    //            database.execSQL(
    //                    "ALTER TABLE group_info ADD COLUMN showRead INTEGER NOT NULL DEFAULT 0 ");
    //        } catch (Throwable e) {
    //        }
    //    }
    //};
}