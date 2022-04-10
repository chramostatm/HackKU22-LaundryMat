package sample;

public class BalanceSheet {
    public float debt = 10000;
    public float currentCapital = 500;
    public float interest = 0.01F;

    //if debt exceeds over $20,000 then you lose.

    public BalanceSheet(float startDebt, float startCapital, float interest) {
        this.debt = startDebt;
        this.currentCapital = startCapital;
        this.interest = interest;
    }
    public BalanceSheet()
    {}

    public float getCurrentCapital() {
        return currentCapital;
    }

    public void repair()
    {
        currentCapital -= Constants.repairCost;
    }

    public void setCurrentCapital(float inCapital)
    {
        currentCapital = inCapital;
    }
}

