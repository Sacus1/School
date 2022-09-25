import factoriel
import unittest


class TestFacto(unittest.TestCase):

    def testTypeException(self):
        self.assertRaises(TypeError, factoriel.factoriel, "1")

    def testTypeReturn(self):
        self.assertEqual(type(1), type(factoriel.factoriel(1)))

    def testResultat(self):
        self.assertEqual(6, factoriel.factoriel(3))


if __name__ == '__main__':
    unittest.main()