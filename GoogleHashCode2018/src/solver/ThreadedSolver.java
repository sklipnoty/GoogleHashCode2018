package solver;

import domain.Ride;
import domain.SelfDrivingRides;
import domain.Vehicle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import util.Utils;

public class ThreadedSolver
{
    private int NUM_THREADS = 4;
    private ExecutorService executor;
    private SelfDrivingRides sdr;

    public ThreadedSolver(SelfDrivingRides sdr)
    {
        this.executor = Executors.newFixedThreadPool(NUM_THREADS);
        this.sdr = sdr;
    }
    
    public Map<Vehicle, List<Ride>> solve() throws InterruptedException, ExecutionException
    {
        List<Callable<Solver>> solvers = new ArrayList<>();
        
        for (int i = 0; i < NUM_THREADS; i++)
        {
            solvers.add((Callable<Solver>) () ->
            {
                return new Solver(sdr, "geeftdannelegestringmeeboeimijnie");
            });
        }
        
        List<Future<Solver>> futureSolvers = executor.invokeAll(solvers);
//        executor.awaitTermination(1, TimeUnit.SECONDS);
//        executor.shutdown();
        
        Map<Vehicle, List<Ride>> solution = null;
        int bestScore = -1;
        
        for (Future<Solver> s : futureSolvers)
        {
            int score = Utils.calculateScoreForEntireSolution(s.get().solution, sdr);
            if (score > bestScore)
            {
                bestScore = score;
                solution = s.get().solution;
            }
        }
        
        return solution;
    }
}