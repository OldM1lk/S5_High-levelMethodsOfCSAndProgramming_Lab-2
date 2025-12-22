ALTER TABLE resources
    ADD CONSTRAINT fk_resource_parent
    FOREIGN KEY (parent_id)
    REFERENCES resources(id);

ALTER TABLE permissions
    ADD CONSTRAINT fk_permission_user
    FOREIGN KEY (user_id)
    REFERENCES users(id);

ALTER TABLE permissions
    ADD CONSTRAINT fk_permission_resource
    FOREIGN KEY (resource_id)
    REFERENCES resources(id);
