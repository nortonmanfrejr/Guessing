import java.util.*;

public class Guessing
{
    static Random rd = new Random();
    static Scanner sc = new Scanner(System.in);

    private static final String Success = "Parabêns você acertou o numero aleatório.";
    private static final String Wrong = "Você errou o numero, deseja continuar?";
    private static final String LastAttempt = "Você está entrando na sua ultima tentativa, boa sorte!";

    private static final String EndGuessing = "Você está encerrando suas tentantivas de adivinhação, até a proxima.";

    private static Integer GeneratedRandomNumber;
    private static List<Integer> HistoryAttempts;

    public static void main(String[] args)
    {
        RandomNumbGen();
        int Attempts = 5;

        HistoryAttempts = new ArrayList<>();

        for(int i = 1; i <= Attempts; i++)
        {

            if (i == Attempts) print(LastAttempt);
            print(String.format("Tentativa %d de %d", i, Attempts));

            int Guess = sc.nextInt();
            
            while(Exists(Guess, HistoryAttempts.toArray()))
            {
                print(String.format("Você já tentou com o número %d, use outro.", Guess));
                Guess = sc.nextInt();
            }
            HistoryAttempts.add(Guess);

            // case guess equals random number, you are winner and break the loop.
            if (Guess == GeneratedRandomNumber)
            {
                print (Success);
                ShowHistory();
                break;
            }

            String MinorHigher = (Guess < GeneratedRandomNumber) ? "Menor" : "Maior";
            print(String.format("O Numero que você digitou ainda é %s que o numero aleatório\n / ---------------- /", MinorHigher));

            print(Wrong);
            if (!GuessingContinue(sc.next()))
            {
                print(EndGuessing);
                ShowHistory();
                break;
            }
        }
    }

    // -------------------------------------------------------------------------------------------
    // Aux Tools
    private static final String[] ValidStringsToContinue = {"SIM","S","YES","Y"};
    private static final String[] ValidStringsToStop = {"NAO","NÃO","N","NO"};

    private static boolean Exists(Object anyValue, Object[] anyList)
    {
        return Arrays.asList(anyList).contains(anyValue);
    }

    private static boolean GuessingContinue(String s)
     {
        s = s.toUpperCase();
        int ErrorAttemptsToContinue = 0;

        String ErrMsg;
        while(!Exists(s,ValidStringsToContinue) && !Exists(s,ValidStringsToStop))
        {
            ErrorAttemptsToContinue++;
            ErrMsg = ErrorAttemptsToContinue > 2
                    ? "Não reconheço o que foi escrito, por favor escreva uma palavra afirmativa/negativa. EX: S/N caso deseja continuar."
                    : "Identifiquei que você está escrevendo algo invalido, digite S/N para continuar.";
            print(ErrMsg);

            s = sc.next();
            s = s.toUpperCase();
        }

        // verify the existence of s in array and return a boolean.
        return Exists(s, ValidStringsToContinue);
    }

    // Create this function to reduce the quantity times where you write System.out.println()
    // Change 12/2 for 6
    private static void print(String str)
    {
        System.out.println(str);
    }

    private static final int MinLimit = 1;
    private static void RandomNumbGen()
    {
        print("Defina um valor limite para randomização do numero, sendo sempre numeros positivos");
        int NumberLimit = sc.nextInt();

        while (NumberLimit < MinLimit) // while minor MinLimit don't accept
        {
            print("Numero limite definido invalido, por favor defina outro.");
            NumberLimit = sc.nextInt();
        }

        GeneratedRandomNumber = rd.nextInt(NumberLimit);
    }

    private static void ShowHistory()
    {
        int HistorySize = HistoryAttempts.size();
        print(String.format("Você finalizou o jogo em %d tentativas, segue o histórico de tentativas.", HistorySize));
        for (int i = 0; i < HistorySize; i++)
            print(String.format("%dº Tentativa: %d", i + 1, HistoryAttempts.get(i)));
    }
}