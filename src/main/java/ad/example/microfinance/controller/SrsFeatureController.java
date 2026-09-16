package ad.example.microfinance.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.*;

/**
 * REST facade for the normalized SRS-15 feature tables.
 * The table whitelist prevents arbitrary SQL/table access while keeping the
 * college project easy to extend from the React UI.
 */
@RestController
@RequestMapping("/api/v1/srs")
@CrossOrigin(origins = {"http://localhost:8081", "http://127.0.0.1:8081", "http://localhost:3000"})
public class SrsFeatureController {

    private final JdbcTemplate jdbc;

    private static final Set<String> TABLES = Set.of(
            "roles", "permissions", "bank_accounts", "group_members", "group_meetings",
            "group_meeting_attendance", "loan_application_documents", "loan_approvals",
            "loan_delinquency", "recovery_actions", "shg_members", "shg_meetings",
            "shg_meeting_attendance", "shg_internal_loans", "insurance_products",
            "insurance_policies", "payment_transactions", "nach_mandates", "nach_transactions",
            "collection_visits", "offline_sync_records", "staff_targets", "staff_incentives",
            "staff_visits"
    );

    private static final Map<String, String> LABELS = Map.ofEntries(
            Map.entry("roles", "Roles"), Map.entry("permissions", "Permissions"),
            Map.entry("bank_accounts", "Bank Accounts"), Map.entry("group_members", "Group Members"),
            Map.entry("group_meetings", "Group Meetings"), Map.entry("group_meeting_attendance", "Meeting Attendance"),
            Map.entry("loan_application_documents", "Loan Application Documents"), Map.entry("loan_approvals", "Loan Approvals"),
            Map.entry("loan_delinquency", "Loan Delinquency"), Map.entry("recovery_actions", "Recovery Actions"),
            Map.entry("shg_members", "SHG Members"), Map.entry("shg_meetings", "SHG Meetings"),
            Map.entry("shg_meeting_attendance", "SHG Meeting Attendance"), Map.entry("shg_internal_loans", "SHG Internal Loans"),
            Map.entry("insurance_products", "Insurance Products"), Map.entry("insurance_policies", "Insurance Policies"),
            Map.entry("payment_transactions", "Payment Transactions"), Map.entry("nach_mandates", "NACH Mandates"),
            Map.entry("nach_transactions", "NACH Transactions"), Map.entry("collection_visits", "Collection Visits"),
            Map.entry("offline_sync_records", "Offline Sync"), Map.entry("staff_targets", "Staff Targets"),
            Map.entry("staff_incentives", "Staff Incentives"), Map.entry("staff_visits", "Staff Visits")
    );

    public SrsFeatureController(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    @GetMapping("/modules")
    public List<Map<String, Object>> modules() {
        return TABLES.stream().sorted().map(t -> {
            Map<String,Object> m = new LinkedHashMap<>();
            m.put("key", t); m.put("name", LABELS.getOrDefault(t, t));
            m.put("columns", columns(t));
            return m;
        }).toList();
    }

    @GetMapping("/{table}")
    public List<Map<String,Object>> all(@PathVariable String table) {
        checkTable(table);
        return jdbc.queryForList("SELECT * FROM " + table + " ORDER BY id DESC");
    }

    @GetMapping("/{table}/{id}")
    public Map<String,Object> one(@PathVariable String table, @PathVariable Long id) {
        checkTable(table);
        List<Map<String,Object>> rows = jdbc.queryForList("SELECT * FROM " + table + " WHERE id = ?", id);
        if (rows.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Record not found");
        return rows.get(0);
    }

    @PostMapping("/{table}")
    public ResponseEntity<Map<String,Object>> create(@PathVariable String table, @RequestBody Map<String,Object> body) {
        checkTable(table);
        Map<String,String> allowed = columns(table);
        List<String> names = new ArrayList<>(); List<Object> values = new ArrayList<>();
        body.forEach((k,v) -> {
            if (!"id".equalsIgnoreCase(k) && allowed.containsKey(k.toLowerCase())) { names.add(allowed.get(k.toLowerCase())); values.add(v); }
        });
        if (names.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No valid fields supplied");
        String placeholders = String.join(",", Collections.nCopies(names.size(), "?"));
        jdbc.update("INSERT INTO " + table + " (" + String.join(",", names) + ") VALUES (" + placeholders + ")", values.toArray());
        return ResponseEntity.status(HttpStatus.CREATED).body(last(table));
    }

    @PutMapping("/{table}/{id}")
    public Map<String,Object> update(@PathVariable String table, @PathVariable Long id, @RequestBody Map<String,Object> body) {
        checkTable(table); Map<String,String> allowed = columns(table);
        List<String> sets = new ArrayList<>(); List<Object> values = new ArrayList<>();
        body.forEach((k,v) -> {
            if (!"id".equalsIgnoreCase(k) && allowed.containsKey(k.toLowerCase())) { sets.add(allowed.get(k.toLowerCase()) + " = ?"); values.add(v); }
        });
        if (sets.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No valid fields supplied");
        values.add(id);
        int count = jdbc.update("UPDATE " + table + " SET " + String.join(",", sets) + " WHERE id = ?", values.toArray());
        if (count == 0) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Record not found");
        return one(table, id);
    }

    @DeleteMapping("/{table}/{id}")
    public ResponseEntity<Void> delete(@PathVariable String table, @PathVariable Long id) {
        checkTable(table); int count = jdbc.update("DELETE FROM " + table + " WHERE id = ?", id);
        if (count == 0) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Record not found");
        return ResponseEntity.noContent().build();
    }

    private void checkTable(String table) {
        if (!TABLES.contains(table)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unknown SRS module");
    }

    private Map<String,String> columns(String table) {
        try {
            Map<String,String> result = new LinkedHashMap<>();
            try (var connection = Objects.requireNonNull(jdbc.getDataSource()).getConnection()) {
                DatabaseMetaData md = connection.getMetaData();
                try (ResultSet rs = md.getColumns(null, null, table, null)) {
                    while (rs.next()) result.put(rs.getString("COLUMN_NAME").toLowerCase(), rs.getString("COLUMN_NAME"));
                }
            }
            return result;
        } catch (Exception e) { throw new IllegalStateException("Unable to inspect database schema", e); }
    }

    private Map<String,Object> last(String table) {
        List<Map<String,Object>> rows = jdbc.queryForList("SELECT * FROM " + table + " ORDER BY id DESC LIMIT 1");
        return rows.isEmpty() ? Map.of() : rows.get(0);
    }
}
