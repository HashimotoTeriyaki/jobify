create table if not exists employment_type
(
    id          serial primary key not null,
    name        varchar(20)        not null,
    description varchar(300)       not null
);

insert into employment_type (name, description)
values ('B2B', 'Business-to-business is a situation where one business makes a commercial transaction with another.'),
       ('PERMANENT',
        'Permanent employment is a long-term employment arrangement where an individual is hired without a predetermined end date, typically providing job security, benefits, and a stable income.'),
       ('MANDATE',
        'A mandate contract is a legal agreement in which an individual (the mandatary) agrees to perform a specific task or service on behalf of another party (the mandator), usually for a specified period or until the task is completed.'),
       ('SPECIFIC_TASK',
        'A specific task contract is an employment agreement where an individual is hired to complete a defined task or project. The contract ends once the task is completed.'),
       ('INTERNSHIP',
        'An internship is a temporary position, often held by students or recent graduates, that provides practical experience in a particular field or profession, typically lasting for a fixed period and often with the possibility of leading to permanent employment.');

create table if not exists type_of_work
(
    id          serial primary key not null,
    name        varchar(20)        not null,
    description varchar(255)       not null
);

insert into type_of_work (name, description)
values ('FULL_TIME',
        'An employment in which workers work a minimum number of hours defined as such by their employer.'),
       ('PART_TIME', 'A form of employment that carries fewer hours per week than a full-time job.'),
       ('INTERNSHIP',
        'An internship is a temporary position within a company or organization, usually for a fixed period, where individuals, often students or recent graduates, gain practical experience and exposure to a particular industry or field.'),
       ('FREELANCE',
        'A form of self-employment where individuals offer their skills or services to clients or businesses on a temporary or project basis, working independently and without long-term employment commitments.');

create table if not exists experience_level
(
    id          serial primary key not null,
    level       varchar(20)        not null,
    description varchar(255)       not null
);

insert into experience_level (level, description)
values ('JUNIOR', 'Entry-level position typically held by individuals with limited experience or fresh graduates.'),
       ('MID', 'Intermediate level position requiring moderate experience and skill.'),
       ('SENIOR', 'Advanced level position typically held by experienced professionals with significant expertise.'),
       ('LEAD', 'Leadership or managerial level position responsible for guiding and overseeing projects or teams.');

create table if not exists operating_mode
(
    id          serial primary key not null,
    name        varchar(20)        not null,
    description varchar(255)       not null
);

insert into operating_mode (name, description)
values ('REMOTE',
        'Employees work entirely from a location other than the office, often from home or another remote location.'),
       ('HYBRID',
        'Combination of remote and office work, where employees have the flexibility to work from both home and the office, typically on scheduled days.'),
       ('OFFICE', 'Employees work primarily from the office location, adhering to a traditional in-office work setup.');

create table if not exists main_technology
(
    id   serial primary key not null,
    name varchar(20)        not null
);

insert into main_technology (name)
values ('JS'),
       ('HTML'),
       ('PHP'),
       ('RUBY'),
       ('PYTHON'),
       ('JAVA'),
       ('.NET'),
       ('SCALA'),
       ('C'),
       ('MOBILE'),
       ('TESTING'),
       ('DEVOPS'),
       ('ADMIN'),
       ('UXUI'),
       ('PM'),
       ('GAME'),
       ('ANALYTICS'),
       ('SECURITY'),
       ('DATA'),
       ('GO'),
       ('SUPPORT'),
       ('ERP'),
       ('ARCHITECTURE');

create table if not exists currency
(
    id   serial primary key not null,
    name varchar(20)        not null
);

insert into currency (name)
values ('PLN'),
       ('EUR'),
       ('USD'),
       ('GBP');

create table if not exists job_offer
(
    id                  serial primary key not null,
    title               varchar(100)       not null,
    company_name        varchar(20)        not null,
    city                varchar(20)        not null,
    street              varchar(20)        not null,
    description         varchar(4400)      not null,
    remote_interview    boolean            not null,
    main_technology_id  int                not null,
    experience_level_id int                not null,
    type_of_work_id     int                not null,
    apply_url           varchar(255),
    contact_email       varchar(255)       not null,
    contact_phone       varchar(255),
    created_date        timestamp(6)       not null,
    last_modified_date  timestamp(6)       not null,
    constraint fk_main_technology foreign key (main_technology_id) references main_technology (id),
    constraint fk_experience_level foreign key (experience_level_id) references experience_level (id),
    constraint fk_type_of_work foreign key (type_of_work_id) references type_of_work (id)
);

create table if not exists offer_operating_mode
(
    id                serial primary key not null,
    job_offer_id      int,
    operating_mode_id int                not null,
    constraint fk_job_offer foreign key (job_offer_id) references job_offer (id)
);

create table if not exists employment
(
    id                 serial primary key not null,
    salary_from        int,
    salary_to          int,
    employment_type_id int                not null,
    currency_id        int                not null,
    job_offer_id       int,
    constraint fk_employment_type foreign key (employment_type_id) references employment_type (id),
    constraint fk_currency foreign key (currency_id) references currency (id),
    constraint fk_job_offer foreign key (job_offer_id) references job_offer (id)
);

create table if not exists skill
(
    id   serial primary key not null,
    name varchar(20)        not null
);

insert into skill (name)
values
    -- Programming Languages
    ('JAVA'),
    ('PYTHON'),
    ('JAVASCRIPT'),
    ('C#'),
    ('C++'),
    ('PHP'),
    ('TYPESCRIPT'),
    ('RUBY'),
    ('SWIFT'),
    ('GO'),
    ('KOTLIN'),
    ('RUST'),
    -- Frameworks
    ('SPRING BOOT'),
    ('REACT'),
    ('ANGULAR'),
    ('VUE.JS'),
    ('.NET CORE'),
    ('DJANGO'),
    ('FLASK'),
    ('NODE.JS'),
    ('RUBY ON RAILS'),
    ('EXPRESS.JS'),
    ('LARAVEL'),
    ('ASP.NET MVC');

create table if not exists required_skill
(
    id           serial primary key                         not null,
    level        int check ( (level <= 5) and (level >= 1)) not null,
    job_offer_id int,
    skill_id     int                                        not null,
    constraint fk_job_offer foreign key (job_offer_id) references job_offer (id),
    constraint fk_skill foreign key (skill_id) references skill (id)
);