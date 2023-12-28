# this will get rid of the link in front
UPDATE form_hash_request
SET LINK = CONCAT('register', SUBSTRING_INDEX(LINK, 'register', -1))
WHERE LINK LIKE '%register%';


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

# needs dropped on production
drop table form_settings;

# needs added on production
INSERT INTO app_settings (setting_key, setting_value, description, data_type, updated_at, group_name)
VALUES
    ('app_port', '8080', 'The port that the application should run on, 8080 for testing, 443 for production', 'integer', '2023-12-27 23:29:39', '2024_gybe'),
    ('current_membership_chair', '1084', 'The membership person currently active, sometimes near the end of a term another will take their place before year end', 'integer', '2023-12-15 12:39:37', NULL),
    ('form_id', '233427895534163', 'The JotForm ID number for the renewal form', 'long', '2023-12-28 00:08:03', '2024_gybe'),
    ('form_url', 'https://form.jotform.com/', 'The link to jotform', 'string', '2023-12-27 23:17:45', '2024_gybe'),
    ('host_name', 'localhost', 'Our current host, localhost for testing, ecsail.org for production', 'string', '2023-12-27 23:29:39', '2024_gybe'),
    ('scheme', 'http', 'This will be http:// when testing local and https:// if in production', 'string', '2023-12-28 00:06:17', '2024_gybe'),
    ('selected_year', '2023', 'Usually the current year unless, testing', 'integer', '2023-12-27 23:17:45', '2024_gybe');
