INSERT INTO user_roles (user_id, roles_id)
SELECT id, 1 as role
FROM user
WHERE name = 'admin';