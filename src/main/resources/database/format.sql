#Format the first name
#Fixes upper case names, leaves the rest alone
UPDATE person
SET F_NAME = CONCAT(UPPER(LEFT(F_NAME, 1)), LOWER(SUBSTRING(F_NAME, 2)))
WHERE LENGTH(F_NAME) > 1  -- Ensure more than 1 character
  AND F_NAME = BINARY UPPER(F_NAME)  -- Case-sensitive check to avoid lowercase -- Ensure the name is all uppercase
  AND F_NAME NOT LIKE '%.%'  -- Exclude names with periods
  AND F_NAME NOT LIKE '% %';  -- Exclude names with spaces (to ignore initials like L. R.)


#format the last name
UPDATE person
SET L_NAME = CONCAT(UPPER(LEFT(L_NAME, 1)), LOWER(SUBSTRING(L_NAME, 2)))
WHERE LENGTH(L_NAME) > 1  -- Ensure more than 1 character
  AND L_NAME = BINARY UPPER(L_NAME)  -- Case-sensitive check to avoid lowercase -- Ensure the name is all uppercase
  AND L_NAME NOT LIKE '%.%'  -- Exclude names with periods
  AND L_NAME NOT LIKE '% %';

#format the city
UPDATE membership
SET CITY = CONCAT(UPPER(LEFT(CITY, 1)), LOWER(SUBSTRING(CITY, 2)))
WHERE LENGTH(CITY) > 1  -- Ensure more than 1 character
  AND CITY = BINARY UPPER(CITY)  -- Case-sensitive check to avoid lowercase -- Ensure the name is all uppercase
  AND CITY NOT LIKE '%.%'  -- Exclude names with periods
  AND CITY NOT LIKE '% %';


SELECT * FROM membership
WHERE LENGTH(CITY) > 1  -- Ensure more than 1 character
  AND CITY = BINARY UPPER(CITY)  -- Case-sensitive check to avoid lowercase -- Ensure the name is all uppercase
  AND CITY NOT LIKE '%.%'  -- Exclude names with periods
  AND CITY NOT LIKE '% %';

SELECT *
FROM person
ORDER BY MS_ID DESC  -- Order by primary key in descending order
LIMIT 100;           -- Limit the results to the last 100

select * from membership where MS_ID=1406