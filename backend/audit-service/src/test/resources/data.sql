INSERT INTO projects (id, project_name, project_code, description, status, start_date, end_date, created_by, created_at, updated_by, updated_at)
VALUES (1, 'Test Project', 'TEST001', 'Test Project Description', 'Active', '2024-03-20', '2024-12-31', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP);

INSERT INTO personnel (id, project_id, employee_id, name, role, department, company, start_date, end_date, pledge_status, pledge_submission_date, pledge_file_path, created_by, created_at, updated_by, updated_at)
VALUES (1, 1, 'EMP001', 'John Doe', 'Developer', 'IT', 'AGS', '2024-03-20', '2024-12-31', 'Submitted', '2024-03-20', '/pledges/EMP001.pdf', 'admin', CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP); 