
CREATE TABLE IF NOT EXISTS applicationlog (
    id uuid,
    application_no text,
    action text,
    action_by text,
    action_date tuple<timestamp,varchar>,
    PRIMARY KEY(id)
);
