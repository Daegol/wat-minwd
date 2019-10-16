import org.ojalgo.OjAlgoUtils;
import org.ojalgo.netio.BasicLogger;
import org.ojalgo.optimisation.Expression;
import org.ojalgo.optimisation.ExpressionsBasedModel;
import org.ojalgo.optimisation.Optimisation;
import org.ojalgo.optimisation.Variable;

public class Main {

    public static void main(String[] args) {

       // BasicLogger.debug();
       // BasicLogger.debug(Main.class.getSimpleName());
       // BasicLogger.debug(OjAlgoUtils.getTitle());
       // BasicLogger.debug(OjAlgoUtils.getDate());
       // BasicLogger.debug();

        // Create variables expressing servings of each of the considered foods
        // Set lower and upper limits on the number of servings as well as the weight (cost of a
        // serving) for each variable.
        final Variable tmpBread = Variable.make("Bread").lower(0).upper(10).weight(0.05);
        final Variable tmpCorn = Variable.make("Corn").lower(0).upper(10).weight(0.18);
        final Variable tmpMilk = Variable.make("Milk").lower(0).upper(10).weight(0.23);

        // Create a model and add the variables to it.
        final ExpressionsBasedModel tmpModel = new ExpressionsBasedModel();
        tmpModel.addVariable(tmpBread);
        tmpModel.addVariable(tmpCorn);
        tmpModel.addVariable(tmpMilk);

        // Create a vitamin A constraint.
        // Set lower and upper limits and then specify how much vitamin A a serving of each of the
        // foods contain.
        final Expression tmpVitaminA = tmpModel.addExpression("Vitamin A").lower(5000).upper(50000);
        tmpVitaminA.set(tmpBread, 0).set(tmpCorn, 107).set(tmpMilk, 500);

        // Create a calories constraint...
        final Expression tmpCalories = tmpModel.addExpression("Calories").lower(2000).upper(2250);
        tmpCalories.set(tmpBread, 65).set(tmpCorn, 72).set(tmpMilk, 121);

        // Solve the problem - minimise the cost
        Optimisation.Result tmpResult = tmpModel.minimise();

        // Print the result
        BasicLogger.debug();
        BasicLogger.debug(tmpResult);
        BasicLogger.debug();

        // Modify the model to require an integer valued solution.
        BasicLogger.debug("Adding integer constraints...");
        tmpBread.integer(true);
        tmpCorn.integer(true);
        tmpMilk.integer(true);

        // Solve again
        tmpResult = tmpModel.minimise();

        // Print the result, and the model
        BasicLogger.debug();
        BasicLogger.debug(tmpResult);
        BasicLogger.debug();
        BasicLogger.debug(tmpModel);
        BasicLogger.debug();
    }
}
