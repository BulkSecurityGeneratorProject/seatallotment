
CREATE TABLE IF NOT EXISTS seatPartition (
    id uuid,
    district text,
    total int,
    vacant int,
    occupied int,
    PRIMARY KEY(id)
);
