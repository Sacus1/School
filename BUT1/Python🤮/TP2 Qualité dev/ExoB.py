import unittest

# La fonctionnalité attendu est que tout les element du tableau soit strictement inférieur a 7
class BorneTestCase(unittest.TestCase):  # Creation classe fille de TestCase
    def test_tableau_borne_max(self):
        t = tab()
        for i in range(len(t)):
            with self.subTest(i=i):
                self.assertTrue(t[i] < 7)


def tab():
    return [1, 3, 5, 8, 7]


if __name__ == "__main__":
    unittest.main()
