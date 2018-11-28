public class Type {
    private String Port;
    private int EneryCons;
    private String Cooler;
    private String Group;
    public String getPort ()
    {
        return Port;
    }
    public void setPort (String Port)
    {
        this.Port = Port;
    }
    public int getEneryCons ()
    {
        return EneryCons;
    }
    public void setEneryCons (int EneryCons)
    {
        this.EneryCons = EneryCons;
    }
    public String getCooler ()
    {
        return Cooler;
    }
    public void setCooler (String Cooler)
    {
        this.Cooler = Cooler;
    }
    public String getGroup ()
    {
        return Group;
    }
    public void setGroup (String Group)
    {
        this.Group = Group;
    }
    @Override
    public String toString(){
        String type = "\tEnergy consuming: " + EneryCons + "\n\tCooler: " + Cooler + "\n\tGroup:" + Group
                + "\n\tPort: " + Port;

        return type;
    }
}
