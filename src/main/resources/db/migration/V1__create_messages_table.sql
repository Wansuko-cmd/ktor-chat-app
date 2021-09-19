CREATE TABLE messages(
    id varchar(36) UNIQUE not null,
    user_name text not null,
    text text not null,
    created_at timestamp,
    updated_at timestamp
);
