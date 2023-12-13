# this will get rid of the link in front
UPDATE form_hash_request
SET LINK = CONCAT('register', SUBSTRING_INDEX(LINK, 'register', -1))
WHERE LINK LIKE '%register%';


