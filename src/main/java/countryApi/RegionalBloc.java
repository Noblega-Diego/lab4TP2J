package countryApi;

@lombok.Data
public class RegionalBloc {
    private String acronym;
    private String name;
    private String[] otherAcronyms;
    private String[] otherNames;
}
