
import domain.SelfDrivingRides;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class StartUp
{
    private static final String INPUT_FILE = "resources/a_example.in";
    
    public static void main(String[] args) throws IOException
    {
        Scanner in = new Scanner(Paths.get(INPUT_FILE).toAbsolutePath());
        SelfDrivingRides problem = new SelfDrivingRides(in);
    }
}
