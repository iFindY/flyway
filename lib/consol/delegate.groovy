class SMS {
    String f, t, b

    def static send(block) {
        SMS m = new SMS()
        block.delegate = m
        block()
        m.send()
    }


    def from(String fromNumber) {
        f = fromNumber
    }

    def to(String toNumber) {
        t = toNumber
    }

    def body(String body) {
        b = body
    }

    def send() {
        println b
    }
}


SMS.send {
    from '555-432-1234'
    to '555-678-4321'
    body 'Hey there!'
}

