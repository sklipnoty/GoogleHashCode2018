
import domain.SelfDrivingRides;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
import output.SolutionWriter;
import solver.Solver;

public class StartUp
{
    private static final String INPUT_FILE = "resources/b_should_be_easy.in";
    
    public static void main(String[] args) throws IOException
    {
        Scanner in = new Scanner(Paths.get(INPUT_FILE).toAbsolutePath());
        SelfDrivingRides problem = new SelfDrivingRides(in);
        Solver solv = new Solver(problem);
        SolutionWriter solutionWriter = new SolutionWriter();
        solutionWriter.writeSolution(solv.rides);
    }
}
