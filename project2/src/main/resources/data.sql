insert into _user(id, firstname, lastname, username, email, password, enabled)
values (1,'Admin','Root','admin','admin@admin.com','{noop}admin',true),
       (2,'Reader','One','reader','reader@x.com','{noop}reader',true),
       (3,'Member','One','member','member@x.com','{noop}member',true);

-- roles (element collection) live in join table; with H2 + element collection, Spring will create a table.
-- Add via application runner in Java if you prefer, but for demo you can register through /api/v1/auth/register.

insert into news(id, topic, summary, content, reporter_name, reported_at, image_url)
values (100, 'News A', 'Short A','Long Content A','admin', CURRENT_TIMESTAMP, null),
       (101, 'News B', 'Short B','Long Content B','member', CURRENT_TIMESTAMP, null),
       (102, 'News C', 'Short C','Long Content C','reader', CURRENT_TIMESTAMP, null);
