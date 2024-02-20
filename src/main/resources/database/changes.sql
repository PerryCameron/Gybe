# added 12/15/2023  is on test server, not sure about production UPDATE: table existed on production
# but I needed to add the column group
CREATE TABLE ECSC_SQL.app_settings
(
    setting_key   VARCHAR(255) NOT NULL PRIMARY KEY,
    setting_value VARCHAR(255) NOT NULL,
    description   TEXT,
    data_type     VARCHAR(50),
    group         VARCHAR(25),
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

create table theme
(
    STICKER_ID        int auto_increment primary key,
    sticker_name    varchar(40)          null,
    url   varchar(200)          null,
    year_color   varchar(10)          null
)
    collate = utf8mb4_unicode_ci;

