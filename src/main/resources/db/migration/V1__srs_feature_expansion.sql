-- SRS-15 feature expansion.
-- Existing core tables are intentionally NOT recreated here; the application
-- currently manages those legacy tables with JPA. Flyway baselines existing DBs
-- and creates only the additional normalized feature tables below.

CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_code VARCHAR(80) NOT NULL UNIQUE,
    role_name VARCHAR(150) NOT NULL,
    description VARCHAR(500),
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS permissions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    permission_code VARCHAR(120) NOT NULL UNIQUE,
    permission_name VARCHAR(180) NOT NULL,
    description VARCHAR(500),
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    assigned_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    assigned_by BIGINT,
    PRIMARY KEY (user_id, role_id),
    INDEX idx_user_roles_role (role_id)
);

CREATE TABLE IF NOT EXISTS role_permissions (
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    INDEX idx_role_permissions_permission (permission_id)
);

CREATE TABLE IF NOT EXISTS bank_accounts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    borrower_id BIGINT,
    shg_id BIGINT,
    account_holder_name VARCHAR(180) NOT NULL,
    account_number VARCHAR(80) NOT NULL,
    ifsc_code VARCHAR(20) NOT NULL,
    bank_name VARCHAR(180),
    account_type VARCHAR(40),
    verification_status VARCHAR(40) DEFAULT 'PENDING',
    verification_reference VARCHAR(120),
    verified_at DATETIME,
    is_primary BOOLEAN DEFAULT FALSE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_bank_account_number (account_number)
);

CREATE TABLE IF NOT EXISTS group_members (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    group_type VARCHAR(20) NOT NULL,
    group_id BIGINT NOT NULL,
    borrower_id BIGINT NOT NULL,
    member_role VARCHAR(40) DEFAULT 'MEMBER',
    joined_date DATE,
    exit_date DATE,
    status VARCHAR(40) DEFAULT 'ACTIVE',
    attendance_percent DECIMAL(5,2),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_group_borrower (group_type, group_id, borrower_id),
    INDEX idx_group_members_group (group_type, group_id),
    INDEX idx_group_members_borrower (borrower_id)
);

CREATE TABLE IF NOT EXISTS group_meetings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    group_type VARCHAR(20) NOT NULL,
    group_id BIGINT NOT NULL,
    meeting_date DATE NOT NULL,
    meeting_type VARCHAR(50),
    agenda TEXT,
    minutes TEXT,
    conducted_by BIGINT,
    gps_lat DECIMAL(10,7),
    gps_lng DECIMAL(10,7),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_group_meetings_group_date (group_type, group_id, meeting_date)
);

CREATE TABLE IF NOT EXISTS group_meeting_attendance (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    meeting_id BIGINT NOT NULL,
    borrower_id BIGINT NOT NULL,
    present BOOLEAN NOT NULL DEFAULT FALSE,
    remarks VARCHAR(500),
    UNIQUE KEY uk_meeting_borrower (meeting_id, borrower_id)
);

CREATE TABLE IF NOT EXISTS loan_application_documents (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    application_id BIGINT NOT NULL,
    document_type VARCHAR(80) NOT NULL,
    document_name VARCHAR(255) NOT NULL,
    document_url VARCHAR(1000),
    verification_status VARCHAR(40) DEFAULT 'PENDING',
    verified_by BIGINT,
    verified_at DATETIME,
    remarks VARCHAR(1000),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_app_docs_application (application_id)
);

CREATE TABLE IF NOT EXISTS loan_approvals (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    application_id BIGINT NOT NULL,
    approval_level INT NOT NULL,
    approver_user_id BIGINT NOT NULL,
    decision VARCHAR(40) NOT NULL,
    remarks VARCHAR(2000),
    decided_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_application_level (application_id, approval_level)
);

CREATE TABLE IF NOT EXISTS loan_delinquency (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    loan_id BIGINT NOT NULL,
    snapshot_date DATE NOT NULL,
    dpd INT NOT NULL DEFAULT 0,
    par_bucket VARCHAR(30),
    npa_status VARCHAR(40),
    outstanding_amount DECIMAL(18,2) DEFAULT 0,
    early_warning_flag BOOLEAN DEFAULT FALSE,
    reason VARCHAR(1000),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_delinquency_snapshot (loan_id, snapshot_date)
);

CREATE TABLE IF NOT EXISTS recovery_actions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    loan_id BIGINT NOT NULL,
    borrower_id BIGINT NOT NULL,
    action_type VARCHAR(80) NOT NULL,
    action_date DATE NOT NULL,
    assigned_to BIGINT,
    outcome VARCHAR(1000),
    next_action_date DATE,
    gps_lat DECIMAL(10,7),
    gps_lng DECIMAL(10,7),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_recovery_loan (loan_id)
);

CREATE TABLE IF NOT EXISTS shg_members (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    shg_id BIGINT NOT NULL,
    borrower_id BIGINT NOT NULL,
    member_role VARCHAR(40) DEFAULT 'MEMBER',
    joined_date DATE,
    exit_date DATE,
    status VARCHAR(40) DEFAULT 'ACTIVE',
    UNIQUE KEY uk_shg_member (shg_id, borrower_id)
);

CREATE TABLE IF NOT EXISTS shg_meetings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    shg_id BIGINT NOT NULL,
    meeting_date DATE NOT NULL,
    agenda TEXT,
    minutes TEXT,
    savings_collected DECIMAL(18,2) DEFAULT 0,
    conducted_by BIGINT,
    gps_lat DECIMAL(10,7),
    gps_lng DECIMAL(10,7),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS shg_meeting_attendance (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    meeting_id BIGINT NOT NULL,
    borrower_id BIGINT NOT NULL,
    present BOOLEAN NOT NULL DEFAULT FALSE,
    remarks VARCHAR(500),
    UNIQUE KEY uk_shg_meeting_member (meeting_id, borrower_id)
);

CREATE TABLE IF NOT EXISTS shg_internal_loans (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    shg_id BIGINT NOT NULL,
    borrower_id BIGINT NOT NULL,
    principal_amount DECIMAL(18,2) NOT NULL,
    interest_rate DECIMAL(7,3),
    issue_date DATE NOT NULL,
    maturity_date DATE,
    outstanding_amount DECIMAL(18,2),
    status VARCHAR(40) DEFAULT 'ACTIVE',
    purpose VARCHAR(500)
);

CREATE TABLE IF NOT EXISTS insurance_products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_code VARCHAR(80) NOT NULL UNIQUE,
    product_name VARCHAR(180) NOT NULL,
    insurer_name VARCHAR(180),
    coverage_type VARCHAR(80),
    premium_rate DECIMAL(7,3),
    coverage_amount DECIMAL(18,2),
    tenure_months INT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS insurance_policies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    policy_number VARCHAR(100) NOT NULL UNIQUE,
    insurance_product_id BIGINT NOT NULL,
    borrower_id BIGINT NOT NULL,
    loan_id BIGINT,
    nominee_name VARCHAR(180),
    nominee_relationship VARCHAR(80),
    premium_amount DECIMAL(18,2),
    start_date DATE,
    end_date DATE,
    status VARCHAR(40) DEFAULT 'ACTIVE',
    insurer_reference VARCHAR(120),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS payment_transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    transaction_reference VARCHAR(120) NOT NULL UNIQUE,
    transaction_type VARCHAR(50) NOT NULL,
    loan_id BIGINT,
    borrower_id BIGINT,
    amount DECIMAL(18,2) NOT NULL,
    payment_mode VARCHAR(40),
    provider VARCHAR(80),
    provider_reference VARCHAR(150),
    status VARCHAR(40) DEFAULT 'PENDING',
    initiated_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    completed_at DATETIME,
    failure_reason VARCHAR(1000),
    reconciliation_status VARCHAR(40) DEFAULT 'PENDING'
);

CREATE TABLE IF NOT EXISTS nach_mandates (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    borrower_id BIGINT NOT NULL,
    loan_id BIGINT,
    mandate_reference VARCHAR(120) NOT NULL UNIQUE,
    bank_account_id BIGINT,
    max_amount DECIMAL(18,2),
    frequency VARCHAR(40),
    start_date DATE,
    end_date DATE,
    status VARCHAR(40) DEFAULT 'PENDING',
    registered_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS nach_transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    mandate_id BIGINT NOT NULL,
    emi_id BIGINT,
    transaction_date DATE NOT NULL,
    amount DECIMAL(18,2) NOT NULL,
    provider_reference VARCHAR(150),
    status VARCHAR(40) DEFAULT 'PENDING',
    return_code VARCHAR(50),
    return_reason VARCHAR(500),
    reconciled_at DATETIME
);

CREATE TABLE IF NOT EXISTS collection_visits (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    loan_id BIGINT,
    borrower_id BIGINT NOT NULL,
    collector_id BIGINT NOT NULL,
    visit_date DATETIME NOT NULL,
    visit_type VARCHAR(60),
    purpose VARCHAR(500),
    outcome VARCHAR(1000),
    gps_lat DECIMAL(10,7),
    gps_lng DECIMAL(10,7),
    next_visit_date DATE,
    offline_created BOOLEAN DEFAULT FALSE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS offline_sync_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    device_id VARCHAR(150) NOT NULL,
    client_record_id VARCHAR(150) NOT NULL,
    entity_type VARCHAR(100) NOT NULL,
    payload_json JSON NOT NULL,
    client_created_at DATETIME,
    received_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    sync_status VARCHAR(40) DEFAULT 'PENDING',
    conflict_reason VARCHAR(1000),
    UNIQUE KEY uk_offline_client_record (device_id, client_record_id)
);

CREATE TABLE IF NOT EXISTS staff_targets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    branch_id BIGINT,
    target_month DATE NOT NULL,
    collection_target DECIMAL(18,2) DEFAULT 0,
    disbursement_target DECIMAL(18,2) DEFAULT 0,
    borrower_target INT DEFAULT 0,
    achievement_percent DECIMAL(7,2) DEFAULT 0,
    UNIQUE KEY uk_staff_target_month (user_id, target_month)
);

CREATE TABLE IF NOT EXISTS staff_incentives (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    period_start DATE NOT NULL,
    period_end DATE NOT NULL,
    incentive_type VARCHAR(80),
    eligible_amount DECIMAL(18,2) DEFAULT 0,
    paid_amount DECIMAL(18,2) DEFAULT 0,
    status VARCHAR(40) DEFAULT 'CALCULATED',
    calculated_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS staff_visits (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    borrower_id BIGINT,
    group_id BIGINT,
    visit_date DATETIME NOT NULL,
    visit_type VARCHAR(60),
    gps_lat DECIMAL(10,7),
    gps_lng DECIMAL(10,7),
    notes VARCHAR(2000),
    status VARCHAR(40) DEFAULT 'PLANNED'
);

-- Seed the role catalogue without assuming application user IDs.
INSERT IGNORE INTO roles (role_code, role_name, description) VALUES
('ADMIN', 'Administrator', 'System administration and configuration'),
('LOAN_OFFICER', 'Loan Officer', 'Field sourcing, verification and collection'),
('BRANCH_MANAGER', 'Branch Manager', 'Branch operations and first-level approvals'),
('CREDIT_COMMITTEE', 'Credit Committee', 'Credit review and multi-level approval'),
('COMPLIANCE', 'Compliance Officer', 'KYC, audit and regulatory compliance'),
('BORROWER', 'Borrower', 'Borrower self-service access');

-- Explicit permissions from the SRS functional areas.
INSERT IGNORE INTO permissions (permission_code, permission_name, description) VALUES
('BORROWER_READ', 'Read borrowers', 'View borrower profiles'),
('BORROWER_WRITE', 'Manage borrowers', 'Create and update borrower records'),
('KYC_VERIFY', 'Verify KYC', 'Perform KYC verification'),
('LOAN_APPLY', 'Create loan applications', 'Create and submit loan applications'),
('LOAN_REVIEW', 'Review loan applications', 'Review applications and recommendations'),
('LOAN_APPROVE', 'Approve loans', 'Approve or reject applications'),
('LOAN_DISBURSE', 'Disburse loans', 'Create and reconcile disbursements'),
('REPAYMENT_COLLECT', 'Collect repayments', 'Record repayment transactions'),
('COLLECTION_VISIT', 'Manage field visits', 'Plan and record collection visits'),
('REPORT_VIEW', 'View reports', 'View MIS and regulatory reports'),
('AUDIT_VIEW', 'View audit trail', 'Review audit events'),
('PRODUCT_ADMIN', 'Manage loan products', 'Configure lending products'),
('GROUP_ADMIN', 'Manage groups', 'Manage JLG/SHG groups and meetings');

-- Initial role/permission catalogue (idempotent for repeatable local setup).
INSERT IGNORE INTO roles (role_code, role_name, description) VALUES
('BORROWER','Borrower','Loan applicant / borrower'),
('LOAN_OFFICER','Loan Officer','Field lending and collection officer'),
('BRANCH_MANAGER','Branch Manager','Branch approval and oversight'),
('CREDIT_COMMITTEE','Credit Committee','Multi-level credit approval'),
('COMPLIANCE_OFFICER','Compliance Officer','KYC, audit and regulatory compliance'),
('ADMIN','Administrator','System administration');

INSERT IGNORE INTO permissions (permission_code, permission_name, description) VALUES
('BORROWER_READ','Read Borrowers','View borrower records'),
('BORROWER_WRITE','Manage Borrowers','Create/update borrower records'),
('LOAN_APPLY','Create Loan Applications','Submit loan applications'),
('LOAN_APPROVE','Approve Loans','Approve or reject applications'),
('LOAN_DISBURSE','Disburse Loans','Process loan disbursement'),
('COLLECTION_WRITE','Record Collections','Record repayments and field visits'),
('REPORT_READ','View Reports','View portfolio and regulatory reports'),
('AUDIT_READ','View Audit Trail','View audit records'),
('ADMIN_MANAGE','Manage System','Manage users, roles and configuration');
