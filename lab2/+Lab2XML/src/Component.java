public class Component
{
    private String id;

    private String Name;

    private float Prise;

    private Type type;

    private String nameAtr;

    private String Critical;

    private String Origin;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getName ()
    {
        return Name;
    }

    public void setName (String Name)
    {
        this.Name = Name;
    }

    public float getPrise ()
    {
        return Prise;
    }

    public void setPrise (float Prise)
    {
        this.Prise = Prise;
    }

    public Type getType ()
    {
        return type;
    }

    public void setType (Type type)
    {
        this.type = type;
    }

    public String getNameAtr ()
    {
        return nameAtr;
    }

    public void setNameAtr (String name)
    {
        this.nameAtr = name;
    }

    public String getCritical ()
    {
        return Critical;
    }

    public void setCritical (String Critical)
    {
        this.Critical = Critical;
    }

    public String getOrigin ()
    {
        return Origin;
    }

    public void setOrigin (String Origin)
    {
        this.Origin = Origin;
    }
    @Override
    public String toString(){
        String comp = "ID: " + id + "\nName: " + nameAtr + " " + Name + "\nOrigin: " + Origin + "\nPrice: " + Prise
                + "\nCritical: " + Critical + "\nType:\n" + type.toString();
        return comp;
    }
}