
public enum ProductCategory {
	BOARDGAME("board game", "Enter max number of players:", "Max Players"),
    ACCESSORY("accessory", "Enter compatibility:", "Compatibility");

    private final String displayName;
    private final String extraPrompt;
    private final String extraLabel;

    ProductCategory(String displayName, String extraPrompt, String extraLabel) {
        this.displayName = displayName;
        this.extraPrompt = extraPrompt;
        this.extraLabel = extraLabel;
    }

    public String getDisplayName() { return displayName; }
    public String getExtraPrompt() { return extraPrompt; }
    public String getExtraLabel() { return extraLabel; }
}

