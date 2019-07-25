
CREATE TABLE IF NOT EXISTS applicationLog (
    id uuid,
    application_no text,
    action text,
    action_by text,
    action_date tuple<timestamp,varchar>,
    PRIMARY KEY(id)
);
