
CREATE TABLE IF NOT EXISTS application (
    id uuid,
    application_no text,
    applicant_name text,
    applicant_email text,
    applicant_mobile text,
    applicant_dob tuple<timestamp,varchar>,
    applicant_address text,
    course text,
    institute text,
    speciality text,
    course_doc text,
    first_preference text,
    second_preference text,
    third_preference text,
    entryby text,
    entry_date tuple<timestamp,varchar>,
    status text,
    PRIMARY KEY(id)
);
