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

GO

INSERT INTO driver(full_name, username, email, password, register_date, availability, rating)
VALUES ('Иван Иванов', 'Vanyuha', 'Vano228@tut.by', 'Vanya228', '12-12-2015', true, '3.0');