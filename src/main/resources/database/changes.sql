
# added to production 7/19/2024
INSERT INTO app_settings VALUES ('logoPath', '/Gybe/Stickers/2024-tbg.png', 'Path to logo', 'string', CURRENT_TIMESTAMP, 'directory');
INSERT INTO app_settings VALUES ('selectedYear', '2024', 'The year', 'integer', CURRENT_TIMESTAMP, 'directory');
INSERT INTO app_settings VALUES ('normalFontSize', '10', 'defaultFont', 'float', CURRENT_TIMESTAMP, 'directory');
INSERT INTO app_settings VALUES ('fixedLeading', '25', 'defaultFixedLeading', 'float', CURRENT_TIMESTAMP, 'directory');
INSERT INTO app_settings VALUES ('fixedLeadingNarrow', '10', 'Other FixedLeading', 'float', CURRENT_TIMESTAMP, 'directory');
INSERT INTO app_settings VALUES ('titleBoxHeight', '20', 'Font size in slips', 'float', CURRENT_TIMESTAMP, 'directory');
INSERT INTO app_settings VALUES ('mainColor', '#913bb5', 'Main Color', 'webColor', CURRENT_TIMESTAMP, 'directory');
# INSERT INTO app_settings VALUES ('emailColor', '#0000FF', 'Font size in slips', 'webColor', CURRENT_TIMESTAMP, 'directory');
INSERT INTO app_settings VALUES ('numberOfRowsByNumber', '28', 'this needs to go up to 29 when you get more memberships', 'integer', CURRENT_TIMESTAMP, 'directory');
INSERT INTO app_settings VALUES ('numberOfCommodoreColumns', '2', 'Number of Columns on commodore page', 'integer', CURRENT_TIMESTAMP, 'directory');
INSERT INTO app_settings VALUES ('width', '5.5', 'width', 'float', CURRENT_TIMESTAMP, 'directory');
INSERT INTO app_settings VALUES ('height', '8.5', 'height', 'float', CURRENT_TIMESTAMP, 'directory');
INSERT INTO app_settings VALUES ('titleFontSize', '35', 'This is the font size for the cover text', 'float', CURRENT_TIMESTAMP, 'directory:cover');
INSERT INTO app_settings VALUES ('titleFixedLeading', '32', 'This is the fixed leading value for the title', 'float', CURRENT_TIMESTAMP, 'directory:cover');
INSERT INTO app_settings VALUES ('logoTopPadding', '10', 'This is the padding between the top of the page and the logo', 'float', CURRENT_TIMESTAMP, 'directory:cover');
INSERT INTO app_settings VALUES ('titleTopPadding', '20', 'This is the padding between the logo and the Title', 'float', CURRENT_TIMESTAMP, 'directory:cover');
INSERT INTO app_settings VALUES ('salutationTopPadding', '10', 'This is the padding between the top of the page and the salutation', 'float', CURRENT_TIMESTAMP, 'directory:cover');
INSERT INTO app_settings VALUES ('messageTopPadding', '5', 'This is the padding between the salutation and the message', 'float', CURRENT_TIMESTAMP, 'directory:cover');
INSERT INTO app_settings VALUES ('paragraphPadding', '5', 'This is the padding between the paragraphs of the message', 'float', CURRENT_TIMESTAMP, 'directory:cover');
INSERT INTO app_settings VALUES ('PositionHeadingFontSize', '14', 'This is the font size for the headings in the board of directors page', 'float', CURRENT_TIMESTAMP, 'directory:boardOfDirectors');
INSERT INTO app_settings VALUES ('positionFixedLeading', '10', 'This fixed leading for normal text in BOD page', 'float', CURRENT_TIMESTAMP, 'directory:boardOfDirectors');
INSERT INTO app_settings VALUES ('bodTopPadding', '10', 'This is the padding between the top of the page and Officers heading', 'float', CURRENT_TIMESTAMP, 'directory:boardOfDirectors');
INSERT INTO app_settings VALUES ('bodTablePadding', '5', 'This is the padding between the top of the page and Officers heading', 'float', CURRENT_TIMESTAMP, 'directory:boardOfDirectors');
INSERT INTO app_settings VALUES ('bodFooterFontSize', '8', 'This is the font size for text in the footer', 'float', CURRENT_TIMESTAMP, 'directory:boardOfDirectors');
INSERT INTO app_settings VALUES ('fontName', 'SF-Compact-Rounded-Bold.otf', 'This is the font for headings', 'string', CURRENT_TIMESTAMP, 'directory');
INSERT INTO app_settings VALUES ('tocAddressFontSize', '12', 'The font size of the address at the bottom of Table of Contents', 'float', CURRENT_TIMESTAMP, 'directory:tableOfContents');
INSERT INTO app_settings VALUES ('tocTitleFontSize', '18', 'The font size of the title', 'float', CURRENT_TIMESTAMP, 'directory:tableOfContents');
INSERT INTO app_settings VALUES ('tocChapterFontSize', '14', 'The font size of the chapters in Table of Contents', 'float', CURRENT_TIMESTAMP, 'directory:tableOfContents');
INSERT INTO app_settings VALUES ('tocTopMarginPadding', '20', 'This is the padding between the top of the page and the title', 'float', CURRENT_TIMESTAMP, 'directory:tableOfContents');
INSERT INTO app_settings VALUES ('tocChapterPadding', '15', 'This is the padding between the chapters', 'float', CURRENT_TIMESTAMP, 'directory:tableOfContents');
INSERT INTO app_settings VALUES ('tocAddressPadding', '20', 'This is the padding between the chapters and the address', 'float', CURRENT_TIMESTAMP, 'directory:tableOfContents');
INSERT INTO app_settings VALUES ('tocTitleFixedLeading', '12', 'This fixed leading for the text for the club name', 'float', CURRENT_TIMESTAMP, 'directory:tableOfContents');
INSERT INTO app_settings VALUES ('tocTextFixedLeading', '15', 'This fixed leading for for the text below the club name', 'float', CURRENT_TIMESTAMP, 'directory:tableOfContents');
INSERT INTO app_settings VALUES ('membershipInformationTitleFontSize', '18', 'The font size of the title of the Membership Information title page', 'float', CURRENT_TIMESTAMP, 'directory:memberInfoTitle');
INSERT INTO app_settings VALUES ('membershipInfoTitlePaddingTop', '90', 'memberInfTitle: This is the padding between the top of the page and the title', 'float', CURRENT_TIMESTAMP, 'directory:memberInfoTitle');
INSERT INTO app_settings VALUES ('membershipInfoTitlePaddingBottom', '40', 'memberInfTitle: This is the padding between the bottom of the title and top of statements', 'float', CURRENT_TIMESTAMP, 'directory:memberInfoTitle');
INSERT INTO app_settings VALUES ('mipHeaderColor', '#ffffff', 'Header color for membership information pages', 'webColor', CURRENT_TIMESTAMP, 'directory:membershipInformationPage');
INSERT INTO app_settings VALUES ('mipEmailColor', '#0000FF', 'The color of the email in the membership information page', 'webColor', CURRENT_TIMESTAMP, 'directory:membershipInformationPage');
INSERT INTO app_settings VALUES ('membershipEmail', 'membership@ecsail.org', 'This is the contact email to be used in the directory', 'string', CURRENT_TIMESTAMP, 'directory');
INSERT INTO app_settings VALUES ('mipTopPadding', '10', 'This is the top padding for the membership information page', 'float', CURRENT_TIMESTAMP, 'directory:membershipInformationPage');
INSERT INTO app_settings VALUES ('mipPadding', '3', 'This is the padding in between each membership information', 'float', CURRENT_TIMESTAMP, 'directory:membershipInformationPage');
INSERT INTO app_settings VALUES ('mbnFontSize', '8', 'The font size in the members by number pages', 'float', CURRENT_TIMESTAMP, 'directory:membersByNumbers');
INSERT INTO app_settings VALUES ('mbnFixedLeading', '18', 'This is the space between membership entries in each column', 'float', CURRENT_TIMESTAMP, 'directory:membersByNumbers');
INSERT INTO app_settings VALUES ('mbnTopPadding', '20', 'This is the top padding for the members by number page', 'float', CURRENT_TIMESTAMP, 'directory:membersByNumbers');
INSERT INTO app_settings VALUES ('A', '1:1:1', 'Location of dock A (table, order placed, page)', 'SlipPlacementDTO', CURRENT_TIMESTAMP, 'directory:slip');
INSERT INTO app_settings VALUES ('B', '2:3:1', 'Location of dock B (table, order placed, page)', 'SlipPlacementDTO', CURRENT_TIMESTAMP, 'directory:slip');
INSERT INTO app_settings VALUES ('C', '3:4:2', 'Location of dock C (table, order placed, page)', 'SlipPlacementDTO', CURRENT_TIMESTAMP, 'directory:slip');
INSERT INTO app_settings VALUES ('D', '4:5:2', 'Location of dock D (table, order placed, page)', 'SlipPlacementDTO', CURRENT_TIMESTAMP, 'directory:slip');
INSERT INTO app_settings VALUES ('F', '1:2:1', 'Location of dock F (table, order placed, page)', 'SlipPlacementDTO', CURRENT_TIMESTAMP, 'directory:slip');
INSERT INTO app_settings VALUES ('slipColor', '#ebeef2', 'The Color of the slips', 'webColor', CURRENT_TIMESTAMP, 'directory:slip');
INSERT INTO app_settings VALUES ('slipSubleaseColor', '#0000FF', 'The color of subleases in the slip chart', 'webColor', CURRENT_TIMESTAMP, 'directory:slip');
INSERT INTO app_settings VALUES ('F02', '48-Hour', 'Alt Dock designation', 'SlipAltDTO', CURRENT_TIMESTAMP, 'directory:slip');
INSERT INTO app_settings VALUES ('F04', '48-Hour', 'Alt Dock designation', 'SlipAltDTO', CURRENT_TIMESTAMP, 'directory:slip');
INSERT INTO app_settings VALUES ('CR2', 'Racing', 'Alt Dock designation', 'SlipAltDTO', CURRENT_TIMESTAMP, 'directory:slip');
INSERT INTO app_settings VALUES ('CR1', 'Racing', 'Alt Dock designation', 'SlipAltDTO', CURRENT_TIMESTAMP, 'directory:slip');
INSERT INTO app_settings VALUES ('slipPage1TopPadding', '0', 'This is the padding above the slips on page 1', 'float', CURRENT_TIMESTAMP, 'directory:slip');
INSERT INTO app_settings VALUES ('slipPage2TopPadding', '20', 'This is the padding above the slips on page 1', 'float', CURRENT_TIMESTAMP, 'directory:slip');
INSERT INTO app_settings VALUES ('dockTopPadding', '10', 'This is the padding above each dock section', 'float', CURRENT_TIMESTAMP, 'directory:slip');
INSERT INTO app_settings VALUES ('legendTopPadding', '40', 'This is the padding above the legend', 'float', CURRENT_TIMESTAMP, 'directory:slip');
INSERT INTO app_settings VALUES ('dockTopHeight', '5', 'This is the height of the top cap of a dock', 'float', CURRENT_TIMESTAMP, 'directory:slip');
INSERT INTO app_settings VALUES ('dockSectionHeight', '18', 'This is the height of a dock section', 'float', CURRENT_TIMESTAMP, 'directory:slip');
INSERT INTO app_settings VALUES ('dockSectionConnectorHeight', '5', 'This is the height of the connecting dock between dock sections', 'float', CURRENT_TIMESTAMP, 'directory:slip');
INSERT INTO app_settings VALUES ('dockSectionBottomHeight', '12', 'This is the height of the bottom cap of a dock', 'float', CURRENT_TIMESTAMP, 'directory:slip');
INSERT INTO app_settings VALUES ('dockFontSize', '6', 'This is the font for each slip (owners and slip number)', 'float', CURRENT_TIMESTAMP, 'directory:slip');
INSERT INTO app_settings VALUES ('dockWidth', '70', 'This is the width of both left and right docks', 'float', CURRENT_TIMESTAMP, 'directory:slip');
INSERT INTO app_settings VALUES ('centerDockWidth', '20', 'This is the width of the center dock', 'float', CURRENT_TIMESTAMP, 'directory:slip');
INSERT INTO app_settings VALUES ('legendSubFontSize', '9', 'The font size of the sub-text in the legend', 'float', CURRENT_TIMESTAMP, 'directory:slip');
INSERT INTO app_settings VALUES ('legendTitleFontSize', '12', 'The font size of the title in the legend', 'float', CURRENT_TIMESTAMP, 'directory:slip');
INSERT INTO app_settings VALUES ('pcTopPadding', '10', 'The top padding of the past commodore page', 'float', CURRENT_TIMESTAMP, 'directory:pastCommodore');
INSERT INTO app_settings VALUES ('soyTopPadding', '10', 'The top padding of the Sportsman of the year page', 'float', CURRENT_TIMESTAMP, 'directory:sportsmanshipAward');
INSERT INTO app_settings VALUES ('pcLeftPadding', '25', 'The left padding of the Commodore page', 'float', CURRENT_TIMESTAMP, 'directory:pastCommodore');
INSERT INTO app_settings VALUES ('soyLeftPadding', '25', 'The left padding of the Sportsman of the year page', 'float', CURRENT_TIMESTAMP, 'directory:sportsmanshipAward');
INSERT INTO app_settings VALUES ('pcFixedLeading', '10', 'The fixed leading of the past commodore page', 'float', CURRENT_TIMESTAMP, 'directory:pastCommodore');
INSERT INTO app_settings VALUES ('soyFixedLeading', '10', 'The fixed leading of the Sportsman of the year page', 'float', CURRENT_TIMESTAMP, 'directory:sportsmanshipAward');
INSERT INTO app_settings VALUES ('dockTextFixedLeading', '6', 'The fixed leading of the dock text', 'float', CURRENT_TIMESTAMP, 'directory:slip');

# setting("dockTextFixedLeading")
# setting("soyFixedLeading")





# added to test server 7/8/2023
# added to production server 7/19/2024
create table commodore_message
(
    id   int auto_increment
        primary key,
    fiscal_year int,
    salutation varchar(300) not null,
    message varchar(3000) not null,
    commodore varchar(100) not null,
    pid int not null
)
    collate = utf8mb4_unicode_ci;


# added to production server 6/16/2023
CREATE TABLE logins (
                        id           INT AUTO_INCREMENT PRIMARY KEY,
                        username     VARCHAR(50) NOT NULL,
                        p_id         INT NOT NULL,
                        req_date     TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                        login_status BOOLEAN NOT NULL
);


# added 12/15/2023  is on test server, not sure about production UPDATE: table existed on production
# but I needed to add the column group_name
CREATE TABLE ECSC_SQL.app_settings
(
    setting_key   VARCHAR(255) NOT NULL PRIMARY KEY,
    setting_value VARCHAR(255) NOT NULL,
    description   TEXT,
    data_type     VARCHAR(50),
    group_name         VARCHAR(25),
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