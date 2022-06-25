DROP TABLE IF EXISTS Point_History;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Point;

create table point (
                       id BINARY(16) not null,
                       point integer,
                       created_time timestamp,
                       updated_time timestamp,
                       deleted_time timestamp,
                       primary key (id)
);

create table point_history (
                               id BINARY(16) not null,
                               type varchar(255),
                               change integer,
                               image_num integer,
                               place_id BINARY(16),
                               review_id BINARY(16),
                               point_id BINARY(16),
                               created_time timestamp,
                               updated_time timestamp,
                               deleted_time timestamp,
                               primary key (id)
);

create table user (
                      id BINARY(16) not null,
                      email varchar(255),
                      point_id BINARY(16),
                      created_time timestamp,
                      updated_time timestamp,
                      deleted_time timestamp,
                      primary key (id)
);

create index ph_index on point_history (review_id, place_id);

