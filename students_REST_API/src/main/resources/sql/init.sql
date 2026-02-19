-- Table Creation
CREATE TABLE IF NOT EXISTS student (
    reg_no VARCHAR(255) PRIMARY KEY,
    roll_no INTEGER NOT NULL,
    name VARCHAR(255) NOT NULL,
    standard INTEGER NOT NULL,
    school VARCHAR(255) NOT NULL,
    gender VARCHAR(20) NOT NULL,
    percentage DOUBLE PRECISION NOT NULL
);

-- Dummy Data for Testing
INSERT INTO student (reg_no, roll_no, name, standard, school, gender, percentage) VALUES
('R001', 1, 'Alice', 5, 'DPS', 'FEMALE', 85.5),
('R002', 2, 'Bob', 5, 'DPS', 'MALE', 78.0),
('R003', 3, 'Charlie', 5, 'KV', 'MALE', 35.0),
('R004', 4, 'Diana', 6, 'KV', 'FEMALE', 92.0),
('R005', 5, 'Eve', 6, 'DPS', 'FEMALE', 45.0),
('R006', 6, 'Frank', 5, 'KV', 'MALE', 38.0),
('R007', 7, 'Grace', 7, 'DPS', 'FEMALE', 60.0),
('R008', 8, 'Hank', 7, 'KV', 'MALE', 88.0),
('R009', 9, 'Ivy', 5, 'DPS', 'FEMALE', 41.0),
('R010', 10, 'Jack', 6, 'KV', 'MALE', 39.5);
