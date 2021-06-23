package pl.nethos.rekrutacja.konto.dto;

public class WhiteListDto {
    private WhiteListResultDto result;

    public WhiteListResultDto getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "WhiteListDto{" +
                "result=" + result.toString() +
                '}';
    }
}
