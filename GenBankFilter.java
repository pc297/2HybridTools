import java.io.FilenameFilter;
import java.io.File;
class GenBankFilter implements FilenameFilter
{
    public boolean accept(File dir, String name)
    {
        return (name.endsWith(".gb"));
    }
}

