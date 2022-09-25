import unittest

# La fonctionnalité attendu est que tout les element du tableau soit strictement inférieur a 7
class BorneTestCase(unittest.TestCase):  # Creation classe fille de TestCase
    def setUp(self):
        self.t = tab()

    def test_tableau_borne_max(self):
        for i in range(len(self.t)):
            with self.subTest(i=i):
                self.assertTrue(self.t[i] < 7)

    def test_tableau_borne_min(self):
        for i in range(len(self.t)):
            with self.subTest(i=i):
                self.assertTrue(self.t[i] > 2)


def tab():
    return [1, 3, 5, 8, 7]


if __name__ == "__main__":
    unittest.main()
