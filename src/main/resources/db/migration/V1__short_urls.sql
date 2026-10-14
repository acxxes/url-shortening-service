create table public.short_urls
(
    id           bigint generated always as identity
        primary key,
    url          text                                   not null,
    short_code   varchar(50)                            not null
        unique,
    created_at   timestamp with time zone default now() not null,
    updated_at   timestamp with time zone default now() not null,
    access_count bigint                   default 0     not null
);


