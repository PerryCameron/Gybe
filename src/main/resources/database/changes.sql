# this will get rid of the link in front
UPDATE form_hash_request
SET LINK = CONCAT('register', SUBSTRING_INDEX(LINK, 'register', -1))
WHERE LINK LIKE '%register%';


# added 12/15/2023  is on test server, not sure about production
CREATE TABLE ECSC_SQL.app_settings
(
    setting_key   VARCHAR(255) NOT NULL PRIMARY KEY,
    setting_value VARCHAR(255) NOT NULL,
    description   TEXT,
    data_type     VARCHAR(50),
    group         VARCHAR(25),
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);