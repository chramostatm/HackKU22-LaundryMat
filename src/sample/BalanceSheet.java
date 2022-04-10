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
    public BalanceSheet() {
        this.debt = 0;
        this.currentCapital = 0;
        this.interest = 0;
    }
}

