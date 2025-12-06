public class TicketVendingMachine {

    public static void main(String[] args) {
        TicketMachine machine = new TicketMachine();
        machine.selectTicket(100);
        machine.insertMoney(50);
        machine.insertMoney(50);
        machine.dispenseTicket();
        machine.reset();
    }
}

class TicketMachine {
    private State idleState = new IdleState(this);
    private State waitingState = new WaitingForMoneyState(this);
    private State moneyReceivedState = new MoneyReceivedState(this);
    private State ticketDispensedState = new TicketDispensedState(this);
    private State canceledState = new TransactionCanceledState(this);

    private State state = idleState;
    private int moneyInserted = 0;
    private int ticketPrice = 0;

    public void setState(State state) {
        this.state = state;
    }

    public void selectTicket(int price) {
        this.ticketPrice = price;
        state.selectTicket();
    }

    public void insertMoney(int amount) {
        state.insertMoney(amount);
    }

    public void dispenseTicket() {
        state.dispenseTicket();
    }

    public void cancel() {
        state.cancel();
    }

    public void reset() {
        state.reset();
    }

    public int getTicketPrice() {
        return ticketPrice;
    }

    public int getMoneyInserted() {
        return moneyInserted;
    }

    public void addMoney(int amount) {
        moneyInserted += amount;
    }

    public void clearMoney() {
        moneyInserted = 0;
    }

    public State getIdleState() {
        return idleState;
    }

    public State getWaitingState() {
        return waitingState;
    }

    public State getMoneyReceivedState() {
        return moneyReceivedState;
    }

    public State getTicketDispensedState() {
        return ticketDispensedState;
    }

    public State getCanceledState() {
        return canceledState;
    }
}

interface State {
    void selectTicket();
    void insertMoney(int amount);
    void dispenseTicket();
    void cancel();
    void reset();
}

class IdleState implements State {
    private TicketMachine machine;

    public IdleState(TicketMachine m) {
        this.machine = m;
    }

    public void selectTicket() {
        machine.setState(machine.getWaitingState());
    }

    public void insertMoney(int amount) {}
    public void dispenseTicket() {}
    public void cancel() {}
    public void reset() {}
}

class WaitingForMoneyState implements State {
    private TicketMachine machine;

    public WaitingForMoneyState(TicketMachine m) {
        this.machine = m;
    }

    public void selectTicket() {}

    public void insertMoney(int amount) {
        machine.addMoney(amount);
        if (machine.getMoneyInserted() >= machine.getTicketPrice()) {
            machine.setState(machine.getMoneyReceivedState());
        }
    }

    public void dispenseTicket() {}
    public void cancel() {
        machine.clearMoney();
        machine.setState(machine.getCanceledState());
    }
    public void reset() {}
}

class MoneyReceivedState implements State {
    private TicketMachine machine;

    public MoneyReceivedState(TicketMachine m) {
        this.machine = m;
    }

    public void selectTicket() {}

    public void insertMoney(int amount) {}

    public void dispenseTicket() {
        machine.clearMoney();
        machine.setState(machine.getTicketDispensedState());
    }

    public void cancel() {
        machine.clearMoney();
        machine.setState(machine.getCanceledState());
    }

    public void reset() {}
}

class TicketDispensedState implements State {
    private TicketMachine machine;

    public TicketDispensedState(TicketMachine m) {
        this.machine = m;
    }

    public void selectTicket() {}
    public void insertMoney(int amount) {}
    public void dispenseTicket() {}
    public void cancel() {}

    public void reset() {
        machine.setState(machine.getIdleState());
    }
}

class TransactionCanceledState implements State {
    private TicketMachine machine;

    public TransactionCanceledState(TicketMachine m) {
        this.machine = m;
    }

    public void selectTicket() {}
    public void insertMoney(int amount) {}
    public void dispenseTicket() {}

    public void cancel() {}

    public void reset() {
        machine.setState(machine.getIdleState());
    }
}
