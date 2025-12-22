INSERT INTO users (login, salt, password_hash) VALUES
('alice', 'salt1', '077317626a29b193cf722eb4ac84e22ccd50a69e2d88072c1f3b33b154cfc9b8'),
('bob', 'salt2', 'e1f114c42c6a926c821b0f8ffe49b38f6b28f29dd2574869cd72455cb6253ab5');

INSERT INTO resources (name, max_volume, parent_id) VALUES
('A', 10, NULL),
('B', 50, (SELECT id FROM resources WHERE name = 'A')),
('C', 45, (SELECT id FROM resources WHERE name = 'B')),
('D', 15, (SELECT id FROM resources WHERE name = 'C')),
('X', 100, (SELECT id FROM resources WHERE name = 'A')),
('Y', 200, (SELECT id FROM resources WHERE name = 'X'));

INSERT INTO permissions (user_id, resource_id, action)
VALUES
((SELECT id FROM users WHERE login = 'alice'),
 (SELECT id FROM resources WHERE name = 'B'),
 'READ'),
((SELECT id FROM users WHERE login = 'alice'),
 (SELECT id FROM resources WHERE name = 'B'),
 'WRITE'),
((SELECT id FROM users WHERE login = 'alice'),
 (SELECT id FROM resources WHERE name = 'B'),
 'EXECUTE'),
((SELECT id FROM users WHERE login = 'bob'),
 (SELECT id FROM resources WHERE name = 'X'),
 'READ');