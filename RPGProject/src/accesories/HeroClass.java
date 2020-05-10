package accesories;

public enum HeroClass {
    MAGE("src/accesories/resources/mage_idle.png"),
    TANKER("src/accesories/resources/knight_idle.png"),
    ASSASIN("src/accesories/resources/monk_idle.png");
    private String urlClass;
    private HeroClass(String urlClass)
    {
        this.urlClass=urlClass;
    }
    public String getUrlClass()
    {
        return urlClass;
    }
}
