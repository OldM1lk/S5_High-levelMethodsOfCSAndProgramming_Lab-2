INSERT INTO users (login, salt, password_hash) VALUES
('alice', 'salt1', '077317626a29b193cf722eb4ac84e22ccd50a69e2d88072c1f3b33b154cfc9b8'),
('bob', 'salt2', 'e1f114c42c6a926c821b0f8ffe49b38f6b28f29dd2574869cd72455cb6253ab5');

INSERT INTO resources (name, max_volume, parent_name) VALUES
('A', 10, NULL),
('B', 50, 'A'),
('C', 45, 'B'),
('D', 15, 'C'),
('X', 100, 'A'),
('Y', 200, 'X');

INSERT INTO permissions (user_login, resource_name, action) VALUES
('alice', 'B', 'READ'),
('alice', 'B', 'WRITE'),
('alice', 'B', 'EXECUTE'),
('bob', 'X', 'READ');
