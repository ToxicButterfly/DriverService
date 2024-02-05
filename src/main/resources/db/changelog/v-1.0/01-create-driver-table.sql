create table driver (
                        availability boolean not null,
                        id serial not null,
                        rating float4,
                        register_date timestamp(6),
                        email varchar(255),
                        full_name varchar(255),
                        password varchar(255),
                        username varchar(255),
                        primary key (id)
)

