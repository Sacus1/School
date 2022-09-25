def div(a, b):
    try:
        return a / b
    except ZeroDivisionError:
        print("Erreur de division par zero")
    except TypeError:
        print("Mauvais type")