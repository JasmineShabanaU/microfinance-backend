# SRS-15 Expanded Implementation

This version keeps the existing working borrower/loan/repayment/JLG/SHG APIs and adds the normalized SRS feature layer.

## Added database modules
- Roles and permissions
- Bank accounts
- JLG/SHG group membership
- Group meetings and attendance
- Loan application documents
- Multi-level loan approvals
- Delinquency / PAR / early-warning records
- Recovery actions
- SHG members, meetings, attendance and internal loans
- Insurance products and policies
- Payment transactions
- NACH mandates and transactions
- Collection visits and GPS
- Offline synchronization records
- Staff targets, incentives and field visits

## Added backend APIs
`/api/v1/srs/modules` returns the available SRS modules and columns.

For each module:
- `GET /api/v1/srs/{module}`
- `GET /api/v1/srs/{module}/{id}`
- `POST /api/v1/srs/{module}`
- `PUT /api/v1/srs/{module}/{id}`
- `DELETE /api/v1/srs/{module}/{id}`

Only a fixed whitelist of SRS tables is exposed.

## External integrations
The integration endpoints are provider-neutral MOCK adapters so the application can run without real provider credentials:
- Aadhaar eKYC
- Credit bureau/CIBIL
- Payment gateway
- NACH
- SMS
- Email

Replace the mock implementation with the licensed/provider API implementation when credentials and contracts are available. No real Aadhaar, CIBIL, bank, payment or messaging network is contacted by this student project build.

## Frontend
Added:
- SRS Modules page with module selector, create, edit, list and delete
- External Integrations status page
- Navigation links for both pages
- API client for the SRS feature layer

## Database migration
Flyway is enabled with `V1__srs_feature_expansion.sql`. Existing core tables remain managed by the current JPA model, while the new normalized SRS tables are created by Flyway.
