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

#added to test server [2024-04-07 20:42:20] completed in 48 ms
#updated test server without auto timestamp on update[2024-04-11 19:22:08] completed in 46 ms
#added to production: [2024-04-19 12:34:21] completed in 56 ms
CREATE TABLE user_auth_request
(
    pass_key   VARCHAR(255)                            NOT NULL
        PRIMARY KEY,
    pid        INT                                     NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP     NOT NULL,  -- Removed ON UPDATE CURRENT_TIMESTAMP
    completed  TIMESTAMP DEFAULT '0000-00-00 00:00:00' NOT NULL,
    FOREIGN KEY (pid) REFERENCES person (P_ID)
);

#updated test server [2024-04-19 12:08:16] completed in 55 ms
#updated production: [2024-04-19 12:37:23] completed in 41 ms
ALTER TABLE users
    ADD CONSTRAINT unique_p_id UNIQUE (p_id);