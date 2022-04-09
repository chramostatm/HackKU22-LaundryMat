package sample;

public class BalanceSheet {
    private float debt = 10000;
    private float currentCapital = 500;

    private float interest = 0.01F;

    //if debt exceeds over $20,000 then you lose.

    public BalanceSheet(float startDebt, float startCapital, float interest) {
        this.debt = startDebt;
        this.currentCapital = startCapital;
        this.interest = interest;
    }


}
