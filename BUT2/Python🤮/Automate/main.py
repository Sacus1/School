def ConvertToBase(n, b):
    a = 0
    i = 0
    while n:
        n, r = divmod(n, b)
        a += 10 ** i * r
        i += 1
    return a


i = 1
n = 4
b = 3


def formula(k):
    x = str(ConvertToBase(i + n * k, b))
    return x


k = 0
A = []
while len(formula(k)) <= 2:
    x = formula(k)
    A.append(x)
    if len(x) == 1:
        A.append("0" + x)
    k += 1

print(A)
