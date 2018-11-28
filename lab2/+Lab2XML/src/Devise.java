import java.util.Comparator;

public class Devise
{
    private Component[] Components;

    public Component[] getComponent ()
    {
        return Components;
    }

    public void setComponent (Component[] Components)
    {
        this.Components = Components;
    }

    @Override
    public String toString(){
        String dev = "";
        for(int i = 0; i < Components.length; i++) {
            dev += Components[i].toString();
            dev +="\n";
        }
        return dev;
    }



}

class SortByName implements Comparator<Component>
{
    public int compare(Component first, Component second){
        return first.getName().compareTo(second.getName());
    }
}
class SortByPrice implements Comparator<Component>
{
    public int compare(Component first, Component second){
        if(first.getPrise() - second.getPrise() < 0)
        {
            return -1;
        }
        else if(first.getPrise() == second.getPrise())
        {
            return 0;
        }
        return 1;
    }
}

