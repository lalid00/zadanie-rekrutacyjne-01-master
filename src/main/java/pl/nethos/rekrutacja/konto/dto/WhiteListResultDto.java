package pl.nethos.rekrutacja.konto.dto;


public class WhiteListResultDto {
    private String accountAssigned;
    private String requestDateTime;
    private String requestId;

    public String getAccountAssigned() {
        return accountAssigned;
    }

    public String getRequestDateTime() {
        return requestDateTime;
    }

    public String getRequestId() {
        return requestId;
    }

    @Override
    public String toString() {
        return "WhiteListResultDto{" +
                "accountAssigned='" + accountAssigned + '\'' +
                ", requestDateTime='" + requestDateTime + '\'' +
                ", requestId='" + requestId + '\'' +
                '}';
    }
}
