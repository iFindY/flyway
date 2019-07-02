public class Person {
    def eatDonuts() { println("yummy") }
}

public class RoboCop {
    @Delegate
    final Person person

    public RoboCop(Person person) {
        this.person = person
    }

    public RoboCop() {
        this.person = new Person()
    }

    def crushCars() {
        println("smash")
    }
}


def person = new RoboCop()
person.eatDonuts()
person.crushCars()

