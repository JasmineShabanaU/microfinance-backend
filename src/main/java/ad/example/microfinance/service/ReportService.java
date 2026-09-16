package ad.example.microfinance.service;

import java.util.Map;

public interface ReportService {
    Map<String, Object> getParReport();
    Map<String, Object> getMfinQuarterlyReport();
    Map<String, Object> getRbiPrioritySectorReport();
    String generateBureauSubmissionFile();
    Map<String, Object> getDailyPortfolioSummary();
}
