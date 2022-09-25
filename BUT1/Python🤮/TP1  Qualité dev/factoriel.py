def factoriel(n):
    Fact = 1
    for i in range(n):
        Fact *= i + 1
    return Fact
