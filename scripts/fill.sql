INSERT INTO users (login, salt, password_hash) VALUES
('alice', 'salt1', 'b28b8b8f64a92a2f83f9f8fd8a04f7f2d5d4b2ab3b6c6ddc07cc6a93d3b9b07a'),
('bob', 'salt2', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5');

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
